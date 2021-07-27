using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AuthClient.tollgate.account.dialog
{
    public partial class StepControl : UserControl
    {
        public StepControl()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Sets the display of the current step.
        /// </summary>
        /// <param name="step">Current step.</param>
        public void setStep(int step)
        {
            this.img_step1.Image = Properties.Resources.stepOff;
            this.img_step2.Image = Properties.Resources.stepOff;
            this.img_step3.Image = Properties.Resources.stepOff;

            switch (step)
            {
                case 0:
                    this.img_step1.Image = Properties.Resources.stepOn;
                    break;
                case 1:
                    this.img_step2.Image = Properties.Resources.stepOn;
                    break;
                case 2:
                    this.img_step3.Image = Properties.Resources.stepOn;
                    break;
            }
        }
    }
}
