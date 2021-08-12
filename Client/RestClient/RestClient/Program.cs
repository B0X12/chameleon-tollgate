using AuthClient.tollgate.util.tollgateLog;
using AuthClient.tollgate.util.tollgateLog.dto;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace RestClient
{
    class Program
    {
        static int Main(string[] args)
        {
            int retCode = (int)ReturnCode.RESULT_UNKNOWN_ERROR;

            // 인자 체크
            if(!CheckArgumentByOption(args))
            {
                retCode = (int)ReturnCode.RESULT_UNKNOWN_ERROR;
                return retCode;
            }

            // C:\Tollgate\server.cfg 파일을 읽어 인증 서버 정보를 세팅
            if(!Config.InitAuthServerByConfigFile())
            {
                retCode = (int)ReturnCode.RESULT_CONFIG_FILE_COMPROMISED;
                return retCode;
            }
            

            // 옵션에 따라 동작 수행
            string option = args[0];
            Handler handler = new Handler();

            switch (option)
            {
                case "--is-server-alive":
                    retCode = (int)handler.IsServerAlive();
                    break;

                case "--get-user":
                    {
                        string uid = args[1];
                        retCode = (int)handler.GetUser(uid);
                    }
                    break;

                case "--get-auth-factor":
                    {
                        string user = args[1];
                        retCode = (int)handler.GetAuthFactor(user);
                    }
                    break;
                    
                case "--verify-usb":
                    {
                        string user = args[1];
                        string sid = args[2];
                        string usb_info = args[3];

                        retCode = (int)handler.VerifyUSB(user, sid, usb_info);
                    }
                    break;
                    
                case "--request-pattern":
                    {
                        string user = args[1];
                        string sid = args[2];

                        retCode = (int)handler.RequestPattern(user, sid);
                    }
                    break;

                case "--request-face":
                    {
                        string user = args[1];
                        string sid = args[2];

                        retCode = (int)handler.RequestFace(user, sid);
                    }
                    break;

                case "--verify-otp":
                    {
                        string user = args[1];
                        string sid = args[2];
                        string otp = args[3];

                        retCode = (int)handler.VerifyOTP(user, sid, otp);
                    }
                    break;

                case "--request-fingerprint":
                    {
                        string user = args[1];
                        string sid = args[2];

                        retCode = (int)handler.RequestFingerprint(user, sid);
                    }
                    break;

                case "--issue-qrcode":
                    {
                        string user = args[1];
                        string sid = args[2];

                        retCode = (int)handler.RequestAndVerifyQR(user, sid);
                    }
                    break;

                default:
                    break;
            }

            Console.WriteLine(retCode);
            return retCode;
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
                    // 포맷: --verify-usb [사용자ID] [SID값] [USB정보]
                    if (parameters.Length == 4)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }

                case "--request-pattern":
                    // 포맷: --request-pattern [사용자ID] [SID값]
                    if (parameters.Length == 3)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }

                case "--request-face":
                    // 포맷: --request-face [사용자ID] [SID값]
                    if (parameters.Length == 3)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }

                case "--verify-otp":
                    // 포맷: --verify-otp [사용자ID] [SID값] [OTP입력값]
                    if (parameters.Length == 4)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }

                case "--request-fingerprint":
                    // 포맷: --request-fingerprint [사용자ID] [SID값]
                    if (parameters.Length == 3)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }

                case "--issue-qrcode":
                    // 포맷: --issue-qrcode [사용자ID] [SID값]
                    if (parameters.Length == 3)
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
