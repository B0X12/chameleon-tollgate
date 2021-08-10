using AuthClient.tollgate.account.dto;
using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;

namespace AuthClient.tollgate.home.setting
{
    public partial class PCEntry : UserControl
    {
        public delegate void TextChangeHandler(PCEntry sender, string beforeText, string afterText);

        [Category("Tollgate")]
        public event TextChangeHandler TextChange;

        [Category("Tollgate")]
        public MapPC MapPCInfo
        {
            get { return mapPCInfo; }
            set
            {
                label_name.Text = value.alias;
                mapPCInfo = value;
            }
        }
        private MapPC mapPCInfo;

        [Category("Tollgate")]
        public bool MainPC
        {
            get { return mainPC; }
            set
            {
                img_crown.Visible = value;
                label_name.ForeColor = value ? Color.FromArgb(229, 179, 73) : Color.Black;
                img_back.Image = value ? Properties.Resources.settingPcMainPC : Properties.Resources.settingPC;
                mainPC = value;
            }
        }
        private bool mainPC;

        [Category("Tollgate")]
        public event EventHandler EditClick
        {
            add { img_edit.Click += value; }
            remove { img_edit.Click -= value; }
        }

        public PCEntry()
        {
            InitializeComponent();

            MainPC = false;

            Font font = new Font(MainForm.FONT_BOLD, 11);
            label_name.Font = font;
            text_edit.Font = font;
        }

        private void img_edit_Click(object sender, EventArgs e)
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
