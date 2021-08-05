using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Text;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AuthClient.tollgate.home.setting
{
    public partial class USBEntry : UserControl
    {
        public delegate void TextChangeHandler(object sender, string beforeText, string afterText);

        [Category("Tollgate")]
        public event TextChangeHandler TextChange;

        [Category("Tollgate")]
        public bool IsUsing
        {
            get { return isUsing; }
            set
            {
                img_button.Image = value ? Properties.Resources.settingUsbDel : Properties.Resources.settingUsbAdd;
                img_usb.Image = value ? Properties.Resources.settingUsbUsing : Properties.Resources.settingUsbNone;
                label_name.BackColor = value ? Color.FromArgb(143, 143, 143) : Color.FromArgb(196, 196, 196);
                isUsing = value;
            }
        }
        private bool isUsing;

        [Category("Tollgate")]
        public new string Text
        {
            get { return label_name.Text; }
            set { label_name.Text = value; }
        }

        [Category("Tollgate")]
        public event EventHandler ButtonClick
        {
            add { img_button.Click += value; }
            remove { img_button.Click -= value; }
        }

        public USBEntry()
        {
            InitializeComponent();

            IsUsing = false;
            Font font = new Font(MainForm.FONT_BOLD, 11);
            label_name.Font = font;
            text_edit.Font = font;

            label_name.Top = text_edit.Top = (ClientSize.Height - label_name.Height) / 2;
            label_name.Left =img_usb.Left + 45;
            text_edit.Left = label_name.Left + 4;
        }

        private void label_name_Click(object sender, EventArgs e)
        {
            text_edit.Text = label_name.Text;
            text_edit.Visible = true;
            text_edit.Focus();
        }

        private void text_edit_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                if (TextChange != null)
                    TextChange(this, label_name.Text, text_edit.Text);
                text_edit.Visible = false;
            }
            else if (e.KeyCode == Keys.Escape)
            {
                text_edit.Visible = false;
            }
        }

        private void text_edit_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == (char)Keys.Enter || e.KeyChar == (char)Keys.Escape)
            {
                e.Handled = true;
            }
        }
    }
}
