using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AuthClient.tollgate.account.dialog
{
    public partial class InitControl : UserControl
    {
        public InitControl()
        {
            InitializeComponent();
        }

        private void InitControl_Load(object sender, EventArgs e)
        {
            panel_flow.HorizontalScroll.Visible = false;

            Login loginControl = new Login();
            loginControl.BackColor = Color.White;
            panel_flow.Controls.Add(loginControl);

            Login loginControl2 = new Login();
            loginControl2.BackColor = Color.Red;
            panel_flow.Controls.Add(loginControl2);
        }

        private void panel_init_Click(object sender, EventArgs e)
        {
            for (int i = 0; i < 300; i++)
            {
                panel_flow.AutoScrollPosition = new Point(100, 100);
            }
        }
    }
}