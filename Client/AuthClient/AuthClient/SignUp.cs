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


            // HTTP Request 설정
            HttpWebRequest req = (HttpWebRequest)WebRequest.Create(RestUtility.GetURL() + "accounts");
            req.ContentType = "application/json";
            req.Method = "POST";

            // JSON 형식의 데이터 생성: [id, pwd]
            using (StreamWriter sw = new StreamWriter(req.GetRequestStream()))
            {
                string json = JsonConvert.SerializeObject(new UserAccount(idField.Text, pwdField.Text));
                sw.Write(json);
            }

            // 서버와 통신 시작
            try
            {
                WebResponse resp = req.GetResponse();
                /*
                using (StreamReader sr = new StreamReader(resp.GetResponseStream()))
                {
                    MessageBox.Show(sr.ReadToEnd());
                }
                */
                MessageBox.Show("회원 가입이 완료되었습니다");
                this.DialogResult = DialogResult.OK;
            }
            catch (WebException we)
            {
                MessageBox.Show("이미 존재하는 회원입니다");
            }

        }
    }

    // 해당 클래스는 RestServer DTO의 UserAccount에 대응됨

}
