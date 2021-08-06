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
        }
    }
}
