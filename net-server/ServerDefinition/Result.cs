using MessagePack;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ServerDefinition
{
    [MessagePackObject]
    public class Result
    {
        [Key("code")]
        public int Code { set; get; }
    }
}
