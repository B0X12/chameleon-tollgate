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
         *   --get-auth-factor	    --get-auth-factor [사용자ID]
		 *	                        [사용자ID]의 인증 요소(플래그 변수)를 가져옴
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
            /*
            Console.WriteLine("---------- Argument Count ----------");
            Console.WriteLine(args.Length);

            Console.WriteLine("---------- Argument List ----------");
            for (int i = 0; i < args.Length; i++)
            {
                Console.WriteLine(args[i]);
            }
            */
            /*
            AuthCommunication ac = new AuthCommunication();
            ac.IsServerAlive();
            */
            
            string option = args[0];
            AuthCommunication ac = new AuthCommunication();

            switch (option)
            {
                case "--is-server-alive":
                    if(ac.IsServerAlive())
                    {
                        return (int)ReturnCode.STATUS_OK; 
                    }
                    else
                    {
                        return (int)ReturnCode.STATUS_FAILED;
                    }

                case "--get-auth-factor":
                    break;

                case "--verify-usb":
                    try
                    {
                        if (ac.VerifyUSB(args[1], args[2]))
                        {
                            return (int)ReturnCode.STATUS_OK;
                        }
                        else
                        {
                            return (int)ReturnCode.STATUS_FAILED;
                        }
                    } 
                    catch(IndexOutOfRangeException e)
                    {
                        Console.WriteLine("IndexOutOfRangeException");
                        return (int)ReturnCode.STATUS_FAILED;
                    }

                case "--request-pattern":
                    try
                    {
                        if (ac.VerifyPattern(args[1]))
                        {
                            return (int)ReturnCode.STATUS_OK;
                        }
                        else
                        {
                            return (int)ReturnCode.STATUS_FAILED;
                        }
                    }
                    catch (IndexOutOfRangeException e)
                    {
                        Console.WriteLine("IndexOutOfRangeException");
                        return (int)ReturnCode.STATUS_FAILED;
                    }
                    
                case "--request-otp":
                    break;

                case "--verify-otp":
                    break;



                default:
                    break;
            }
            
            return (int)ReturnCode.STATUS_FAILED;
        }

        enum ReturnCode {
            STATUS_FAILED = 4444,
            STATUS_OK = 200,
        }
    }
}
