using AuthClient.tollgate.account.dialog;
using AuthClient.tollgate.home.dialog;
using AuthClient.tollgate.rest;
using Newtonsoft.Json;
using System;
using System.Drawing;
using System.Drawing.Text;
using System.Windows.Forms;
using static AuthClient.tollgate.define.Define;

namespace AuthClient.tollgate
{
    public partial class MainForm : Form
    {
        private static PrivateFontCollection fonts;

        public static FontFamily FONT_MEDIUM;
        public static FontFamily FONT_BOLD;
        public static FontFamily FONT_BLACK;

        private InitControl initcontrol;
        private SplashControl splashControl;
        private HomeControl homeControl;

        public MainForm()
        {
            InitializeComponent();

            //OtpTest
            OtpTest ot = new OtpTest();
            ot.Show();

            fonts = new PrivateFontCollection();
            fonts.AddFontFile(Property.FONT_MEDIUM);
            fonts.AddFontFile(Property.FONT_BOLD);
            fonts.AddFontFile(Property.FONT_BLACK);
            FONT_BLACK = fonts.Families[1];
            FONT_BOLD = fonts.Families[0];
            FONT_MEDIUM = fonts.Families[2];

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
            homeControl.User = Config.GetCurrentUser();
            ChangePage(2);
        }

        private void Initcontrol_Login()
        {
            splashControl.User = Config.GetCurrentUser();

            QueryString qs = new QueryString("timestamp", Util.GetCurrentTimestamp());
            RestResult result = new HttpCommunication(Method.GET, URLPath.FACTOR_FLAG + Config.GetCurrentUser(), qs).SendRequest();
            ResponseData<int> data = JsonConvert.DeserializeObject<ResponseData<int>>(result.jsonResult);
            homeControl.InitFactor((Factor)data.getResult());

            ChangePage(1);
        }

        private void ChangePage(int page)
        {
            for (int i = 0; i < panel_main.Controls.Count; i++)
            {
                panel_main.Controls[i].Visible = (i == page);
            }
        }
    }
}
