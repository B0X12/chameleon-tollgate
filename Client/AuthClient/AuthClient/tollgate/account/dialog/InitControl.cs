using AuthClient.tollgate.account.service;
using System;
using System.Net;
using System.Windows.Forms;

namespace AuthClient.tollgate.account.dialog
{
    public partial class InitControl : UserControl
    {
        private const int FLOW_SIZE = 531;
        private const int FLOW_INTERVAL = 17;

        /// <summary>
        /// Current step number.
        /// </summary>
        /// <remarks> Values from 0 to 2.</remarks>
        private int step = 0;
        private InitServerControl initServerControl;
        private LoginControl loginCon;
        private SignupControl signupControl;

        public delegate void LoginEvent();
        public event LoginEvent Login;

        public InitControl()
        {
            InitializeComponent();
        }

        private void InitControl_Load(object sender, EventArgs e)
        {
            //panel_flow.Width = panel_flow.Parent.Width + SystemInformation.VerticalScrollBarWidth;
            panel_flow.Height = panel_flow.Parent.Height + SystemInformation.HorizontalScrollBarHeight;

            initServerControl = new InitServerControl();
            initServerControl.ConnectButtonClick += InitServerControl_ConnectButtonClick;

            loginCon = new LoginControl();
            loginCon.LoginButtonClick += LoginCon_LoginButtonClick;
            loginCon.SignupButtonClick += LoginCon_SignupButtonClick;

            signupControl = new SignupControl();
            signupControl.SignupButtonClick += SignupControl_SignupButtonClick;


            // --------------- 프로그램 초기 설정 세팅: 인증 서버 URI, 이 PC와 연동된 사용자 ID ---------------
            string user = "";

            try
            {
                //  C:\\Tollgate\\tollgate.cfg 파일을 읽어 기본 서버 주소(URL) 초기화
                //  초기화 성공 시 true, 초기화 실패 시 false 
                if (Config.InitAuthServerByConfigFile())
                {
                    //  Server의 데이터베이스에서 map_pc 테이블을 검사하여
                    //  해당 PC와 연동된 유저의 ID 받아 옴
                    AccountService ac = new AccountService();        
                    user = ac.GetRegisteredUserIDBySID();

                    // 이 PC와 연동된 사용자가 없음
                    if (user.Equals(""))
                    {
                        // 로그인 단계로 이동
                        step = 1;
                        view_step.setStep(step);
                        panel_flow.Controls.Add(loginCon);
                    }

                    // 이 PC와 연동된 사용자가 존재함
                    else
                    {
                        // 로그인 단계로 이동
                        step = 1;
                        view_step.setStep(step);
                        panel_flow.Controls.Add(loginCon);
                        
                        // ID 값 고정
                        loginCon.ID = user;
                        loginCon.FixID = false;
                        loginCon.registeredUserExist = true;
                    }
                }

                // 서버 IP 초기화 실패
                else
                {
                    // 인증 서버 설정 단계로 이동
                    panel_flow.Controls.Add(initServerControl);
                    step = 0;
                    view_step.setStep(step);
                }
            } 
            catch (WebException)
            {
                // 인증 서버 설정 단계로 이동
                panel_flow.Controls.Add(initServerControl);
                step = 0;
                view_step.setStep(step);

                Config.DeleteConfigFile();
                MessageBox.Show("인증 서버로부터 데이터를 불러올 수 없습니다");
            }
        }

        private void SignupControl_SignupButtonClick(object sender, EventArgs e)
        {
            // 아이디 필드 검사
            if (signupControl.ID.Length == 0)
            {
                MessageBox.Show("아이디를 입력해 주십시오");
                return;
            }

            // 패스워드 필드 검사
            if (signupControl.PWD.Length == 0)
            {
                MessageBox.Show("패스워드를 입력해 주십시오");
                return;
            }

            // 비밀번호 및 비밀번호 확인 검사
            if (!(signupControl.PWD.Equals(signupControl.Confirm)))
            {
                MessageBox.Show("비밀번호가 같지 않습니다");
                return;
            }

            // ----------- 회원 가입 절차 -----------
            AccountService ac = new AccountService();

            try
            {
                if (ac.IsSignupSuccess(signupControl.ID, signupControl.PWD))
                {
                    // 회원 가입에 성공할 경우 
                    ac.MappingSIDWithUser(signupControl.ID);

                    // ID 값 고정
                    loginCon.ID = signupControl.ID;
                    loginCon.FixID = false;
                    loginCon.registeredUserExist = true;

                    flowPrev();
                }
                else
                {
                    MessageBox.Show("이미 등록된 회원입니다");
                }
            }

            // 인증 서버에서 기타 오류 발생할 경우 처리
            catch (WebException)
            {
                Config.DeleteConfigFile();
                Application.Restart();
            }
        }

        private void LoginCon_SignupButtonClick(object sender, EventArgs e)
        {
            flowNext();
        }

        private void LoginCon_LoginButtonClick(object sender, EventArgs e)
        {
            // 아이디 필드 검사
            if (loginCon.ID.Length == 0)
            {
                MessageBox.Show("아이디를 입력해 주십시오");
                return;
            }

            // 패스워드 필드 검사
            if (loginCon.PWD.Length == 0)
            {
                MessageBox.Show("패스워드를 입력해 주십시오");
                return;
            }


            // 로그인 절차
            AccountService ac = new AccountService();

            try
            {
                if (ac.IsLoginSuccess(loginCon.ID, loginCon.PWD))
                {
                    Config.SetCurrentUser(loginCon.ID);

                    // 인증 서버에 등록된 계정이 없을 경우, 해당 계정과 컴퓨터를 인증 서버에 등록함
                    if (!loginCon.registeredUserExist)
                    {
                        ac.MappingSIDWithUser(loginCon.ID);
                    }

                    if (Login != null)
                        Login();
                } 
                else
                {
                    MessageBox.Show("아이디 또는 비밀번호가 일치하지 않습니다");
                }
            }

            // 인증 서버에서 기타 오류 발생할 경우 처리
            catch (WebException we)
            {
                MessageBox.Show(we.Message);
                Config.DeleteConfigFile();
                Application.Restart();
            }
        }

        private void InitServerControl_ConnectButtonClick(object sender, EventArgs e)
        {
            AccountService ac = new AccountService();
            Config.InitializeBaseURL(initServerControl.IP, true);

            // --------------- 서버가 살아 있는지 확인 ---------------
            try
            {
                if (ac.IsServerAlive())
                {
                    Config.CreateConfigFile(Config.GetBaseURL());

                    //  Server의 데이터베이스에서 map_pc 테이블을 검사하여
                    //  해당 PC와 연동된 유저의 ID 받아 옴
                    string user = ac.GetRegisteredUserIDBySID();

                    // 이 PC와 연동된 사용자가 존재함
                    if (!user.Equals(""))
                    {
                        loginCon.ID = user;
                        loginCon.FixID = false;
                        loginCon.registeredUserExist = true;
                    }

                    flowNext();
                }
                // 타임 스탬프 일치하지 않음 / Hello 문자열 미반환
                else
                {
                    MessageBox.Show("유효하지 않은 인증 서버입니다");
                }
            } 

            // 인증 서버와 통신 문제 발생
            catch (WebException)
            {
                MessageBox.Show("인증 서버와 연결할 수 없습니다");
            }

            // 올바르지 않은 URI 포맷
            catch (UriFormatException)
            {
                MessageBox.Show("인증 서버 주소 형식이 올바르지 않습니다");
            }
        }

        /// <summary>
        /// Slide over FlowLayoutPanel.
        /// </summary>
        /// <remarks>
        ///     Slide over to the next screen of <paramref name="step"/> and increase <paramref name="step"/> by 1.
        /// </remarks>
        private void flowNext()
        {
            if (step == 2)
                return;
            else if (step == 0)
            {
                panel_flow.Controls.Add(loginCon);
                panel_flow.Controls.SetChildIndex(loginCon, 1);
            }
            else if (step == 1)
            {
                panel_flow.Controls.Add(signupControl);
                panel_flow.Controls.SetChildIndex(signupControl, 1);
            }
            step++;

            for (int i = FLOW_INTERVAL; i < FLOW_SIZE; i += FLOW_INTERVAL)
            {
                panel_flow.HorizontalScroll.Value = i;
                panel_flow.Update();
            }

            panel_flow.Controls.RemoveAt(0);
            view_step.setStep(step);
        }

        /// <summary>
        /// Slide back FlowLayoutPanel.
        /// </summary>
        /// <remarks>
        ///     Slide back to the previous screen of <paramref name="step"/> and reduce <paramref name="step"/> by 1.
        /// </remarks>
        private void flowPrev()
        {
            if (step == 0)
                return;
            else if (step == 1)
            {
                panel_flow.Controls.Add(initServerControl);
                panel_flow.Controls.SetChildIndex(initServerControl, 0);
            }
            else if (step == 2)
            {
                panel_flow.Controls.Add(loginCon);
                panel_flow.Controls.SetChildIndex(loginCon, 0);
            }
            step--;

            for (int i = FLOW_SIZE - FLOW_INTERVAL; i > 0; i -= FLOW_INTERVAL)
            {
                try
                {
                    panel_flow.HorizontalScroll.Value = i;
                }
                catch (ArgumentOutOfRangeException)
                {
                    panel_flow.HorizontalScroll.Value = 0;
                }
                panel_flow.Update();
            }

            panel_flow.Controls.RemoveAt(1);
            view_step.setStep(step);
        }

        /// <summary>
        /// Wallpaper's click event fuction for test
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void picture_wallpaper_Click(object sender, EventArgs e)
        {
            flowPrev();
        }
    }
}