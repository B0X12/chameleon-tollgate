using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace RestClient
{
    public partial class QRForm : Form
    {
        private int timeElapseSecond = 0;
        private const int TIMEOUT_SECOND = 60;

        public QRForm()
        {
            InitializeComponent();

            // 창 위치 설정
            this.StartPosition = FormStartPosition.Manual;

            int desktopWidth = Screen.PrimaryScreen.Bounds.Width;
            int desktopHeight = Screen.PrimaryScreen.Bounds.Height;
            int windowWidth = this.Size.Width;
            int windowHeight = this.Size.Height;

            this.Location = new Point((desktopWidth - windowWidth) / 2, (desktopHeight - windowHeight) / 2 - 110);

            // 타이머 설정
            this.timer1.Interval = 1000;
            this.timer1.Enabled = true;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            ZXing.BarcodeWriter barcordWriter = new ZXing.BarcodeWriter();
            barcordWriter.Format = ZXing.BarcodeFormat.QR_CODE;

            barcordWriter.Options.Width = this.pictureBox1.Width;
            barcordWriter.Options.Height = this.pictureBox1.Height;
            barcordWriter.Options.Margin = 1;

            string strQRCode = textBox1.Text;

            this.pictureBox1.Image = barcordWriter.Write(strQRCode);
        }

        private void close_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            if (timeElapseSecond >= TIMEOUT_SECOND)
            {
                this.timer1.Stop();
                Application.Exit();
            }

            this.timeElapseSecond++;

            this.TimerProgressBar.Value = (timeElapseSecond * 100) / TIMEOUT_SECOND;

            if (this.TimerProgressBar.Value >= 80)
            {
                this.TimerProgressBar.ForeColor = Color.Red;
            }
            else if (this.TimerProgressBar.Value >= 50)
            {
                this.TimerProgressBar.ForeColor = Color.Orange;
            }
        }
    }
}
