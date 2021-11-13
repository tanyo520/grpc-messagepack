using Grpc.Net.Client;
using MagicOnion.Client;
using MessagePack;
using ServerDefinition;
using System;
using System.IO;
using System.Net.Http;
using System.Threading.Tasks;

namespace Client
{
    internal class Program
    {
        public static byte[] ReadFile(string fileName)
        {
            FileStream fs = new FileStream(fileName, FileMode.OpenOrCreate);
            byte[] buffer = new byte[fs.Length];
            try
            {
                fs.Read(buffer, 0, buffer.Length);
                fs.Seek(0, SeekOrigin.Begin);
                return buffer;
            }
            catch
            {
                return buffer;
            }
            finally
            {
                if (fs != null)
                    fs.Close();
            }
        }
        static async Task Main(string[] args)
        {
            byte[] by = ReadFile("d:/tmp6945048575281581405.txt");
            byte[] ss=  MessagePackSerializer.Serialize<Hello>(new Hello() { Y = 2, X = 1 });
            Hello h = MessagePackSerializer.Deserialize<Hello>(ss);
            AppContext.SetSwitch("System.Net.Http.SocketsHttpHandler.Http2UnencryptedSupport", true);
            AppContext.SetSwitch("System.Net.Http.SocketsHttpHandler.Http2Support", true);
            var httpClientHandler = new HttpClientHandler();
            // Return `true` to allow certificates that are untrusted/invalid
            //   httpClientHandler.ServerCertificateCustomValidationCallback = HttpClientHandler.DangerousAcceptAnyServerCertificateValidator;
            var httpClient = new HttpClient(httpClientHandler);
            // Connect to the server using gRPC channel.
            var channel = GrpcChannel.ForAddress("http://localhost:5000");

            // NOTE: If your project targets non-.NET Standard 2.1, use `Grpc.Core.Channel` class instead.
            //var channel = new Channel("localhost", 5001, new SslCredentials());

            // Create a proxy to call the server transparently.
            var client = MagicOnionClient.Create<ITest>(channel);

            // Call the server-side method using the proxy.
            var result = await client.Sum(new Hello() { X = 1, Y = 2 });
            Console.WriteLine($"Result: {result}");
            Console.WriteLine("Hello World!");
        }
    }
}
