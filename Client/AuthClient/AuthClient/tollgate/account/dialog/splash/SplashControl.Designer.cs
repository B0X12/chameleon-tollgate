
namespace AuthClient.tollgate.account.dialog
{
    partial class SplashControl
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
            this.label_hello = new System.Windows.Forms.Label();
            this.img_smile = new System.Windows.Forms.PictureBox();
            this.label_sub = new AuthClient.tollgate.account.dialog.SubTitle();
            this.btn_splash = new AuthClient.tollgate.account.dialog.SplashButton();
            ((System.ComponentModel.ISupportInitialize)(this.img_smile)).BeginInit();
            this.SuspendLayout();
            // 
            // label_hello
            // 
            this.label_hello.AutoSize = true;
            this.label_hello.BackColor = System.Drawing.Color.Transparent;
            this.label_hello.Location = new System.Drawing.Point(521, 288);
            this.label_hello.Margin = new System.Windows.Forms.Padding(0);
            this.label_hello.Name = "label_hello";
            this.label_hello.Size = new System.Drawing.Size(33, 12);
            this.label_hello.TabIndex = 5;
            this.label_hello.Text = "temp";
            // 
            // img_smile
            // 
            this.img_smile.BackColor = System.Drawing.Color.Transparent;
            this.img_smile.Image = global::AuthClient.Properties.Resources.splashSmile;
            this.img_smile.Location = new System.Drawing.Point(766, 274);
            this.img_smile.Margin = new System.Windows.Forms.Padding(0);
            this.img_smile.Name = "img_smile";
            this.img_smile.Size = new System.Drawing.Size(39, 40);
            this.img_smile.TabIndex = 7;
            this.img_smile.TabStop = false;
            // 
            // label_sub
            // 
            this.label_sub.AutoSize = true;
            this.label_sub.BackColor = System.Drawing.Color.Transparent;
            this.label_sub.Font = new System.Drawing.Font("굴림", 12.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.label_sub.Location = new System.Drawing.Point(458, 358);
            this.label_sub.Name = "label_sub";
            this.label_sub.Size = new System.Drawing.Size(372, 17);
            this.label_sub.TabIndex = 6;
            this.label_sub.Text = "이제 언제나 TOLLGATE와 함께하실 수 있습니다.";
            // 
            // btn_splash
            // 
            this.btn_splash.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.btn_splash.BackColor = System.Drawing.Color.Transparent;
            this.btn_splash.Location = new System.Drawing.Point(533, 428);
            this.btn_splash.Margin = new System.Windows.Forms.Padding(0);
            this.btn_splash.Name = "btn_splash";
            this.btn_splash.Size = new System.Drawing.Size(219, 41);
            this.btn_splash.TabIndex = 1;
            // 
            // SplashControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.BackgroundImage = global::AuthClient.Properties.Resources.splashWallpaper;
            this.Controls.Add(this.img_smile);
            this.Controls.Add(this.label_sub);
            this.Controls.Add(this.btn_splash);
            this.Controls.Add(this.label_hello);
            this.DoubleBuffered = true;
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "SplashControl";
            this.Size = new System.Drawing.Size(1280, 720);
            this.Load += SplashControl_Load;
            ((System.ComponentModel.ISupportInitialize)(this.img_smile)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private SplashButton btn_splash;
        private System.Windows.Forms.Label label_hello;
        private SubTitle label_sub;
        private System.Windows.Forms.PictureBox img_smile;
    }
}
