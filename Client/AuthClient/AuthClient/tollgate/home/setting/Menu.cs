using System;
using System.Windows.Forms;

namespace AuthClient.tollgate.home.setting
{
    public partial class Menu : UserControl
    {
        public event EventHandler AccountClick
        {
            add { img_account.Click += value; }
            remove { img_account.Click -= value; }
        }

        public event EventHandler PcClick
        {
            add { img_pc.Click += value; }
            remove { img_pc.Click -= value; }
        }

        public event EventHandler UsbClick
        {
            add { img_usb.Click += value; }
            remove { img_usb.Click -= value; }
        }

        public Menu()
        {
            InitializeComponent();

            img_account.Left = img_pc.Left = img_usb.Left = 13;
            img_account.Top = 37;
            img_pc.Top = img_account.Bottom + 17;
            img_usb.Top = img_pc.Bottom + 17;
        }

        private void img_account_Click(object sender, EventArgs e)
        {
            img_account.Image = Properties.Resources.settingAccountOn;
            img_pc.Image = Properties.Resources.settingPcOff;
            img_usb.Image = Properties.Resources.settingUsbOff;
        }

        private void img_pc_Click(object sender, EventArgs e)
        {
            img_account.Image = Properties.Resources.settingAccountOff;
            img_pc.Image = Properties.Resources.settingPcOn;
            img_usb.Image = Properties.Resources.settingUsbOff;
        }

        private void img_usb_Click(object sender, EventArgs e)
        {
            img_account.Image = Properties.Resources.settingAccountOff;
            img_pc.Image = Properties.Resources.settingPcOff;
            img_usb.Image = Properties.Resources.settingUsbOn;
        }
    }
}
