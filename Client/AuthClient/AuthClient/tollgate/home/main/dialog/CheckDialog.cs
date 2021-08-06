using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AuthClient.tollgate.home.main.dialog
{
    [Flags]
    public enum CheckFlag : short
    {
        MOBILE = 1,
        FINGER = MOBILE << 1,
        FACE = FINGER << 1,
        USB = FACE << 1,
        PATTERN = USB << 1
    }

    public partial class CheckDialog : Form
    {
        public CheckDialog(CheckFlag flag)
        {
            InitializeComponent();

            MaximizeBox = false;

            if ((flag & CheckFlag.MOBILE) != 0)
                panel_list.Controls.Add(new CheckEntry(CheckFlag.MOBILE));
            if ((flag & CheckFlag.FINGER) != 0)
                panel_list.Controls.Add(new CheckEntry(CheckFlag.FINGER));
            if ((flag & CheckFlag.FACE) != 0)
                panel_list.Controls.Add(new CheckEntry(CheckFlag.FACE));
            if ((flag & CheckFlag.USB) != 0)
                panel_list.Controls.Add(new CheckEntry(CheckFlag.USB));
            if ((flag & CheckFlag.PATTERN) != 0)
                panel_list.Controls.Add(new CheckEntry(CheckFlag.PATTERN));

            int y = 0;
            foreach (Control control in panel_list.Controls)
            {
                control.Location = new Point(0, y);
                y += 93;
            }

            int count = panel_list.Controls.Count - 1;
            y = 25;
            for (int i = 0; i < count; i++)
            {
                PictureBox line = new PictureBox();
                line.Margin = new Padding(0, 0, 0, 0);
                line.Size = new Size(1, 67);
                line.Image = Properties.Resources.checkLine;
                panel_list.Controls.Add(line);
                line.Location = new Point(6, y);
                line.BringToFront();
                y += 93;
            }
        }

        private void CheckDialog_Load(object sender, EventArgs e)
        {
            StartPosition = FormStartPosition.Manual;
            int x = Owner.Location.X + ((Owner.Width - Width) / 2);
            int y = Owner.Location.Y + ((Owner.Height - Height) / 2);
            Location = new Point(x, y);
        }
    }
}