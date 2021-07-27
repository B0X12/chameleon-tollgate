using System;
using System.Windows.Forms;
using System.Drawing;
using System.Drawing.Text;

namespace AuthClient.tollgate.account.dialog
{
    public partial class ConnectButton : UserControl
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

        public ConnectButton()
        {
            InitializeComponent();

            PrivateFontCollection fonts = new PrivateFontCollection();
            fonts.AddFontFile(Property.FONT_BOLD);
            Font font = new Font(fonts.Families[0], 11);
            label.Font = font;
        }
    }
}
