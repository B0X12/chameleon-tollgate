using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AuthClient.tollgate.util
{
    class RegisterList
    {
        public string regPath;
        public string regName;
        public string regValue;
        public RegistryValueKind regType;

        public RegisterList(in string regPath, in string regName, in string regValue, RegistryValueKind regType)
        {
            this.regPath = regPath;
            this.regName = regName;
            this.regValue = regValue;
            this.regType = regType;
        }
    }
}
