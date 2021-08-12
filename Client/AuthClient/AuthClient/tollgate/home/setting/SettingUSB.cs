using AuthClient.tollgate.home.main.dialog;
using AuthClient.tollgate.usb.dto;
using AuthClient.tollgate.usb.service;
using System;
using System.Collections.Generic;
using System.IO;
using System.Management;
using System.Windows.Forms;

namespace AuthClient.tollgate.home.setting
{
    public partial class SettingUSB : UserControl
    {
        private List<USBEntry> usbList = new List<USBEntry>();
        private int lastRecognizedUSBCount = 0;

        public SettingUSB()
        {
            InitializeComponent();

            panel_list.Width = panel_list.Parent.Width + SystemInformation.VerticalScrollBarWidth + 95;
            panel_list.Height = panel_list.Parent.Height + 30;
        }

        private void SettingUSB_VisibleChanged(object sender, EventArgs e)
        {
            // Visible 속성이 False일 경우
            if (!Visible)
            {
                usbRecognitionTimer.Enabled = false;
                return;
            }

            lastRecognizedUSBCount = 0;
            usbList.Clear();
            panel_list.Controls.Clear();


            // --------------- 1. 서버로부터 현재 등록된 USB alias를 받아옴 ---------------
            USBService us = new USBService();
            List<USBInfo> registeredUSBList = us.GetRegisteredUSBList(Config.GetCurrentUser());

            foreach (USBInfo ui in registeredUSBList)
            {
                USBEntry temp = new USBEntry();
                temp.IsRegistered = true;
                temp.UsbInfo = ui;
                temp.TextChange += UsbEntry_TextChange;
                temp.ButtonClick += UsbEntry_ButtonClick;
                usbList.Add(temp);
            }

            // --------------- 2. 패널에 USB 목록 표시 ---------------
            foreach (USBEntry ue in usbList)
            {
                panel_list.Controls.Add(ue);
            }

            // --------------- 3. USB 실시간 감지 타이머 Enable ---------------
            usbRecognitionTimer.Enabled = true;
        }

        // +, - 버튼에 대한 이벤트
        private void UsbEntry_ButtonClick(USBEntry sender)
        {
            USBService us = new USBService();

            // 등록된 USB일 경우 (-일 경우) : USB 등록 해제
            if (sender.IsRegistered)
            {
                if ((MainControl.FACTOR & define.Define.Factor.USB) != 0)
                {
                    // 등록된 마지막 USB일 경우 등록 해제 불가
                    if(usbList.Count == 1)
                    {
                        MessageBox.Show("현재 USB 인증 요소를 사용하고 있으므로 등록 해제할 수 없습니다");
                        return;
                    }
                }

                if (us.UnregisterUSBInfo(sender.UsbInfo))
                {
                    MessageBox.Show(sender.UsbInfo.alias + " USB의 등록 해제가 완료되었습니다");
                    usbList.Remove(sender);
                    panel_list.Controls.Remove(sender);
                }
                else
                {
                    MessageBox.Show("등록 해제에 실패하였습니다");
                }
            }

            // 등록되지 않은 USB일 경우 (+일 경우) : USB 등록
            else
            {
                if (us.RegisterUSBInfo(sender.UsbInfo))
                {
                    MessageBox.Show(sender.UsbInfo.alias + " USB가 " + Config.GetCurrentUser() + " 사용자에 등록되었습니다");

                    USBEntry temp = new USBEntry();
                    temp.IsRegistered = true;
                    temp.UsbInfo = sender.UsbInfo;
                    temp.TextChange += UsbEntry_TextChange;
                    temp.ButtonClick += UsbEntry_ButtonClick;

                    usbList.Add(temp);
                    panel_list.Controls.Add(temp);
                }
                else
                {
                    MessageBox.Show("이미 등록된 USB입니다");
                }
            }
        }

        private void UsbEntry_TextChange(USBEntry sender, string beforeText, string afterText)
        {
            //MessageBox.Show(beforeText + " -> " + afterText);
            USBInfo ui = sender.UsbInfo;
            ui.alias = afterText;

            USBService us = new USBService();
            if (us.UpdateUSBAlias(ui))
            {
                MessageBox.Show("별명이 성공적으로 변경되었습니다");

                // USB Entry 항목의 별명 변경
                sender.UsbInfo = ui;
            }
            else
            {
                MessageBox.Show("변경에 실패하였습니다");
            }
        }

        private void usbRecognitionTimer_Tick(object sender, EventArgs e)
        {
            DriveInfo[] driveArray = DriveInfo.GetDrives();

            // 현재 인식된 USB의 개수가 변함이 없을 경우 아무 작업도 안 함
            if (driveArray.Length == lastRecognizedUSBCount)
            {
                return;
            }


            // ---------- 패널 리스트 컨트롤 초기화, 마지막으로 인식된 USB 개수 카운트 갱신 ----------
            lastRecognizedUSBCount = driveArray.Length;
            panel_list.Controls.Clear();


            // ---------- 패널 리스트 컨트롤에서 사용자가 등록한 USB 항목을 우선 추가 ----------
            foreach (USBEntry ui in usbList)
            {
                panel_list.Controls.Add(ui);
            }


            // ---------- 이후, 현재 인식된 USB 엔트리를 추가 ----------
            foreach (DriveInfo devItem in driveArray)
            {
                // 드라이브 타입 및 Ready 여부 검사
                if (devItem.IsReady == true && devItem.DriveType == DriveType.Removable)
                {
                    // USB의 정보를 받아온 후 가공
                    string usbID = GetDriveUniqueInformation(devItem.RootDirectory.Name);
                    if (usbID.Length == 0)
                    {
                        continue;
                    }
                    usbID = usbID.Replace("\\", "");
                    usbID = usbID.Replace("&", "");

                    // 인식한 USB에 대하여 USB Entry 항목 생성
                    USBEntry temp = new USBEntry();
                    temp.IsRegistered = false;
                    temp.UsbInfo = new USBInfo(Config.GetCurrentUser(), Util.EncryptSHA512(usbID), devItem.VolumeLabel);
                    temp.TextChange += UsbEntry_TextChange;
                    temp.ButtonClick += UsbEntry_ButtonClick;

                    panel_list.Controls.Add(temp);
                }
            }
        }

        private string GetDriveUniqueInformation(string driveLetter)
        {
            string szSerialNumberDevice = null;

            ManagementObject oLogicalDisk = new ManagementObject("Win32_LogicalDisk.DeviceID='" + driveLetter.TrimEnd('\\') + "'");
            foreach (ManagementObject oDiskPartition in oLogicalDisk.GetRelated("Win32_DiskPartition"))
            {
                foreach (ManagementObject oDiskDrive in oDiskPartition.GetRelated("Win32_DiskDrive"))
                {
                    string szPNPDeviceID = oDiskDrive["PNPDeviceID"].ToString();
                    if (!szPNPDeviceID.StartsWith("USBSTOR"))
                        return "";

                    string[] aszToken = szPNPDeviceID.Split(new char[] { '\\', '&' });
                    szSerialNumberDevice = aszToken[aszToken.Length - 2];
                }
            }

            if (null != szSerialNumberDevice)
            {
                ManagementObjectSearcher oSearcher = new ManagementObjectSearcher(@"root\CIMV2", "Select * from Win32_USBHub");
                foreach (ManagementObject oResult in oSearcher.Get())
                {
                    object oValue = oResult["DeviceID"];
                    if (oValue == null)
                        continue;

                    string szDeviceID = oValue.ToString();

                    // ---------- USB의 시리얼 번호를 이용하여 USB의 정보를 검색 ----------
                    string[] aszToken = szDeviceID.Split(new char[] { '\\' });
                    if (szSerialNumberDevice != aszToken[aszToken.Length - 1])
                        continue;

                    return szDeviceID;
                }
            }

            return "";
        }
    }
}
