
namespace AuthClient
{
    partial class MainDialog
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
            this.usbDlgButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // usbDlgButton
            // 
            this.usbDlgButton.Location = new System.Drawing.Point(38, 39);
            this.usbDlgButton.Name = "usbDlgButton";
            this.usbDlgButton.Size = new System.Drawing.Size(269, 55);
            this.usbDlgButton.TabIndex = 0;
            this.usbDlgButton.Text = "USB 인증 다이얼로그";
            this.usbDlgButton.UseVisualStyleBackColor = true;
            // 
            // MainDialog
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.usbDlgButton);
            this.Name = "MainDialog";
            this.Text = "Main";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button usbDlgButton;
    }
}