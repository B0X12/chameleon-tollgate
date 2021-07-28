using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Drawing.Text;

namespace AuthClient.tollgate.account.dialog
{
    public partial class LoginButton : UserControl
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

        public LoginButton()
        {
            InitializeComponent();

            PrivateFontCollection fonts = new PrivateFontCollection();
            fonts.AddFontFile(Property.FONT_BOLD);
            Font font = new Font(fonts.Families[0], 11);
            label.Font = font;
        }
    }
}
