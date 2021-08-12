using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using RestClient.dto;
using RestClient.rest;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Net;
using System.Net.Json;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace RestClient
{
    public partial class QRForm : Form
    {
        private string user = "";
        private string sid = "";
        private string qrData = "";

        public QRForm(string user, string sid)
        {
            InitializeComponent();

            // 창 위치 설정
            this.StartPosition = FormStartPosition.Manual;

            int desktopWidth = Screen.PrimaryScreen.Bounds.Width;
            int desktopHeight = Screen.PrimaryScreen.Bounds.Height;
            int windowWidth = this.Size.Width;
            int windowHeight = this.Size.Height;

            this.Location = new Point((desktopWidth - windowWidth) / 2, (desktopHeight - windowHeight) / 2 - 140);

            this.user = user;
            this.sid = sid;
        }

        private void QRForm_Shown(object sender, EventArgs e)
        {
            ZXing.BarcodeWriter barcordWriter = new ZXing.BarcodeWriter();
            barcordWriter.Format = ZXing.BarcodeFormat.QR_CODE;

            barcordWriter.Options.Width = this.pictureBox1.Width;
            barcordWriter.Options.Height = this.pictureBox1.Height;
            barcordWriter.Options.Margin = 1;

            // --------------- 1. 사용자 ID를 이용하여 QR 생성에 사용할 값을 읽어옴 ---------------
            this.qrData = GetCreateQRData(this.user);

            if ((qrData == ReturnMessage.FAIL))
            {
                this.DialogResult = DialogResult.Abort;
                this.Close();
            }

            // ---------------- 2. 읽어온 값을 이용하여 QR 코드 생성하여 표시함 ----------------
            JsonObjectCollection jo = new JsonObjectCollection();
            jo.Add(new JsonStringValue("userId", this.user));
            jo.Add(new JsonStringValue("data", qrData));

            this.pictureBox1.Image = barcordWriter.Write(jo.ToString());


            // --------------- 3. 서버로부터 인증을 기다림 ---------------
            Thread t = new Thread(WaitForQRVerification);
            t.Start();
        }

        private string GetCreateQRData(string user)
        {
            // 통신 TimeStamp
            long currentTimestamp = Util.GetCurrentTimestamp();

            // DTO 준비
            AuthQr authqr = new AuthQr(user, currentTimestamp);

            // 통신 세팅
            QueryString qs = new QueryString("timestamp", currentTimestamp);
            HttpCommunication hc = new HttpCommunication(Config.GetServerURL(), Method.POST, URLPath.DATA_QR, qs, authqr);

            try
            {
                RestResult result = hc.SendRequest();

                // 서버 응답 코드가 200일 경우
                if (result.statusCode == HttpStatusCode.OK)
                {
                    ResponseData<string> rd = JsonConvert.DeserializeObject<ResponseData<string>>(result.jsonResult);

                    // 타임 스탬프 일치 / otp DB 저장 성공
                    if (rd.getTimestamp().Equals(currentTimestamp))
                    {
                        string rdResult = rd.getResult();
                        if (rdResult != null)
                            return rdResult;    // (타임스탬프 + id)의 SHA512 해시 값
                    }
                }
            }

            // 실패시
            catch { }

            return "FAIL";
        }

        private string ResultStandby(string user, string sid, string qrData)
        {
            long currentTimestamp = Util.GetCurrentTimestamp();

            // DTO 준비
            AuthQr authqr = new AuthQr(user, currentTimestamp, qrData);

            // 통신 세팅
            QueryString qs = new QueryString("timestamp", currentTimestamp);
            qs.AddQueryString("sid", sid);
            HttpCommunication hc = new HttpCommunication(Config.GetServerURL(), Method.POST, URLPath.AUTH_QR, qs, authqr);

            try
            {
                RestResult result = hc.SendRequest();

                // 서버 응답 코드가 200일 경우
                if (result.statusCode == HttpStatusCode.OK)
                {
                    ResponseData<string> rd = JsonConvert.DeserializeObject<ResponseData<string>>(result.jsonResult);

                    // 타임 스탬프 일치 / otp DB 저장 성공
                    if (rd.getTimestamp().Equals(currentTimestamp))
                    {
                        string rdResult = rd.getResult();
                        if (rdResult != null)
                            return rdResult;
                    }
                }
            }

            // 실패시
            catch { }

            return ReturnMessage.FAIL;
        }

        private void WaitForQRVerification()
        {
            string result = ResultStandby(this.user, this.sid, this.qrData);

            if (result == "true")
            {
                this.DialogResult = DialogResult.OK;
            }
            else
            {
                this.DialogResult = DialogResult.Cancel;
            }
            this.Close();
        }
    }
}
