import ServerDefinition.Hello;
import ServerDefinition.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.MethodDescriptor;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static io.grpc.stub.ClientCalls.blockingUnaryCall;

public class GrpcServicesTestStub extends io.grpc.stub.AbstractStub<GrpcServicesTestStub>{
    public static final String SERVICE_NAME = "TestService";
    private GrpcServicesTestStub(io.grpc.Channel channel) {
        super(channel);
    }
    private GrpcServicesTestStub(io.grpc.Channel channel,
                                     io.grpc.CallOptions callOptions) {
        super(channel, callOptions);
    }
    private static byte[] steamToByte(InputStream input) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        byte[] b = new byte[1024];
        while ((len = input.read(b, 0, b.length)) != -1) {
            baos.write(b, 0, len);
        }
        byte[] buffer = baos.toByteArray();
        return buffer;
    }
    static <T> MethodDescriptor.Marshaller<T> marshallerFor(Class<T> clz) {
      //  Gson gson = new Gson();
        //MessagePack pack=new MessagePack();
        ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
        return new MethodDescriptor.Marshaller<T>() {
            @Override
            public InputStream stream(T value) {
                byte[] bytes = new byte[0];
                try {
                    bytes = objectMapper.writeValueAsBytes(value);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return new ByteArrayInputStream(bytes);
            }
            @Override
            public T parse(InputStream stream) {
                InputStreamReader inputStream =new InputStreamReader(stream, StandardCharsets.UTF_8);
                try {
                    byte[]  bytes= steamToByte(stream);
                    return objectMapper.readValue(bytes,clz);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }
    static final MethodDescriptor<Hello, Result> CREATE_METHOD =
            MethodDescriptor.newBuilder(
                    marshallerFor(Hello.class),
                    marshallerFor(Result.class))
                    .setFullMethodName(
                            MethodDescriptor.generateFullMethodName("ITest", "Sum"))
                    .setType(MethodDescriptor.MethodType.UNARY)
                    .build();
    @Override
    protected GrpcServicesTestStub build(Channel channel, CallOptions callOptions) {
        return new GrpcServicesTestStub(channel, callOptions);
    }
    public static GrpcServicesTestStub newTestStub(
            io.grpc.Channel channel) {
        return new GrpcServicesTestStub(channel);
    }
    public Result Sum(Hello request) {
        return blockingUnaryCall(
                getChannel(), CREATE_METHOD, getCallOptions(), request);
    }
}
