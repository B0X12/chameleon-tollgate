using AuthClient.tollgate.rest;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Windows.Forms;
using static AuthClient.tollgate.define.Define;

namespace AuthClient.tollgate.home.history
{
    public partial class HistoryControl : UserControl
    {
        public HistoryControl()
        {
            InitializeComponent();
            panel_list.Width = panel_list.Parent.Width + SystemInformation.VerticalScrollBarWidth;
        }

        private void HistoryControl_VisibleChanged(object sender, EventArgs e)
        {
            if (!Visible)
                return;

            panel_list.Controls.Clear();
            HistoryPack historyPack = new HistoryPack(true, Util.GetCurrentTimestamp());
            RestResult result = new HttpCommunication(Method.POST, URLPath.HISTORY + Config.GetCurrentUser(), null, historyPack).SendRequest();
            if (result.statusCode != System.Net.HttpStatusCode.OK)
                return;
            ResponseData<List<HistoryRecord>> data = JsonConvert.DeserializeObject<ResponseData<List<HistoryRecord>>>(result.jsonResult);
            foreach (HistoryRecord record in data.getResult())
            {
                HistoryEntry entry = new HistoryEntry();
                entry.Date = record.timestamp.Replace('-', '/');
                entry.Pc = record.pc;
                entry.Factor = getFactor(record.factor);
                entry.Success = record.result == "1";
                panel_list.Controls.Add(entry);
            }
        }

        private Factor getFactor(string value)
        {
            if (value == "fingerprint")
                return Factor.FINGER;
            else if (value == "pattern")
                return Factor.PATTERN;
            else if (value == "otp")
                return Factor.OTP;
            else if (value == "face")
                return Factor.FACEID;
            else if (value == "usb")
                return Factor.USB;
            return Factor.QR;
        }
    }
}
