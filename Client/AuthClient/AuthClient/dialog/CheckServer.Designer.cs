
namespace AuthClient
{
    partial class CheckServerDialog
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.serverInfoTextBox = new System.Windows.Forms.TextBox();
            this.CheckButton = new System.Windows.Forms.Button();
            this.statusMsgLabel = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // serverInfoTextBox
            // 
            this.serverInfoTextBox.Location = new System.Drawing.Point(29, 64);
            this.serverInfoTextBox.Name = "serverInfoTextBox";
            this.serverInfoTextBox.Size = new System.Drawing.Size(454, 25);
            this.serverInfoTextBox.TabIndex = 0;
            // 
            // CheckButton
            // 
            this.CheckButton.Location = new System.Drawing.Point(171, 105);
            this.CheckButton.Name = "CheckButton";
            this.CheckButton.Size = new System.Drawing.Size(157, 33);
            this.CheckButton.TabIndex = 1;
            this.CheckButton.Text = "인증 서버에 접속";
            this.CheckButton.UseVisualStyleBackColor = true;
            this.CheckButton.Click += new System.EventHandler(this.CheckButton_Click);
            // 
            // statusMsgLabel
            // 
            this.statusMsgLabel.AutoSize = true;
            this.statusMsgLabel.Location = new System.Drawing.Point(29, 23);
            this.statusMsgLabel.Name = "statusMsgLabel";
            this.statusMsgLabel.Size = new System.Drawing.Size(299, 15);
            this.statusMsgLabel.TabIndex = 2;
            this.statusMsgLabel.Text = "인증 서버의 [IP:포트 번호]를 입력하십시오";
            // 
            // CheckServerDialog
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(518, 160);
            this.Controls.Add(this.statusMsgLabel);
            this.Controls.Add(this.CheckButton);
            this.Controls.Add(this.serverInfoTextBox);
            this.Name = "CheckServerDialog";
            this.Text = "인증 서버 확인";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox serverInfoTextBox;
        private System.Windows.Forms.Button CheckButton;
        private System.Windows.Forms.Label statusMsgLabel;
    }
}