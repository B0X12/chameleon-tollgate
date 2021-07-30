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
        public QRForm()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            ZXing.BarcodeWriter barcordWriter = new ZXing.BarcodeWriter();
            barcordWriter.Format = ZXing.BarcodeFormat.QR_CODE;

            barcordWriter.Options.Width = this.pictureBox1.Width;
            barcordWriter.Options.Height = this.pictureBox1.Height;

            string strQRCode = textBox1.Text;

            this.pictureBox1.Image = barcordWriter.Write(strQRCode);
        }
    }
}
