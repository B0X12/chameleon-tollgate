
namespace AuthClient.tollgate.home.setting
{
    partial class TG_TextBox
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
            this.textBox = new System.Windows.Forms.TextBox();
            this.img_check = new System.Windows.Forms.PictureBox();
            this.img_bg = new System.Windows.Forms.PictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.img_check)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_bg)).BeginInit();
            this.SuspendLayout();
            // 
            // textBox
            // 
            this.textBox.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(248)))), ((int)(((byte)(248)))), ((int)(((byte)(247)))));
            this.textBox.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.textBox.Font = new System.Drawing.Font("새굴림", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.textBox.Location = new System.Drawing.Point(9, 13);
            this.textBox.Margin = new System.Windows.Forms.Padding(0);
            this.textBox.Name = "textBox";
            this.textBox.Size = new System.Drawing.Size(287, 15);
            this.textBox.TabIndex = 3;
            this.textBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.textBox_KeyPress);
            // 
            // img_check
            // 
            this.img_check.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(248)))), ((int)(((byte)(248)))), ((int)(((byte)(247)))));
            this.img_check.Image = global::AuthClient.Properties.Resources.uncheck;
            this.img_check.Location = new System.Drawing.Point(310, 13);
            this.img_check.Margin = new System.Windows.Forms.Padding(0);
            this.img_check.Name = "img_check";
            this.img_check.Size = new System.Drawing.Size(15, 15);
            this.img_check.TabIndex = 4;
            this.img_check.TabStop = false;
            // 
            // img_bg
            // 
            this.img_bg.Dock = System.Windows.Forms.DockStyle.Fill;
            this.img_bg.Image = global::AuthClient.Properties.Resources.textbox;
            this.img_bg.Location = new System.Drawing.Point(0, 0);
            this.img_bg.Margin = new System.Windows.Forms.Padding(0);
            this.img_bg.Name = "img_bg";
            this.img_bg.Size = new System.Drawing.Size(341, 41);
            this.img_bg.TabIndex = 0;
            this.img_bg.TabStop = false;
            // 
            // TG_TextBox
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Transparent;
            this.Controls.Add(this.img_check);
            this.Controls.Add(this.textBox);
            this.Controls.Add(this.img_bg);
            this.Name = "TG_TextBox";
            this.Size = new System.Drawing.Size(341, 41);
            ((System.ComponentModel.ISupportInitialize)(this.img_check)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_bg)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.PictureBox img_bg;
        private System.Windows.Forms.TextBox textBox;
        private System.Windows.Forms.PictureBox img_check;
    }
}
