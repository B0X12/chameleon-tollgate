using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AuthClient
{
    public partial class MainDialog : Form
    {
        public MainDialog()
        {
            InitializeComponent();
            userLabel.Text += Config.GetCurrentUser();
        }

        private void usbDlgButton_Click(object sender, EventArgs e)
        {
            USBConfigDialog usbCfgDlg = new USBConfigDialog();
            usbCfgDlg.ShowDialog();
        }
    }
}
