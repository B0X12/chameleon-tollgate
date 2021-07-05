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
using AuthClient.dto;
using Newtonsoft.Json;


namespace AuthClient
{
    public partial class SignUpDialog : Form
    {
        public SignUpDialog()
        {
            InitializeComponent();
        }

        private void signUpButton_Click(object sender, EventArgs e)
        {
            // 아이디 필드 검사
            if(idField.Text.Length == 0)
            {
                MessageBox.Show("아이디를 입력해 주십시오");
                return;
            }

            // 패스워드 필드 검사
            if(pwdField.Text.Length == 0)
            {
                MessageBox.Show("패스워드를 입력해 주십시오");
                return;
            }
            
            // 비밀번호 및 비밀번호 확인 검사
            if (!(pwdField.Text.Equals(pwdCheckField.Text)))
            {
                MessageBox.Show("비밀번호가 같지 않습니다");
                return;
            }

            // 회원 가입 절차
            Account ac = new Account(idField.Text, pwdField.Text);
            
            string logonResult = HttpCommunication.SendRequestPOST("account/signup", ac);
            
            if (logonResult.Equals("true"))
            {
                this.DialogResult = DialogResult.OK;
            } 
            else
            {
                MessageBox.Show("등록된 회원이 이미 존재합니다");
            }
        }
    }
}
