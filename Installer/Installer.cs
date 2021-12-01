using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Net;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Windows.Forms;

namespace Installer
{
    class Installer
    {
        private static readonly String KEY = "Software\\Журавлев";
        private static int count = 20;
        private String path;

        public Installer(String path)
        {
            this.path = path;
        }

        public delegate void OnProgressChange(int progress);

        public void startInstall(OnProgressChange onProgressChange)
        {
            Thread thread = new Thread(() =>
            {
                var bytes = Resource1.lab_jar;
                var outStream = File.OpenWrite(path);
                int step = bytes.Length / count;
                int i = 0;
                for (int progress = 1; progress < count; progress++)
                {
                    outStream.Write(bytes, i, step);
                    i += step;
                    onProgressChange.Invoke(progress*5);
                }
                outStream.Write(bytes, i, bytes.Length-i);
                outStream.Close();
                createSignature();
                onProgressChange.Invoke(count*5);
            });
            thread.Start();
        }

        private void createSignature()
        {
            var data = parseData();
            Console.WriteLine(data);

            var rsaCryptoService = new RSACryptoServiceProvider();
            var publicKey = rsaCryptoService.ExportSubjectPublicKeyInfo();
            var sign = rsaCryptoService.SignData(Encoding.UTF8.GetBytes(data), HashAlgorithmName.SHA256.Name);

            writeToRegistry(publicKey, sign);
        }

        private void writeToRegistry(byte[] publicKey, byte[] sign)
        {
            var registryKey = Registry.CurrentUser.CreateSubKey(KEY);
            registryKey.SetValue("Signature", sign);
            registryKey.SetValue("Key", publicKey);
            registryKey.Close();
        }

        private String parseData()
        {
            var data = new StringBuilder();
            // имя пользователя
            data.Append(System.Environment.GetEnvironmentVariable("USERNAME"));
            // имя компьютера
            data.Append(Environment.GetEnvironmentVariable("COMPUTERNAME"));
            // путь к папке с ОС Windows
            data.Append(Environment.GetEnvironmentVariable("windir"));
            // путь к папке с системными файлами ОС Windows
            data.Append(Environment.GetEnvironmentVariable("SystemRoot"));
            // ширина экрана
            data.Append(SystemInformation.PrimaryMonitorSize.Width);
            // набор дисковых устройств
            DriveInfo[] drives = DriveInfo.GetDrives();
            foreach (var item in drives)
            {
                if (item.DriveType == DriveType.Fixed)
                    data.Append(item.Name);
            }
            // объем диска, на котором установлена программа
            var info = new System.IO.DriveInfo(path);
            data.Append(info.TotalSize);
            return data.ToString();
        }
    }
}
