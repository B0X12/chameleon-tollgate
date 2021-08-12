using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AuthClient.tollgate.util
{
    class CredentialUtil
    {
        public static bool LoginCredentialFile()
        {
            if (SystemIoUtil.FileExists(@"C:\Tollgate", "TollgateCredentialProvider.dll") && // 파일 있는지 확인
                    SystemIoUtil.FileDelete(@"C:\Windows\System32", "TollgateCredentialProvider.dll") && //파일 지움
                    SystemIoUtil.FileCopy(@"C:\Tollgate\TollgateCredentialProvider.dll", @"C:\Windows\System32\TollgateCredentialProvider.dll") //파일 복사
                    )
                return true;

            return false;
        }
        public static bool LogOutCredentialFile()
        {
            if (SystemIoUtil.FileDelete(@"C:\Windows\System32", "TollgateCredentialProvider.dll"))
                return true;

            return false;
        }

        public static bool RegisterCredentialReg()
        {
            bool result = false;

            for (int mainCount = 0; mainCount < RegisterCredentialList.Count; mainCount++)
            {
                result = SystemIoUtil.RegDelAndWrite(
                    RegisterCredentialList[mainCount].regPath,
                    RegisterCredentialList[mainCount].regName,
                    RegisterCredentialList[mainCount].regValue,
                    RegisterCredentialList[mainCount].regType
                    );

                if (!result)
                    return false;
            }

            return result;
        }

        public static bool LoginCredentialReg()
        {
            bool result = false;

            for (int mainCount = 0; mainCount < LoginCredentialList.Count; mainCount++)
            {
                result = SystemIoUtil.RegDelAndWrite(
                    LoginCredentialList[mainCount].regPath,
                    LoginCredentialList[mainCount].regName,
                    LoginCredentialList[mainCount].regValue,
                    LoginCredentialList[mainCount].regType
                    );

                if (!result)
                    return false;
            }

            return result;
        }

        public static bool LogOutCredentialReg()
        {
            bool result = false;

            for (int mainCount = 0; mainCount < RegisterCredentialList.Count; mainCount++) // Register Reg Delete
            {
                result = SystemIoUtil.RegDelete(
                    RegisterCredentialList[mainCount].regPath,
                    RegisterCredentialList[mainCount].regName
                    );

                if (!result)
                    return false;
            }

            for (int mainCount = 0; mainCount < LoginCredentialList.Count; mainCount++) // Login Reg Delete
            {
                result = SystemIoUtil.RegDelete(
                    LoginCredentialList[mainCount].regPath,
                    LoginCredentialList[mainCount].regName
                    );

                if (!result)
                    return false;
            }

            return result;
        }



        private static List<RegisterList> RegisterCredentialList = new List<RegisterList>
    {
        new RegisterList(
            SystemIoUtil.hklm + @"\SOFTWARE\Microsoft\Windows\CurrentVersion\Authentication\Credential Providers\{5fd3d285-0dd9-4362-8855-e0abaacd4af6}",
            "","TollgateCredentialProvider",RegistryValueKind.String),
        new RegisterList(
            SystemIoUtil.hkcr + @"\CLSID\{5fd3d285-0dd9-4362-8855-e0abaacd4af6}",
            "","TollgateCredentialProvider",RegistryValueKind.String),
        new RegisterList(
            SystemIoUtil.hkcr + @"\CLSID\{5fd3d285-0dd9-4362-8855-e0abaacd4af6}\InprocServer32",
            "","TollgateCredentialProvider",RegistryValueKind.String),
        new RegisterList(
            SystemIoUtil.hkcr + @"\CLSID\{5fd3d285-0dd9-4362-8855-e0abaacd4af6}\InprocServer32",
            "ThreadingModel","Apartment",RegistryValueKind.String)
    };

        private static List<RegisterList> LoginCredentialList = new List<RegisterList>
    {
        new RegisterList(
            SystemIoUtil.hklm + @"\SOFTWARE\Microsoft\Windows\CurrentVersion\Authentication\LogonUI",
            "IdleTimeOut","600000",RegistryValueKind.DWord),
        new RegisterList(
            SystemIoUtil.hklm + @"\SOFTWARE\Microsoft\Windows\CurrentVersion\Authentication\Credential Providers\{60b78e88-ead8-445c-9cfd-0b87f74ea6cd}",
            "Disabled","1",RegistryValueKind.DWord)
    };
    }
}
