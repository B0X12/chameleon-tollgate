using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;

namespace AuthClient.tollgate.account.dialog
{
    public partial class LoginTitle : UserControl
    {
        public event EventHandler SignupButtonClick
        {
            add { btn_signup.Click += value; }
            remove { btn_signup.Click -= value; }
        }

        private bool canSignup;

        [Category("Tollgate"), DefaultValue(true)]
        public bool CanSignup
        {
            get { return canSignup; }
            set {
                btn_signup.Enabled = value;
                if (value)
                    img_back.Image = Properties.Resources.loginTitle;
                else
                    img_back.Image = Properties.Resources.loginTitleNoSignup;
                canSignup = value;
            }
        }

        public LoginTitle()
        {
            InitializeComponent();

            btn_signup.Parent = img_back;
            btn_signup.FlatAppearance.BorderSize = 0;
            btn_signup.FlatAppearance.MouseDownBackColor = Color.Transparent;
            btn_signup.FlatAppearance.MouseOverBackColor = Color.Transparent;
            btn_signup.BackColor = Color.Transparent;
        }
    }
}
