using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using static AuthClient.tollgate.define.Define;

namespace AuthClient.tollgate.home.history
{
    public partial class HistoryEntry : UserControl
    {
        private string date;
        private string pc;
        private Factor factor;
        private bool success;

        [Category("Tollgate")]
        public string Date
        {
            get { return date; }
            set
            {
                date = value;
                label.Text = date + "  ·  " + pc;
            }
        }

        [Category("Tollgate")]
        public string Pc
        {
            get { return pc; }
            set
            {
                pc = value;
                label.Text = date + "  ·  " + pc;
            }
        }

        [Category("Tollgate")]
        public Factor Factor
        {
            get { return factor; }
            set
            {
                switch (value)
                {
                    case Factor.QR:
                        img_label.Image = Properties.Resources.historyQrLabel;
                        break;
                    case Factor.FINGER:
                        img_label.Image = Properties.Resources.historyFingerLabel;
                        break;
                    case Factor.FACEID:
                        img_label.Image = Properties.Resources.historyFaceidLabel;
                        break;
                    case Factor.USB:
                        img_label.Image = Properties.Resources.historyUsbLabel;
                        break;
                    case Factor.OTP:
                        img_label.Image = Properties.Resources.historyOtpLabel;
                        break;
                    case Factor.PATTERN:
                        img_label.Image = Properties.Resources.historyPatternLabel;
                        break;
                }
                factor = value;
            }
        }

        [Category("Tollgate")]
        public bool Success
        {
            get { return success; }
            set
            {
                img_result.Image = value ? Properties.Resources.historyBtnSuccess : Properties.Resources.historyBtnFail;
                success = value;
            }
        }

        public HistoryEntry()
        {
            InitializeComponent();
            Font font = new Font(MainForm.FONT_BOLD, 9);
            label.Font = font;
            label.Left = 92;
            label.Top = (ClientSize.Height - label.Height) / 2;
        }
    }
}
