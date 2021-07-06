using AuthClient.dto;
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

namespace AuthClient
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

            Account ua = new Account(idField.Text, pwdField.Text);

            string logonResult = HttpCommunication.SendRequestPOST(AccountURL.LOGIN, ua);
            
            if (logonResult.Equals("true"))
            {
                Config.SetCurrentUser(idField.Text);
                this.DialogResult = DialogResult.OK;
            } 
            else
            {
                MessageBox.Show("아이디 또는 패스워드가 일치하지 않습니다");
            }
        }
    }
}
