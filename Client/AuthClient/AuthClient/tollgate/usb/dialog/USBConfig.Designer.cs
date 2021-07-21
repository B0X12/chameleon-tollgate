
namespace AuthClient.tollgate.usb.dialog
{
    partial class USBConfigDialog
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
            this.usbInfoList = new System.Windows.Forms.ListView();
            this.usbNameColumn = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.usbAddButton = new System.Windows.Forms.Button();
            this.usbUnregisterButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // usbInfoList
            // 
            this.usbInfoList.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.usbNameColumn});
            this.usbInfoList.FullRowSelect = true;
            this.usbInfoList.GridLines = true;
            this.usbInfoList.HideSelection = false;
            this.usbInfoList.Location = new System.Drawing.Point(27, 22);
            this.usbInfoList.Name = "usbInfoList";
            this.usbInfoList.Size = new System.Drawing.Size(408, 136);
            this.usbInfoList.TabIndex = 0;
            this.usbInfoList.UseCompatibleStateImageBehavior = false;
            this.usbInfoList.View = System.Windows.Forms.View.Details;
            // 
            // usbNameColumn
            // 
            this.usbNameColumn.Text = "USB 이름";
            this.usbNameColumn.Width = 250;
            // 
            // usbAddButton
            // 
            this.usbAddButton.Location = new System.Drawing.Point(464, 22);
            this.usbAddButton.Name = "usbAddButton";
            this.usbAddButton.Size = new System.Drawing.Size(123, 60);
            this.usbAddButton.TabIndex = 1;
            this.usbAddButton.Text = "USB 추가";
            this.usbAddButton.UseVisualStyleBackColor = true;
            this.usbAddButton.Click += new System.EventHandler(this.usbAddButton_Click);
            // 
            // usbUnregisterButton
            // 
            this.usbUnregisterButton.Location = new System.Drawing.Point(464, 101);
            this.usbUnregisterButton.Name = "usbUnregisterButton";
            this.usbUnregisterButton.Size = new System.Drawing.Size(123, 57);
            this.usbUnregisterButton.TabIndex = 1;
            this.usbUnregisterButton.Text = "USB 등록 해제";
            this.usbUnregisterButton.UseVisualStyleBackColor = true;
            this.usbUnregisterButton.Click += new System.EventHandler(this.usbUnregisterButton_Click);
            // 
            // USBConfigDialog
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(611, 182);
            this.Controls.Add(this.usbUnregisterButton);
            this.Controls.Add(this.usbAddButton);
            this.Controls.Add(this.usbInfoList);
            this.Name = "USBConfigDialog";
            this.Text = "USBConfig";
            this.Activated += new System.EventHandler(this.USBConfigDialog_Activated);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.ListView usbInfoList;
        private System.Windows.Forms.Button usbAddButton;
        private System.Windows.Forms.Button usbUnregisterButton;
        private System.Windows.Forms.ColumnHeader usbNameColumn;
    }
}