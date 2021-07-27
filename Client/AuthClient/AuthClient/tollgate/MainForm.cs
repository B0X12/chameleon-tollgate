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
    public partial class MainForm : Form
    {
        private const int FLOW_SIZE = 720;
        private const int FLOW_INTERVAL = 10;

        private InitControl initcontrol;
        private SplashControl splashControl;

        public MainForm()
        {
            InitializeComponent();

            panel_flow_main.Width = panel_flow_main.Parent.Width + SystemInformation.VerticalScrollBarWidth;
            //panel_flow_main.Height = panel_flow_main.Parent.Height + SystemInformation.HorizontalScrollBarHeight;

            initcontrol = new InitControl();
            initcontrol.loginButtonClick += flowNext;

            splashControl = new SplashControl();
        }

        private void MainForm_Load(object sender, EventArgs e)
        {
            panel_flow_main.Controls.Add(initcontrol);
            panel_flow_main.Controls.Add(splashControl);
            //panel_flow_main.VerticalScroll.Value = FLOW_SIZE;
        }

        private void flowNext()
        {
            /*
            for (int i = FLOW_SIZE - FLOW_INTERVAL; i > 0; i -= FLOW_INTERVAL)
            {
                try
                {
                    panel_flow_main.VerticalScroll.Value = i;
                }
                catch (ArgumentOutOfRangeException ex)
                {
                    panel_flow_main.VerticalScroll.Value = 0;
                }
                panel_flow_main.Update();
            }*/

            panel_flow_main.Controls.RemoveAt(0);
        }
    }
}
