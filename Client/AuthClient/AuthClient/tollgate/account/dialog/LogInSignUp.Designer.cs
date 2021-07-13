
namespace AuthClient.tollgate.account.dialog
{
    partial class LogOnSignUpDialog
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
            this.logOnButton = new System.Windows.Forms.Button();
            this.signUpButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // logOnButton
            // 
            this.logOnButton.Location = new System.Drawing.Point(31, 27);
            this.logOnButton.Name = "logOnButton";
            this.logOnButton.Size = new System.Drawing.Size(156, 141);
            this.logOnButton.TabIndex = 0;
            this.logOnButton.Text = "로그인";
            this.logOnButton.UseVisualStyleBackColor = true;
            this.logOnButton.Click += new System.EventHandler(this.logOnButton_Click);
            // 
            // signUpButton
            // 
            this.signUpButton.Location = new System.Drawing.Point(212, 27);
            this.signUpButton.Name = "signUpButton";
            this.signUpButton.Size = new System.Drawing.Size(156, 141);
            this.signUpButton.TabIndex = 0;
            this.signUpButton.Text = "회원 가입";
            this.signUpButton.UseVisualStyleBackColor = true;
            this.signUpButton.Click += new System.EventHandler(this.signUpButton_Click);
            // 
            // LogOnSignUpDialog
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(403, 194);
            this.Controls.Add(this.signUpButton);
            this.Controls.Add(this.logOnButton);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "LogOnSignUpDialog";
            this.Text = "LogOnSignUp";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button logOnButton;
        private System.Windows.Forms.Button signUpButton;
    }
}