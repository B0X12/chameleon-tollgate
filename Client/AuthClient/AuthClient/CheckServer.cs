using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AuthClient
{
    public partial class CheckServerDialog : Form
    {
        public CheckServerDialog()
        {
            InitializeComponent();
        }

        private void CheckButton_Click(object sender, EventArgs e)
        {
            RestUtility.InitRestClient(serverInfoTextBox.Text, false);
            statusMsgLabel.Text = "다음 서버에 연결하는 중입니다.. " + serverInfoTextBox.Text;

            // 서버 연결 성공
            if (RestUtility.IsServerAlive())
            {
                this.DialogResult = DialogResult.OK;
            }

            // 서버 연결 실패
            else
            {
                statusMsgLabel.Text = "인증 서버의 [IP:포트 번호]를 입력하십시오";
            }
        }
    }
}
