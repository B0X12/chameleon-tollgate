using AuthClient.tollgate.define;
using System;
using System.ComponentModel;
using System.Windows.Forms;

namespace AuthClient.tollgate.home.main.dialog
{
    public partial class SelectCard : UserControl
    {
        private Define.Factor factor;

        [Category("Tollgate")]
        public Define.Factor Factor
        {
            get
            {
                return factor;
            }
            set
            {
                switch (value)
                {
                    case Define.Factor.FINGER:
                        img_icon.Image = Properties.Resources.mainFingerIcon;
                        img_label.Image = Properties.Resources.mainFingerLabel;
                        break;
                    case Define.Factor.FACEID:
                        img_icon.Image = Properties.Resources.mainFaceidIcon;
                        img_label.Image = Properties.Resources.mainFaceidLabel;
                        break;
                    case Define.Factor.USB:
                        img_icon.Image = Properties.Resources.mainUsbIcon;
                        img_label.Image = Properties.Resources.mainUsbLabel;
                        break;
                    case Define.Factor.OTP:
                        img_icon.Image = Properties.Resources.mainOtpIcon;
                        img_label.Image = Properties.Resources.mainOtpLabel;
                        break;
                    case Define.Factor.QR:
                        img_icon.Image = Properties.Resources.mainQrIcon;
                        img_label.Image = Properties.Resources.mainQrLabel;
                        break;
                    case Define.Factor.PATTERN:
                        img_icon.Image = Properties.Resources.mainPatternIcon;
                        img_label.Image = Properties.Resources.mainPatternLabel;
                        break;
                }
                setControls();
                factor = value;
            }
        }

        [Category("Tollgate")]
        public bool on
        {
            get
            {
                return btn_toggle.On;
            }
            set
            {
                btn_toggle.On = value;
            }
        }

        [Category("Tollgate")]
        public event EventHandler ToggleClick
        {
            add
            {
                btn_toggle.Click += value;
            }
            remove
            {
                btn_toggle.Click -= value;
            }
        }

        public SelectCard()
        {
            InitializeComponent();

            img_icon.Parent = img_back;
            img_label.Parent = img_back;
            btn_toggle.Parent = img_back;
        }

        private void setControls()
        {
            img_icon.Size = img_icon.Image.Size;
            img_icon.Top = (66 - img_icon.Height) / 2 + 45;
            img_icon.Left = (ClientSize.Width - img_icon.Width) / 2;

            img_label.Top = 133;
            img_label.Left = (ClientSize.Width - img_label.Width) / 2;

            btn_toggle.Top = 218;
            btn_toggle.Left = (ClientSize.Width - btn_toggle.Width) / 2;
        }
    }
}
