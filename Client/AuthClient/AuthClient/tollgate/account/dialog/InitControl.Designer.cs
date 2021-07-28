
namespace AuthClient.tollgate.account.dialog
{
    partial class InitControl
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
            this.panel_init = new System.Windows.Forms.TableLayoutPanel();
            this.panel_border_flow = new System.Windows.Forms.Panel();
            this.panel_flow = new NoWheelFlowPanel();
            this.picture_wallpaper = new System.Windows.Forms.PictureBox();
            this.view_step = new AuthClient.tollgate.account.dialog.StepControl();
            this.panel_init.SuspendLayout();
            this.panel_border_flow.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.picture_wallpaper)).BeginInit();
            this.SuspendLayout();
            // 
            // panel_init
            // 
            this.panel_init.ColumnCount = 2;
            this.panel_init.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 749F));
            this.panel_init.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.panel_init.Controls.Add(this.panel_border_flow, 1, 0);
            this.panel_init.Controls.Add(this.picture_wallpaper, 0, 0);
            this.panel_init.Controls.Add(this.view_step, 1, 1);
            this.panel_init.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel_init.Location = new System.Drawing.Point(0, 0);
            this.panel_init.Margin = new System.Windows.Forms.Padding(0);
            this.panel_init.Name = "panel_init";
            this.panel_init.RowCount = 2;
            this.panel_init.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 531F));
            this.panel_init.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.panel_init.Size = new System.Drawing.Size(1280, 720);
            this.panel_init.TabIndex = 0;
            // 
            // panel_border_flow
            // 
            this.panel_border_flow.Controls.Add(this.panel_flow);
            this.panel_border_flow.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel_border_flow.Location = new System.Drawing.Point(749, 0);
            this.panel_border_flow.Margin = new System.Windows.Forms.Padding(0);
            this.panel_border_flow.Name = "panel_border_flow";
            this.panel_border_flow.Size = new System.Drawing.Size(531, 531);
            this.panel_border_flow.TabIndex = 1;
            // 
            // panel_flow
            // 
            this.panel_flow.AutoScroll = true;
            this.panel_flow.Location = new System.Drawing.Point(0, 0);
            this.panel_flow.Name = "panel_flow";
            this.panel_flow.Size = new System.Drawing.Size(531, 531);
            this.panel_flow.TabIndex = 0;
            this.panel_flow.WrapContents = false;
            // 
            // picture_wallpaper
            // 
            this.picture_wallpaper.Dock = System.Windows.Forms.DockStyle.Fill;
            this.picture_wallpaper.Image = global::AuthClient.Properties.Resources.initWallpaper;
            this.picture_wallpaper.Location = new System.Drawing.Point(0, 0);
            this.picture_wallpaper.Margin = new System.Windows.Forms.Padding(0);
            this.picture_wallpaper.Name = "picture_wallpaper";
            this.panel_init.SetRowSpan(this.picture_wallpaper, 2);
            this.picture_wallpaper.Size = new System.Drawing.Size(749, 720);
            this.picture_wallpaper.TabIndex = 2;
            this.picture_wallpaper.TabStop = false;
            this.picture_wallpaper.Click += new System.EventHandler(this.picture_wallpaper_Click);
            // 
            // view_step
            // 
            this.view_step.BackColor = System.Drawing.Color.White;
            this.view_step.Dock = System.Windows.Forms.DockStyle.Fill;
            this.view_step.Location = new System.Drawing.Point(749, 531);
            this.view_step.Margin = new System.Windows.Forms.Padding(0);
            this.view_step.Name = "view_step";
            this.view_step.Size = new System.Drawing.Size(531, 189);
            this.view_step.TabIndex = 0;
            this.view_step.TabStop = false;
            // 
            // InitControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.Controls.Add(this.panel_init);
            this.DoubleBuffered = true;
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "InitControl";
            this.Size = new System.Drawing.Size(1280, 720);
            this.Load += new System.EventHandler(this.InitControl_Load);
            this.panel_init.ResumeLayout(false);
            this.panel_border_flow.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.picture_wallpaper)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel panel_init;
        private System.Windows.Forms.Panel panel_border_flow;
        private System.Windows.Forms.PictureBox picture_wallpaper;
        private StepControl view_step;
        private NoWheelFlowPanel panel_flow;
    }
}
