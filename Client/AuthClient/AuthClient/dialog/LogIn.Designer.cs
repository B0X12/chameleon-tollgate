
namespace AuthClient
{
    partial class LogOnDialog
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
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.idField = new System.Windows.Forms.TextBox();
            this.pwdField = new System.Windows.Forms.TextBox();
            this.logonButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(33, 43);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(52, 15);
            this.label1.TabIndex = 0;
            this.label1.Text = "아이디";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(33, 93);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(67, 15);
            this.label2.TabIndex = 0;
            this.label2.Text = "비밀번호";
            // 
            // idField
            // 
            this.idField.Location = new System.Drawing.Point(126, 40);
            this.idField.Name = "idField";
            this.idField.Size = new System.Drawing.Size(221, 25);
            this.idField.TabIndex = 1;
            // 
            // pwdField
            // 
            this.pwdField.Location = new System.Drawing.Point(126, 90);
            this.pwdField.Name = "pwdField";
            this.pwdField.Size = new System.Drawing.Size(221, 25);
            this.pwdField.TabIndex = 1;
            // 
            // logonButton
            // 
            this.logonButton.Location = new System.Drawing.Point(126, 138);
            this.logonButton.Name = "logonButton";
            this.logonButton.Size = new System.Drawing.Size(117, 37);
            this.logonButton.TabIndex = 2;
            this.logonButton.Text = "로그인";
            this.logonButton.UseVisualStyleBackColor = true;
            this.logonButton.Click += new System.EventHandler(this.logonButton_Click);
            // 
            // LogOnDialog
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(382, 195);
            this.Controls.Add(this.logonButton);
            this.Controls.Add(this.pwdField);
            this.Controls.Add(this.idField);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Name = "LogOnDialog";
            this.Text = "LogOn";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox idField;
        private System.Windows.Forms.TextBox pwdField;
        private System.Windows.Forms.Button logonButton;
    }
}