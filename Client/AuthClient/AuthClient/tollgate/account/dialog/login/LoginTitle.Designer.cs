
namespace AuthClient.tollgate.account.dialog
{
    partial class LoginTitle
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

        #region 구성 요소 디자이너에서 생성한 코드

        /// <summary> 
        /// 디자이너 지원에 필요한 메서드입니다. 
        /// 이 메서드의 내용을 코드 편집기로 수정하지 마세요.
        /// </summary>
        private void InitializeComponent()
        {
            this.btn_signup = new System.Windows.Forms.Button();
            this.img_back = new System.Windows.Forms.PictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.img_back)).BeginInit();
            this.SuspendLayout();
            // 
            // btn_signup
            // 
            this.btn_signup.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btn_signup.Location = new System.Drawing.Point(105, 56);
            this.btn_signup.Name = "btn_signup";
            this.btn_signup.Size = new System.Drawing.Size(77, 21);
            this.btn_signup.TabIndex = 1;
            this.btn_signup.TabStop = false;
            this.btn_signup.UseVisualStyleBackColor = true;
            this.btn_signup.Click += new System.EventHandler(this.btn_signup_Click);
            // 
            // img_back
            // 
            this.img_back.BackColor = System.Drawing.Color.White;
            this.img_back.Image = global::AuthClient.Properties.Resources.loginTitle;
            this.img_back.Location = new System.Drawing.Point(0, 0);
            this.img_back.Name = "img_back";
            this.img_back.Size = new System.Drawing.Size(336, 78);
            this.img_back.TabIndex = 0;
            this.img_back.TabStop = false;
            // 
            // LoginTitle
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Transparent;
            this.Controls.Add(this.btn_signup);
            this.Controls.Add(this.img_back);
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "LoginTitle";
            this.Size = new System.Drawing.Size(336, 78);
            ((System.ComponentModel.ISupportInitialize)(this.img_back)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.PictureBox img_back;
        private System.Windows.Forms.Button btn_signup;
    }
}
