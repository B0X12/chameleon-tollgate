using AuthClient.tollgate;
using AuthClient.tollgate.util.tollgateLog;
using AuthClient.tollgate.util.tollgateLog.dto;
using AuthClient.tollgate.util.tollgateLog.dto.code;
using System;
using System.Windows.Forms;

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
