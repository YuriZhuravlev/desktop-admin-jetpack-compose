using System;
using System.Windows.Forms;

namespace Installer
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();

            folderBrowserDialog1.ShowNewFolderButton = true;
        }

        private String path;

        private void btnSelectFolder_Click(object sender, EventArgs e)
        {
            var result = folderBrowserDialog1.ShowDialog();
            if (result == DialogResult.OK)
            {
                path = folderBrowserDialog1.SelectedPath;
                etFolder.Text = path;
                btnInstall.Enabled = true;
            }
        }

        private void btnClose_Click(object sender, EventArgs e)
        {
            Close();
        }

        private void btnInstall_Click(object sender, EventArgs e)
        {
            btnInstall.Enabled = false;
            var installer = new Installer(path + "\\lab.jar");
            installer.startInstall(OnProgressChanged);
            tvInstall.Visible = true;
            pbLoading.Visible = true;
        }

        void OnProgressChanged(int progress)
        {
            this.BeginInvoke((MethodInvoker)delegate
            {
                pbLoading.Value = progress;
                if (progress == 100)
                {
                    tvInstall.Text = "Завершено";
                }
            });
        }
    }
}
