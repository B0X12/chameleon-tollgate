using System;
using System.ComponentModel;
using System.Windows.Forms;

namespace AuthClient.tollgate.account.dialog
{
    public partial class SignupControl : UserControl
    {
        [Category("Tollgate")]
        public event EventHandler SignupButtonClick
        {
            add { btn_signup.Click += value; }
            remove { btn_signup.Click -= value; }
        }

        [Category("Tollgate")]
        public event KeyEventHandler ConfirmKeyDown
        {
            add { text_confirm.KeyDown += value; }
            remove { text_confirm.KeyDown -= value; }
        }

        [Category("Tollgate")]
        public string ID
        {
            get { return text_id.Text; }
            set { text_id.Text = value; }
        }

        [Category("Tollgate")]
        public string PWD
        {
            get { return text_pwd.Text; }
            set { text_pwd.Text = value; }
        }

        [Category("Tollgate")]
        public string Confirm
        {
            get { return text_confirm.Text; }
            set { text_confirm.Text = value; }
        }

        public SignupControl()
        {
            InitializeComponent();
        }

        private void text_confirm_KeyPress(object sender, KeyPressEventArgs e)
        {
            text_confirm.IsCheck = text_pwd.Text == text_confirm.Text + e.KeyChar;
        }

        private void text_pwd_KeyPress(object sender, KeyPressEventArgs e)
        {
            text_confirm.IsCheck = text_confirm.Text == text_pwd.Text + e.KeyChar;
        }
    }
}
