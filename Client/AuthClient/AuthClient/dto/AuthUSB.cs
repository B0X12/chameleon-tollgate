using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AuthClient.dto
{
    class AuthUSB
    {
        public string id { get; set; }
        public string usb_id { get; set; }

        public AuthUSB(string id, string usb_id)
        {
            this.id = id;
            this.usb_id = usb_id;
        }
    }
}
