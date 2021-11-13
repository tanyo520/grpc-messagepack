using MessagePack;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ServerDefinition
{
    [MessagePackObject]
   public  class Hello
    {
        [Key("x")]
        public int X { set; get; }
        [Key("y")]
        public int Y { set; get; }
    }
}
