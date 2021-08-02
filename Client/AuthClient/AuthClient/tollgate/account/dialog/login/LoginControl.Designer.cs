
namespace AuthClient.tollgate.account.dialog
{
    partial class LoginControl
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
            this.text_id = new AuthClient.tollgate.account.dialog.LoginID();
            this.text_pwd = new AuthClient.tollgate.account.dialog.LoginPwd();
            this.img_title = new AuthClient.tollgate.account.dialog.LoginTitle();
            this.btn_login = new AuthClient.tollgate.account.dialog.LoginButton();
            this.panel_base_server.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel_base_server
            // 
            this.panel_base_server.ColumnCount = 3;
            this.panel_base_server.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.panel_base_server.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 341F));
            this.panel_base_server.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.panel_base_server.Controls.Add(this.text_id, 1, 3);
            this.panel_base_server.Controls.Add(this.text_pwd, 1, 5);
            this.panel_base_server.Controls.Add(this.img_title, 1, 1);
            this.panel_base_server.Controls.Add(this.btn_login, 1, 7);
            this.panel_base_server.Location = new System.Drawing.Point(0, 0);
            this.panel_base_server.Margin = new System.Windows.Forms.Padding(0);
            this.panel_base_server.Name = "panel_base_server";
            this.panel_base_server.RowCount = 8;
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 146F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 78F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 68F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 41F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 8F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 41F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 39F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.panel_base_server.Size = new System.Drawing.Size(531, 531);
            this.panel_base_server.TabIndex = 1;
            // 
            // text_id
            // 
            this.text_id.BackColor = System.Drawing.Color.Transparent;
            this.text_id.Fix = true;
            this.text_id.Font = new System.Drawing.Font("새굴림", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.text_id.Location = new System.Drawing.Point(95, 292);
            this.text_id.Margin = new System.Windows.Forms.Padding(0);
            this.text_id.Name = "text_id";
            this.text_id.Size = new System.Drawing.Size(341, 41);
            this.text_id.TabIndex = 1;
            // 
            // text_pwd
            // 
            this.text_pwd.BackColor = System.Drawing.Color.Transparent;
            this.text_pwd.Location = new System.Drawing.Point(95, 341);
            this.text_pwd.Margin = new System.Windows.Forms.Padding(0);
            this.text_pwd.Name = "text_pwd";
            this.text_pwd.Size = new System.Drawing.Size(341, 41);
            this.text_pwd.TabIndex = 2;
            // 
            // img_title
            // 
            this.img_title.BackColor = System.Drawing.Color.Transparent;
            this.img_title.Location = new System.Drawing.Point(95, 146);
            this.img_title.Margin = new System.Windows.Forms.Padding(0);
            this.img_title.Name = "img_title";
            this.img_title.Size = new System.Drawing.Size(336, 78);
            this.img_title.TabIndex = 6;
            this.img_title.TabStop = false;
            // 
            // btn_login
            // 
            this.btn_login.BackColor = System.Drawing.Color.Transparent;
            this.btn_login.Location = new System.Drawing.Point(95, 421);
            this.btn_login.Margin = new System.Windows.Forms.Padding(0);
            this.btn_login.Name = "btn_login";
            this.btn_login.Size = new System.Drawing.Size(341, 41);
            this.btn_login.TabIndex = 0;
            // 
            // LoginControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.panel_base_server);
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "LoginControl";
            this.Size = new System.Drawing.Size(531, 531);
            this.panel_base_server.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel panel_base_server;
        private LoginID text_id;
        private LoginPwd text_pwd;
        private LoginTitle img_title;
        private LoginButton btn_login;
    }
}
