
namespace AuthClient.tollgate.home.main.dialog
{
    partial class CheckEntry
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
            this.img_stat = new System.Windows.Forms.PictureBox();
            this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
            this.img_text = new System.Windows.Forms.PictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.img_stat)).BeginInit();
            this.tableLayoutPanel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.img_text)).BeginInit();
            this.SuspendLayout();
            // 
            // img_stat
            // 
            this.img_stat.Image = global::AuthClient.Properties.Resources.checkStatWait;
            this.img_stat.Location = new System.Drawing.Point(0, 5);
            this.img_stat.Margin = new System.Windows.Forms.Padding(0);
            this.img_stat.Name = "img_stat";
            this.img_stat.Size = new System.Drawing.Size(13, 13);
            this.img_stat.TabIndex = 0;
            this.img_stat.TabStop = false;
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.ColumnCount = 3;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 13F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 23F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 349F));
            this.tableLayoutPanel1.Controls.Add(this.img_stat, 0, 1);
            this.tableLayoutPanel1.Controls.Add(this.img_text, 2, 0);
            this.tableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel1.Margin = new System.Windows.Forms.Padding(0);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 2;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 5F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel1.Size = new System.Drawing.Size(385, 52);
            this.tableLayoutPanel1.TabIndex = 1;
            // 
            // img_text
            // 
            this.img_text.Dock = System.Windows.Forms.DockStyle.Fill;
            this.img_text.Image = global::AuthClient.Properties.Resources.checkPhoneText;
            this.img_text.Location = new System.Drawing.Point(36, 0);
            this.img_text.Margin = new System.Windows.Forms.Padding(0);
            this.img_text.Name = "img_text";
            this.tableLayoutPanel1.SetRowSpan(this.img_text, 2);
            this.img_text.Size = new System.Drawing.Size(349, 52);
            this.img_text.TabIndex = 1;
            this.img_text.TabStop = false;
            // 
            // CheckEntry
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.tableLayoutPanel1);
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "CheckEntry";
            this.Size = new System.Drawing.Size(385, 52);
            ((System.ComponentModel.ISupportInitialize)(this.img_stat)).EndInit();
            this.tableLayoutPanel1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.img_text)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.PictureBox img_stat;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel1;
        private System.Windows.Forms.PictureBox img_text;
    }
}
