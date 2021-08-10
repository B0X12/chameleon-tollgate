
namespace AuthClient.tollgate.home.dialog
{
    partial class HomeControl
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(HomeControl));
            this.panel_main = new System.Windows.Forms.TableLayoutPanel();
            this.btn_side_main = new AuthClient.tollgate.home.SideBarMain();
            this.img_logo = new System.Windows.Forms.PictureBox();
            this.btn_side_setting = new AuthClient.tollgate.home.main.dialog.SidebarSetting();
            this.panel_content = new AuthClient.tollgate.DoubleBufferPanel();
            this.card_user = new AuthClient.tollgate.home.main.dialog.UserCard();
            this.panel_main.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.img_logo)).BeginInit();
            this.SuspendLayout();
            // 
            // panel_main
            // 
            this.panel_main.BackColor = System.Drawing.Color.Transparent;
            this.panel_main.ColumnCount = 7;
            this.panel_main.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 43F));
            this.panel_main.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 7F));
            this.panel_main.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 6F));
            this.panel_main.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 176F));
            this.panel_main.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 18F));
            this.panel_main.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 32F));
            this.panel_main.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.panel_main.Controls.Add(this.btn_side_main, 2, 3);
            this.panel_main.Controls.Add(this.img_logo, 3, 1);
            this.panel_main.Controls.Add(this.btn_side_setting, 2, 5);
            this.panel_main.Controls.Add(this.panel_content, 6, 0);
            this.panel_main.Controls.Add(this.card_user, 1, 7);
            this.panel_main.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel_main.Location = new System.Drawing.Point(0, 0);
            this.panel_main.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.panel_main.Name = "panel_main";
            this.panel_main.RowCount = 9;
            this.panel_main.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 57F));
            this.panel_main.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 41F));
            this.panel_main.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 55F));
            this.panel_main.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 42F));
            this.panel_main.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 8F));
            this.panel_main.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 42F));
            this.panel_main.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.panel_main.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 269F));
            this.panel_main.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 64F));
            this.panel_main.Size = new System.Drawing.Size(1280, 720);
            this.panel_main.TabIndex = 0;
            // 
            // btn_side_main
            // 
            this.btn_side_main.BackColor = System.Drawing.Color.Transparent;
            this.btn_side_main.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("btn_side_main.BackgroundImage")));
            this.panel_main.SetColumnSpan(this.btn_side_main, 4);
            this.btn_side_main.Location = new System.Drawing.Point(50, 153);
            this.btn_side_main.Margin = new System.Windows.Forms.Padding(0);
            this.btn_side_main.Name = "btn_side_main";
            this.btn_side_main.Size = new System.Drawing.Size(182, 42);
            this.btn_side_main.TabIndex = 0;
            this.btn_side_main.Click += new System.EventHandler(this.btn_side_main_Click);
            // 
            // img_logo
            // 
            this.img_logo.Dock = System.Windows.Forms.DockStyle.Fill;
            this.img_logo.Image = global::AuthClient.Properties.Resources.logo;
            this.img_logo.Location = new System.Drawing.Point(56, 57);
            this.img_logo.Margin = new System.Windows.Forms.Padding(0);
            this.img_logo.Name = "img_logo";
            this.img_logo.Size = new System.Drawing.Size(176, 41);
            this.img_logo.TabIndex = 1;
            this.img_logo.TabStop = false;
            // 
            // btn_side_setting
            // 
            this.btn_side_setting.BackColor = System.Drawing.Color.Transparent;
            this.btn_side_setting.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("btn_side_setting.BackgroundImage")));
            this.panel_main.SetColumnSpan(this.btn_side_setting, 4);
            this.btn_side_setting.Location = new System.Drawing.Point(50, 203);
            this.btn_side_setting.Margin = new System.Windows.Forms.Padding(0);
            this.btn_side_setting.Name = "btn_side_setting";
            this.btn_side_setting.Size = new System.Drawing.Size(182, 42);
            this.btn_side_setting.TabIndex = 2;
            this.btn_side_setting.Click += new System.EventHandler(this.btn_side_setting_Click);
            // 
            // panel_content
            // 
            this.panel_content.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel_content.Location = new System.Drawing.Point(282, 0);
            this.panel_content.Margin = new System.Windows.Forms.Padding(0);
            this.panel_content.Name = "panel_content";
            this.panel_main.SetRowSpan(this.panel_content, 9);
            this.panel_content.Size = new System.Drawing.Size(998, 720);
            this.panel_content.TabIndex = 3;
            // 
            // card_user
            // 
            this.card_user.BackColor = System.Drawing.Color.Transparent;
            this.card_user.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("card_user.BackgroundImage")));
            this.panel_main.SetColumnSpan(this.card_user, 4);
            this.card_user.Location = new System.Drawing.Point(43, 387);
            this.card_user.Margin = new System.Windows.Forms.Padding(0);
            this.card_user.Name = "card_user";
            this.card_user.Size = new System.Drawing.Size(207, 269);
            this.card_user.TabIndex = 4;
            this.card_user.LogoutClick += new System.EventHandler(this.card_user_LogoutClick);
            // 
            // HomeControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.BackgroundImage = global::AuthClient.Properties.Resources.mainWallpaper;
            this.Controls.Add(this.panel_main);
            this.DoubleBuffered = true;
            this.Name = "HomeControl";
            this.Size = new System.Drawing.Size(1280, 720);
            this.panel_main.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.img_logo)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel panel_main;
        private SideBarMain btn_side_main;
        private System.Windows.Forms.PictureBox img_logo;
        private main.dialog.SidebarSetting btn_side_setting;
        private DoubleBufferPanel panel_content;
        private main.dialog.UserCard card_user;
    }
}
