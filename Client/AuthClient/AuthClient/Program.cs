using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AuthClient
{
    static class Program
    {
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            bool bAuthServerExist = false;
            bool bUserAccountExist = false;

            /*
             *      TODO
             *      설정 파일/레지스트리 읽어 들여
             *      인증 서버 등록 여부 / 등록된 사용자 존재 여부 검사 수행하여
             *      위 bool 타입 플래그 변수 값을 true/false로 초기화 함
             */

            // ---------- 등록된 서버가 없을 경우 ----------
            if (!bAuthServerExist)
            {
                CheckServerDialog dlg_cs = new CheckServerDialog();

                if (dlg_cs.ShowDialog() == DialogResult.Cancel)
                {
                    Environment.Exit(0);
                }
                dlg_cs.Close();
            }

            // ---------- 유저 계정 정보가 존재할 경우 ----------
            if (bUserAccountExist)
            {
                LogOnDialog dlg_logon = new LogOnDialog();
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
                }

                LogOnDialog dlg_logon = new LogOnDialog();
                result = dlg_logon.ShowDialog();

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
