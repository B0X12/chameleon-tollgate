using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RestClient
{
    class Program
    {
        /*
         *   옵션			        설명
         *   -------------------------------------------------------------------
         *   --is-server-alive	    --is-server-alive
		 *	                        서버가 살아 있는지 체크
		 *	                        서버가 살아 있다면, Hello 문자열 반환
		 *	                        그 외의 경우 Denied 문자열 반환
         *
         *   --get-auth-factor	    --get-auth-factor [UID]
		 *	                        [UID]와 연동된 사용자의 인증 요소(플래그 변수)를 가져옴
		 *	                        해당 인증 요소를 읽어 인증 윈도우 클래스 준비
		 *	                        반환 값: 0 이상의 양수, 0일 경우 에러
         *
         *   --verify-usb		    --verify-usb [사용자ID] [USB식별값]
		 *	                        USB 인증 옵션
		 *	                        반환 문자열: Verified / Denied
         *
         *   --request-pattern      --request-pattern [사용자ID]
         *                          서버에게 패턴 인증 요청
         *                          반환 문자열: Verified / Denied
         *   
         *   --request-face         --request-face [사용자ID]
         *                          서버에게 안면 인증 요청
         *                          반환 문자열: Verified / Denied
         *                          
         *   
         *   --request-otp
         *   
         *   --verify-otp
         *   
         *   --request-fingerprint  
         *   
         *
         *
         */

        static int Main(string[] args)
        {
            // 인자 체크
            if(!CheckArgumentByOption(args))
            {
                return (int)ReturnCode.RESULT_UNKNOWN_ERROR;
            }

            // C:\Tollgate\server.cfg 파일을 읽어 인증 서버 정보를 세팅
            Config cfg = new Config();
            string baseURL = cfg.InitAuthServerByConfigFile();

            if(baseURL == "")
            {
                return (int)ReturnCode.RESULT_CONFIG_FILE_COMPROMISED;
            }
            

            // 옵션에 따라 동작 수행
            string option = args[0];
            Handler handler = new Handler(baseURL);

            switch (option)
            {
                
                case "--is-server-alive":
                    return (int)handler.IsServerAlive();

                case "--get-user":
                    {
                        string uid = args[1];
                        int retCode = (int)handler.GetUser(uid);
                        return retCode;
                    }

                case "--get-auth-factor":
                    {
                        string user = args[1];
                        int retCode = (int)handler.GetAuthFactor(user);
                        return retCode;
                    }
                    
                case "--verify-usb":
                    {
                        string user = args[1];
                        string usb_info = args[2];

                        return (int)handler.VerifyUSB(user, usb_info);
                    }
                    
                case "--request-pattern":
                    {
                        string user = args[1];

                        return (int)handler.RequestPattern(user);
                    }

                case "--request-face":
                    {
                        string user = args[1];

                        return (int)handler.RequestFace(user);
                    }

                case "--request-otp":
                    {
                        string user = args[1];

                        return (int)handler.RequestOTP(user);
                    }

                case "--verify-otp":
                    {
                        string user = args[1];
                        string otp = args[2];

                        return (int)handler.VerifyOTP(user, otp);
                    }

                case "--request-fingerprint":
                    {
                        string user = args[1];

                        return (int)handler.RequestFingerprint(user);
                    }

                default:
                    break;
            }

            return (int)ReturnCode.RESULT_UNKNOWN_ERROR;
        }

        static bool CheckArgumentByOption(string[] parameters)
        {
            // --------------- 옵션 검사 ---------------
            if (parameters.Length == 0)
            {
                return false;
            }

            // --------------- 옵션에 따른 옵션 인자 검사 ---------------
            string option = parameters[0];
            switch (option)
            {
                case "--is-server-alive":
                    // 포맷: --is-server-alive
                    if (parameters.Length == 1)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }

                case "--get-user":
                    // 포맷: --get-user [sid]
                    if (parameters.Length == 2)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }

                case "--get-auth-factor":
                    // 포맷: --get-auth-factor [user]
                    if (parameters.Length == 2)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }

                case "--verify-usb":
                    // 포맷: --verify-usb [사용자ID] [USB정보]
                    if (parameters.Length == 3)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }

                case "--request-pattern":
                    // 포맷: --request-pattern [사용자ID]
                    if (parameters.Length == 2)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }

                case "--request-face":
                    // 포맷: --request-face [사용자ID]
                    if (parameters.Length == 2)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }

                case "--request-otp":
                    // 포맷: --request-otp [사용자ID]
                    if (parameters.Length == 2)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }

                case "--verify-otp":
                    // 포맷: --verify-otp [사용자ID] [OTP입력값]
                    if (parameters.Length == 3)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }

                case "--request-fingerprint":
                    // 포맷: --request-fingerprint [사용자ID]
                    if (parameters.Length == 2)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
            }

            return false;
        }
    }
}
