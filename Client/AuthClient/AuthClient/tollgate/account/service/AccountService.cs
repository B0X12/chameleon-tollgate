using AuthClient.tollgate.account.dto;
using AuthClient.tollgate.rest;
using Newtonsoft.Json;
using System;
using System.IO;
using System.Net;
using System.Windows.Forms;

namespace AuthClient.tollgate.account.service
{
    class AccountService
    {
        public bool IsServerAlive()
        {
            // 통신 세팅
            long currentTimestamp = Util.GetCurrentTimestamp();
            QueryString qs = new QueryString("timestamp", currentTimestamp);
            HttpCommunication hc = new HttpCommunication(Method.GET, URLPath.SERVER_HELLO, qs);

            try
            {
                RestResult result = hc.SendRequest();

                // 서버 응답 코드가 200일 경우
                if (result.statusCode == HttpStatusCode.OK)
                {
                    ResponseData<string> rd = JsonConvert.DeserializeObject<ResponseData<string>>(result.jsonResult);

                    // 타임 스탬프 체크 및 응답 문자 확인 - Hello
                    if (rd.getTimestamp().Equals(currentTimestamp) && rd.getResult().Equals("Hello"))
                    {
                        return true;
                    }
                }
            }
            // 존재하지 않는 서버로 연결 시도
            catch (WebException)
            {
                throw new WebException();
            }
            catch (UriFormatException)
            {
                throw new UriFormatException();
            }
            
            // 서버 응답 코드가 200이 아닐 경우 / 타임 스탬프 mismatch
            return false;
        }

        public bool IsLoginSuccess(string id, string pwd)
        {
            // DTO 준비
            string hashedPwd = Util.EncryptSHA512(pwd);
            Account ua = new Account(id, hashedPwd);

            // 통신 세팅
            long currentTimestamp = Util.GetCurrentTimestamp();
            QueryString qs = new QueryString("timestamp", currentTimestamp);
            HttpCommunication hc = new HttpCommunication(Method.POST, URLPath.LOGIN, qs, ua);

            RestResult result = hc.SendRequest();

            // 서버 응답 코드가 200일 경우
            if (result.statusCode == HttpStatusCode.OK)
            {
                ResponseData<bool> rd = JsonConvert.DeserializeObject<ResponseData<bool>>(result.jsonResult);

                // 타임 스탬프 일치 / 로그인 결과 성공
                if (rd.getTimestamp().Equals(currentTimestamp) && (rd.getResult() == true))
                {
                    return true;
                }
                // 타임 스탬프 일치 / 로그인 결과 실패
                else
                {
                    return false;
                }
            }
            // 서버 응답 코드가 200이 아닐 경우
            else
            {
                throw new WebException("인증 서버로 요청하는 중 오류가 발생하였습니다");
            }
        }

        public bool IsSignupSuccess(string id, string pwd)
        {
            // DTO 준비
            string hashedPwd = Util.EncryptSHA512(pwd);
            Account ac = new Account(id, hashedPwd);

            // 통신 세팅
            long currentTimestamp = Util.GetCurrentTimestamp();
            QueryString qs = new QueryString("timestamp", currentTimestamp);
            HttpCommunication hc = new HttpCommunication(Method.POST, URLPath.SIGNUP, qs, ac);

            try
            {
                RestResult result = hc.SendRequest();

                // 서버 응답 코드가 200일 경우
                if (result.statusCode == HttpStatusCode.OK)
                {
                    ResponseData<bool> rd = JsonConvert.DeserializeObject<ResponseData<bool>>(result.jsonResult);

                    // 타임 스탬프 일치 / 회원 가입 결과 성공
                    if (rd.getTimestamp().Equals(currentTimestamp) && (rd.getResult() == true))
                    {
                        return true;
                    }
                    // 회원 가입 결과 실패
                    else
                    {
                        return false;
                    }
                }
            }

            // 기타 데이터베이스 관련 오류
            catch (WebException we)
            {
                WebResponse resp = we.Response;
                StreamReader reader = new StreamReader(resp.GetResponseStream());
                string jsonErrorStr = reader.ReadToEnd();
                ErrorData ed = JsonConvert.DeserializeObject<ErrorData>(jsonErrorStr);
                MessageBox.Show(ed.getMessage());

                throw we;
            }

            return false;
        }

        public string GetRegisteredUserIDBySID()
        {
            // 통신 준비
            string sid = Util.GetSystemUniqueIdentifier();          // PATH 세팅
            long currentTimestamp = Util.GetCurrentTimestamp();
            QueryString qs = new QueryString("timestamp", currentTimestamp);
            HttpCommunication hc = new HttpCommunication(Method.GET, URLPath.MAP_PC + sid, qs);

            RestResult result = hc.SendRequest();

            // 서버 응답 코드가 200일 경우
            if (result.statusCode == HttpStatusCode.OK)
            {
                ResponseData<string> rd = JsonConvert.DeserializeObject<ResponseData<string>>(result.jsonResult);

                string userID = rd.getResult();

                // 타임 스탬프 일치 / 해당 컴퓨터와 연동된 컴퓨터(UID) 존재함
                if (rd.getTimestamp().Equals(currentTimestamp) && (!userID.Equals("")))
                {
                    return userID;
                }
            }

            return "";
        }

        public bool MappingSIDWithUser(string user)
        {
            // DTO 준비
            string sid = Util.GetSystemUniqueIdentifier();
            string alias = Util.GetSystemName();
            MapPC mapPC = new MapPC(user, sid, alias);

            // 통신 세팅
            long currentTimestamp = Util.GetCurrentTimestamp();
            QueryString qs = new QueryString("timestamp", currentTimestamp);
            HttpCommunication hc = new HttpCommunication(Method.POST, URLPath.MAP_PC, qs, mapPC);

            try
            {
                RestResult result = hc.SendRequest();

                // 서버 응답 코드가 200일 경우
                if (result.statusCode == HttpStatusCode.OK)
                {
                    ResponseData<bool> rd = JsonConvert.DeserializeObject<ResponseData<bool>>(result.jsonResult);

                    // 타임 스탬프 일치 / 회원 가입 결과 성공
                    if (rd.getTimestamp().Equals(currentTimestamp) && (rd.getResult() == true))
                    {
                        return true;
                    }
                    // 회원 가입 결과 실패
                    else
                    {
                        return false;
                    }
                }
                // 서버 응답 코드가 200이 아닐 경우
                else
                {
                    throw new WebException("현재 사용자와 컴퓨터를 연동하는 데 실패했습니다");
                }
            }

            // 기타 데이터베이스 관련 오류
            catch (WebException we)
            {
                throw we;
            }
        }

        public bool UnmapSIDWithUser(string user)
        {
            // 통신 준비
            string sid = Util.GetSystemUniqueIdentifier();          // PATH 세팅

            long currentTimestamp = Util.GetCurrentTimestamp();
            QueryString qs = new QueryString("timestamp", currentTimestamp);
            HttpCommunication hc = new HttpCommunication(Method.DELETE, URLPath.MAP_PC + sid, qs);

            RestResult result = hc.SendRequest();

            // 서버 응답 코드가 200일 경우
            if (result.statusCode == HttpStatusCode.OK)
            {
                return true;
            }

            return false;
        }
    }
}
