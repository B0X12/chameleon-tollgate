using Newtonsoft.Json;
using RestClient.dto;
using RestClient.rest;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace RestClient
{
    class Handler
    {
        private void SetRestClientMessage(string message)
        {
            Console.WriteLine(message);
        }

        internal ReturnCode IsServerAlive()
        {
            // 통신 세팅
            long currentTimestamp = Util.GetCurrentTimestamp();
            QueryString qs = new QueryString("timestamp", currentTimestamp);
            HttpCommunication hc = new HttpCommunication(Config.GetServerURL(), Method.GET, URLPath.SERVER_HELLO, qs);

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
                        // 응답 문자 확인 - Hello
                        if (rd.getResult().Equals("Hello"))
                        {
                            SetRestClientMessage(rd.getResult());
                            return ReturnCode.RESULT_CONNECTION_SUCCESS;
                        }
                    }
                    // 타임 스탬프 불일치
                    else
                    {
                        return ReturnCode.RESULT_TIMESTAMP_MISMATCH;
                    }
                }
                // 서버 응답 코드가 200이 아닐 경우
                else
                {
                    return ReturnCode.RESULT_UNAUTHORIZED_ACCESS;
                }

                return ReturnCode.RESULT_UNKNOWN_ERROR;
            }

            // 존재하지 않는 서버로 연결 시도
            catch (WebException)
            {
                return ReturnCode.RESULT_CONNECTION_FAILED;
            }
        }

        internal ReturnCode GetUser(string sid)
        {
            // 통신 세팅
            long currentTimestamp = Util.GetCurrentTimestamp();
            QueryString qs = new QueryString("timestamp", currentTimestamp);
            HttpCommunication hc = new HttpCommunication(Config.GetServerURL(), Method.GET, URLPath.ACCOUNT_USER + sid, qs);

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
                        SetRestClientMessage(rd.getResult());
                        return ReturnCode.RESULT_CONNECTION_SUCCESS;
                    }
                    // 타임 스탬프 불일치
                    else
                    {
                        return ReturnCode.RESULT_TIMESTAMP_MISMATCH;
                    }
                }

                // 서버 응답 코드가 400(Bad Request)일 경우
                else if (result.statusCode == HttpStatusCode.BadRequest)
                {
                    return ReturnCode.RESULT_UNAUTHORIZED_ACCESS;
                }

                // 기타 서버 응답 코드 처리
                else
                {
                    return ReturnCode.RESULT_UNKNOWN_ERROR;
                }
            }
            // 존재하지 않는 서버로 연결 시도
            catch (WebException)
            {
                return ReturnCode.RESULT_CONNECTION_FAILED;
            }
        }

        internal ReturnCode GetAuthFactor(string user)
        {
            // 통신 세팅
            long currentTimestamp = Util.GetCurrentTimestamp();
            QueryString qs = new QueryString("timestamp", currentTimestamp);
            HttpCommunication hc = new HttpCommunication(Config.GetServerURL(), Method.GET, URLPath.ACCOUNT_FACTOR + user, qs);

            try
            {
                RestResult result = hc.SendRequest();

                // 서버 응답 코드가 200일 경우
                if (result.statusCode == HttpStatusCode.OK)
                {
                    ResponseData<int> rd = JsonConvert.DeserializeObject<ResponseData<int>>(result.jsonResult);

                    // 타임 스탬프 일치
                    if (rd.getTimestamp().Equals(currentTimestamp))
                    {
                        SetRestClientMessage(rd.getResult().ToString());
                        return ReturnCode.RESULT_CONNECTION_SUCCESS;
                    }
                    // 타임 스탬프 불일치
                    else
                    {
                        return ReturnCode.RESULT_TIMESTAMP_MISMATCH;
                    }
                }

                // 서버 응답 코드가 400(Bad Request)일 경우
                else if (result.statusCode == HttpStatusCode.BadRequest)
                {
                    return ReturnCode.RESULT_UNAUTHORIZED_ACCESS;
                }

                // 기타 서버 응답 코드 처리
                else
                {
                    return ReturnCode.RESULT_UNKNOWN_ERROR;
                }
            }
            // 존재하지 않는 서버로 연결 시도
            catch (WebException)
            {
                return ReturnCode.RESULT_CONNECTION_FAILED;
            }
        }

        internal ReturnCode VerifyUSB(string user, string sid, string usb_info)
        {
            // 통신 세팅
            long currentTimestamp = Util.GetCurrentTimestamp();
            QueryString qs = new QueryString("timestamp", currentTimestamp);
            qs.AddQueryString("sid", sid);
            string usbHash = Util.EncryptSHA512(usb_info);
            HttpCommunication hc = new HttpCommunication(Config.GetServerURL(), Method.GET, URLPath.VERIFY_USB + user + "/" + usbHash, qs);

            try
            {
                RestResult result = hc.SendRequest();

                // 서버 응답 코드가 200일 경우
                if (result.statusCode == HttpStatusCode.OK)
                {
                    ResponseData<bool> rd = JsonConvert.DeserializeObject<ResponseData<bool>>(result.jsonResult);

                    // 타임 스탬프 일치
                    if (rd.getTimestamp().Equals(currentTimestamp))
                    {
                        // USB 인증 여부 확인 - 성공
                        if (rd.getResult() == true)
                        {
                            SetRestClientMessage("Verified");
                            return ReturnCode.RESULT_CONNECTION_SUCCESS;
                        }
                        // USB 인증 여부 확인 - 실패
                        else
                        {
                            SetRestClientMessage("Denied");
                            return ReturnCode.RESULT_CONNECTION_SUCCESS;
                        }
                    }
                    // 타임 스탬프 불일치
                    else
                    {
                        return ReturnCode.RESULT_TIMESTAMP_MISMATCH;
                    }
                }

                // 서버 응답 코드가 400(Bad Request)일 경우
                else if (result.statusCode == HttpStatusCode.BadRequest)
                {
                    return ReturnCode.RESULT_UNAUTHORIZED_ACCESS;
                }

                // 기타 서버 응답 코드 처리
                else
                {
                    return ReturnCode.RESULT_UNKNOWN_ERROR;
                }
            }

            // 존재하지 않는 서버로 연결 시도
            catch (WebException)
            {
                return ReturnCode.RESULT_CONNECTION_FAILED;
            }
        }

        internal ReturnCode RequestPattern(string user, string sid)
        {
            // 통신 세팅
            long currentTimestamp = Util.GetCurrentTimestamp();
            QueryString qs = new QueryString("pc", sid);
            qs.AddQueryString("timestamp", currentTimestamp);
            HttpCommunication hc = new HttpCommunication(Config.GetServerURL(), Method.GET, URLPath.REQUEST_PATTERN + user, qs);

            try
            {
                RestResult result = hc.SendRequest();

                // 서버 응답 코드가 200일 경우
                if (result.statusCode == HttpStatusCode.OK)
                {
                    ResponseData<bool> rd = JsonConvert.DeserializeObject<ResponseData<bool>>(result.jsonResult);

                    // 타임 스탬프 일치
                    if (rd.getTimestamp().Equals(currentTimestamp))
                    {
                        // USB 인증 여부 확인 - 성공
                        if (rd.getResult() == true)
                        {
                            SetRestClientMessage("Verified");
                            return ReturnCode.RESULT_CONNECTION_SUCCESS;
                        }
                        // USB 인증 여부 확인 - 실패
                        else
                        {
                            SetRestClientMessage("Denied");
                            return ReturnCode.RESULT_CONNECTION_SUCCESS;
                        }
                    }
                    // 타임 스탬프 불일치
                    else
                    {
                        return ReturnCode.RESULT_TIMESTAMP_MISMATCH;
                    }
                }
                
                // 서버 타임아웃
                else if(result.statusCode == HttpStatusCode.PartialContent)
                {
                    return ReturnCode.RESULT_CONNECTION_TIMEOUT;
                }

                // 서버 응답 코드가 400(Bad Request)일 경우
                else if (result.statusCode == HttpStatusCode.BadRequest)
                {
                    return ReturnCode.RESULT_UNAUTHORIZED_ACCESS;
                }

                // 기타 서버 응답 코드 처리
                else
                {
                    return ReturnCode.RESULT_UNKNOWN_ERROR;
                }
            }

            // 존재하지 않는 서버로 연결 시도
            catch (WebException)
            {
                return ReturnCode.RESULT_CONNECTION_FAILED;
            }
        }

        internal ReturnCode RequestFace(string user, string sid)
        {
            // 통신 세팅
            long currentTimestamp = Util.GetCurrentTimestamp();
            QueryString qs = new QueryString("timestamp", currentTimestamp);
            qs.AddQueryString("sid", sid);
            HttpCommunication hc = new HttpCommunication(Config.GetServerURL(), Method.GET, URLPath.REQUEST_FACE + user, qs);

            try
            {
                RestResult result = hc.SendRequest();

                // 서버 응답 코드가 200일 경우
                if (result.statusCode == HttpStatusCode.OK)
                {
                    ResponseData<bool> rd = JsonConvert.DeserializeObject<ResponseData<bool>>(result.jsonResult);

                    // 타임 스탬프 일치
                    if (rd.getTimestamp().Equals(currentTimestamp))
                    {
                        // 안면 인증 여부 확인 - 성공
                        if (rd.getResult() == true)
                        {
                            SetRestClientMessage("Verified");
                            return ReturnCode.RESULT_CONNECTION_SUCCESS;
                        }
                        // 안면 인증 여부 확인 - 실패
                        else
                        {
                            SetRestClientMessage("Denied");
                            return ReturnCode.RESULT_CONNECTION_SUCCESS;
                        }
                    }
                    // 타임 스탬프 불일치
                    else
                    {
                        return ReturnCode.RESULT_TIMESTAMP_MISMATCH;
                    }
                }

                // 데이터베이스에 패턴 없음
                else if(result.statusCode == HttpStatusCode.NotFound)
                {
                    return ReturnCode.RESULT_UNKNOWN_ERROR;
                }

                // 서버 응답 코드가 400(Bad Request)일 경우
                else if (result.statusCode == HttpStatusCode.BadRequest)
                {
                    return ReturnCode.RESULT_UNAUTHORIZED_ACCESS;
                }

                // 서버 타임아웃
                else if (result.statusCode == HttpStatusCode.PartialContent)
                {
                    return ReturnCode.RESULT_CONNECTION_TIMEOUT;
                }

                // 기타 서버 응답 코드 처리
                else
                {
                    return ReturnCode.RESULT_UNKNOWN_ERROR;
                }
            }

            // 존재하지 않는 서버로 연결 시도
            catch (WebException)
            {
                return ReturnCode.RESULT_CONNECTION_FAILED;
            }
        }

        internal ReturnCode VerifyOTP(string user, string sid, string otp)
        {
            // 통신 TimeStamp
            long currentTimestamp = Util.GetCurrentTimestamp();

            // DTO 준비
            AuthOtp authotp = new AuthOtp(user, currentTimestamp, otp);

            // 통신 세팅
            QueryString qs = new QueryString("timestamp", currentTimestamp);
            HttpCommunication hc = new HttpCommunication(Config.GetServerURL(), Method.POST, URLPath.VERIFY_OTP, qs, authotp);

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
                                    SetRestClientMessage("Verified");
                                    return ReturnCode.RESULT_CONNECTION_SUCCESS;
                                default:
                                    SetRestClientMessage("Denied");
                                    return ReturnCode.RESULT_CONNECTION_SUCCESS;
                            }
                        }
                    }
                }
            }

            // 실패시
            catch (WebException)
            {
                return ReturnCode.RESULT_UNKNOWN_ERROR;
            }

            return ReturnCode.RESULT_UNKNOWN_ERROR;
        }

        internal ReturnCode RequestFingerprint(string user, string sid)
        {
            // 통신 세팅
            long currentTimestamp = Util.GetCurrentTimestamp();
            QueryString qs = new QueryString("timestamp", currentTimestamp);
            HttpCommunication hc = new HttpCommunication(Config.GetServerURL(), Method.GET, URLPath.REQUEST_FINGERPRINT + user, qs);

            try
            {
                RestResult result = hc.SendRequest();

                // 서버 응답 코드가 200일 경우
                if (result.statusCode == HttpStatusCode.OK)
                {
                    ResponseData<bool> rd = JsonConvert.DeserializeObject<ResponseData<bool>>(result.jsonResult);

                    // 타임 스탬프 일치
                    if (rd.getTimestamp().Equals(currentTimestamp))
                    {
                        // USB 인증 여부 확인 - 성공
                        if (rd.getResult() == true)
                        {
                            SetRestClientMessage("Verified");
                            return ReturnCode.RESULT_CONNECTION_SUCCESS;
                        }
                        // USB 인증 여부 확인 - 실패
                        else
                        {
                            SetRestClientMessage("Denied");
                            return ReturnCode.RESULT_CONNECTION_SUCCESS;
                        }
                    }
                    // 타임 스탬프 불일치
                    else
                    {
                        return ReturnCode.RESULT_TIMESTAMP_MISMATCH;
                    }
                }

                // 서버 타임아웃
                else if (result.statusCode == HttpStatusCode.PartialContent)
                {
                    return ReturnCode.RESULT_CONNECTION_TIMEOUT;
                }

                // 서버 응답 코드가 400(Bad Request)일 경우
                else if (result.statusCode == HttpStatusCode.BadRequest)
                {
                    return ReturnCode.RESULT_UNAUTHORIZED_ACCESS;
                }

                // 기타 서버 응답 코드 처리
                else
                {
                    return ReturnCode.RESULT_UNKNOWN_ERROR;
                }
            }

            // 존재하지 않는 서버로 연결 시도
            catch (WebException)
            {
                return ReturnCode.RESULT_CONNECTION_FAILED;
            }
        }

        internal ReturnCode RequestAndVerifyQR(string user, string sid)
        {
            QRForm qr = new QRForm(user, sid);
            qr.ShowDialog();

            switch(qr.DialogResult)
            {
                case DialogResult.OK:
                    SetRestClientMessage("Verified");
                    return ReturnCode.RESULT_CONNECTION_SUCCESS;

                case DialogResult.Cancel:
                    return ReturnCode.RESULT_CONNECTION_TIMEOUT;

                case DialogResult.Abort:
                    return ReturnCode.RESULT_CONNECTION_FAILED;

                default:
                    return ReturnCode.RESULT_UNKNOWN_ERROR;
            }
        }
    }
}
