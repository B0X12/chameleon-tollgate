
namespace AuthClient.tollgate.account.dialog
{
    partial class SignUpDialog
    {
        /// <summary>
        /// 필수 디자이너 변수입니다.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 사용 중인 모든 리소스를 정리합니다.
        /// </summary>
        /// <param name="disposing">관리되는 리소스를 삭제해야 하면 true이고, 그렇지 않으면 false입니다.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form 디자이너에서 생성한 코드

        /// <summary>
        /// 디자이너 지원에 필요한 메서드입니다. 
        /// 이 메서드의 내용을 코드 편집기로 수정하지 마세요.
        /// </summary>
        private void InitializeComponent()
        {
            this.signUpButton = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.idField = new System.Windows.Forms.TextBox();
            this.pwdField = new System.Windows.Forms.TextBox();
            this.pwdCheckField = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // signUpButton
            // 
            this.signUpButton.Location = new System.Drawing.Point(152, 163);
            this.signUpButton.Name = "signUpButton";
            this.signUpButton.Size = new System.Drawing.Size(127, 40);
            this.signUpButton.TabIndex = 0;
            this.signUpButton.Text = "회원 가입";
            this.signUpButton.UseVisualStyleBackColor = true;
            this.signUpButton.Click += new System.EventHandler(this.signUpButton_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(38, 31);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(52, 15);
            this.label1.TabIndex = 1;
            this.label1.Text = "아이디";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(38, 76);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(67, 15);
            this.label2.TabIndex = 2;
            this.label2.Text = "비밀번호";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(38, 121);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(102, 15);
            this.label3.TabIndex = 3;
            this.label3.Text = "비밀번호 확인";
            // 
            // idField
            // 
            this.idField.Location = new System.Drawing.Point(152, 26);
            this.idField.Name = "idField";
            this.idField.Size = new System.Drawing.Size(240, 25);
            this.idField.TabIndex = 4;
            // 
            // pwdField
            // 
            this.pwdField.Location = new System.Drawing.Point(152, 71);
            this.pwdField.Name = "pwdField";
            this.pwdField.PasswordChar = '●';
            this.pwdField.Size = new System.Drawing.Size(240, 25);
            this.pwdField.TabIndex = 4;
            // 
            // pwdCheckField
            // 
            this.pwdCheckField.Location = new System.Drawing.Point(152, 116);
            this.pwdCheckField.Name = "pwdCheckField";
            this.pwdCheckField.PasswordChar = '●';
            this.pwdCheckField.Size = new System.Drawing.Size(240, 25);
            this.pwdCheckField.TabIndex = 4;
            // 
            // SignUpDialog
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(445, 220);
            this.Controls.Add(this.pwdCheckField);
            this.Controls.Add(this.pwdField);
            this.Controls.Add(this.idField);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.signUpButton);
            this.Name = "SignUpDialog";
            this.Text = "회원 가입";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button signUpButton;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox idField;
        private System.Windows.Forms.TextBox pwdField;
        private System.Windows.Forms.TextBox pwdCheckField;
    }
}

