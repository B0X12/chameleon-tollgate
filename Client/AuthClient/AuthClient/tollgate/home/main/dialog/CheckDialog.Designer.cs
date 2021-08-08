
namespace AuthClient.tollgate.home.main.dialog
{
    partial class CheckDialog
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
            this.panel_table = new System.Windows.Forms.TableLayoutPanel();
            this.img_title = new System.Windows.Forms.PictureBox();
            this.img_bg = new System.Windows.Forms.PictureBox();
            this.panel_list = new System.Windows.Forms.Panel();
            this.panel_table.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.img_title)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_bg)).BeginInit();
            this.SuspendLayout();
            // 
            // panel_table
            // 
            this.panel_table.ColumnCount = 3;
            this.panel_table.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 77F));
            this.panel_table.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.panel_table.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 328F));
            this.panel_table.Controls.Add(this.img_title, 1, 1);
            this.panel_table.Controls.Add(this.img_bg, 2, 0);
            this.panel_table.Controls.Add(this.panel_list, 1, 3);
            this.panel_table.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel_table.Location = new System.Drawing.Point(0, 0);
            this.panel_table.Margin = new System.Windows.Forms.Padding(0);
            this.panel_table.Name = "panel_table";
            this.panel_table.RowCount = 5;
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 88F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 75F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 75F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.panel_table.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 15F));
            this.panel_table.Size = new System.Drawing.Size(914, 554);
            this.panel_table.TabIndex = 0;
            // 
            // img_title
            // 
            this.img_title.Dock = System.Windows.Forms.DockStyle.Fill;
            this.img_title.Image = global::AuthClient.Properties.Resources.checkTitle;
            this.img_title.Location = new System.Drawing.Point(77, 88);
            this.img_title.Margin = new System.Windows.Forms.Padding(0);
            this.img_title.Name = "img_title";
            this.img_title.Size = new System.Drawing.Size(509, 75);
            this.img_title.TabIndex = 1;
            this.img_title.TabStop = false;
            // 
            // img_bg
            // 
            this.img_bg.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.img_bg.Image = global::AuthClient.Properties.Resources.checkBg;
            this.img_bg.Location = new System.Drawing.Point(586, 28);
            this.img_bg.Margin = new System.Windows.Forms.Padding(0);
            this.img_bg.Name = "img_bg";
            this.panel_table.SetRowSpan(this.img_bg, 4);
            this.img_bg.Size = new System.Drawing.Size(328, 511);
            this.img_bg.TabIndex = 3;
            this.img_bg.TabStop = false;
            // 
            // panel_list
            // 
            this.panel_list.AutoScroll = true;
            this.panel_list.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel_list.Location = new System.Drawing.Point(77, 238);
            this.panel_list.Margin = new System.Windows.Forms.Padding(0);
            this.panel_list.Name = "panel_list";
            this.panel_list.Size = new System.Drawing.Size(509, 301);
            this.panel_list.TabIndex = 4;
            // 
            // CheckDialog
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.ClientSize = new System.Drawing.Size(914, 554);
            this.Controls.Add(this.panel_table);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Margin = new System.Windows.Forms.Padding(3, 4, 3, 4);
            this.Name = "CheckDialog";
            this.Text = "CheckDialog";
            this.Load += new System.EventHandler(this.CheckDialog_Load);
            this.Shown += new System.EventHandler(this.CheckDialog_Shown);
            this.panel_table.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.img_title)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.img_bg)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.PictureBox img_bg;
        private System.Windows.Forms.TableLayoutPanel panel_table;
        private System.Windows.Forms.PictureBox img_title;
        private System.Windows.Forms.Panel panel_list;
    }
}