using AuthClient.tollgate.account.dto;
using AuthClient.tollgate.account.service;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AuthClient.tollgate.home.setting
{
    public partial class SettingPC : UserControl
    {
        

        public SettingPC()
        {
            InitializeComponent();
            panel_list.Width = panel_list.Parent.Width + SystemInformation.VerticalScrollBarWidth + 23;
            panel_list.Height = panel_list.Parent.Height + 29;
        }

        private void SettingPC_VisibleChanged(object sender, EventArgs e)
        {
            if (!Visible)
            {    
                return;
            }

            panel_list.Controls.Clear();

            AccountService service = new AccountService();
            List<MapPC> mapPCList = service.GetRegisteredPCList(Config.GetCurrentUser());

            foreach (MapPC mp in mapPCList)
            {
                PCEntry temp = new PCEntry();
                temp.MapPCInfo = mp;
                temp.TextChange += PCEntry_TextChange;
                
                if(mp.pc == Util.GetSystemUniqueIdentifier())
                {
                    temp.MainPC = true;
                }

                panel_list.Controls.Add(temp);
            }
        }

        private void PCEntry_TextChange(PCEntry sender, string beforeText, string afterText)
        {
            MapPC mp = sender.MapPCInfo;
            mp.alias = afterText;

            AccountService service = new AccountService();
            if (service.UpdatePCAlias(mp))
            {
                MessageBox.Show("별명이 성공적으로 변경되었습니다");

                // PC Entry 항목의 별명 변경
                sender.MapPCInfo = mp;
            }
            else
            {
                MessageBox.Show("변경에 실패하였습니다");
            }
        }
    }
}
