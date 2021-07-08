
namespace AuthClient.tollgate.usb.dialog
{
    partial class USBConfigDialog
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.progressBar1 = new System.Windows.Forms.ProgressBar();
            this.statusMessageLabel = new System.Windows.Forms.Label();
            this.usbRecognitionTimer = new System.Windows.Forms.Timer(this.components);
            this.registerOKButton = new System.Windows.Forms.Button();
            this.registerCancelButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // progressBar1
            // 
            this.progressBar1.Location = new System.Drawing.Point(65, 144);
            this.progressBar1.MarqueeAnimationSpeed = 20;
            this.progressBar1.Name = "progressBar1";
            this.progressBar1.Size = new System.Drawing.Size(352, 27);
            this.progressBar1.Style = System.Windows.Forms.ProgressBarStyle.Marquee;
            this.progressBar1.TabIndex = 0;
            // 
            // statusMessageLabel
            // 
            this.statusMessageLabel.Dock = System.Windows.Forms.DockStyle.Top;
            this.statusMessageLabel.Font = new System.Drawing.Font("맑은 고딕", 13.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.statusMessageLabel.Location = new System.Drawing.Point(0, 0);
            this.statusMessageLabel.Name = "statusMessageLabel";
            this.statusMessageLabel.Size = new System.Drawing.Size(489, 141);
            this.statusMessageLabel.TabIndex = 1;
            this.statusMessageLabel.Text = "USB를 인식하는 중..";
            this.statusMessageLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // usbRecognitionTimer
            // 
            this.usbRecognitionTimer.Interval = 200;
            this.usbRecognitionTimer.Tick += new System.EventHandler(this.usbRecognitionTimer_Tick);
            // 
            // registerOKButton
            // 
            this.registerOKButton.Location = new System.Drawing.Point(99, 144);
            this.registerOKButton.Name = "registerOKButton";
            this.registerOKButton.Size = new System.Drawing.Size(108, 36);
            this.registerOKButton.TabIndex = 2;
            this.registerOKButton.Text = "등록";
            this.registerOKButton.UseVisualStyleBackColor = true;
            this.registerOKButton.Click += new System.EventHandler(this.registerOKButton_Click);
            // 
            // registerCancelButton
            // 
            this.registerCancelButton.Location = new System.Drawing.Point(264, 144);
            this.registerCancelButton.Name = "registerCancelButton";
            this.registerCancelButton.Size = new System.Drawing.Size(108, 36);
            this.registerCancelButton.TabIndex = 2;
            this.registerCancelButton.Text = "취소";
            this.registerCancelButton.UseVisualStyleBackColor = true;
            this.registerCancelButton.Click += new System.EventHandler(this.registerCancelButton_Click);
            // 
            // USBConfigDialog
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(489, 202);
            this.Controls.Add(this.registerCancelButton);
            this.Controls.Add(this.registerOKButton);
            this.Controls.Add(this.statusMessageLabel);
            this.Controls.Add(this.progressBar1);
            this.Name = "USBConfigDialog";
            this.Text = "USBConfig";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.ProgressBar progressBar1;
        private System.Windows.Forms.Label statusMessageLabel;
        private System.Windows.Forms.Timer usbRecognitionTimer;
        private System.Windows.Forms.Button registerOKButton;
        private System.Windows.Forms.Button registerCancelButton;
    }
}