using System.Windows.Forms;

namespace AuthClient.tollgate.home.main.dialog
{
    public partial class MainControl : UserControl
    {
        public MainControl()
        {
            InitializeComponent();

            panel_select_flow.Width = panel_select_flow.Parent.Width + SystemInformation.VerticalScrollBarWidth;
        }

        private void card_finger_ToggleClick(object sender, System.EventArgs e)
        {
            CheckDialog checker = new CheckDialog(CheckFlag.MOBILE | CheckFlag.FINGER);
            if(checker.ShowDialog(this) != DialogResult.OK)
                card_finger.On = false;
        }

        private void card_face_ToggleClick(object sender, System.EventArgs e)
        {
            CheckDialog checker = new CheckDialog(CheckFlag.MOBILE | CheckFlag.FACE);
            if (checker.ShowDialog(this) != DialogResult.OK)
                card_face.On = false;
        }

        private void card_usb_ToggleClick(object sender, System.EventArgs e)
        {
            CheckDialog checker = new CheckDialog(CheckFlag.USB);
            if (checker.ShowDialog(this) != DialogResult.OK)
                card_usb.On = false;
        }

        private void card_otp_ToggleClick(object sender, System.EventArgs e)
        {
            CheckDialog checker = new CheckDialog(CheckFlag.MOBILE);
            if (checker.ShowDialog(this) != DialogResult.OK)
                card_otp.On = false;
        }

        private void card_qr_ToggleClick(object sender, System.EventArgs e)
        {
            CheckDialog checker = new CheckDialog(CheckFlag.MOBILE);
            if (checker.ShowDialog(this) != DialogResult.OK)
                card_qr.On = false;
        }

        private void card_pattern_ToggleClick(object sender, System.EventArgs e)
        {
            CheckDialog checker = new CheckDialog(CheckFlag.MOBILE | CheckFlag.PATTERN);
            if (checker.ShowDialog(this) != DialogResult.OK)
                card_pattern.On = false;
        }
    }
}