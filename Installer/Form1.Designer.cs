
namespace Installer
{
    partial class Form1
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
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
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.folderBrowserDialog1 = new System.Windows.Forms.FolderBrowserDialog();
            this.btnSelectFolder = new System.Windows.Forms.Button();
            this.etFolder = new System.Windows.Forms.TextBox();
            this.btnInstall = new System.Windows.Forms.Button();
            this.btnClose = new System.Windows.Forms.Button();
            this.tvInstall = new System.Windows.Forms.Label();
            this.pbLoading = new System.Windows.Forms.ProgressBar();
            this.SuspendLayout();
            // 
            // btnSelectFolder
            // 
            this.btnSelectFolder.Location = new System.Drawing.Point(323, 197);
            this.btnSelectFolder.Name = "btnSelectFolder";
            this.btnSelectFolder.Size = new System.Drawing.Size(164, 23);
            this.btnSelectFolder.TabIndex = 0;
            this.btnSelectFolder.Text = "Выбрать";
            this.btnSelectFolder.UseVisualStyleBackColor = true;
            this.btnSelectFolder.Click += new System.EventHandler(this.btnSelectFolder_Click);
            // 
            // etFolder
            // 
            this.etFolder.Location = new System.Drawing.Point(12, 197);
            this.etFolder.Name = "etFolder";
            this.etFolder.ReadOnly = true;
            this.etFolder.Size = new System.Drawing.Size(305, 23);
            this.etFolder.TabIndex = 1;
            // 
            // btnInstall
            // 
            this.btnInstall.Enabled = false;
            this.btnInstall.Location = new System.Drawing.Point(369, 226);
            this.btnInstall.Name = "btnInstall";
            this.btnInstall.Size = new System.Drawing.Size(118, 23);
            this.btnInstall.TabIndex = 2;
            this.btnInstall.Text = "Установить";
            this.btnInstall.UseVisualStyleBackColor = true;
            this.btnInstall.Click += new System.EventHandler(this.btnInstall_Click);
            // 
            // btnClose
            // 
            this.btnClose.Location = new System.Drawing.Point(245, 226);
            this.btnClose.Name = "btnClose";
            this.btnClose.Size = new System.Drawing.Size(118, 23);
            this.btnClose.TabIndex = 3;
            this.btnClose.Text = "Отмена";
            this.btnClose.UseVisualStyleBackColor = true;
            this.btnClose.Click += new System.EventHandler(this.btnClose_Click);
            // 
            // tvInstall
            // 
            this.tvInstall.AutoSize = true;
            this.tvInstall.Location = new System.Drawing.Point(12, 150);
            this.tvInstall.Name = "tvInstall";
            this.tvInstall.Size = new System.Drawing.Size(63, 15);
            this.tvInstall.TabIndex = 4;
            this.tvInstall.Text = "Установка";
            this.tvInstall.Visible = false;
            // 
            // pbLoading
            // 
            this.pbLoading.Location = new System.Drawing.Point(12, 168);
            this.pbLoading.Name = "pbLoading";
            this.pbLoading.Size = new System.Drawing.Size(475, 13);
            this.pbLoading.Style = System.Windows.Forms.ProgressBarStyle.Continuous;
            this.pbLoading.TabIndex = 5;
            this.pbLoading.Visible = false;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(507, 261);
            this.Controls.Add(this.pbLoading);
            this.Controls.Add(this.tvInstall);
            this.Controls.Add(this.btnClose);
            this.Controls.Add(this.btnInstall);
            this.Controls.Add(this.etFolder);
            this.Controls.Add(this.btnSelectFolder);
            this.Name = "Form1";
            this.Text = "Установка";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.FolderBrowserDialog folderBrowserDialog1;
        private System.Windows.Forms.Button btnSelectFolder;
        private System.Windows.Forms.TextBox etFolder;
        private System.Windows.Forms.Button btnInstall;
        private System.Windows.Forms.Button btnClose;
        private System.Windows.Forms.Label tvInstall;
        private System.Windows.Forms.ProgressBar pbLoading;
    }
}

