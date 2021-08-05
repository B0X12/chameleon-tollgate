using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AuthClient.tollgate.home.setting
{
    public partial class SettingUSB : UserControl
    {
        public SettingUSB()
        {
            InitializeComponent();

            panel_list.Width = panel_list.Parent.Width + SystemInformation.VerticalScrollBarWidth + 95;
            panel_list.Height = panel_list.Parent.Height + 30;
        }
    }
}
