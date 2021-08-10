using System;
using System.ComponentModel;
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
                    button.Image = Properties.Resources.mainToggleOn;
                else
                    button.Image = Properties.Resources.mainToggleOff;
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
        }

        private void button_Click(object sender, EventArgs e)
        {
            On = !on;
        }
    }
}
