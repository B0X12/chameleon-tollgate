using AuthClient.tollgate.otp.dto;
using AuthClient.tollgate.rest;
using Newtonsoft.Json;
using System;
using System.IO;
using System.Net;
using System.Windows.Forms;

namespace AuthClient.tollgate.otp.service
{
    class OtpService
    {
        internal int PostOtpRegister(in string id)
        {
            // 통신 TimeStamp
            long currentTimestamp = Util.GetCurrentTimestamp();

            // DTO 준비
            AuthOtp authotp = new AuthOtp(id);

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
                        {
                            switch (rdResult)
                            {
                                case ReturnMessage.SUCCESS:
                                    Console.WriteLine("성공");
                                    return ReturnMessageValue.SUCCESS;
                                case ReturnMessage.REGISTER_UNKNOWN:
                                    Console.WriteLine("실패 - DATABASE");
                                    return ReturnMessageValue.REGISTER_DATABASE;
                                case ReturnMessage.REGISTER_INFORMATION:
                                    Console.WriteLine("실패 - INFORMATION");
                                    return ReturnMessageValue.REGISTER_INFORMATION;
                            }
                        }
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
            return ReturnMessageValue.FAIL;
        }

        internal int PostOtpVerify(in string id, in string InputOtpData)
        {
            // 통신 TimeStamp
            long currentTimestamp = Util.GetCurrentTimestamp();

            // DTO 준비
            AuthOtp authotp = new AuthOtp(id, 0, InputOtpData);

            // 통신 세팅
            QueryString qs = new QueryString("timestamp", currentTimestamp);
            HttpCommunication hc = new HttpCommunication(Method.POST, URLPath.AUTH_OTP, qs, authotp);

            try
            {
                RestResult result = hc.SendRequest();

                // 서버 응답 코드가 200일 경우
                if (result.statusCode == HttpStatusCode.OK)
                {
                    ResponseData<string> rd = JsonConvert.DeserializeObject<ResponseData<string>>(result.jsonResult);

                    // 타임 스탬프 일치
                    if (rd.getTimestamp().Equals(currentTimestamp))
                    {
                        string rdResult = rd.getResult();
                        if (rdResult != null)
                        {
                            switch (rdResult)
                            {
                                case ReturnMessage.SUCCESS:
                                    Console.WriteLine("성공");
                                    return ReturnMessageValue.SUCCESS;
                                case ReturnMessage.VERIFY_UNKNOWN:
                                    Console.WriteLine("실패 - DATABASE");
                                    return ReturnMessageValue.VERIFY_DATABASE;
                                case ReturnMessage.VERIFY_INFORMATION:
                                    Console.WriteLine("실패 - INFORMATION");
                                    return ReturnMessageValue.VERIFY_INFORMATION;
                                case ReturnMessage.VERIFY_TIMEOUT:
                                    Console.WriteLine("실패 - TIMEOUT");
                                    return ReturnMessageValue.VERIFY_TIMEOUT;
                                case ReturnMessage.VERIFY_FAIL:
                                    Console.WriteLine("실패 - FAIL : OTP Different");
                                    return ReturnMessageValue.VERIFY_FAIL;
                            }
                        }
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

            return ReturnMessageValue.FAIL;
        }

    }
}
