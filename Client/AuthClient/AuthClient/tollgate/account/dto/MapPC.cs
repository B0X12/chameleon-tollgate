using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AuthClient.tollgate.account.dto
{
    public class MapPC
    {
        public string id { get; set; }
        public string pc { get; set; }
        public string alias { get; set; }

        public MapPC(string id, string pc, string alias)
        {
            this.id = id;
            this.pc = pc;
            this.alias = alias;
        }
    }
}
