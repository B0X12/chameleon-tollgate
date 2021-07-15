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
         *
         *   --get-auth-factor	    --get-auth-factor [UID]
		 *	                        [UID]와 연동된 사용자의 인증 요소(플래그 변수)를 가져옴
		 *	                        해당 인증 요소를 읽어 인증 윈도우 클래스 준비
		 *	                        반환 값: 0 이상의 양수, 0일 경우 에러
         *
         *
         *   --verify-usb		    --verify-usb [사용자ID] [USB식별값]
		 *	                        USB 인증 옵션
		 *	                        반환 값: true / false
         *
         *
         *   --request-pattern      --request-pattern [사용자ID]
         *                          서버에게 패턴 인증 요청
         *                          반환 값: true / false
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
                /*
                case "--is-server-alive":
                    return (int)handler.IsServerAlive();
                */  
                case "--get-auth-factor":
                    {
                        string uid = args[1];
                        return (int)handler.GetAuthFactor(uid);
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

                        return (int)handler.VerifyPattern(user);
                        
                    }
                    

                case "--request-otp":
                    break;

                case "--verify-otp":
                    break;



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

                case "--get-auth-factor":
                    // 포맷: --get-auth-factor [SID]
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

                case "--request-otp":
                    break;

                case "--verify-otp":
                    break;
            }

            return false;
        }
    }
}
