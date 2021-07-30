using System;
using System.ComponentModel;
using System.Windows.Forms;

namespace AuthClient.tollgate.account.dialog
{
    public partial class LoginControl : UserControl
    {
        [Category("Tollgate")]
        public event EventHandler LoginButtonClick
        {
            add { btn_login.Click += value; }
            remove { btn_login.Click -= value; }
        }

        [Category("Tollgate")]
        public event EventHandler SignupButtonClick
        {
            add { img_title.SignupButtonClick += value; }
            remove { img_title.SignupButtonClick -= value; }
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
        public bool FixID
        {
            get { return text_id.Fix; }
            set { text_id.Fix = value; }
        }

        public LoginControl()
        {
            InitializeComponent();
        }
    }
}
