
namespace AuthClient.tollgate.otp.dialog
{
    partial class OtpTest
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
            this.button_OtpCertification = new System.Windows.Forms.Button();
            this.textBox_InputOtp = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.button_OtpSend = new System.Windows.Forms.Button();
            this.button_Login = new System.Windows.Forms.Button();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.button_Register = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            this.textBox_ID = new System.Windows.Forms.TextBox();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // button_OtpCertification
            // 
            this.button_OtpCertification.Location = new System.Drawing.Point(21, 25);
            this.button_OtpCertification.Name = "button_OtpCertification";
            this.button_OtpCertification.Size = new System.Drawing.Size(237, 50);
            this.button_OtpCertification.TabIndex = 0;
            this.button_OtpCertification.Text = "OTP 등록";
            this.button_OtpCertification.UseVisualStyleBackColor = true;
            this.button_OtpCertification.Click += new System.EventHandler(this.button_OtpCertification_Click);
            // 
            // textBox_InputOtp
            // 
            this.textBox_InputOtp.Location = new System.Drawing.Point(119, 84);
            this.textBox_InputOtp.Name = "textBox_InputOtp";
            this.textBox_InputOtp.Size = new System.Drawing.Size(119, 25);
            this.textBox_InputOtp.TabIndex = 1;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(36, 89);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(77, 15);
            this.label1.TabIndex = 2;
            this.label1.Text = "인증번호 :";
            // 
            // button_OtpSend
            // 
            this.button_OtpSend.Location = new System.Drawing.Point(18, 118);
            this.button_OtpSend.Name = "button_OtpSend";
            this.button_OtpSend.Size = new System.Drawing.Size(237, 54);
            this.button_OtpSend.TabIndex = 3;
            this.button_OtpSend.Text = "OTP 인증";
            this.button_OtpSend.UseVisualStyleBackColor = true;
            this.button_OtpSend.Click += new System.EventHandler(this.button_OtpSend_Click);
            // 
            // button_Login
            // 
            this.button_Login.Location = new System.Drawing.Point(281, 12);
            this.button_Login.Name = "button_Login";
            this.button_Login.Size = new System.Drawing.Size(110, 58);
            this.button_Login.TabIndex = 8;
            this.button_Login.Text = "로그인";
            this.button_Login.UseVisualStyleBackColor = true;
            this.button_Login.Click += new System.EventHandler(this.button_Login_Click);
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.textBox_InputOtp);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Controls.Add(this.button_OtpSend);
            this.groupBox1.Controls.Add(this.button_OtpCertification);
            this.groupBox1.Location = new System.Drawing.Point(3, 2);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(272, 192);
            this.groupBox1.TabIndex = 9;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "groupBox1";
            // 
            // button_Register
            // 
            this.button_Register.Location = new System.Drawing.Point(281, 91);
            this.button_Register.Name = "button_Register";
            this.button_Register.Size = new System.Drawing.Size(110, 58);
            this.button_Register.TabIndex = 14;
            this.button_Register.Text = "회원가입";
            this.button_Register.UseVisualStyleBackColor = true;
            this.button_Register.Click += new System.EventHandler(this.button_Register_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(32, 206);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(35, 15);
            this.label2.TabIndex = 21;
            this.label2.Text = "ID : ";
            // 
            // textBox_ID
            // 
            this.textBox_ID.Location = new System.Drawing.Point(82, 200);
            this.textBox_ID.Name = "textBox_ID";
            this.textBox_ID.Size = new System.Drawing.Size(159, 25);
            this.textBox_ID.TabIndex = 20;
            // 
            // OtpTest
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(397, 247);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.textBox_ID);
            this.Controls.Add(this.button_Register);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.button_Login);
            this.Name = "OtpTest";
            this.Text = "Main";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button button_OtpCertification;
        private System.Windows.Forms.TextBox textBox_InputOtp;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button button_OtpSend;
        private System.Windows.Forms.Button button_Login;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Button button_Register;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox textBox_ID;
    }
}