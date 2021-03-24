package my.test.dixa.proxy;

import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import my.test.dixa.primenumber.PrimeNumberGrpc;
import my.test.dixa.primenumber.PrimeNumberRequest;
import my.test.dixa.primenumber.PrimeNumberResponse;
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
    private static final Logger logger = Logger.getLogger(PrimeNumberClient.class.getName());

    private static final String target = "localhost:50051";
    private final PrimeNumberGrpc.PrimeNumberBlockingStub blockingStub;
    private final ManagedChannel channel;

    public PrimeService(@Autowired ManagedChannel channel) {
        // Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
        this.blockingStub = PrimeNumberGrpc.newBlockingStub(channel);
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
