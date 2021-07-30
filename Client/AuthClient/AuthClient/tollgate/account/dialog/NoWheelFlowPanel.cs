using System.Windows.Forms;

namespace AuthClient.tollgate.account.dialog
{
    class NoWheelFlowPanel : FlowLayoutPanel
    {
        public NoWheelFlowPanel()
        {
            SetStyle(ControlStyles.OptimizedDoubleBuffer | ControlStyles.UserPaint | ControlStyles.AllPaintingInWmPaint, true);
            UpdateStyles();
        }

        protected override void OnMouseWheel(MouseEventArgs e)
        {
        }
    }
}