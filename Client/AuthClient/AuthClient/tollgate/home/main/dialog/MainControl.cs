using AuthClient.tollgate.account.service;
using AuthClient.tollgate.otp.service;
using System.Windows.Forms;
using static AuthClient.tollgate.define.Define;

namespace AuthClient.tollgate.home.main.dialog
{
    public partial class MainControl : UserControl
    {
        public static Factor FACTOR;

        public MainControl()
        {
            InitializeComponent();

            panel_select_flow.Width = panel_select_flow.Parent.Width + SystemInformation.VerticalScrollBarWidth;
        }

        public void InitFactor(Factor factor)
        {
            card_finger.On = (factor & Factor.FINGER) != 0;
            usingList.setUsing(Factor.FINGER, card_finger.On);
            card_face.On = (factor & Factor.FACEID) != 0;
            usingList.setUsing(Factor.FACEID, card_face.On);
            card_usb.On = (factor & Factor.USB) != 0;
            usingList.setUsing(Factor.USB, card_usb.On);
            card_otp.On = (factor & Factor.OTP) != 0;
            usingList.setUsing(Factor.OTP, card_otp.On);
            card_qr.On = (factor & Factor.QR) != 0;
            usingList.setUsing(Factor.QR, card_qr.On);
            card_pattern.On = (factor & Factor.PATTERN) != 0;
            usingList.setUsing(Factor.PATTERN, card_pattern.On);
            FACTOR = factor;
        }

        private void card_finger_ToggleClick(object sender, System.EventArgs e)
        {
            // 토글 버튼 Off -> On 상태 시 이벤트
            if (card_finger.On)
            {
                CheckDialog checker = new CheckDialog(CheckFlag.MOBILE | CheckFlag.FINGER);
                if (checker.ShowDialog(this) != DialogResult.OK)
                    card_finger.On = false;
            }

            AccountService service = new AccountService();
            service.UpdateAuthFactorFlag(Config.GetCurrentUser(), AuthFactorFlag.AUTH_FACTOR_FINGERPRINT, card_finger.On);
            usingList.setUsing(Factor.FINGER, card_finger.On);

        }

        private void card_face_ToggleClick(object sender, System.EventArgs e)
        {
            // 토글 버튼 Off -> On 상태 시 이벤트
            if (card_face.On)
            {
                CheckDialog checker = new CheckDialog(CheckFlag.MOBILE | CheckFlag.FACE);
                if (checker.ShowDialog(this) != DialogResult.OK)
                    card_face.On = false;
            }

            AccountService service = new AccountService();
            service.UpdateAuthFactorFlag(Config.GetCurrentUser(), AuthFactorFlag.AUTH_FACTOR_FACE, card_face.On);
            usingList.setUsing(Factor.FACEID, card_face.On);
        }

        private void card_usb_ToggleClick(object sender, System.EventArgs e)
        {
            // 토글 버튼 Off -> On 상태 시 이벤트
            if (card_usb.On)
            {
                CheckDialog checker = new CheckDialog(CheckFlag.USB);
                if (checker.ShowDialog(this) != DialogResult.OK)
                {
                    card_usb.On = false;
                }
            }

            AccountService service = new AccountService();
            service.UpdateAuthFactorFlag(Config.GetCurrentUser(), AuthFactorFlag.AUTH_FACTOR_USB, card_usb.On);
            usingList.setUsing(Factor.USB, card_usb.On);
        }

        private void card_otp_ToggleClick(object sender, System.EventArgs e)
        {
            // 토글 버튼 Off -> On 상태 시 이벤트
            if (card_otp.On)
            {
                string registerResult = OtpService.PostOtpRegister(Config.GetCurrentUser());

                //switch (registerResult)
                //{
                //    case ReturnMessage.FAIL_REGISTER_INFORMATION:
                //        MessageBox.Show("실패 : 핸드폰 앱의 최초 로그인이 필요합니다.");
                //        return;
                //    case ReturnMessage.FAIL_REGISTER_UNKNOWN:
                //        MessageBox.Show("실패 : 이미 OTP가 등록되어 있습니다.");
                //        return;
                //}
                
                CheckDialog checker = new CheckDialog(CheckFlag.MOBILE);
                if (checker.ShowDialog(this) != DialogResult.OK)
                    card_otp.On = false;
            }

            AccountService service = new AccountService();
            service.UpdateAuthFactorFlag(Config.GetCurrentUser(), AuthFactorFlag.AUTH_FACTOR_OTP, card_otp.On);
            usingList.setUsing(Factor.OTP, card_otp.On);
        }

        private void card_qr_ToggleClick(object sender, System.EventArgs e)
        {
            // 토글 버튼 Off -> On 상태 시 이벤트
            if (card_qr.On)
            {
                CheckDialog checker = new CheckDialog(CheckFlag.MOBILE);
                if (checker.ShowDialog(this) != DialogResult.OK)
                    card_qr.On = false;
            }

            AccountService service = new AccountService();
            service.UpdateAuthFactorFlag(Config.GetCurrentUser(), AuthFactorFlag.AUTH_FACTOR_QR, card_qr.On);
            usingList.setUsing(Factor.QR, card_qr.On);
        }

        private void card_pattern_ToggleClick(object sender, System.EventArgs e)
        {
            // 토글 버튼 Off -> On 상태 시 이벤트
            if (card_pattern.On)
            {
                CheckDialog checker = new CheckDialog(CheckFlag.MOBILE | CheckFlag.PATTERN);
                if (checker.ShowDialog(this) != DialogResult.OK)
                {
                    card_pattern.On = false;
                }
            }

            AccountService service = new AccountService();
            service.UpdateAuthFactorFlag(Config.GetCurrentUser(), AuthFactorFlag.AUTH_FACTOR_PATTERN, card_pattern.On);
            usingList.setUsing(Factor.PATTERN, card_pattern.On);
        }
    }
}