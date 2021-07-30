using System;
using System.Drawing;
using System.Windows.Forms;

namespace AuthClient.tollgate.account.dialog
{
    public partial class LoginTitle : UserControl
    {
        public delegate void SignupClickEvent();
        public event EventHandler SignupButtonClick
        {
            add { btn_signup.Click += value; }
            remove { btn_signup.Click -= value; }
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
