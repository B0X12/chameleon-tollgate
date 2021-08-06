
namespace AuthClient.tollgate.account.dialog
{
    partial class SplashButton
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
            this.img_back = new System.Windows.Forms.PictureBox();
            this.label = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.img_back)).BeginInit();
            this.SuspendLayout();
            // 
            // img_back
            // 
            this.img_back.Dock = System.Windows.Forms.DockStyle.Fill;
            this.img_back.Image = global::AuthClient.Properties.Resources.splashButton;
            this.img_back.Location = new System.Drawing.Point(0, 0);
            this.img_back.Margin = new System.Windows.Forms.Padding(0);
            this.img_back.Name = "img_back";
            this.img_back.Size = new System.Drawing.Size(219, 41);
            this.img_back.TabIndex = 0;
            this.img_back.TabStop = false;
            // 
            // label
            // 
            this.label.AutoSize = true;
            this.label.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(200)))), ((int)(((byte)(64)))));
            this.label.Location = new System.Drawing.Point(47, 11);
            this.label.Name = "label";
            this.label.Size = new System.Drawing.Size(125, 12);
            this.label.TabIndex = 1;
            this.label.Text = "→ 메인 화면으로 이동";
            // 
            // SplashButton
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Transparent;
            this.Controls.Add(this.label);
            this.Controls.Add(this.img_back);
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "SplashButton";
            this.Size = new System.Drawing.Size(219, 41);
            ((System.ComponentModel.ISupportInitialize)(this.img_back)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.PictureBox img_back;
        private System.Windows.Forms.Label label;
    }
}
