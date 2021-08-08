using AuthClient.tollgate.account.service;
using System.Windows.Forms;
using static AuthClient.tollgate.define.Define;

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
            // 토글 버튼 Off -> On 상태 시 이벤트
            if (card_finger.On)
            {
                CheckDialog checker = new CheckDialog(CheckFlag.MOBILE | CheckFlag.FINGER);
                if (checker.ShowDialog(this) != DialogResult.OK)
                    card_finger.On = false;
            }

            // 토글 버튼 On -> Off 상태 시 이벤트
            else
            {

            }
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

            // 토글 버튼 On -> Off 상태 시 이벤트
            else
            {

            }
        }

        private void card_usb_ToggleClick(object sender, System.EventArgs e)
        {
            // 토글 버튼 Off -> On 상태 시 이벤트
            if (card_usb.On)
            {
                CheckDialog checker = new CheckDialog(CheckFlag.USB);
                
                // 체크 실패
                if (checker.ShowDialog(this) != DialogResult.OK)
                {
                    card_usb.On = false;
                }
                // 체크 성공
                else
                {
                    // USB Auth Factor Flag 적용 - 추후 Credential Provider에서 읽음
                    AccountService service = new AccountService();
                    service.UpdateAuthFactorFlag(Config.GetCurrentUser(), AuthFactorFlag.AUTH_FACTOR_USB, true);
                }
            }

            // 토글 버튼 On -> Off 상태 시 이벤트
            else
            {
                // USB Auth Factor Flag 해제
                AccountService service = new AccountService();
                service.UpdateAuthFactorFlag(Config.GetCurrentUser(), AuthFactorFlag.AUTH_FACTOR_USB, false);
            }            
        }

        private void card_otp_ToggleClick(object sender, System.EventArgs e)
        {
            // 토글 버튼 Off -> On 상태 시 이벤트
            if (card_otp.On)
            {
                CheckDialog checker = new CheckDialog(CheckFlag.MOBILE);
                if (checker.ShowDialog(this) != DialogResult.OK)
                    card_otp.On = false;
            }

            // 토글 버튼 On -> Off 상태 시 이벤트
            else
            {

            }

            
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

            // 토글 버튼 On -> Off 상태 시 이벤트
            else
            {

            }
        }

        private void card_pattern_ToggleClick(object sender, System.EventArgs e)
        {
            // 토글 버튼 Off -> On 상태 시 이벤트
            if(card_pattern.On)
            {
                CheckDialog checker = new CheckDialog(CheckFlag.MOBILE | CheckFlag.PATTERN);
                if (checker.ShowDialog(this) != DialogResult.OK)
                {
                    card_pattern.On = false;
                }
            }

            // 토글 버튼 On -> Off 상태 시 이벤트
            else
            {

            }
            
        }
    }
}