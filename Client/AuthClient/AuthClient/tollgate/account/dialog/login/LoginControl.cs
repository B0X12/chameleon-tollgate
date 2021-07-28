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
    public partial class LoginControl : UserControl
    {
        public delegate void LoginClickEvent();
        public event LoginClickEvent loginButtonClick;
        public event LoginTitle.SignupClickEvent signupButtonClick;

        public LoginControl()
        {
            InitializeComponent();

            img_title.signupButtonClick += btn_signup_Click;
        }

        private void btn_signup_Click()
        {
            if (signupButtonClick != null)
                signupButtonClick();
        }

        private void btn_login_Click(object sender, EventArgs e)
        {
            if (loginButtonClick != null)
                loginButtonClick();
        }


    }
}
