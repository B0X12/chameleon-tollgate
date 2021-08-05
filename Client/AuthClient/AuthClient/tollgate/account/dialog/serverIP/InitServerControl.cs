using System;
using System.ComponentModel;
using System.Windows.Forms;

namespace AuthClient.tollgate.account.dialog
{
    public partial class InitServerControl : UserControl
    {
        [Category("Tollgate")]
        public event EventHandler ConnectButtonClick
        {
            add { btn_connect.Click += value; }
            remove { btn_connect.Click -= value; }
        }

        [Category("Tollgate")]
        public event KeyEventHandler TextKeyDown
        {
            add { text_serverIP.KeyDown += value; }
            remove { text_serverIP.KeyDown -= value; }
        }

        [Category("Tollgate")]
        public string IP
        {
            get { return text_serverIP.Text; }
            set { text_serverIP.Text = value; }
        }

        [Category("Tollgate")]
        public bool Fix
        {
            get { return text_serverIP.Enabled; }
            set { text_serverIP.Enabled = value; }
        }

        public InitServerControl()
        {
            InitializeComponent();

        }
    }
}
