using AuthClient.tollgate.account.dialog;
using AuthClient.tollgate.home.dialog;
using System;
using System.Windows.Forms;

namespace AuthClient.tollgate
{
    public partial class MainForm : Form
    {
        private const int FLOW_SIZE = 720;
        private const int FLOW_INTERVAL = 10;

        private InitControl initcontrol;
        private SplashControl splashControl;
        private HomeControl homeControl;

        public MainForm()
        {
            InitializeComponent();

            MaximizeBox = false;

            initcontrol = new InitControl();
            initcontrol.Login += Initcontrol_Login;
            panel_main.Controls.Add(initcontrol);

            splashControl = new SplashControl();
            splashControl.SplashButtonClick += SplashControl_SplashButtonClick; ;
            panel_main.Controls.Add(splashControl);

            homeControl = new HomeControl();
            panel_main.Controls.Add(homeControl);

            ChangePage(0);
        }

        private void SplashControl_SplashButtonClick(object sender, EventArgs e)
        {
            ChangePage(2);
        }

        private void Initcontrol_Login()
        {
            splashControl.User = Config.GetCurrentUser();
            ChangePage(1);
        }

        private void ChangePage(int page)
        {
            for(int i = 0; i < panel_main.Controls.Count; i++)
            {
                panel_main.Controls[i].Visible = (i == page);
            }
        }
    }
}
