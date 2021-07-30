
namespace AuthClient.tollgate.home.main.dialog
{
    partial class MainControl
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
            this.img_select_title = new System.Windows.Forms.PictureBox();
            this.img_list_title = new System.Windows.Forms.PictureBox();
            this.panel_select_border = new System.Windows.Forms.Panel();
            this.panel_select_flow = new DoubleBufferPanel();
            this.usingList = new AuthClient.tollgate.home.main.dialog.UsingList();
            this.card_finger = new AuthClient.tollgate.home.main.dialog.SelectCard();
            this.card_face = new AuthClient.tollgate.home.main.dialog.SelectCard();
            this.card_usb = new AuthClient.tollgate.home.main.dialog.SelectCard();
            this.card_otp = new AuthClient.tollgate.home.main.dialog.SelectCard();
            this.card_qr = new AuthClient.tollgate.home.main.dialog.SelectCard();
            this.card_pattern = new AuthClient.tollgate.home.main.dialog.SelectCard();
            this.panel_table.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.img_select_title)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_list_title)).BeginInit();
            this.panel_select_border.SuspendLayout();
            this.panel_select_flow.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel_table
            // 
            this.panel_table.ColumnCount = 4;
            this.panel_table.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 74F));
            this.panel_table.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 9F));
            this.panel_table.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.panel_table.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 115F));
            this.panel_table.Controls.Add(this.img_select_title, 2, 5);
            this.panel_table.Controls.Add(this.img_list_title, 2, 1);
            this.panel_table.Controls.Add(this.usingList, 1, 3);
            this.panel_table.Controls.Add(this.panel_select_border, 1, 7);
            this.panel_table.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel_table.Location = new System.Drawing.Point(0, 0);
            this.panel_table.Margin = new System.Windows.Forms.Padding(0);
            this.panel_table.Name = "panel_table";
            this.panel_table.RowCount = 8;
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 150F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 60F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 11F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 87F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 50F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 60F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 38F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.panel_table.Size = new System.Drawing.Size(998, 720);
            this.panel_table.TabIndex = 0;
            // 
            // img_select_title
            // 
            this.img_select_title.BackColor = System.Drawing.Color.Transparent;
            this.img_select_title.Dock = System.Windows.Forms.DockStyle.Fill;
            this.img_select_title.Image = global::AuthClient.Properties.Resources.mainSelectTitle;
            this.img_select_title.Location = new System.Drawing.Point(83, 358);
            this.img_select_title.Margin = new System.Windows.Forms.Padding(0);
            this.img_select_title.Name = "img_select_title";
            this.img_select_title.Size = new System.Drawing.Size(800, 60);
            this.img_select_title.TabIndex = 1;
            this.img_select_title.TabStop = false;
            // 
            // img_list_title
            // 
            this.img_list_title.BackColor = System.Drawing.Color.Transparent;
            this.img_list_title.Dock = System.Windows.Forms.DockStyle.Fill;
            this.img_list_title.Image = global::AuthClient.Properties.Resources.mainListTitle;
            this.img_list_title.Location = new System.Drawing.Point(83, 150);
            this.img_list_title.Margin = new System.Windows.Forms.Padding(0);
            this.img_list_title.Name = "img_list_title";
            this.img_list_title.Size = new System.Drawing.Size(800, 60);
            this.img_list_title.TabIndex = 0;
            this.img_list_title.TabStop = false;
            // 
            // panel_select_border
            // 
            this.panel_table.SetColumnSpan(this.panel_select_border, 2);
            this.panel_select_border.Controls.Add(this.panel_select_flow);
            this.panel_select_border.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel_select_border.Location = new System.Drawing.Point(74, 456);
            this.panel_select_border.Margin = new System.Windows.Forms.Padding(0);
            this.panel_select_border.Name = "panel_select_border";
            this.panel_select_border.Size = new System.Drawing.Size(809, 264);
            this.panel_select_border.TabIndex = 3;
            // 
            // panel_select_flow
            // 
            this.panel_select_flow.AutoScroll = true;
            this.panel_select_flow.Controls.Add(this.card_finger);
            this.panel_select_flow.Controls.Add(this.card_face);
            this.panel_select_flow.Controls.Add(this.card_usb);
            this.panel_select_flow.Controls.Add(this.card_otp);
            this.panel_select_flow.Controls.Add(this.card_qr);
            this.panel_select_flow.Controls.Add(this.card_pattern);
            this.panel_select_flow.Location = new System.Drawing.Point(0, 0);
            this.panel_select_flow.Margin = new System.Windows.Forms.Padding(0);
            this.panel_select_flow.Name = "panel_select_flow";
            this.panel_select_flow.Size = new System.Drawing.Size(826, 264);
            this.panel_select_flow.TabIndex = 0;
            // 
            // usingList
            // 
            this.usingList.BackColor = System.Drawing.Color.Transparent;
            this.panel_table.SetColumnSpan(this.usingList, 2);
            this.usingList.Location = new System.Drawing.Point(74, 221);
            this.usingList.Margin = new System.Windows.Forms.Padding(0);
            this.usingList.Name = "usingList";
            this.usingList.Size = new System.Drawing.Size(610, 87);
            this.usingList.TabIndex = 2;
            // 
            // card_finger
            // 
            this.card_finger.BackColor = System.Drawing.Color.Transparent;
            this.card_finger.Factor = AuthClient.tollgate.define.Define.Factor.FINGER;
            this.card_finger.Location = new System.Drawing.Point(0, 0);
            this.card_finger.Margin = new System.Windows.Forms.Padding(0);
            this.card_finger.Name = "card_finger";
            this.card_finger.on = false;
            this.card_finger.Size = new System.Drawing.Size(269, 310);
            this.card_finger.TabIndex = 0;
            // 
            // card_face
            // 
            this.card_face.BackColor = System.Drawing.Color.Transparent;
            this.card_face.Factor = AuthClient.tollgate.define.Define.Factor.FACEID;
            this.card_face.Location = new System.Drawing.Point(269, 0);
            this.card_face.Margin = new System.Windows.Forms.Padding(0);
            this.card_face.Name = "card_face";
            this.card_face.on = false;
            this.card_face.Size = new System.Drawing.Size(269, 310);
            this.card_face.TabIndex = 1;
            // 
            // card_usb
            // 
            this.card_usb.BackColor = System.Drawing.Color.Transparent;
            this.card_usb.Factor = AuthClient.tollgate.define.Define.Factor.USB;
            this.card_usb.Location = new System.Drawing.Point(538, 0);
            this.card_usb.Margin = new System.Windows.Forms.Padding(0);
            this.card_usb.Name = "card_usb";
            this.card_usb.on = false;
            this.card_usb.Size = new System.Drawing.Size(269, 310);
            this.card_usb.TabIndex = 2;
            // 
            // card_otp
            // 
            this.card_otp.BackColor = System.Drawing.Color.Transparent;
            this.card_otp.Factor = AuthClient.tollgate.define.Define.Factor.OTP;
            this.card_otp.Location = new System.Drawing.Point(0, 310);
            this.card_otp.Margin = new System.Windows.Forms.Padding(0);
            this.card_otp.Name = "card_otp";
            this.card_otp.on = false;
            this.card_otp.Size = new System.Drawing.Size(269, 310);
            this.card_otp.TabIndex = 3;
            // 
            // card_qr
            // 
            this.card_qr.BackColor = System.Drawing.Color.Transparent;
            this.card_qr.Factor = AuthClient.tollgate.define.Define.Factor.QR;
            this.card_qr.Location = new System.Drawing.Point(269, 310);
            this.card_qr.Margin = new System.Windows.Forms.Padding(0);
            this.card_qr.Name = "card_qr";
            this.card_qr.on = false;
            this.card_qr.Size = new System.Drawing.Size(269, 310);
            this.card_qr.TabIndex = 4;
            // 
            // card_pattern
            // 
            this.card_pattern.BackColor = System.Drawing.Color.Transparent;
            this.card_pattern.Factor = AuthClient.tollgate.define.Define.Factor.PATTERN;
            this.card_pattern.Location = new System.Drawing.Point(538, 310);
            this.card_pattern.Margin = new System.Windows.Forms.Padding(0);
            this.card_pattern.Name = "card_pattern";
            this.card_pattern.on = false;
            this.card_pattern.Size = new System.Drawing.Size(269, 310);
            this.card_pattern.TabIndex = 5;
            // 
            // MainControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Transparent;
            this.Controls.Add(this.panel_table);
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "MainControl";
            this.Size = new System.Drawing.Size(998, 720);
            this.panel_table.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.img_select_title)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_list_title)).EndInit();
            this.panel_select_border.ResumeLayout(false);
            this.panel_select_flow.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel panel_table;
        private System.Windows.Forms.PictureBox img_list_title;
        private System.Windows.Forms.PictureBox img_select_title;
        private UsingList usingList;
        private System.Windows.Forms.Panel panel_select_border;
        private DoubleBufferPanel panel_select_flow;
        private SelectCard card_finger;
        private SelectCard card_face;
        private SelectCard card_usb;
        private SelectCard card_otp;
        private SelectCard card_qr;
        private SelectCard card_pattern;
    }
}
