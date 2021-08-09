using AuthClient.tollgate.define;
using System.Windows.Forms;

namespace AuthClient.tollgate.home.main.dialog
{
    public partial class UsingList : UserControl
    {
        public UsingList()
        {
            InitializeComponent();

            img_finger.Left = 30;
            img_finger.Top = (ClientSize.Height - 18 - img_finger.Height) / 2 + 9;

            img_faceid.Left = img_finger.Left + 54;
            img_faceid.Top = img_finger.Top;

            img_usb.Left = img_faceid.Left + 54;
            img_usb.Top = img_faceid.Top;

            img_otp.Left = img_usb.Left + 54;
            img_otp.Top = img_usb.Top;

            img_qr.Left = img_otp.Left + 54;
            img_qr.Top = img_otp.Top; ;

            img_pattern.Left = img_qr.Left + 54;
            img_pattern.Top = img_qr.Top;
        }

        /// <summary>
        /// 등록된 인증수단 목록의 사용 여부 표시를 설정합니다.
        /// </summary>
        /// <param name="factor">설정할 인증 요소입니다.</param>
        /// <param name="isOn">표시될 등록 여부입니다.</param>
        public void setUsing(Define.Factor factor, bool isOn)
        {
            switch (factor)
            {
                case Define.Factor.FINGER:
                    if (isOn)
                        img_finger.Image = Properties.Resources.mainFingerOn;
                    else
                        img_finger.Image = Properties.Resources.mainFingerOff;
                    break;
                case Define.Factor.FACEID:
                    if (isOn)
                        img_faceid.Image = Properties.Resources.mainFaceidOn;
                    else
                        img_faceid.Image = Properties.Resources.mainFaceidOff;
                    break;
                case Define.Factor.USB:
                    if (isOn)
                        img_usb.Image = Properties.Resources.mainUsbOn;
                    else
                        img_usb.Image = Properties.Resources.mainUsbOff;
                    break;
                case Define.Factor.OTP:
                    if (isOn)
                        img_otp.Image = Properties.Resources.mainOtpOn;
                    else
                        img_otp.Image = Properties.Resources.mainOtpOff;
                    break;
                case Define.Factor.QR:
                    if (isOn)
                        img_qr.Image = Properties.Resources.mainQrOn;
                    else
                        img_qr.Image = Properties.Resources.mainQrOff;
                    break;
                case Define.Factor.PATTERN:
                    if (isOn)
                        img_pattern.Image = Properties.Resources.mainPatternOn;
                    else
                        img_pattern.Image = Properties.Resources.mainPatternOff;
                    break;
                default:
                    return;
            }
        }
    }
}
