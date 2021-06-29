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
        public LogOnDialog()
        {
            InitializeComponent();
        }

        private void logonButton_Click(object sender, EventArgs e)
        {
            // HTTP Request 설정
            HttpWebRequest req = (HttpWebRequest)WebRequest.Create(RestUtility.GetURL() + "logOn");
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
                String logonResult = "false";
                
                using (StreamReader sr = new StreamReader(resp.GetResponseStream()))
                {
                    //MessageBox.Show(sr.ReadToEnd());
                    logonResult = sr.ReadToEnd();
                }

                if(logonResult.Equals("false"))
                {
                    MessageBox.Show("로그인 실패");
                }
                else
                {
                    MessageBox.Show("로그인 성공");
                    this.DialogResult = DialogResult.OK;
                }
            }
            catch (WebException we)
            {
                MessageBox.Show("로그인 중 오류가 발생하였습니다: " + we.Message);
            }
        }
    }
}
