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
    public partial class SignupControl : UserControl
    {
        public delegate void SignupClickEvent();
        public event SignupClickEvent signupButtonClick;

        public SignupControl()
        {
            InitializeComponent();
        }

        private void btn_signup_Click(object sender, EventArgs e)
        {
            if (signupButtonClick != null)
                signupButtonClick();
        }
    }
}
