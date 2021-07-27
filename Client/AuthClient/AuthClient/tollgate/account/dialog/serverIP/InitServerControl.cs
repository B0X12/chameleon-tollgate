using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AuthClient.tollgate.account.dialog
{
    public partial class InitServerControl : UserControl
    {
        public delegate void AfterConnect();
        public event AfterConnect connectButtonClick;

        public InitServerControl()
        {
            InitializeComponent();

        }

        private void button_connect_Click(object sender, EventArgs e)
        {
            if (connectButtonClick != null)
                connectButtonClick();
        }
    }
}
