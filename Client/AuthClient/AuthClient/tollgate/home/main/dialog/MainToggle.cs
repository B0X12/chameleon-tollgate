using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;

namespace AuthClient.tollgate.home.main.dialog
{
    public partial class MainToggle : UserControl
    {
        private bool on;

        [Category("Tollgate")]
        public bool On
        {
            get
            {
                return on;
            }
            set
            {
                if (value)
                    button.BackgroundImage = Properties.Resources.mainToggleOn;
                else
                    button.BackgroundImage = Properties.Resources.mainToggleOff;
                on = value;
            }
        }

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

        public MainToggle()
        {
            InitializeComponent();

            button.FlatAppearance.BorderSize = 0;
            button.FlatAppearance.MouseDownBackColor = Color.Transparent;
            button.FlatAppearance.MouseOverBackColor = Color.Transparent;
            button.BackColor = Color.Transparent;

        }

        private void button_Click(object sender, EventArgs e)
        {
            On = !on;
        }
    }
}
