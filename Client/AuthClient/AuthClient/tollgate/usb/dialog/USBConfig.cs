using AuthClient.tollgate.usb.dto;
using AuthClient.tollgate.usb.service;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AuthClient.tollgate.usb.dialog
{
    public partial class USBConfigDialog : Form
    {
        public USBConfigDialog()
        {
            InitializeComponent();
        }

        private void usbAddButton_Click(object sender, EventArgs e)
        {
            USBRegisterDialog usbRegisterDlg = new USBRegisterDialog();
            usbRegisterDlg.ShowDialog();
        }

        private void usbUnregisterButton_Click(object sender, EventArgs e)
        {
            if (usbInfoList.SelectedIndices.Count > 0) 
            { 
                ListViewItem lvi = usbInfoList.Items[usbInfoList.FocusedItem.Index];
                USBInfo ui = (USBInfo)lvi.Tag;

                USBService us = new USBService();
                if(us.UnregisterUSBInfo(ui))
                {
                    MessageBox.Show(ui.alias + " USB의 등록 해제가 완료되었습니다");
                } 
                else
                {
                    MessageBox.Show("등록 해제에 실패하였습니다");
                }
            } 
            else
            {
                MessageBox.Show("삭제할 USB 항목을 선택하십시오");
            }

        }

        private void USBConfigDialog_Activated(object sender, EventArgs e)
        {
            usbInfoList.Items.Clear();

            USBService us = new USBService();
            List<USBInfo> usbList = us.GetRegisteredUSBList(Config.GetCurrentUser());
            
            foreach (USBInfo ui in usbList)
            {
                ListViewItem lvi = new ListViewItem();
                lvi.Text = ui.alias;
                lvi.Tag = ui;
                usbInfoList.Items.Add(lvi);
            }
        }
    }
}
