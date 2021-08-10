using AuthClient.tollgate.account.service;
using AuthClient.tollgate.home.main.dialog;
using System;
using System.Windows.Forms;
using static AuthClient.tollgate.define.Define;

namespace AuthClient.tollgate.home.dialog
{
    public partial class HomeControl : UserControl
    {
        MainControl mainControl;
        SettingControl settingControl;

        public string User
        {
            set
            {
                card_user.Text = value;
                settingControl.User = value;
            }
        }

        public HomeControl()
        {
            InitializeComponent();

            btn_side_main.setOn();
            btn_side_setting.setOff();

            mainControl = new MainControl();
            settingControl = new SettingControl();
            panel_content.Controls.Add(mainControl);
            panel_content.Controls.Add(settingControl);
        }

        private void btn_side_main_Click(object sender, EventArgs e)
        {
            btn_side_main.setOn();
            btn_side_setting.setOff();
            BackgroundImage = Properties.Resources.mainWallpaper;
            settingControl.Hide();
            mainControl.Show();
        }

        private void btn_side_setting_Click(object sender, EventArgs e)
        {
            btn_side_main.setOff();
            btn_side_setting.setOn();
            BackgroundImage = Properties.Resources.settingWallpaper;
            mainControl.Hide();
            settingControl.Show();
        }

        private void card_user_LogoutClick(object sender, EventArgs e)
        {
            DialogResult dr = MessageBox.Show(
                "로그아웃 하시겠습니까?\r\n로그아웃 하실 경우 해당 PC와 인증 서버와의 연동이 해제되고\r\n시스템에 적용된 MFA가 해제됩니다.",
                "로그아웃",
                MessageBoxButtons.YesNo,
                MessageBoxIcon.Question);

            if (dr == DialogResult.Yes)
            {
                // --------------- PC와 인증 서버와의 연동 해제 ---------------
                AccountService accountService = new AccountService();
                accountService.UnmapSIDWithUser(Config.GetCurrentUser());

                // --------------- 시스템에 적용된 Credential Provider 해제 ---------------
                // TODO

                Application.Restart();
            }
        }

        public void InitFactor(Factor factor)
        {
            mainControl.InitFactor(factor);
        }
    }
}