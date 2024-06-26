﻿using AuthClient.tollgate.usb.dto;
using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;

namespace AuthClient.tollgate.home.setting
{
    public partial class USBEntry : UserControl
    {
        public delegate void TextChangeHandler(USBEntry sender, string beforeText, string afterText);
        public delegate void ButtonClickHandler(USBEntry sender);

        [Category("Tollgate")]
        public event TextChangeHandler TextChange;

        [Category("Tollgate")]
        public bool IsRegistered
        {
            get { return isRegistered; }
            set
            {
                img_button.Image = value ? Properties.Resources.settingUsbDel : Properties.Resources.settingUsbAdd;
                img_edit.Visible = value;
                isRegistered = value;
            }
        }
        private bool isRegistered;

        [Category("Tollgate")]
        public USBInfo UsbInfo
        {
            get { return usbInfo; }
            set
            {
                label_name.Text = value.alias;
                usbInfo = value;
            }
        }
        private USBInfo usbInfo;

        [Category("Tollgate")]
        public event ButtonClickHandler ButtonClick
        {
            add { buttonClick += value; }
            remove { buttonClick -= value; }
        }
        private event ButtonClickHandler buttonClick;

        public USBEntry()
        {
            InitializeComponent();

            img_button.Click += Img_button_Click;

            IsRegistered = false;
            Font font = new Font(MainForm.FONT_BOLD, 11);
            label_name.Font = font;
            text_edit.Font = font;

            label_name.Top = text_edit.Top = img_button.Top = img_edit.Top = (ClientSize.Height - label_name.Height) / 2 - 4;
            label_name.Left = 66;
            text_edit.Left = label_name.Left + 4;
            img_edit.Left = 340;
        }

        private void Img_button_Click(object sender, EventArgs e)
        {
            buttonClick(this);
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

        private void img_edit_Click(object sender, EventArgs e)
        {
            text_edit.Text = label_name.Text;
            text_edit.Visible = true;
            text_edit.Focus();
        }
    }
}
