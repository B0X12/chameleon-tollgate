using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AuthClient.tollgate.home.setting
{
    public partial class TG_TextBox : UserControl
    {
        [Category("Tollgate")]
        public string Hint
        {
            get { return hint; }
            set
            {
                hint = value;
                SetPlaceholder(textBox, null);
            }
        }
        private string hint;

        [Category("Tollgate")]
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

        [Category("Tollgate")]
        public new bool Enabled
        {
            get { return textBox.Enabled; }
            set
            {
                textBox.ForeColor = value ? Color.Black : Color.DarkGray;
                textBox.Enabled = value;
            }
        }

        [Category("Tollgate")]
        public bool UseCheck
        {
            get { return useCheck; }
            set
            {
                img_check.Visible = value;
                useCheck = value;
            }
        }
        private bool useCheck;

        [Category("Tollgate")]
        public bool IsCheck
        {
            get { return isCheck; }
            set
            {
                if (value)
                    img_check.Image = Properties.Resources.check;
                else
                    img_check.Image = Properties.Resources.uncheck;
                isCheck = value;
            }
        }
        private bool isCheck;

        [Category("Tollgate")]
        public char PasswordChar
        {
            get { return passwordChar; }
            set { passwordChar = value; }
        }
        private char passwordChar;

        [Category("Tollgate")]
        public new event KeyEventHandler KeyDown
        {
            add { textBox.KeyDown += value; }
            remove { textBox.KeyDown -= value; }
        }

        public TG_TextBox()
        {
            InitializeComponent();
            UseCheck = false;

            textBox.ForeColor = Color.DarkGray;
            textBox.Text = hint;
            textBox.GotFocus += RemovePlaceholder;
            textBox.LostFocus += SetPlaceholder;
        }

        public new bool Focus()
        {
            return textBox.Focus();
        }

        private void RemovePlaceholder(object sender, EventArgs e)
        {
            TextBox txt = (TextBox)sender;
            if (txt.Text == hint)
            {
                txt.PasswordChar = passwordChar;
                txt.ForeColor = Color.Black;
                txt.Text = string.Empty;
            }
        }

        private void SetPlaceholder(object sender, EventArgs e)
        {
            TextBox txt = (TextBox)sender;
            if (string.IsNullOrWhiteSpace(txt.Text))
            {
                txt.PasswordChar = '\0';
                txt.ForeColor = Color.DarkGray;
                txt.Text = hint;
            }
        }

        private void textBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == (char)Keys.Enter)
                e.Handled = true;
        }
    }
}
