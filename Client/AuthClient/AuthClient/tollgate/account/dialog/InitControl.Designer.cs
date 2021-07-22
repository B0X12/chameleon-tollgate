
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
            this.panel_flow = new System.Windows.Forms.FlowLayoutPanel();
            this.panel_init.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel_init
            // 
            this.panel_init.ColumnCount = 2;
            this.panel_init.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.panel_init.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 438F));
            this.panel_init.Controls.Add(this.panel_flow, 1, 0);
            this.panel_init.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel_init.Location = new System.Drawing.Point(0, 0);
            this.panel_init.Margin = new System.Windows.Forms.Padding(0);
            this.panel_init.Name = "panel_init";
            this.panel_init.RowCount = 2;
            this.panel_init.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 560F));
            this.panel_init.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.panel_init.Size = new System.Drawing.Size(1280, 720);
            this.panel_init.TabIndex = 0;
            this.panel_init.Click += new System.EventHandler(this.panel_init_Click);
            // 
            // panel_flow
            // 
            this.panel_flow.AutoScroll = true;
            this.panel_flow.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel_flow.Location = new System.Drawing.Point(842, 0);
            this.panel_flow.Margin = new System.Windows.Forms.Padding(0);
            this.panel_flow.Name = "panel_flow";
            this.panel_flow.Size = new System.Drawing.Size(438, 560);
            this.panel_flow.TabIndex = 0;
            this.panel_flow.WrapContents = false;
            // 
            // InitControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.Controls.Add(this.panel_init);
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "InitControl";
            this.Size = new System.Drawing.Size(1280, 720);
            this.Load += new System.EventHandler(this.InitControl_Load);
            this.panel_init.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel panel_init;
        private System.Windows.Forms.FlowLayoutPanel panel_flow;
    }
}
