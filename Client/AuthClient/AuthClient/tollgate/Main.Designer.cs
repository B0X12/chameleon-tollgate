
namespace AuthClient.tollgate
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
            this.userLabel = new System.Windows.Forms.Label();
            this.logoutButton = new System.Windows.Forms.Button();
            this.usbConfigButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
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
            // logoutButton
            // 
            this.logoutButton.Location = new System.Drawing.Point(606, 23);
            this.logoutButton.Name = "logoutButton";
            this.logoutButton.Size = new System.Drawing.Size(182, 33);
            this.logoutButton.TabIndex = 2;
            this.logoutButton.Text = "이 PC와 연동 해제";
            this.logoutButton.UseVisualStyleBackColor = true;
            this.logoutButton.Click += new System.EventHandler(this.logoutButton_Click);
            // 
            // usbConfigButton
            // 
            this.usbConfigButton.Location = new System.Drawing.Point(556, 117);
            this.usbConfigButton.Name = "usbConfigButton";
            this.usbConfigButton.Size = new System.Drawing.Size(144, 49);
            this.usbConfigButton.TabIndex = 3;
            this.usbConfigButton.Text = "USB 설정 창";
            this.usbConfigButton.UseVisualStyleBackColor = true;
            this.usbConfigButton.Click += new System.EventHandler(this.usbConfigButton_Click);
            // 
            // MainDialog
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.usbConfigButton);
            this.Controls.Add(this.logoutButton);
            this.Controls.Add(this.userLabel);
            this.Name = "MainDialog";
            this.Text = "Main";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Label userLabel;
        private System.Windows.Forms.Button logoutButton;
        private System.Windows.Forms.Button usbConfigButton;
    }
}