using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AuthClient.dto
{
    class UserAccount
    {
        public string id { get; set; }
        public string pwd { get; set; }

        public UserAccount(string id, string pwd)
        {
            this.id = id;
            this.pwd = pwd;
        }
    }
}
