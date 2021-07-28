
namespace AuthClient.tollgate.account.dialog
{
    partial class MainForm
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
            this.panel_flow_main = new DoubleBufferPanel();
            this.panel_border_main = new System.Windows.Forms.Panel();
            this.panel_border_main.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel_flow_main
            // 
            this.panel_flow_main.AutoScroll = false;
            this.panel_flow_main.Location = new System.Drawing.Point(0, 0);
            this.panel_flow_main.Margin = new System.Windows.Forms.Padding(0);
            this.panel_flow_main.Name = "panel_flow_main";
            this.panel_flow_main.Size = new System.Drawing.Size(1280, 720);
            this.panel_flow_main.TabIndex = 0;
            // 
            // panel_border_main
            // 
            this.panel_border_main.Controls.Add(this.panel_flow_main);
            this.panel_border_main.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel_border_main.Location = new System.Drawing.Point(0, 0);
            this.panel_border_main.Name = "panel_border_main";
            this.panel_border_main.Size = new System.Drawing.Size(1280, 720);
            this.panel_border_main.TabIndex = 1;
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.Control;
            this.ClientSize = new System.Drawing.Size(1280, 720);
            this.Controls.Add(this.panel_border_main);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Name = "MainForm";
            this.Text = "Tollgate";
            this.Load += new System.EventHandler(this.MainForm_Load);
            this.panel_border_main.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private DoubleBufferPanel panel_flow_main;
        private System.Windows.Forms.Panel panel_border_main;
    }
}