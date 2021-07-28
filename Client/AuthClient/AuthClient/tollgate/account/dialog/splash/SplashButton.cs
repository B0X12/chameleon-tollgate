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
    public partial class SplashButton : UserControl
    {
        public SplashButton()
        {
            InitializeComponent();

            PrivateFontCollection fonts = new PrivateFontCollection();
            fonts.AddFontFile(Property.FONT_BOLD);
            Font font = new Font(fonts.Families[0], 11);
            label.Font = font;

            label.Left = ((img_back.Width - label.Width) / 2) + img_back.Left;
            label.Top = ((img_back.Height - label.Height) / 2) + img_back.Top;
        }
    }
}