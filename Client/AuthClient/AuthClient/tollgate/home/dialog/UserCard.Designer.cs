
namespace AuthClient.tollgate.home.main.dialog
{
    partial class UserCard
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
            this.img_icon = new System.Windows.Forms.PictureBox();
            this.img_logout = new System.Windows.Forms.PictureBox();
            this.label_id = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.img_icon)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_logout)).BeginInit();
            this.SuspendLayout();
            // 
            // img_icon
            // 
            this.img_icon.Image = global::AuthClient.Properties.Resources.mainUserIcon;
            this.img_icon.Location = new System.Drawing.Point(77, 51);
            this.img_icon.Margin = new System.Windows.Forms.Padding(0);
            this.img_icon.Name = "img_icon";
            this.img_icon.Size = new System.Drawing.Size(41, 41);
            this.img_icon.TabIndex = 0;
            this.img_icon.TabStop = false;
            // 
            // img_logout
            // 
            this.img_logout.Image = global::AuthClient.Properties.Resources.mainLogout;
            this.img_logout.Location = new System.Drawing.Point(27, 174);
            this.img_logout.Name = "img_logout";
            this.img_logout.Size = new System.Drawing.Size(151, 35);
            this.img_logout.TabIndex = 1;
            this.img_logout.TabStop = false;
            // 
            // label_id
            // 
            this.label_id.AutoSize = true;
            this.label_id.Location = new System.Drawing.Point(80, 130);
            this.label_id.Name = "label_id";
            this.label_id.Size = new System.Drawing.Size(38, 12);
            this.label_id.TabIndex = 2;
            this.label_id.Text = "label1";
            // 
            // UserCard
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Transparent;
            this.BackgroundImage = global::AuthClient.Properties.Resources.mainUserBg;
            this.Controls.Add(this.label_id);
            this.Controls.Add(this.img_logout);
            this.Controls.Add(this.img_icon);
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "UserCard";
            this.Size = new System.Drawing.Size(207, 269);
            ((System.ComponentModel.ISupportInitialize)(this.img_icon)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_logout)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.PictureBox img_icon;
        private System.Windows.Forms.PictureBox img_logout;
        private System.Windows.Forms.Label label_id;
    }
}
