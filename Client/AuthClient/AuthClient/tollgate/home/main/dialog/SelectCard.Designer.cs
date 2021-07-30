
namespace AuthClient.tollgate.home.main.dialog
{
    partial class SelectCard
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
            this.btn_toggle = new AuthClient.tollgate.home.main.dialog.MainToggle();
            this.img_back = new System.Windows.Forms.PictureBox();
            this.img_label = new System.Windows.Forms.PictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.img_icon)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_back)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_label)).BeginInit();
            this.SuspendLayout();
            // 
            // img_icon
            // 
            this.img_icon.Location = new System.Drawing.Point(101, 46);
            this.img_icon.Margin = new System.Windows.Forms.Padding(0);
            this.img_icon.Name = "img_icon";
            this.img_icon.Size = new System.Drawing.Size(66, 66);
            this.img_icon.TabIndex = 1;
            this.img_icon.TabStop = false;
            // 
            // btn_toggle
            // 
            this.btn_toggle.BackColor = System.Drawing.Color.Transparent;
            this.btn_toggle.Location = new System.Drawing.Point(98, 243);
            this.btn_toggle.Margin = new System.Windows.Forms.Padding(0);
            this.btn_toggle.Name = "btn_toggle";
            this.btn_toggle.On = false;
            this.btn_toggle.Size = new System.Drawing.Size(72, 29);
            this.btn_toggle.TabIndex = 4;
            // 
            // img_back
            // 
            this.img_back.Dock = System.Windows.Forms.DockStyle.Fill;
            this.img_back.Image = global::AuthClient.Properties.Resources.mainSelectBg;
            this.img_back.Location = new System.Drawing.Point(0, 0);
            this.img_back.Name = "img_back";
            this.img_back.Size = new System.Drawing.Size(269, 310);
            this.img_back.TabIndex = 0;
            this.img_back.TabStop = false;
            // 
            // img_label
            // 
            this.img_label.Location = new System.Drawing.Point(9, 131);
            this.img_label.Name = "img_label";
            this.img_label.Size = new System.Drawing.Size(250, 55);
            this.img_label.TabIndex = 5;
            this.img_label.TabStop = false;
            // 
            // SelectCard
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Transparent;
            this.Controls.Add(this.img_label);
            this.Controls.Add(this.btn_toggle);
            this.Controls.Add(this.img_icon);
            this.Controls.Add(this.img_back);
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "SelectCard";
            this.Size = new System.Drawing.Size(269, 310);
            ((System.ComponentModel.ISupportInitialize)(this.img_icon)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_back)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_label)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion
        private System.Windows.Forms.PictureBox img_icon;
        private MainToggle btn_toggle;
        private System.Windows.Forms.PictureBox img_back;
        private System.Windows.Forms.PictureBox img_label;
    }
}
