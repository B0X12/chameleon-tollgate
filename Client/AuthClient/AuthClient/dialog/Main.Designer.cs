
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
            this.userLabel = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // usbDlgButton
            // 
            this.usbDlgButton.Location = new System.Drawing.Point(41, 101);
            this.usbDlgButton.Name = "usbDlgButton";
            this.usbDlgButton.Size = new System.Drawing.Size(145, 55);
            this.usbDlgButton.TabIndex = 0;
            this.usbDlgButton.Text = "USB 등록";
            this.usbDlgButton.UseVisualStyleBackColor = true;
            this.usbDlgButton.Click += new System.EventHandler(this.usbDlgButton_Click);
            // 
            // userLabel
            // 
            this.userLabel.AutoSize = true;
            this.userLabel.Location = new System.Drawing.Point(41, 23);
            this.userLabel.Name = "userLabel";
            this.userLabel.Size = new System.Drawing.Size(78, 15);
            this.userLabel.TabIndex = 1;
            this.userLabel.Text = "Welcome! ";
            // 
            // MainDialog
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.userLabel);
            this.Controls.Add(this.usbDlgButton);
            this.Name = "MainDialog";
            this.Text = "Main";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button usbDlgButton;
        private System.Windows.Forms.Label userLabel;
    }
}