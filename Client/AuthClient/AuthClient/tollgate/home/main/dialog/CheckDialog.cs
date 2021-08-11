using AuthClient.tollgate.rest;
using AuthClient.tollgate.usb.service;
using Newtonsoft.Json;
using System;
using System.Drawing;
using System.Windows.Forms;
using static AuthClient.tollgate.home.main.dialog.CheckEntry;

namespace AuthClient.tollgate.home.main.dialog
{
    [Flags]
    public enum CheckFlag : short
    {
        MOBILE = 1,
        FINGER = MOBILE << 1,
        FACE = FINGER << 1,
        USB = FACE << 1,
        PATTERN = USB << 1
    }

    public partial class CheckDialog : Form
    {
        private bool result = false;
        private int checkFactorCount = 0;

        public CheckDialog(CheckFlag checkFlag)
        {
            InitializeComponent();

            MaximizeBox = false;

            if ((checkFlag & CheckFlag.MOBILE) != 0)
            {
                panel_list.Controls.Add(new CheckEntry(CheckFlag.MOBILE));
                checkFactorCount++;
            }
            if ((checkFlag & CheckFlag.FINGER) != 0)
            {
                panel_list.Controls.Add(new CheckEntry(CheckFlag.FINGER));
                checkFactorCount++;
            }
            if ((checkFlag & CheckFlag.FACE) != 0)
            {
                panel_list.Controls.Add(new CheckEntry(CheckFlag.FACE));
                checkFactorCount++;
            }
            if ((checkFlag & CheckFlag.USB) != 0)
            {
                panel_list.Controls.Add(new CheckEntry(CheckFlag.USB));
                checkFactorCount++;
            }
            if ((checkFlag & CheckFlag.PATTERN) != 0)
            {
                panel_list.Controls.Add(new CheckEntry(CheckFlag.PATTERN));
                checkFactorCount++;
            }

            int y = 0;
            foreach (Control control in panel_list.Controls)
            {
                control.Location = new Point(0, y);
                y += 93;
            }

            int count = panel_list.Controls.Count - 1;
            y = 25;
            for (int i = 0; i < count; i++)
            {
                PictureBox line = new PictureBox();
                line.Margin = new Padding(0, 0, 0, 0);
                line.Size = new Size(1, 67);
                line.Image = Properties.Resources.checkLine;
                panel_list.Controls.Add(line);
                line.Location = new Point(6, y);
                line.BringToFront();
                y += 93;
            }
        }

        private void CheckDialog_Load(object sender, EventArgs e)
        {
            StartPosition = FormStartPosition.Manual;
            int x = Owner.Location.X + ((Owner.Width - Width) / 2);
            int y = Owner.Location.Y + ((Owner.Height - Height) / 2);
            Location = new Point(x, y);
        }

        private void CheckDialog_Shown(object sender, EventArgs e)
        {
            int passedCheckFactor = 0;

            // ---------- 현재 컨트롤의 체크 요소만큼 루프 ----------
            for (int count = 0; count < panel_list.Controls.Count; count++)
            {
                // 형 변환 검사
                if (panel_list.Controls[count] is CheckEntry)
                {
                    CheckEntry ce = (CheckEntry)panel_list.Controls[count];

                    // DOING 상태로 전환
                    ce.Stat = CheckStat.DOING;

                    // 체크 결과, 성공했을 경우 통과한 항목 1 카운트
                    if (CheckListHandler(ce.Flag))
                    {
                        ce.Stat = CheckStat.PASS;
                        passedCheckFactor++;
                    }
                    // 체크 결과, 실패했을 경우 반복문 break : 다음 검사 항목 검사하지 않음
                    else
                    {
                        ce.Stat = CheckStat.FAIL;
                        break;
                    }
                }
            }

            // ---------- 모든 체크 요소가 Pass 상태이면 On 버튼 활성화 ----------
            result = passedCheckFactor == checkFactorCount;
            img_button.Visible = true;
        }

        private bool CheckListHandler(CheckFlag cf)
        {
            string path = "";
            switch (cf)
            {
                // 모바일 기기 연동 체크
                case CheckFlag.MOBILE:
                    path = "android/";
                    break;

                // 등록된 USB가 있는지 체크
                case CheckFlag.USB:
                    USBService us = new USBService();
                    path = "usb/";
                    break;
                // 모바일 기기 연동 체크, 패턴 인증의 Init Factor 체크
                case CheckFlag.PATTERN:
                    path = "pattern/";
                    break;

                // 모바일 기기 연동 체크, 지문 인증의 Init Factor 체크
                case CheckFlag.FINGER:
                    path = "finger_print/";
                    break;

                // 모바일 기기 연동 체크, 안면 인증의 Init Factor 체크
                case CheckFlag.FACE:
                    path = "face/";
                    break;

                default:
                    return false;
            }

            QueryString qs = new QueryString("timestamp", Util.GetCurrentTimestamp());
            RestResult result = new HttpCommunication(Method.GET, URLPath.CONF_INIT + path + Config.GetCurrentUser(), qs).SendRequest();
            if (result.statusCode != System.Net.HttpStatusCode.OK)
            {
                MessageBox.Show("알 수 없는 오류가 발생했습니다.");
                return false;
            }
            ResponseData<bool> data = JsonConvert.DeserializeObject<ResponseData<bool>>(result.jsonResult);
            return data.getResult();
        }

        private void img_button_Click(object sender, EventArgs e)
        {
            DialogResult = result ? DialogResult.OK : DialogResult.No;
        }
    }
}