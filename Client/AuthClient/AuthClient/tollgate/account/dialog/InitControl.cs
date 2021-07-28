using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Windows.Forms;

namespace AuthClient.tollgate.account.dialog
{
    public partial class InitControl : UserControl
    {
        private const int FLOW_SIZE = 531;
        private const int FLOW_INTERVAL = 17;

        /// <summary>
        /// Current step number.
        /// </summary>
        /// <remarks> Values from 0 to 2.</remarks>
        private int step = 0;
        private InitServerControl initServerControl;
        private LoginControl loginCon;
        private SignupControl signupControl;

        public delegate void LoginClickEvent();
        public event LoginClickEvent loginButtonClick;

        public InitControl()
        {
            InitializeComponent();
        }

        private void InitControl_Load(object sender, EventArgs e)
        {
            //panel_flow.Width = panel_flow.Parent.Width + SystemInformation.VerticalScrollBarWidth;
            panel_flow.Height = panel_flow.Parent.Height + SystemInformation.HorizontalScrollBarHeight;

            initServerControl = new InitServerControl();
            initServerControl.connectButtonClick += flowNext;
            panel_flow.Controls.Add(initServerControl);

            loginCon = new LoginControl();
            loginCon.signupButtonClick += flowNext;
            loginCon.loginButtonClick += btn_login_Click;

            signupControl = new SignupControl();
            signupControl.signupButtonClick += flowPrev;

            view_step.setStep(step);
        }

        private void btn_login_Click()
        {
            if (loginButtonClick != null)
                loginButtonClick();
        }

        /// <summary>
        /// Slide over FlowLayoutPanel.
        /// </summary>
        /// <remarks>
        ///     Slide over to the next screen of <paramref name="step"/> and increase <paramref name="step"/> by 1.
        /// </remarks>
        private void flowNext()
        {
            if (step == 2)
                return;
            else if (step == 0)
            {
                panel_flow.Controls.Add(loginCon);
                panel_flow.Controls.SetChildIndex(loginCon, 1);
            }
            else if (step == 1)
            {
                panel_flow.Controls.Add(signupControl);
                panel_flow.Controls.SetChildIndex(signupControl, 1);
            }
            step++;

            for (int i = FLOW_INTERVAL; i < FLOW_SIZE; i += FLOW_INTERVAL)
            {
                panel_flow.HorizontalScroll.Value = i;
                panel_flow.Update();
            }

            panel_flow.Controls.RemoveAt(0);
            view_step.setStep(step);
        }

        /// <summary>
        /// Slide back FlowLayoutPanel.
        /// </summary>
        /// <remarks>
        ///     Slide back to the previous screen of <paramref name="step"/> and reduce <paramref name="step"/> by 1.
        /// </remarks>
        private void flowPrev()
        {
            if (step == 0)
                return;
            else if (step == 1)
            {
                panel_flow.Controls.Add(initServerControl);
                panel_flow.Controls.SetChildIndex(initServerControl, 0);
            }
            else if (step == 2)
            {
                panel_flow.Controls.Add(loginCon);
                panel_flow.Controls.SetChildIndex(loginCon, 0);
            }
            step--;

            for (int i = FLOW_SIZE - FLOW_INTERVAL; i > 0; i -= FLOW_INTERVAL)
            {
                try
                {
                    panel_flow.HorizontalScroll.Value = i;
                }
                catch (ArgumentOutOfRangeException ex)
                {
                    panel_flow.HorizontalScroll.Value = 0;
                }
                panel_flow.Update();
            }

            panel_flow.Controls.RemoveAt(1);
            view_step.setStep(step);
        }

        /// <summary>
        /// Wallpaper's click event fuction for test
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void picture_wallpaper_Click(object sender, EventArgs e)
        {
            flowPrev();
        }
    }
}