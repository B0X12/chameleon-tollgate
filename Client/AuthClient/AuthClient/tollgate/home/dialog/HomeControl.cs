using AuthClient.tollgate.home.main.dialog;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Imaging;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AuthClient.tollgate.home.dialog
{
    public partial class HomeControl : UserControl
    {
        MainControl mainControl;

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
            panel_content.Controls.Add(mainControl);
        }

        private void btn_side_main_Click(object sender, EventArgs e)
        {
            btn_side_main.setOn();
            btn_side_setting.setOff();
        }

        private void btn_side_setting_Click(object sender, EventArgs e)
        {
            btn_side_main.setOff();
            btn_side_setting.setOn();
        }
    }
}
