package my.test.dixa.proxy;

import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import my.test.dixa.helloworld.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PrimeService {
    private static final Logger logger = Logger.getLogger(HelloWorldClient.class.getName());

    private static final String target = "localhost:50051";
    private final GreeterGrpc.GreeterBlockingStub blockingStub;
    private final ManagedChannel channel;

    public PrimeService(@Autowired ManagedChannel channel) {
        // Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
        this.blockingStub = GreeterGrpc.newBlockingStub(channel);
        this.channel = channel;
    }

    @PreDestroy
    public void destroyChannel() {
        try {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Say hello to server.
     */
    public void greet(String name) {
        logger.info("Will try to greet " + name + " ...");
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply response;
        try {
            response = blockingStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Greeting: " + response.getMessage());
    }

    /**
     * Request prime numbers from server
     */
    public List<Integer> primeNumber(int upperLimit) {
        logger.info("Trying to fetch prime numbers upto " + upperLimit);
        PrimeNumberRequest request = PrimeNumberRequest.newBuilder().setLimit(upperLimit).build();
        PrimeNumberResponse response;
        try {
            response = blockingStub.primeNumbers(request);
            logger.info("PrimeNumbers: " + response.getNumberList());
            return response.getNumberList();
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
        }
        return Collections.emptyList();
    }

}
