using AuthClient.tollgate.account.service;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AuthClient.tollgate
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
            Application.Restart();
            /*
            USBConfigDialog usbCfgDlg = new USBConfigDialog();
            usbCfgDlg.ShowDialog();
            */
        }

        private void logoutButton_Click(object sender, EventArgs e)
        {
            AccountService accountService = new AccountService();
            accountService.UnmapSIDWithUser(Config.GetCurrentUser());
            Application.Restart();
        }
    }
}
