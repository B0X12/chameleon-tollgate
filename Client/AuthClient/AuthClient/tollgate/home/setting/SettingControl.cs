using AuthClient.tollgate.home.setting;
using System;
using System.Windows.Forms;

namespace AuthClient.tollgate.home
{
    public partial class SettingControl : UserControl
    {
        public string User
        {
            set { settingAccount.ID = value; }
        }

        SettingAccount settingAccount;
        SettingPC settingPC;
        SettingUSB settingUSB;

        public SettingControl()
        {
            InitializeComponent();

            settingAccount = new SettingAccount();
            settingPC = new SettingPC();
            settingUSB = new SettingUSB();
            settingPC.Visible = settingUSB.Visible = false;

            panel_content.Controls.Add(settingAccount);
            panel_content.Controls.Add(settingPC);
            panel_content.Controls.Add(settingUSB);
        }

        private void view_menu_AccountClick(object sender, EventArgs e)
        {
            settingAccount.Show();
            settingPC.Hide();
            settingUSB.Hide();
        }

        private void view_menu_PcClick(object sender, EventArgs e)
        {
            settingAccount.Hide();
            settingPC.Show();
            settingUSB.Hide();
        }

        private void view_menu_UsbClick(object sender, EventArgs e)
        {
            settingAccount.Hide();
            settingPC.Hide();
            settingUSB.Show();
        }
    }
}
