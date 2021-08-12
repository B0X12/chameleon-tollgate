
namespace AuthClient.tollgate.home.history
{
    partial class HistoryEntry
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
            this.img_bottomLine = new System.Windows.Forms.PictureBox();
            this.img_label = new System.Windows.Forms.PictureBox();
            this.img_result = new System.Windows.Forms.PictureBox();
            this.label = new System.Windows.Forms.Label();
            this.img_pipe = new System.Windows.Forms.PictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.img_bottomLine)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_label)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_result)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_pipe)).BeginInit();
            this.SuspendLayout();
            // 
            // img_bottomLine
            // 
            this.img_bottomLine.BackgroundImage = global::AuthClient.Properties.Resources.historyBottomLine;
            this.img_bottomLine.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.img_bottomLine.Location = new System.Drawing.Point(0, 53);
            this.img_bottomLine.Margin = new System.Windows.Forms.Padding(0);
            this.img_bottomLine.Name = "img_bottomLine";
            this.img_bottomLine.Size = new System.Drawing.Size(524, 1);
            this.img_bottomLine.TabIndex = 1;
            this.img_bottomLine.TabStop = false;
            // 
            // img_label
            // 
            this.img_label.Location = new System.Drawing.Point(1, 16);
            this.img_label.Margin = new System.Windows.Forms.Padding(0);
            this.img_label.Name = "img_label";
            this.img_label.Size = new System.Drawing.Size(60, 27);
            this.img_label.TabIndex = 0;
            this.img_label.TabStop = false;
            // 
            // img_result
            // 
            this.img_result.Location = new System.Drawing.Point(450, 14);
            this.img_result.Name = "img_result";
            this.img_result.Size = new System.Drawing.Size(67, 26);
            this.img_result.TabIndex = 2;
            this.img_result.TabStop = false;
            // 
            // label
            // 
            this.label.AutoSize = true;
            this.label.BackColor = System.Drawing.Color.White;
            this.label.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(164)))), ((int)(((byte)(164)))), ((int)(((byte)(164)))));
            this.label.Location = new System.Drawing.Point(93, 20);
            this.label.Name = "label";
            this.label.Size = new System.Drawing.Size(38, 12);
            this.label.TabIndex = 3;
            this.label.Text = "label1";
            // 
            // img_pipe
            // 
            this.img_pipe.Image = global::AuthClient.Properties.Resources.historyPipeline;
            this.img_pipe.Location = new System.Drawing.Point(72, 18);
            this.img_pipe.Margin = new System.Windows.Forms.Padding(0);
            this.img_pipe.Name = "img_pipe";
            this.img_pipe.Size = new System.Drawing.Size(1, 17);
            this.img_pipe.TabIndex = 4;
            this.img_pipe.TabStop = false;
            // 
            // HistoryEntry
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.img_pipe);
            this.Controls.Add(this.label);
            this.Controls.Add(this.img_result);
            this.Controls.Add(this.img_bottomLine);
            this.Controls.Add(this.img_label);
            this.Margin = new System.Windows.Forms.Padding(35, 0, 0, 0);
            this.Name = "HistoryEntry";
            this.Size = new System.Drawing.Size(524, 54);
            ((System.ComponentModel.ISupportInitialize)(this.img_bottomLine)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_label)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_result)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_pipe)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.PictureBox img_label;
        private System.Windows.Forms.PictureBox img_bottomLine;
        private System.Windows.Forms.PictureBox img_result;
        private System.Windows.Forms.Label label;
        private System.Windows.Forms.PictureBox img_pipe;
    }
}
