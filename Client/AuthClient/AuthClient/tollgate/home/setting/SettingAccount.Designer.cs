
namespace AuthClient.tollgate.home.setting
{
    partial class SettingAccount
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
            this.edit_confirm = new AuthClient.tollgate.home.setting.TG_TextBox();
            this.img_pwd = new System.Windows.Forms.PictureBox();
            this.img_id = new System.Windows.Forms.PictureBox();
            this.img_title = new System.Windows.Forms.PictureBox();
            this.edit_id = new AuthClient.tollgate.home.setting.TG_TextBox();
            this.edit_pwd = new AuthClient.tollgate.home.setting.TG_TextBox();
            this.img_edit = new System.Windows.Forms.PictureBox();
            this.panel_table.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.img_pwd)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_id)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_title)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_edit)).BeginInit();
            this.SuspendLayout();
            // 
            // panel_table
            // 
            this.panel_table.ColumnCount = 5;
            this.panel_table.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 13F));
            this.panel_table.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 7F));
            this.panel_table.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 112F));
            this.panel_table.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 131F));
            this.panel_table.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.panel_table.Controls.Add(this.edit_confirm, 2, 11);
            this.panel_table.Controls.Add(this.img_pwd, 2, 7);
            this.panel_table.Controls.Add(this.img_id, 2, 3);
            this.panel_table.Controls.Add(this.img_title, 0, 1);
            this.panel_table.Controls.Add(this.edit_id, 2, 5);
            this.panel_table.Controls.Add(this.edit_pwd, 2, 9);
            this.panel_table.Controls.Add(this.img_edit, 4, 13);
            this.panel_table.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel_table.Location = new System.Drawing.Point(0, 0);
            this.panel_table.Margin = new System.Windows.Forms.Padding(0);
            this.panel_table.Name = "panel_table";
            this.panel_table.RowCount = 15;
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 230F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 24F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 35F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 18F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 13F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 41F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 30F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 18F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 13F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 41F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 8F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 41F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 41F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 41F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.panel_table.Size = new System.Drawing.Size(689, 720);
            this.panel_table.TabIndex = 0;
            // 
            // edit_confirm
            // 
            this.edit_confirm.BackColor = System.Drawing.Color.Transparent;
            this.panel_table.SetColumnSpan(this.edit_confirm, 3);
            this.edit_confirm.Hint = "비밀번호 확인";
            this.edit_confirm.IsCheck = false;
            this.edit_confirm.Location = new System.Drawing.Point(20, 471);
            this.edit_confirm.Margin = new System.Windows.Forms.Padding(0);
            this.edit_confirm.Name = "edit_confirm";
            this.edit_confirm.PasswordChar = '●';
            this.edit_confirm.Size = new System.Drawing.Size(341, 41);
            this.edit_confirm.TabIndex = 5;
            this.edit_confirm.UseCheck = true;
            this.edit_confirm.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.edit_confirm_KeyPress);
            // 
            // img_pwd
            // 
            this.panel_table.SetColumnSpan(this.img_pwd, 3);
            this.img_pwd.Image = global::AuthClient.Properties.Resources.settingAccountPasswd;
            this.img_pwd.Location = new System.Drawing.Point(20, 391);
            this.img_pwd.Margin = new System.Windows.Forms.Padding(0);
            this.img_pwd.Name = "img_pwd";
            this.img_pwd.Size = new System.Drawing.Size(112, 18);
            this.img_pwd.TabIndex = 2;
            this.img_pwd.TabStop = false;
            // 
            // img_id
            // 
            this.panel_table.SetColumnSpan(this.img_id, 3);
            this.img_id.Image = global::AuthClient.Properties.Resources.settingAccountId;
            this.img_id.Location = new System.Drawing.Point(20, 289);
            this.img_id.Margin = new System.Windows.Forms.Padding(0);
            this.img_id.Name = "img_id";
            this.img_id.Size = new System.Drawing.Size(112, 18);
            this.img_id.TabIndex = 1;
            this.img_id.TabStop = false;
            // 
            // img_title
            // 
            this.panel_table.SetColumnSpan(this.img_title, 3);
            this.img_title.Image = global::AuthClient.Properties.Resources.settingAccountTitle;
            this.img_title.Location = new System.Drawing.Point(0, 230);
            this.img_title.Margin = new System.Windows.Forms.Padding(0);
            this.img_title.Name = "img_title";
            this.img_title.Size = new System.Drawing.Size(112, 24);
            this.img_title.TabIndex = 0;
            this.img_title.TabStop = false;
            // 
            // edit_id
            // 
            this.edit_id.BackColor = System.Drawing.Color.Transparent;
            this.panel_table.SetColumnSpan(this.edit_id, 3);
            this.edit_id.Enabled = false;
            this.edit_id.Hint = "아이디";
            this.edit_id.IsCheck = false;
            this.edit_id.Location = new System.Drawing.Point(20, 320);
            this.edit_id.Margin = new System.Windows.Forms.Padding(0);
            this.edit_id.Name = "edit_id";
            this.edit_id.PasswordChar = '\0';
            this.edit_id.Size = new System.Drawing.Size(341, 41);
            this.edit_id.TabIndex = 3;
            this.edit_id.UseCheck = false;
            // 
            // edit_pwd
            // 
            this.edit_pwd.BackColor = System.Drawing.Color.Transparent;
            this.panel_table.SetColumnSpan(this.edit_pwd, 3);
            this.edit_pwd.Hint = "비밀번호";
            this.edit_pwd.IsCheck = false;
            this.edit_pwd.Location = new System.Drawing.Point(20, 422);
            this.edit_pwd.Margin = new System.Windows.Forms.Padding(0);
            this.edit_pwd.Name = "edit_pwd";
            this.edit_pwd.PasswordChar = '●';
            this.edit_pwd.Size = new System.Drawing.Size(341, 41);
            this.edit_pwd.TabIndex = 4;
            this.edit_pwd.UseCheck = false;
            this.edit_pwd.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.edit_pwd_KeyPress);
            // 
            // img_edit
            // 
            this.img_edit.Image = global::AuthClient.Properties.Resources.settingAccountEdit;
            this.img_edit.Location = new System.Drawing.Point(263, 553);
            this.img_edit.Margin = new System.Windows.Forms.Padding(0);
            this.img_edit.Name = "img_edit";
            this.img_edit.Size = new System.Drawing.Size(131, 41);
            this.img_edit.TabIndex = 6;
            this.img_edit.TabStop = false;
            this.img_edit.Click += new System.EventHandler(this.img_edit_Click);
            // 
            // SettingAccount
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Transparent;
            this.Controls.Add(this.panel_table);
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "SettingAccount";
            this.Size = new System.Drawing.Size(689, 720);
            this.panel_table.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.img_pwd)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_id)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_title)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_edit)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel panel_table;
        private System.Windows.Forms.PictureBox img_title;
        private System.Windows.Forms.PictureBox img_id;
        private System.Windows.Forms.PictureBox img_pwd;
        private TG_TextBox edit_id;
        private TG_TextBox edit_pwd;
        private TG_TextBox edit_confirm;
        private System.Windows.Forms.PictureBox img_edit;
    }
}
