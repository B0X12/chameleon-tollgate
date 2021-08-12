using AuthClient.tollgate.account.service;
using AuthClient.tollgate.home.history;
using AuthClient.tollgate.home.main.dialog;
using AuthClient.tollgate.util;
using System;
using System.Windows.Forms;
using static AuthClient.tollgate.define.Define;

namespace AuthClient.tollgate.home.dialog
{
    public partial class HomeControl : UserControl
    {
        MainControl mainControl;
        SettingControl settingControl;
        HistoryControl historyControl;

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
            btn_side_history.setOff();

            mainControl = new MainControl();
            settingControl = new SettingControl();
            historyControl = new HistoryControl();
            panel_content.Controls.Add(mainControl);
            panel_content.Controls.Add(settingControl);
            panel_content.Controls.Add(historyControl);
        }

        private void btn_side_main_Click(object sender, EventArgs e)
        {
            btn_side_main.setOn();
            btn_side_setting.setOff();
            btn_side_history.setOff();
            BackgroundImage = Properties.Resources.mainWallpaper;
            settingControl.Hide();
            historyControl.Hide();
            mainControl.Show();
        }

        private void btn_side_setting_Click(object sender, EventArgs e)
        {
            btn_side_main.setOff();
            btn_side_setting.setOn();
            btn_side_history.setOff();
            BackgroundImage = Properties.Resources.settingWallpaper;
            mainControl.Hide();
            historyControl.Hide();
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
                if (CredentialUtil.LogOutCredentialFile())
                {
                    if (!CredentialUtil.LogOutCredentialReg())
                    {
                        MessageBox.Show("Credential Provider 해제에 실패하였습니다");
                        return;
                    }
                }

                // --------------- PC와 인증 서버와의 연동 해제 ---------------
                AccountService accountService = new AccountService();
                accountService.UnmapSIDWithUser(Config.GetCurrentUser());

                Application.Restart();
            }
        }

        public void InitFactor(Factor factor)
        {
            mainControl.InitFactor(factor);
        }

        private void btn_side_history_Click(object sender, EventArgs e)
        {
            btn_side_main.setOff();
            btn_side_setting.setOff();
            btn_side_history.setOn();
            BackgroundImage = Properties.Resources.mainWallpaper;
            mainControl.Hide();
            settingControl.Hide();
            historyControl.Show();
        }
    }
}