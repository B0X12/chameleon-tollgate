
namespace AuthClient.tollgate.home.main.dialog
{
    partial class MainToggle
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
            this.button = new System.Windows.Forms.PictureBox();
            this.SuspendLayout();
            // 
            // button
            // 
            this.button.Image = global::AuthClient.Properties.Resources.mainToggleOn;
            this.button.Location = new System.Drawing.Point(0, 0);
            this.button.Margin = new System.Windows.Forms.Padding(0);
            this.button.Name = "button";
            this.button.Size = new System.Drawing.Size(72, 29);
            this.button.TabIndex = 0;
            this.button.Click += new System.EventHandler(this.button_Click);
            // 
            // MainToggle
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Transparent;
            this.Controls.Add(this.button);
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "MainToggle";
            this.Size = new System.Drawing.Size(72, 29);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.PictureBox button;
    }
}
