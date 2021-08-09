
namespace AuthClient.tollgate.home.setting
{
    partial class Menu
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
            this.img_usb = new System.Windows.Forms.PictureBox();
            this.img_pc = new System.Windows.Forms.PictureBox();
            this.img_account = new System.Windows.Forms.PictureBox();
            this.img_back = new System.Windows.Forms.PictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.img_usb)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_pc)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_account)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_back)).BeginInit();
            this.SuspendLayout();
            // 
            // img_usb
            // 
            this.img_usb.BackColor = System.Drawing.Color.White;
            this.img_usb.Image = global::AuthClient.Properties.Resources.settingUsbOff;
            this.img_usb.Location = new System.Drawing.Point(13, 123);
            this.img_usb.Margin = new System.Windows.Forms.Padding(0);
            this.img_usb.Name = "img_usb";
            this.img_usb.Size = new System.Drawing.Size(157, 31);
            this.img_usb.TabIndex = 3;
            this.img_usb.TabStop = false;
            this.img_usb.Click += new System.EventHandler(this.img_usb_Click);
            // 
            // img_pc
            // 
            this.img_pc.BackColor = System.Drawing.Color.White;
            this.img_pc.Image = global::AuthClient.Properties.Resources.settingPcOff;
            this.img_pc.Location = new System.Drawing.Point(13, 82);
            this.img_pc.Margin = new System.Windows.Forms.Padding(0);
            this.img_pc.Name = "img_pc";
            this.img_pc.Size = new System.Drawing.Size(157, 31);
            this.img_pc.TabIndex = 2;
            this.img_pc.TabStop = false;
            this.img_pc.Click += new System.EventHandler(this.img_pc_Click);
            // 
            // img_account
            // 
            this.img_account.BackColor = System.Drawing.Color.White;
            this.img_account.Image = global::AuthClient.Properties.Resources.settingAccountOn;
            this.img_account.Location = new System.Drawing.Point(13, 37);
            this.img_account.Margin = new System.Windows.Forms.Padding(0);
            this.img_account.Name = "img_account";
            this.img_account.Size = new System.Drawing.Size(157, 31);
            this.img_account.TabIndex = 1;
            this.img_account.TabStop = false;
            this.img_account.Click += new System.EventHandler(this.img_account_Click);
            // 
            // img_back
            // 
            this.img_back.Dock = System.Windows.Forms.DockStyle.Fill;
            this.img_back.Image = global::AuthClient.Properties.Resources.settingMenuBg;
            this.img_back.Location = new System.Drawing.Point(0, 0);
            this.img_back.Margin = new System.Windows.Forms.Padding(0);
            this.img_back.Name = "img_back";
            this.img_back.Size = new System.Drawing.Size(187, 279);
            this.img_back.TabIndex = 0;
            this.img_back.TabStop = false;
            // 
            // Menu
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Transparent;
            this.Controls.Add(this.img_usb);
            this.Controls.Add(this.img_pc);
            this.Controls.Add(this.img_account);
            this.Controls.Add(this.img_back);
            this.Name = "Menu";
            this.Size = new System.Drawing.Size(187, 279);
            ((System.ComponentModel.ISupportInitialize)(this.img_usb)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_pc)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_account)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_back)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.PictureBox img_back;
        private System.Windows.Forms.PictureBox img_account;
        private System.Windows.Forms.PictureBox img_pc;
        private System.Windows.Forms.PictureBox img_usb;
    }
}
