/*
 * Copyright 2015 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package my.test.dixa.proxy;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import my.test.dixa.primenumber.PrimeNumberGrpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.logging.Logger;

/**
 * A simple client that requests a greeting from the {@link HelloWorldServer}.
 */
@SpringBootApplication
public class PrimeNumberClient {
    private static final Logger logger = Logger.getLogger(PrimeNumberClient.class.getName());

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PrimeNumberClient.class, args);
    }

    /**
     * We're hard coding up the port and server to be localhost:XX for now
     * @return
     */
    @Bean
    public ManagedChannel managedChannel() {
        String target = "localhost:50051";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();
        return channel;
    }

    @Bean
    public PrimeNumberGrpc.PrimeNumberBlockingStub blockingStub(ManagedChannel channel) {
        return PrimeNumberGrpc.newBlockingStub(channel);
    }
}
