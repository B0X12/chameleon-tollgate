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
    public partial class LoginTitle : UserControl
    {
        public delegate void SignupClickEvent();
        public event SignupClickEvent signupButtonClick;

        public LoginTitle()
        {
            InitializeComponent();

            btn_signup.Parent = img_back;
            btn_signup.FlatAppearance.BorderSize = 0;
            btn_signup.FlatAppearance.MouseDownBackColor = Color.Transparent;
            btn_signup.FlatAppearance.MouseOverBackColor = Color.Transparent;
            btn_signup.BackColor = Color.Transparent;
        }

        private void btn_signup_Click(object sender, EventArgs e)
        {
            if (signupButtonClick != null)
                signupButtonClick();
        }
    }
}
