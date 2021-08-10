using System.Windows.Forms;

namespace AuthClient.tollgate.home.main.dialog
{
    public partial class CheckEntry : UserControl
    {
        public enum CheckStat
        {
            WAIT,
            DOING,
            PASS,
            FAIL
        }

        private CheckFlag flag;
        public CheckFlag Flag
        {
            get { return flag; }
            set { flag = value; }
        }

        private CheckStat stat;
        public CheckStat Stat
        {
            get { return stat; }
            set
            {
                switch (value)
                {
                    case CheckStat.WAIT:
                        img_stat.Image = Properties.Resources.checkStatWait;
                        break;
                    case CheckStat.DOING:
                        img_stat.Image = Properties.Resources.checkStatDoing;
                        break;
                    case CheckStat.PASS:
                        img_stat.Image = Properties.Resources.checkStatComplete;
                        break;
                    case CheckStat.FAIL:
                        img_stat.Image = Properties.Resources.checkStatFailed;
                        break;
                }
                stat = value;
            }
        }

        public CheckEntry(CheckFlag kind)
        {
            InitializeComponent();

            this.Flag = kind;

            switch (kind)
            {
                case CheckFlag.MOBILE:
                    img_text.Image = Properties.Resources.checkPhoneText;
                    break;
                case CheckFlag.FINGER:
                    img_text.Image = Properties.Resources.checkFingerText;
                    break;
                case CheckFlag.FACE:
                    img_text.Image = Properties.Resources.checkFaceidText;
                    break;
                case CheckFlag.USB:
                    img_text.Image = Properties.Resources.checkUsbText;
                    break;
                case CheckFlag.PATTERN:
                    img_text.Image = Properties.Resources.checkPatternText;
                    break;
            }
        }
    }
}
