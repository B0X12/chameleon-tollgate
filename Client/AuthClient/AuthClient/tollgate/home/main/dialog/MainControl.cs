using System.Windows.Forms;

namespace AuthClient.tollgate.home.main.dialog
{
    public partial class MainControl : UserControl
    {
        public MainControl()
        {
            InitializeComponent();

            panel_select_flow.Width = panel_select_flow.Parent.Width + SystemInformation.VerticalScrollBarWidth;
        }
    }
}
