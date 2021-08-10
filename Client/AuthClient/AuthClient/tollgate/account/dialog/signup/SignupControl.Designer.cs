
namespace AuthClient.tollgate.account.dialog
{
    partial class SignupControl
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
            this.img_subtitle = new System.Windows.Forms.PictureBox();
            this.text_id = new AuthClient.tollgate.home.setting.TG_TextBox();
            this.text_pwd = new AuthClient.tollgate.home.setting.TG_TextBox();
            this.text_confirm = new AuthClient.tollgate.home.setting.TG_TextBox();
            this.btn_signup = new AuthClient.tollgate.account.dialog.signup.SignupButton();
            this.panel_base_server.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.img_title)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_subtitle)).BeginInit();
            this.SuspendLayout();
            // 
            // panel_base_server
            // 
            this.panel_base_server.ColumnCount = 3;
            this.panel_base_server.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.panel_base_server.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 341F));
            this.panel_base_server.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.panel_base_server.Controls.Add(this.img_title, 1, 1);
            this.panel_base_server.Controls.Add(this.img_subtitle, 1, 3);
            this.panel_base_server.Controls.Add(this.text_id, 1, 5);
            this.panel_base_server.Controls.Add(this.text_pwd, 1, 7);
            this.panel_base_server.Controls.Add(this.text_confirm, 1, 9);
            this.panel_base_server.Controls.Add(this.btn_signup, 1, 11);
            this.panel_base_server.Location = new System.Drawing.Point(0, 0);
            this.panel_base_server.Margin = new System.Windows.Forms.Padding(0);
            this.panel_base_server.Name = "panel_base_server";
            this.panel_base_server.RowCount = 12;
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 146F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 39F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 19F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 41F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 42F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 41F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 8F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 41F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 8F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 41F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 30F));
            this.panel_base_server.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.panel_base_server.Size = new System.Drawing.Size(531, 531);
            this.panel_base_server.TabIndex = 1;
            // 
            // img_title
            // 
            this.img_title.Image = global::AuthClient.Properties.Resources.signupTitle;
            this.img_title.Location = new System.Drawing.Point(95, 146);
            this.img_title.Margin = new System.Windows.Forms.Padding(0);
            this.img_title.Name = "img_title";
            this.img_title.Size = new System.Drawing.Size(336, 39);
            this.img_title.TabIndex = 2;
            this.img_title.TabStop = false;
            // 
            // img_subtitle
            // 
            this.img_subtitle.Image = global::AuthClient.Properties.Resources.signupSubtitle;
            this.img_subtitle.Location = new System.Drawing.Point(95, 204);
            this.img_subtitle.Margin = new System.Windows.Forms.Padding(0);
            this.img_subtitle.Name = "img_subtitle";
            this.img_subtitle.Size = new System.Drawing.Size(333, 41);
            this.img_subtitle.TabIndex = 3;
            this.img_subtitle.TabStop = false;
            // 
            // text_id
            // 
            this.text_id.BackColor = System.Drawing.Color.Transparent;
            this.text_id.Font = new System.Drawing.Font("새굴림", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.text_id.Hint = "아이디";
            this.text_id.IsCheck = false;
            this.text_id.Location = new System.Drawing.Point(95, 287);
            this.text_id.Margin = new System.Windows.Forms.Padding(0);
            this.text_id.Name = "text_id";
            this.text_id.PasswordChar = '\0';
            this.text_id.Size = new System.Drawing.Size(341, 41);
            this.text_id.TabIndex = 1;
            this.text_id.UseCheck = false;
            // 
            // text_pwd
            // 
            this.text_pwd.BackColor = System.Drawing.Color.Transparent;
            this.text_pwd.Hint = "비밀번호";
            this.text_pwd.IsCheck = false;
            this.text_pwd.Location = new System.Drawing.Point(95, 336);
            this.text_pwd.Margin = new System.Windows.Forms.Padding(0);
            this.text_pwd.Name = "text_pwd";
            this.text_pwd.PasswordChar = '●';
            this.text_pwd.Size = new System.Drawing.Size(341, 41);
            this.text_pwd.TabIndex = 2;
            this.text_pwd.UseCheck = false;
            this.text_pwd.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.text_pwd_KeyPress);
            // 
            // text_confirm
            // 
            this.text_confirm.BackColor = System.Drawing.Color.Transparent;
            this.text_confirm.Hint = "비밀번호 확인";
            this.text_confirm.IsCheck = false;
            this.text_confirm.Location = new System.Drawing.Point(95, 385);
            this.text_confirm.Margin = new System.Windows.Forms.Padding(0);
            this.text_confirm.Name = "text_confirm";
            this.text_confirm.PasswordChar = '●';
            this.text_confirm.Size = new System.Drawing.Size(341, 41);
            this.text_confirm.TabIndex = 3;
            this.text_confirm.UseCheck = true;
            this.text_confirm.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.text_confirm_KeyPress);
            // 
            // btn_signup
            // 
            this.btn_signup.BackColor = System.Drawing.Color.Transparent;
            this.btn_signup.Location = new System.Drawing.Point(95, 456);
            this.btn_signup.Margin = new System.Windows.Forms.Padding(0);
            this.btn_signup.Name = "btn_signup";
            this.btn_signup.Size = new System.Drawing.Size(341, 41);
            this.btn_signup.TabIndex = 0;
            // 
            // SignupControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.panel_base_server);
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "SignupControl";
            this.Size = new System.Drawing.Size(531, 531);
            this.panel_base_server.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.img_title)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_subtitle)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel panel_base_server;
        private System.Windows.Forms.PictureBox img_title;
        private System.Windows.Forms.PictureBox img_subtitle;
        private AuthClient.tollgate.home.setting.TG_TextBox text_id;
        private AuthClient.tollgate.home.setting.TG_TextBox text_pwd;
        private AuthClient.tollgate.home.setting.TG_TextBox text_confirm;
        private signup.SignupButton btn_signup;
    }
}
