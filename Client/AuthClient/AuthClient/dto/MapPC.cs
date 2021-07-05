using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AuthClient.dto
{
    class MapPC
    {
        public string id { get; set; }
        public string pc { get; set; }
        public string alias { get; set; }

        public MapPC(string id, string pwd, string alias)
        {
            this.id = id;
            this.pc = pc;
            this.alias = alias;
        }
    }
}
