using System;
using System.Drawing;
using System.Windows.Forms;

namespace AuthClient.tollgate.account.dialog
{
    public partial class TextServerControl : UserControl
    {
        private const string hint = "127.xxx.xxx.xxx";

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

        public bool Fix
        {
            get { return textBox.Enabled; }
            set { textBox.Enabled = value; }
        }

        public TextServerControl()
        {
            InitializeComponent();
            textBox.ForeColor = Color.DarkGray;
            textBox.Text = hint;
            textBox.GotFocus += RemovePlaceholder;
            textBox.LostFocus += SetPlaceholder;

            //PrivateFontCollection fonts = new PrivateFontCollection();
            //fonts.AddFontFile("Resources\\Font\\BareunDotumPro1.ttf");
            //Font font = new Font(fonts.Families[0], 10);
            //textBox.Font = font;
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
