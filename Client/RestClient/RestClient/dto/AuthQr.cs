using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RestClient.dto
{
    class AuthQr
    {
        public string userId;
        public long timestamp;
        public string data;

        public AuthQr(in string userId, in long timestamp = 0, in string data = null)
        {
            this.userId = userId;
            this.timestamp = timestamp;
            this.data = data;
        }
    }
}
