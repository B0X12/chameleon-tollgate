using AuthClient.tollgate.account.service;
using Newtonsoft.Json;
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

namespace AuthClient.tollgate.account.dialog
{
    public partial class CheckServerDialog : Form
    {
        public CheckServerDialog()
        {
            InitializeComponent();
        }

        private void CheckButton_Click(object sender, EventArgs e)
        {
            AccountService service = new AccountService();

            Config.InitializeBaseURL(serverInfoTextBox.Text, true);
            statusMsgLabel.Text = "다음 서버에 연결하는 중입니다.. " + serverInfoTextBox.Text;

            // --------------- 서버가 살아 있는지 확인 ---------------

            if(service.IsServerAlive())
            {
                Config.CreateConfigFile(Config.GetBaseURL());
                this.DialogResult = DialogResult.OK;
            } 
            else
            {
                statusMsgLabel.Text = "인증 서버의 [IP:포트 번호]를 입력하십시오";
            }
        }
    }
}
