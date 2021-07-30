
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
            this.panel_content = new System.Windows.Forms.Panel();
            this.panel_main.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.img_logo)).BeginInit();
            this.SuspendLayout();
            // 
            // panel_main
            // 
            this.panel_main.BackColor = System.Drawing.Color.Transparent;
            this.panel_main.ColumnCount = 5;
            this.panel_main.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 50F));
            this.panel_main.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 6F));
            this.panel_main.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 176F));
            this.panel_main.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 50F));
            this.panel_main.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.panel_main.Controls.Add(this.btn_side_main, 1, 3);
            this.panel_main.Controls.Add(this.img_logo, 2, 1);
            this.panel_main.Controls.Add(this.btn_side_setting, 1, 5);
            this.panel_main.Controls.Add(this.panel_content, 4, 0);
            this.panel_main.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel_main.Location = new System.Drawing.Point(0, 0);
            this.panel_main.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.panel_main.Name = "panel_main";
            this.panel_main.RowCount = 7;
            this.panel_main.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 57F));
            this.panel_main.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 41F));
            this.panel_main.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 55F));
            this.panel_main.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 42F));
            this.panel_main.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 8F));
            this.panel_main.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 42F));
            this.panel_main.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.panel_main.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.panel_main.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.panel_main.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.panel_main.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.panel_main.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.panel_main.Size = new System.Drawing.Size(1280, 720);
            this.panel_main.TabIndex = 0;
            // 
            // btn_side_main
            // 
            this.btn_side_main.BackColor = System.Drawing.Color.Transparent;
            this.btn_side_main.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("btn_side_main.BackgroundImage")));
            this.panel_main.SetColumnSpan(this.btn_side_main, 2);
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
            this.panel_main.SetColumnSpan(this.btn_side_setting, 2);
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
            this.panel_main.SetRowSpan(this.panel_content, 7);
            this.panel_content.Size = new System.Drawing.Size(998, 720);
            this.panel_content.TabIndex = 3;
            // 
            // HomeControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackgroundImage = global::AuthClient.Properties.Resources.mainWallpaper;
            this.Controls.Add(this.panel_main);
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
        private System.Windows.Forms.Panel panel_content;
    }
}
