using AuthClient.tollgate.home.main.dialog;
using System;
using System.Windows.Forms;

namespace AuthClient.tollgate.home.dialog
{
    public partial class HomeControl : UserControl
    {
        MainControl mainControl;
        SettingControl settingControl;

        public string User
        {
            set { card_user.Text = value; }
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
    }
}
