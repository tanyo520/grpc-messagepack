import ServerDefinition.Hello;
import ServerDefinition.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.ManagedChannel;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import javax.net.ssl.SSLException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ApplicationDemo {

    public static void main(String[] args) throws IOException {
//        File tempFile = File.createTempFile("c:/target/tmp", ".txt");
//        MessagePacker packer = MessagePack.newDefaultPacker(new FileOutputStream(tempFile));
//        ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
//        Hello hello =  new Hello();
//        hello.setX(1);
//        hello.setY(2);
//        File file = new File("D:/tmp6945048575281581405.txt");
//        Hello he1=  objectMapper.readValue(file,Hello.class);
        // Serialize a Java object to byte array
        //byte[] bytes = objectMapper.writeValueAsBytes(hello);
       // objectMapper.writeValue(tempFile,hello);
        ManagedChannel channel = NettyChannelBuilder.forAddress("localhost", 5000).usePlaintext()
//                .sslContext(GrpcSslContexts.forClient()
//                        .trustManager(InsecureTrustManagerFactory.INSTANCE) // allow localhost self-signed certificates
//                        .build())
                .build();


        GrpcServicesTestStub stub=  GrpcServicesTestStub.newTestStub(channel);
        Hello hello = new Hello();
        hello.setX(1);
        hello.setY(2);
      Result result = stub.Sum(hello);
      System.out.println("this is :"+result.getCode());
       System.out.println("aa");
    }
}
