﻿using System;
using System.Drawing;
using System.Drawing.Text;
using System.Windows.Forms;

namespace AuthClient.tollgate.account.dialog
{
    public partial class SplashButton : UserControl
    {
        public new event EventHandler Click
        {
            add
            {
                base.Click += value;
                foreach (Control control in Controls)
                {
                    control.Click += value;
                }
            }
            remove
            {
                base.Click -= value;
                foreach (Control control in Controls)
                {
                    control.Click -= value;
                }
            }
        }

        public SplashButton()
        {
            InitializeComponent();

            PrivateFontCollection fonts = new PrivateFontCollection();
            fonts.AddFontFile(Property.FONT_BOLD);
            Font font = new Font(fonts.Families[0], 11);
            label.Font = font;

            label.Left = ((img_back.Width - label.Width) / 2);
            label.Top = ((img_back.Height - label.Height) / 2);
        }
    }
}