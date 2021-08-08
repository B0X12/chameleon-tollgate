
namespace AuthClient.tollgate.home.setting
{
    partial class SettingPC
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
            this.panel_table = new System.Windows.Forms.TableLayoutPanel();
            this.img_title = new System.Windows.Forms.PictureBox();
            this.img_subtitle = new System.Windows.Forms.PictureBox();
            this.panel1 = new System.Windows.Forms.Panel();
            this.panel_list = new System.Windows.Forms.FlowLayoutPanel();
            this.panel_table.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.img_title)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_subtitle)).BeginInit();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel_table
            // 
            this.panel_table.ColumnCount = 4;
            this.panel_table.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 62F));
            this.panel_table.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 281F));
            this.panel_table.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.panel_table.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 24F));
            this.panel_table.Controls.Add(this.img_title, 0, 1);
            this.panel_table.Controls.Add(this.img_subtitle, 0, 3);
            this.panel_table.Controls.Add(this.panel1, 0, 5);
            this.panel_table.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel_table.Location = new System.Drawing.Point(0, 0);
            this.panel_table.Margin = new System.Windows.Forms.Padding(0);
            this.panel_table.Name = "panel_table";
            this.panel_table.RowCount = 7;
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 230F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 28F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 11F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 40F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 60F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.panel_table.Size = new System.Drawing.Size(689, 720);
            this.panel_table.TabIndex = 0;
            // 
            // img_title
            // 
            this.img_title.Image = global::AuthClient.Properties.Resources.settingPcTitle;
            this.img_title.Location = new System.Drawing.Point(0, 230);
            this.img_title.Margin = new System.Windows.Forms.Padding(0);
            this.img_title.Name = "img_title";
            this.img_title.Size = new System.Drawing.Size(62, 28);
            this.img_title.TabIndex = 0;
            this.img_title.TabStop = false;
            // 
            // img_subtitle
            // 
            this.panel_table.SetColumnSpan(this.img_subtitle, 2);
            this.img_subtitle.Dock = System.Windows.Forms.DockStyle.Fill;
            this.img_subtitle.Image = global::AuthClient.Properties.Resources.settingPcSubtitle;
            this.img_subtitle.Location = new System.Drawing.Point(0, 269);
            this.img_subtitle.Margin = new System.Windows.Forms.Padding(0);
            this.img_subtitle.Name = "img_subtitle";
            this.img_subtitle.Size = new System.Drawing.Size(343, 40);
            this.img_subtitle.TabIndex = 1;
            this.img_subtitle.TabStop = false;
            // 
            // panel1
            // 
            this.panel_table.SetColumnSpan(this.panel1, 3);
            this.panel1.Controls.Add(this.panel_list);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel1.Location = new System.Drawing.Point(0, 369);
            this.panel1.Margin = new System.Windows.Forms.Padding(0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(665, 331);
            this.panel1.TabIndex = 3;
            // 
            // panel_list
            // 
            this.panel_list.AutoScroll = true;
            this.panel_list.BackColor = System.Drawing.Color.Transparent;
            this.panel_list.Location = new System.Drawing.Point(0, 0);
            this.panel_list.Margin = new System.Windows.Forms.Padding(0);
            this.panel_list.Name = "panel_list";
            this.panel_list.Size = new System.Drawing.Size(678, 331);
            this.panel_list.TabIndex = 2;
            // 
            // SettingPC
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Transparent;
            this.Controls.Add(this.panel_table);
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "SettingPC";
            this.Size = new System.Drawing.Size(689, 720);
            this.VisibleChanged += new System.EventHandler(this.SettingPC_VisibleChanged);
            this.panel_table.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.img_title)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_subtitle)).EndInit();
            this.panel1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel panel_table;
        private System.Windows.Forms.PictureBox img_title;
        private System.Windows.Forms.PictureBox img_subtitle;
        private System.Windows.Forms.FlowLayoutPanel panel_list;
        private System.Windows.Forms.Panel panel1;
    }
}
