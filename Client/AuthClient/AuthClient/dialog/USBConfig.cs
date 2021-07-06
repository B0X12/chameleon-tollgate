using AuthClient.dto;
using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AuthClient
{
    public partial class USBConfigDialog : Form
    {
        public USBConfigDialog()
        {
            InitializeComponent();
            usbRecognitionTimer.Start();
            registerOKButton.Hide();
            registerCancelButton.Hide();
            progressBar1.Show();
        }

        private void usbRecognitionTimer_Tick(object sender, EventArgs e)
        {
            // --------------- 실시간 탐지 ---------------

            DriveInfo[] driveArray = DriveInfo.GetDrives();

            foreach (DriveInfo item in driveArray)
            {
                // 드라이브 타입 및 Ready 여부 검사
                if (item.IsReady == true && item.DriveType == DriveType.Removable)
                {
                    usbRecognitionTimer.Stop();

                    statusMessageLabel.Text = "다음 USB 장치가 검색되었습니다\r\n";
                    statusMessageLabel.Text += (item.VolumeLabel + "\r\n");
                    statusMessageLabel.Text += "해당 장치를 인증 서버에 등록하시겠습니까?";

                    progressBar1.Hide();
                    registerOKButton.Show();
                    registerCancelButton.Show();
                }
            }
        }

        private void registerOKButton_Click(object sender, EventArgs e)
        {
            string usbInfo = ReadUSBInfoByRegistry();

            // VID, PID 등의 정보 불러오기 실패
            if(usbInfo.Length == 0)
            {
                MessageBox.Show("USB 정보를 불러올 수 없습니다.");
                this.DialogResult = DialogResult.Cancel;
            }
            // VID, PID 등의 정보 불러오기 성공
            else
            {
                // USB 정보 문자열 가공
                usbInfo = usbInfo.Replace("\\", "");
                usbInfo = usbInfo.Replace("&", "");

                // 서버로 해당 정보 전송
                AuthUSB usb_info = new AuthUSB(Config.GetCurrentUser(), usbInfo);
                string result = HttpCommunication.SendRequestPOST(RegisterURL.USB, usb_info);

                if (result.Equals("true"))
                {
                    MessageBox.Show("USB가 등록되었습니다: " + usbInfo);
                    this.DialogResult = DialogResult.OK;
                }
                else
                {
                    MessageBox.Show("해당 USB 정보가 이미 존재합니다");
                    this.DialogResult = DialogResult.Cancel;
                }
            }
        }

        private void registerCancelButton_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
        }

        private string ReadUSBInfoByRegistry()
        {
            // 레지스트리 값을 읽어 연결된 USB의 VID, PID 등의 정보를 추출
            string regUSBInfo = "";

            RegistryKey r_key = Registry.LocalMachine;
            RegistryKey r_system = r_key.OpenSubKey("SYSTEM");
            RegistryKey r_currentControlSet = r_system.OpenSubKey("CurrentControlSet");
            RegistryKey r_services = r_currentControlSet.OpenSubKey("Services");
            RegistryKey r_usbstor = r_services.OpenSubKey("USBSTOR");
            RegistryKey r_enum = r_usbstor.OpenSubKey("Enum");

            if (r_currentControlSet != null)
            {
                regUSBInfo = (string)r_enum.GetValue("0");
                r_enum.Close();
            }

            return regUSBInfo;
        }
    }
}
