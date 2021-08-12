using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Net;
using System.IO;
using Newtonsoft.Json;

using AuthClient.tollgate.rest;
using AuthClient.tollgate.otp.dto;

namespace AuthClient.tollgate.otp.service
{
    class OtpService
    {
        internal static string PostOtpRegister(in string id)
        {
            // 통신 TimeStamp
            long currentTimestamp = Util.GetCurrentTimestamp();

            // DTO 준비
            AuthOtp authotp = new AuthOtp(id,currentTimestamp);

            // 통신 세팅
            QueryString qs = new QueryString("timestamp", currentTimestamp);
            HttpCommunication hc = new HttpCommunication(Method.POST, URLPath.REGISTER_OTP, qs, authotp);

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
            catch (WebException we)
            {
                WebResponse resp = we.Response;
                StreamReader reader = new StreamReader(resp.GetResponseStream());
                string jsonErrorStr = reader.ReadToEnd();
                ErrorData ed = JsonConvert.DeserializeObject<ErrorData>(jsonErrorStr);
                MessageBox.Show(ed.getMessage());
            }
            return ReturnMessage.FAIL;
        }

    }
}
