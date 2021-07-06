using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Net;
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
            HttpCommunication.InitializeBaseURL(serverInfoTextBox.Text, true);
            statusMsgLabel.Text = "다음 서버에 연결하는 중입니다.. " + serverInfoTextBox.Text;

            // Is the server alive?
            try
            {
                string strFromServer = HttpCommunication.SendRequestGET(EtcURL.SERVER_HELLO);
                if (strFromServer.Equals("Hello"))
                {
                    // server.cfg 파일 생성
                    Config.CreateConfigFile(HttpCommunication.GetURL());
                    this.DialogResult = DialogResult.OK;
                }
                else
                {
                    statusMsgLabel.Text = "인증 서버의 [IP:포트 번호]를 입력하십시오";
                }
            } 
            catch(WebException we)
            {
                MessageBox.Show(we.Message);
                statusMsgLabel.Text = "인증 서버의 [IP:포트 번호]를 입력하십시오";
            }
        }
    }
}
