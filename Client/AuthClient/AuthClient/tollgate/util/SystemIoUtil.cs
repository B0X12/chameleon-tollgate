using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AuthClient.tollgate.util
{
    class SystemIoUtil
    {
        private static RegistryKey RK = null;
        public static string hkcr = @"HKEY_CLASSES_ROOT";
        public static string hkcu = @"HKEY_CURRENT_USER";
        public static string hklm = @"HKEY_LOCAL_MACHINE";


        public static string GetCurrentFolder()
        {
            string path = System.Reflection.Assembly.GetExecutingAssembly().Location;
            return Path.GetDirectoryName(path);
        }

        internal static bool RegPathKeyCreate(ref string regPath)
        {
            if (regPath.Contains("HKEY_CLASSES_ROOT"))
            {    //RK = Registry.ClassesRoot;
                RK = RegistryKey.OpenBaseKey(RegistryHive.ClassesRoot,
                                              Environment.Is64BitOperatingSystem
                                                  ? RegistryView.Registry64
                                                  : RegistryView.Registry32);
                regPath = regPath.Replace(@"HKEY_CLASSES_ROOT\", ""); // 상위 루트 제거
            }

            else if (regPath.Contains("HKEY_CURRENT_USER"))
            {
                //RK = Registry.CurrentUser;
                RK = RegistryKey.OpenBaseKey(RegistryHive.CurrentUser,
                                              Environment.Is64BitOperatingSystem
                                                  ? RegistryView.Registry64
                                                  : RegistryView.Registry32);
                regPath = regPath.Replace(@"HKEY_CURRENT_USER\", ""); // 상위 루트 제거
            }

            else if (regPath.Contains("HKEY_LOCAL_MACHINE"))
            {
                RK = RegistryKey.OpenBaseKey(RegistryHive.LocalMachine,
                                             Environment.Is64BitOperatingSystem
                                                 ? RegistryView.Registry64
                                                 : RegistryView.Registry32);
                regPath = regPath.Replace(@"HKEY_LOCAL_MACHINE\", ""); // 상위 루트 제거
            }
            else
                return false;

            return true;
        }

        public static bool FileExists(in string filePath, in string fileName)
        {
            try
            {
                if (File.Exists(filePath + @"\" + fileName)) // 파일이 있는지 확인한다
                    return true; // 있다면 true
            }
            catch { };

            return false;
        }

        public static bool FileCopy(in string sourceFilePath, in string destmationFilePath)
        {
            try
            {
                File.Copy(sourceFilePath, destmationFilePath); // 파일을 복사한다.
                return true; // 문제없이 잘 됐다면 성공
            }
            catch { };

            return false;
        }

        public static bool FileDelete(in string filePath, in string fileName)
        {
            try
            {
                if (!SystemIoUtil.FileExists(filePath, fileName)) //파일이 없을 경우
                    return true;

                File.Delete(filePath + @"\" + fileName); // 파일을 제거한다.
                return true; // 문제없이 잘 됐다면 true
            }
            catch { };

            return false;

        }

        //----------------------------------------------------------------------
        public static bool RegExists(in string regPath, in string regName)
        {
            string regPathTemp = regPath; // 쓴 이유 : 이것을 2번쓰는 함수의 경우 RegPathKeyCreate 시 regPath 앞에 루트위치가 지워져서 temp를 통해 그것을 방지

            if (RegPathKeyCreate(ref regPathTemp)) // 레지에 대한 데이터의 객체(RK) 안의 Registry 설정
            {
                try
                {
                    RegistryKey tempRK = RK.OpenSubKey(regPathTemp, false); // regPath의 위치를 연다. 읽기권한으로.

                    if (tempRK != null) // 읽지 못한다면 null
                    {
                        object getRegValue = tempRK.GetValue(regName); // 읽은곳에서 regName의 값을 가져온다.

                        if (getRegValue != null) // null이면 값이 없음 null이 아니면 값이 있음
                            return true; // Existence 여부 true(있다)
                    }
                }
                catch { };
            }
            return false;
        }

        internal static bool RegWrite(string regPath, in string regName, in string regValue, in RegistryValueKind regType)
        {
            if ((RegPathKeyCreate(ref regPath) == true))// 레지에 대한 데이터의 객체(RK) 안의 Registry 설정
            {
                try
                {

                    RK.CreateSubKey(regPath, true).SetValue(regName, regValue, regType); // regPath에 적힌 키를 생성후 그 안에 Value를 생성한다.
                    return true; // 생성도중 문제가 발생하지 않았다면 TRUE
                }
                catch { };
            }
            return false;
        }

        internal static bool RegDelete(string regPath, in string regName)
        {
            if (!SystemIoUtil.RegExists(regPath, regName)) //레지 path에 regName이 없다면 true
                return true;

            if ((RegPathKeyCreate(ref regPath) == true)) // 레지에 대한 데이터의 객체(RK) 안의 Registry 설정
            {
                try
                {
                    RK.OpenSubKey(regPath, true).DeleteValue(regName); // 객체에 대해 레지값을 열고 regName에 해당하는 벨류를 제거하라.
                    return true; // 제거하는 도중 문제가 생기지 않았다면 true
                }
                catch { };
            }
            return false;
        }

        public static bool RegDelAndWrite(string regPath, in string regName, in string regValue, in RegistryValueKind regType)
        {
            bool result = false;
            result = SystemIoUtil.RegDelete(regPath, regName);

            if (result) // 딜리트가 성공적으로 됐을 때
                result = SystemIoUtil.RegWrite(regPath, regName, regValue, regType);

            return result;
        }
    }
}
