### Description:Proxy-service

#### Proxy Service
The `proxy-service` acts as an entry point to the outside world. It's main tasks are:

* expose a HTTP endpoint over REST responding to ​GET /prime/<number>​ that continuously streams all prime numbers up to a given <number> e.g./prime/17 should return 2,3,5,7,11,13,17.
* delegates the actual calculation to the second microservice via aFinagle-Thrift OR gRPC RPC call
* handles wrong inputs in a proper wayPrime-number-server

#### Prime Number Server 
The `prime-number-server` does the actual Prime number calculation -it serves responses continuously over Finagle OR gRPC anduses proper abstractions to communicate failure

### Steps:
Run HelloWorldServer.java
Run HelloWorldClient.java

Try out and see an example output of prime numbers with a curl request like the following 
` curl -X GET http://localhost:8080/prime/number/222 ` 

### Deliverables:

There are three deliverables necessary to complete the task
* proxy-service
* prime-number-server
* protobuf contracts used for communication between the two services are located in the common module

#### Requirements:
* Language of implementation: Java
* Communication between - gRPC
* Basic scenario test cases
* Proper commit history
* README describing implementation choices and preferences
