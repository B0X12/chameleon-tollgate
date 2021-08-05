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

namespace AuthClient.tollgate.home.main.dialog
{
    public partial class UserCard : UserControl
    {
        public event EventHandler LogoutClick {
            add
            {
                img_logout.Click += value;
            }
            remove
            {
                img_logout.Click -= value;
            }
        }

        public new string Text
        {
            get { return label_id.Text; }
            set
            {
                label_id.Text = value;
                label_id.Font = new Font(MainForm.FONT_BOLD, 11);
                label_id.Left = (ClientSize.Width - label_id.Width) / 2;
                label_id.Top = img_icon.Bottom + 14;
            }
        }

        public UserCard()
        {
            InitializeComponent();
            img_icon.Left = (ClientSize.Width - img_icon.Width) / 2;
            img_icon.Top = 57 + 6;
            img_logout.Left = (ClientSize.Width - img_logout.Width) / 2;
            img_logout.Top = img_icon.Bottom + 67;
        }
    }
}