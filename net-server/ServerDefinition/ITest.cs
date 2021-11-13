using MagicOnion;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ServerDefinition
{
    public interface ITest : IService<ITest>
    {
        UnaryResult<Result> Sum(Hello hello);
    }
}
