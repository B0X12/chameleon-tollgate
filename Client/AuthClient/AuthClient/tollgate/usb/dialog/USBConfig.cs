using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AuthClient.tollgate.usb.dialog
{
    public partial class USBConfigDialog : Form
    {
        static int count = 0;

        public USBConfigDialog()
        {
            InitializeComponent();
        }

        private void USBConfigDialog_Activated(object sender, System.EventArgs e)
        {
            count++;
            this.usbUnregisterButton.Text = "창활성화" + count;
            
        }

        private void usbAddButton_Click(object sender, EventArgs e)
        {

        }

        private void usbUnregisterButton_Click(object sender, EventArgs e)
        {

        }
    }
}
