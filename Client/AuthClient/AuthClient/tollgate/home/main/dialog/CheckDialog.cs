using AuthClient.tollgate.usb.dto;
using AuthClient.tollgate.usb.service;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
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
                if(panel_list.Controls[count] is CheckEntry)
                {
                    CheckEntry ce = (CheckEntry)panel_list.Controls[count];

                    // DOING 상태로 전환
                    ce.Stat = CheckStat.DOING;
                    
                    // 체크 결과, 성공했을 경우 통과한 항목 1 카운트
                    if(CheckListHandler(ce.Flag))
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
            if (passedCheckFactor == checkFactorCount)
            {
                // TODO: Auth Factor 업데이트
                MessageBox.Show("모든 항목을 통과했습니다. 해당 인증 요소를 사용할 수 있습니다.");
                this.DialogResult = DialogResult.OK;
            }
            else
            {
                MessageBox.Show("모든 항목을 통과하지 못했습니다. 해당 인증 요소를 사용할 수 없습니다.");
                this.DialogResult = DialogResult.Cancel;
            }
        }

        private bool CheckListHandler(CheckFlag cf)
        {
            switch(cf)
            {
                // 모바일 기기 연동 체크
                case CheckFlag.MOBILE:
                    return false;
                    break;

                // 등록된 USB가 있는지 체크
                case CheckFlag.USB:
                    USBService us = new USBService();
                    List<USBInfo> registeredUSBList = us.GetRegisteredUSBList(Config.GetCurrentUser());
                    
                    if(registeredUSBList.Count > 0)
                    {
                        return true;
                    } 
                    else
                    {
                        return false;
                    }
                    

                // 모바일 기기 연동 체크, 패턴 인증의 Init Factor 체크
                case CheckFlag.PATTERN:
                    return false;
                    break;

                // 모바일 기기 연동 체크, 지문 인증의 Init Factor 체크
                case CheckFlag.FINGER:
                    return false;
                    break;

                // 모바일 기기 연동 체크, 안면 인증의 Init Factor 체크
                case CheckFlag.FACE:
                    return false;
                    break;

                default:
                    return false;
            }
        }
    }
}