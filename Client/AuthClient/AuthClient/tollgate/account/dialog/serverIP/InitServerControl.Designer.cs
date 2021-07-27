
namespace AuthClient.tollgate.account.dialog
{
    partial class InitServerControl
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
            this.panel_base_server = new System.Windows.Forms.TableLayoutPanel();
            this.img_title = new System.Windows.Forms.PictureBox();
            this.button_connect = new AuthClient.tollgate.account.dialog.ConnectButton();
            this.text_serverIP = new AuthClient.tollgate.account.dialog.TextServerControl();
            this.panel_base_server.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.img_title)).BeginInit();
            this.SuspendLayout();
            // 
            // panel_base_server
            // 
            this.panel_base_server.ColumnCount = 3;
            this.panel_base_server.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.panel_base_server.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 341F));
            this.panel_base_server.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.panel_base_server.Controls.Add(this.img_title, 1, 1);
            this.panel_base_server.Controls.Add(this.button_connect, 1, 5);
            this.panel_base_server.Controls.Add(this.text_serverIP, 1, 3);
            this.panel_base_server.Location = new System.Drawing.Point(0, 0);
            this.panel_base_server.Margin = new System.Windows.Forms.Padding(0);
            this.panel_base_server.Name = "panel_base_server";
            this.panel_base_server.RowCount = 6;
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 146F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 78F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 88F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 41F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 33F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.panel_base_server.Size = new System.Drawing.Size(531, 531);
            this.panel_base_server.TabIndex = 0;
            // 
            // img_title
            // 
            this.img_title.Image = global::AuthClient.Properties.Resources.initServerTitle;
            this.img_title.Location = new System.Drawing.Point(95, 146);
            this.img_title.Margin = new System.Windows.Forms.Padding(0);
            this.img_title.Name = "img_title";
            this.img_title.Size = new System.Drawing.Size(336, 78);
            this.img_title.TabIndex = 2;
            this.img_title.TabStop = false;
            // 
            // button_connect
            // 
            this.button_connect.BackColor = System.Drawing.Color.Transparent;
            this.button_connect.Location = new System.Drawing.Point(95, 386);
            this.button_connect.Margin = new System.Windows.Forms.Padding(0);
            this.button_connect.Name = "button_connect";
            this.button_connect.Size = new System.Drawing.Size(341, 41);
            this.button_connect.TabIndex = 0;
            this.button_connect.Click += new System.EventHandler(this.button_connect_Click);
            // 
            // text_serverIP
            // 
            this.text_serverIP.BackColor = System.Drawing.Color.Transparent;
            this.text_serverIP.Location = new System.Drawing.Point(95, 312);
            this.text_serverIP.Margin = new System.Windows.Forms.Padding(0);
            this.text_serverIP.Name = "text_serverIP";
            this.text_serverIP.Size = new System.Drawing.Size(341, 41);
            this.text_serverIP.TabIndex = 1;
            // 
            // InitServerControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.panel_base_server);
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "InitServerControl";
            this.Size = new System.Drawing.Size(531, 531);
            this.panel_base_server.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.img_title)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private TextServerControl text_serverIP;
        private System.Windows.Forms.PictureBox img_title;
        private System.Windows.Forms.TableLayoutPanel panel_base_server;
        private ConnectButton button_connect;
    }
}
