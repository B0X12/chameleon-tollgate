
namespace AuthClient.tollgate.home.setting
{
    partial class USBEntry
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
            this.img_button = new System.Windows.Forms.PictureBox();
            this.img_usb = new System.Windows.Forms.PictureBox();
            this.label_name = new System.Windows.Forms.Label();
            this.text_edit = new System.Windows.Forms.TextBox();
            ((System.ComponentModel.ISupportInitialize)(this.img_button)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_usb)).BeginInit();
            this.SuspendLayout();
            // 
            // img_button
            // 
            this.img_button.Image = global::AuthClient.Properties.Resources.settingUsbDel;
            this.img_button.Location = new System.Drawing.Point(0, 20);
            this.img_button.Margin = new System.Windows.Forms.Padding(0);
            this.img_button.Name = "img_button";
            this.img_button.Size = new System.Drawing.Size(30, 27);
            this.img_button.TabIndex = 0;
            this.img_button.TabStop = false;
            // 
            // img_usb
            // 
            this.img_usb.Image = global::AuthClient.Properties.Resources.settingUsbUsing;
            this.img_usb.Location = new System.Drawing.Point(53, 0);
            this.img_usb.Margin = new System.Windows.Forms.Padding(0);
            this.img_usb.Name = "img_usb";
            this.img_usb.Size = new System.Drawing.Size(192, 68);
            this.img_usb.TabIndex = 1;
            this.img_usb.TabStop = false;
            // 
            // label_name
            // 
            this.label_name.AutoSize = true;
            this.label_name.ForeColor = System.Drawing.Color.White;
            this.label_name.Location = new System.Drawing.Point(92, 28);
            this.label_name.Margin = new System.Windows.Forms.Padding(0);
            this.label_name.Name = "label_name";
            this.label_name.Size = new System.Drawing.Size(38, 12);
            this.label_name.TabIndex = 2;
            this.label_name.Text = "label1";
            this.label_name.Click += new System.EventHandler(this.label_name_Click);
            // 
            // text_edit
            // 
            this.text_edit.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.text_edit.Location = new System.Drawing.Point(92, 28);
            this.text_edit.Name = "text_edit";
            this.text_edit.Size = new System.Drawing.Size(75, 14);
            this.text_edit.TabIndex = 3;
            this.text_edit.Visible = false;
            this.text_edit.KeyDown += new System.Windows.Forms.KeyEventHandler(this.text_edit_KeyDown);
            this.text_edit.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.text_edit_KeyPress);
            // 
            // USBEntry
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Transparent;
            this.Controls.Add(this.text_edit);
            this.Controls.Add(this.label_name);
            this.Controls.Add(this.img_usb);
            this.Controls.Add(this.img_button);
            this.Margin = new System.Windows.Forms.Padding(0, 0, 95, 30);
            this.Name = "USBEntry";
            this.Size = new System.Drawing.Size(245, 68);
            ((System.ComponentModel.ISupportInitialize)(this.img_button)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_usb)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.PictureBox img_button;
        private System.Windows.Forms.PictureBox img_usb;
        private System.Windows.Forms.Label label_name;
        private System.Windows.Forms.TextBox text_edit;
    }
}
