using AuthClient.tollgate.account.service;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AuthClient.tollgate.account.dialog
{
    public partial class LogOnDialog : Form
    {
        private string user;

        public LogOnDialog()
        {
            InitializeComponent();
            this.user = "";
        }

        public LogOnDialog(string user)
        {
            InitializeComponent();
            this.user = user;
            idField.Text = this.user;
            idField.Enabled = false;
        }


        private void logonButton_Click(object sender, EventArgs e)
        {
            // 아이디 필드 검사
            if (idField.Text.Length == 0)
            {
                MessageBox.Show("아이디를 입력해 주십시오");
                return;
            }

            // 패스워드 필드 검사
            if (pwdField.Text.Length == 0)
            {
                MessageBox.Show("패스워드를 입력해 주십시오");
                return;
            }


            AccountService service = new AccountService();

            try
            {
                if(service.IsLoginSuccess(idField.Text, pwdField.Text))
                {
                    Config.SetCurrentUser(idField.Text);

                    // 인증 서버에 등록된 계정이 없을 경우, 해당 계정과 컴퓨터를 인증 서버에 등록함
                    if(this.user.Equals(""))
                    {
                        service.MappingSIDWithUser(idField.Text);
                    }

                    this.DialogResult = DialogResult.OK;
                }
            }

            // 인증 서버에서 기타 오류 발생할 경우 처리
            catch (WebException we)
            {
                MessageBox.Show(we.Message);
                Config.DeleteConfigFile();
                Application.Restart();
            }
        }
    }
}
