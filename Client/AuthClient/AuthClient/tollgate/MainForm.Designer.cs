
namespace AuthClient.tollgate
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
            this.panel_border_main = new System.Windows.Forms.Panel();
            this.panel_main = new AuthClient.tollgate.DoubleBufferPanel();
            this.panel_border_main.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel_border_main
            // 
            this.panel_border_main.Controls.Add(this.panel_main);
            this.panel_border_main.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel_border_main.Location = new System.Drawing.Point(0, 0);
            this.panel_border_main.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.panel_border_main.Name = "panel_border_main";
            this.panel_border_main.Size = new System.Drawing.Size(1600, 900);
            this.panel_border_main.TabIndex = 1;
            // 
            // panel_main
            // 
            this.panel_main.Location = new System.Drawing.Point(0, 0);
            this.panel_main.Margin = new System.Windows.Forms.Padding(0);
            this.panel_main.Name = "panel_main";
            this.panel_main.Size = new System.Drawing.Size(1600, 900);
            this.panel_main.TabIndex = 0;
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(120F, 120F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.BackColor = System.Drawing.SystemColors.Control;
            this.ClientSize = new System.Drawing.Size(1600, 900);
            this.Controls.Add(this.panel_border_main);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.Name = "MainForm";
            this.Text = "Tollgate";
            this.panel_border_main.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private DoubleBufferPanel panel_main;
        private System.Windows.Forms.Panel panel_border_main;
    }
}