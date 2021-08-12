
namespace AuthClient.tollgate.home.history
{
    partial class HistoryControl
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
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.panel_listBg = new AuthClient.tollgate.DoubleBufferPanel();
            this.panel_border_list = new AuthClient.tollgate.DoubleBufferPanel();
            this.panel_list = new System.Windows.Forms.FlowLayoutPanel();
            this.panel_table.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.panel_listBg.SuspendLayout();
            this.panel_border_list.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel_table
            // 
            this.panel_table.ColumnCount = 4;
            this.panel_table.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 69F));
            this.panel_table.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 7F));
            this.panel_table.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 605F));
            this.panel_table.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.panel_table.Controls.Add(this.panel_listBg, 1, 2);
            this.panel_table.Controls.Add(this.pictureBox1, 2, 1);
            this.panel_table.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel_table.Location = new System.Drawing.Point(0, 0);
            this.panel_table.Margin = new System.Windows.Forms.Padding(0);
            this.panel_table.Name = "panel_table";
            this.panel_table.RowCount = 3;
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 184F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 45F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.panel_table.Size = new System.Drawing.Size(998, 720);
            this.panel_table.TabIndex = 0;
            // 
            // pictureBox1
            // 
            this.pictureBox1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pictureBox1.Image = global::AuthClient.Properties.Resources.historyTitle;
            this.pictureBox1.Location = new System.Drawing.Point(76, 184);
            this.pictureBox1.Margin = new System.Windows.Forms.Padding(0);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(605, 45);
            this.pictureBox1.TabIndex = 1;
            this.pictureBox1.TabStop = false;
            // 
            // panel_listBg
            // 
            this.panel_listBg.BackgroundImage = global::AuthClient.Properties.Resources.historyListBg;
            this.panel_table.SetColumnSpan(this.panel_listBg, 2);
            this.panel_listBg.Controls.Add(this.panel_border_list);
            this.panel_listBg.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel_listBg.Location = new System.Drawing.Point(69, 229);
            this.panel_listBg.Margin = new System.Windows.Forms.Padding(0);
            this.panel_listBg.Name = "panel_listBg";
            this.panel_listBg.Size = new System.Drawing.Size(612, 491);
            this.panel_listBg.TabIndex = 0;
            // 
            // panel_border_list
            // 
            this.panel_border_list.Controls.Add(this.panel_list);
            this.panel_border_list.Location = new System.Drawing.Point(0, 28);
            this.panel_border_list.Name = "panel_border_list";
            this.panel_border_list.Size = new System.Drawing.Size(612, 463);
            this.panel_border_list.TabIndex = 0;
            // 
            // panel_list
            // 
            this.panel_list.AutoScroll = true;
            this.panel_list.FlowDirection = System.Windows.Forms.FlowDirection.TopDown;
            this.panel_list.Location = new System.Drawing.Point(0, 0);
            this.panel_list.Margin = new System.Windows.Forms.Padding(0);
            this.panel_list.Name = "panel_list";
            this.panel_list.Size = new System.Drawing.Size(612, 463);
            this.panel_list.TabIndex = 0;
            this.panel_list.WrapContents = false;
            // 
            // HistoryControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Transparent;
            this.Controls.Add(this.panel_table);
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "HistoryControl";
            this.Size = new System.Drawing.Size(998, 720);
            this.VisibleChanged += new System.EventHandler(this.HistoryControl_VisibleChanged);
            this.panel_table.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.panel_listBg.ResumeLayout(false);
            this.panel_border_list.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel panel_table;
        private DoubleBufferPanel panel_listBg;
        private System.Windows.Forms.PictureBox pictureBox1;
        private DoubleBufferPanel panel_border_list;
        private System.Windows.Forms.FlowLayoutPanel panel_list;
    }
}
