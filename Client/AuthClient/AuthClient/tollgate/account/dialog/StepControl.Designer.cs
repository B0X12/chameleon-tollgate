
namespace AuthClient.tollgate.account.dialog
{
    partial class StepControl
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
            this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
            this.img_step1 = new System.Windows.Forms.PictureBox();
            this.img_step3 = new System.Windows.Forms.PictureBox();
            this.img_step2 = new System.Windows.Forms.PictureBox();
            this.tableLayoutPanel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.img_step1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_step3)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_step2)).BeginInit();
            this.SuspendLayout();
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.ColumnCount = 7;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 11F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 15F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 11F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 15F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 11F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel1.Controls.Add(this.img_step1, 1, 0);
            this.tableLayoutPanel1.Controls.Add(this.img_step3, 5, 0);
            this.tableLayoutPanel1.Controls.Add(this.img_step2, 3, 0);
            this.tableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel1.Margin = new System.Windows.Forms.Padding(0);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 2;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 11F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel1.Size = new System.Drawing.Size(531, 189);
            this.tableLayoutPanel1.TabIndex = 3;
            // 
            // img_step1
            // 
            this.img_step1.BackColor = System.Drawing.Color.Transparent;
            this.img_step1.Image = global::AuthClient.Properties.Resources.stepOff;
            this.img_step1.Location = new System.Drawing.Point(234, 0);
            this.img_step1.Margin = new System.Windows.Forms.Padding(0);
            this.img_step1.Name = "img_step1";
            this.img_step1.Size = new System.Drawing.Size(11, 11);
            this.img_step1.TabIndex = 0;
            this.img_step1.TabStop = false;
            // 
            // img_step3
            // 
            this.img_step3.BackColor = System.Drawing.Color.Transparent;
            this.img_step3.Image = global::AuthClient.Properties.Resources.stepOff;
            this.img_step3.Location = new System.Drawing.Point(286, 0);
            this.img_step3.Margin = new System.Windows.Forms.Padding(0);
            this.img_step3.Name = "img_step3";
            this.img_step3.Size = new System.Drawing.Size(11, 11);
            this.img_step3.TabIndex = 2;
            this.img_step3.TabStop = false;
            // 
            // img_step2
            // 
            this.img_step2.BackColor = System.Drawing.Color.Transparent;
            this.img_step2.Image = global::AuthClient.Properties.Resources.stepOff;
            this.img_step2.Location = new System.Drawing.Point(260, 0);
            this.img_step2.Margin = new System.Windows.Forms.Padding(0);
            this.img_step2.Name = "img_step2";
            this.img_step2.Size = new System.Drawing.Size(11, 11);
            this.img_step2.TabIndex = 1;
            this.img_step2.TabStop = false;
            // 
            // StepControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.tableLayoutPanel1);
            this.Name = "StepControl";
            this.Size = new System.Drawing.Size(531, 189);
            this.tableLayoutPanel1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.img_step1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_step3)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_step2)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.PictureBox img_step1;
        private System.Windows.Forms.PictureBox img_step2;
        private System.Windows.Forms.PictureBox img_step3;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel1;
    }
}
