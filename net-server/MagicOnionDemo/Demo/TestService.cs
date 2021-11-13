using MagicOnion;
using MagicOnion.Server;
using ServerDefinition;
using System.Threading.Tasks;

namespace MagicOnionDemo.Demo
{
    public class TestService : ServiceBase<ITest>, ITest
    {
        public async UnaryResult<Result> Sum(Hello hello)
        {
            await Task.Yield();
            return  new Result() {  Code = hello.X + hello.Y } ;
        }
    }
}
