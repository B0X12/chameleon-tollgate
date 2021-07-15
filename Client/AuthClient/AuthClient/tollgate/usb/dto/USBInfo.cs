using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AuthClient.tollgate.usb.dto
{
    class USBInfo
    {
        public string id { get; set; }
        public string usb_id { get; set; }

        public string alias { get; set; }

        public USBInfo(string id, string usb_id, string alias)
        {
            this.id = id;
            this.usb_id = usb_id;
            this.alias = alias;
        }
    }
}
