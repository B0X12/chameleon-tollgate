using System;
using System.Windows.Forms;

namespace AuthClient.tollgate.home.main.dialog
{
    public partial class SidebarSetting : UserControl
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

        public SidebarSetting()
        {
            InitializeComponent();

            img_icon.Left = ClientRectangle.Left + 15;
            img_icon.Top = (ClientSize.Height - img_icon.Height) / 2;

            //PrivateFontCollection fonts = new PrivateFontCollection();
            //fonts.AddFontFile(Property.FONT_BOLD);

            //Font font = new Font(fonts.Families[0], 10);
            //label.Font = font;
            label.Left = ClientRectangle.Left + 41;
            label.Top = (ClientSize.Height - label.Height) / 2;
        }

        public void setOn()
        {
            img_icon.Image = Properties.Resources.mainSideSettingOn;
            BackgroundImage = Properties.Resources.mainSideBg;
        }

        public void setOff()
        {
            img_icon.Image = Properties.Resources.mainSideSettingOff;
            BackgroundImage = null;
        }
    }
}
