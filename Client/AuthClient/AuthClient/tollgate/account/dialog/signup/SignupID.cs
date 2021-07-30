using System;
using System.Drawing;
using System.Windows.Forms;

namespace AuthClient.tollgate.account.dialog.signup
{
    public partial class SignupID : UserControl
    {
        private const string hint = "ID";

        public new string Text
        {
            get
            {
                if (textBox.Text != hint)
                    return textBox.Text;
                return String.Empty;
            }
            set { textBox.Text = value; }
        }

        public SignupID()
        {
            InitializeComponent();
            textBox.ForeColor = Color.DarkGray;
            textBox.Text = hint;
            textBox.GotFocus += RemovePlaceholder;
            textBox.LostFocus += SetPlaceholder;
        }

        private void RemovePlaceholder(object sender, EventArgs e)
        {
            TextBox txt = (TextBox)sender;
            if (txt.Text == hint)
            {
                txt.ForeColor = Color.Black;
                txt.Text = string.Empty;
            }
        }

        private void SetPlaceholder(object sender, EventArgs e)
        {
            TextBox txt = (TextBox)sender;
            if (string.IsNullOrWhiteSpace(txt.Text))
            {
                txt.ForeColor = Color.DarkGray;
                txt.Text = hint;
            }
        }
    }
}
