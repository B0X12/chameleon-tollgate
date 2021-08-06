
namespace AuthClient.tollgate.home.setting
{
    partial class PCEntry
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
            this.label_name = new System.Windows.Forms.Label();
            this.img_edit = new System.Windows.Forms.PictureBox();
            this.img_crown = new System.Windows.Forms.PictureBox();
            this.img_back = new System.Windows.Forms.PictureBox();
            this.text_edit = new System.Windows.Forms.TextBox();
            ((System.ComponentModel.ISupportInitialize)(this.img_edit)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_crown)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_back)).BeginInit();
            this.SuspendLayout();
            // 
            // label_name
            // 
            this.label_name.AutoSize = true;
            this.label_name.BackColor = System.Drawing.Color.White;
            this.label_name.ForeColor = System.Drawing.Color.Black;
            this.label_name.Location = new System.Drawing.Point(18, 41);
            this.label_name.Margin = new System.Windows.Forms.Padding(0);
            this.label_name.Name = "label_name";
            this.label_name.Size = new System.Drawing.Size(25, 12);
            this.label_name.TabIndex = 2;
            this.label_name.Text = "test";
            // 
            // img_edit
            // 
            this.img_edit.BackColor = System.Drawing.Color.White;
            this.img_edit.Image = global::AuthClient.Properties.Resources.settingPcEdit;
            this.img_edit.Location = new System.Drawing.Point(133, 64);
            this.img_edit.Margin = new System.Windows.Forms.Padding(0);
            this.img_edit.Name = "img_edit";
            this.img_edit.Size = new System.Drawing.Size(52, 24);
            this.img_edit.TabIndex = 3;
            this.img_edit.TabStop = false;
            this.img_edit.Click += new System.EventHandler(this.img_edit_Click);
            // 
            // img_crown
            // 
            this.img_crown.BackColor = System.Drawing.Color.White;
            this.img_crown.Image = global::AuthClient.Properties.Resources.settingPcCrown;
            this.img_crown.Location = new System.Drawing.Point(20, 16);
            this.img_crown.Margin = new System.Windows.Forms.Padding(0);
            this.img_crown.Name = "img_crown";
            this.img_crown.Size = new System.Drawing.Size(24, 22);
            this.img_crown.TabIndex = 1;
            this.img_crown.TabStop = false;
            this.img_crown.Visible = false;
            // 
            // img_back
            // 
            this.img_back.Dock = System.Windows.Forms.DockStyle.Fill;
            this.img_back.Image = global::AuthClient.Properties.Resources.settingPC;
            this.img_back.Location = new System.Drawing.Point(0, 0);
            this.img_back.Margin = new System.Windows.Forms.Padding(0);
            this.img_back.Name = "img_back";
            this.img_back.Size = new System.Drawing.Size(203, 148);
            this.img_back.TabIndex = 0;
            this.img_back.TabStop = false;
            // 
            // text_edit
            // 
            this.text_edit.BackColor = System.Drawing.Color.White;
            this.text_edit.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.text_edit.Location = new System.Drawing.Point(22, 41);
            this.text_edit.Margin = new System.Windows.Forms.Padding(0);
            this.text_edit.Name = "text_edit";
            this.text_edit.Size = new System.Drawing.Size(167, 14);
            this.text_edit.TabIndex = 4;
            this.text_edit.Visible = false;
            this.text_edit.KeyDown += new System.Windows.Forms.KeyEventHandler(this.text_edit_KeyDown);
            this.text_edit.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.text_edit_KeyPress);
            // 
            // PCEntry
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Transparent;
            this.Controls.Add(this.text_edit);
            this.Controls.Add(this.img_edit);
            this.Controls.Add(this.label_name);
            this.Controls.Add(this.img_crown);
            this.Controls.Add(this.img_back);
            this.Margin = new System.Windows.Forms.Padding(0, 0, 23, 29);
            this.Name = "PCEntry";
            this.Size = new System.Drawing.Size(203, 148);
            ((System.ComponentModel.ISupportInitialize)(this.img_edit)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_crown)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_back)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.PictureBox img_back;
        private System.Windows.Forms.PictureBox img_crown;
        private System.Windows.Forms.Label label_name;
        private System.Windows.Forms.PictureBox img_edit;
        private System.Windows.Forms.TextBox text_edit;
    }
}
