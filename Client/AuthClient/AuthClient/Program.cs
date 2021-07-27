using System;
using System.Net;
using System.Windows.Forms;
using AuthClient.tollgate;
using AuthClient.tollgate.account.dialog;
using AuthClient.tollgate.account.service;
using AuthClient.tollgate.otp.dialog;
using AuthClient.tollgate.util.tollgateLog;
using AuthClient.tollgate.util.tollgateLog.dto;
using AuthClient.tollgate.util.tollgateLog.dto.code;

namespace AuthClient
{
    static class Program
    {
        [STAThread]
        static void Main()
        {
            tollgateLog.setLogPath(@"C:\Tollgate\Logs");
            tollgateLog.i(LogFactor.FACE, FaceCode.TEST);

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new MainForm());

            

            //OTP Test
            //Application.Run(new OtpTest());
            //return;

            /*
             *      C:\\Tollgate\\tollgate.cfg 파일을 읽어 기본 서버 주소(URL) 초기화
             *      초기화 성공 시 true, 초기화 실패 시 false 
             */
            AuthClientStart:
            if (!Config.InitAuthServerByConfigFile())
            {
                CheckServerDialog dlg_cs = new CheckServerDialog();

                if (dlg_cs.ShowDialog() == DialogResult.Cancel)
                {
                    Environment.Exit(0);
                }
                dlg_cs.Close();
            }


            /*
             *      Server의 데이터베이스에서 map_pc 테이블을 검사하여
             *      해당 PC와 연동된 유저의 ID 받아 옴
             */
            AccountService accountService = new AccountService();
            string user = "";
            try
            {
                user = accountService.GetRegisteredUserIDBySID();
            }
            catch (WebException we)
            {
                Config.DeleteConfigFile();
                MessageBox.Show("인증 서버로부터 데이터를 불러올 수 없습니다");
                goto AuthClientStart;
            }
            

            // ---------- 유저 계정 정보가 존재할 경우 ----------
            if (!user.Equals(""))
            {
                LogOnDialog dlg_logon = new LogOnDialog(user);
                DialogResult result = dlg_logon.ShowDialog();

                // 로그온 성공
                if (result == DialogResult.OK)
                {
                    MainDialog dlg_main = new MainDialog();
                    dlg_main.ShowDialog();
                }
            }

            // ---------- 유저 계정 정보가 존재하지 않을 경우 ----------
            else
            {
                DialogResult result;
                
                LogOnSignUpDialog dlg_lgonsgup = new LogOnSignUpDialog();
                result = dlg_lgonsgup.ShowDialog();


                // ---------- 회원 가입 버튼 클릭 시 ----------
                if (result == DialogResult.No)
                {
                    SignUpDialog dlg_signup = new SignUpDialog();
                    result = dlg_signup.ShowDialog();
                    
                    // ---------- 회원 가입 안 함 ----------
                    if (result == DialogResult.Cancel)
                    {
                        Environment.Exit(0);
                    }

                    // ---------- 회원 가입한 계정으로 로그인 ----------
                    LogOnDialog dlg_logon = new LogOnDialog(Config.GetSignupUser());
                    result = dlg_logon.ShowDialog();
                }

                // ---------- 로그인 버튼 클릭 시 ----------
                else
                {
                    LogOnDialog dlg_logon = new LogOnDialog();
                    result = dlg_logon.ShowDialog();
                }

                
                // 로그온 성공
                if (result == DialogResult.OK)
                {
                    MainDialog dlg_main = new MainDialog();
                    dlg_main.ShowDialog();
                }
            }
        }
    }
}
