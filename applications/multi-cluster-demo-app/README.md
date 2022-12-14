# Getting Started


GemFire 

https://docs.vmware.com/en/VMware-Tanzu-GemFire/9.10/tgf/GUID-getting_started-installation-obtain_gemfire_maven.html


Use Case

```
GemFire Spring client  multi-cluster connections pattern


- Connect two GemFireCluster 
- Hosted in TAS
- support multiple credential
- Spring Boot 
- spring Data GemFire
- Claims region (in cluster 1) and member region (cluster 2)
- Test services insert claim in the cluster 1
- Test insert payment in cluster 2
- Test reading 
https://docs.spring.io/spring-boot-data-geode-build/current/reference/html5/#cloudfoundry-cloudcache-multiinstance-using
- Work with JDK 8
- Should follow customer provided example  



```

# Start GemFire 

Start Gfsh

```shell
cd $GEMFIRE_HOME/bin
./gfsh
```
-------------------------------------
## Start Cluster 1 
In Gfsh

Start Locator 
```shell
start locator --name=gf1-locator  --port=10001 --J=-Dgemfire.jmx-manager-port=1099 --max-heap=250m --initial-heap=250m --bind-address=127.0.0.1 --hostname-for-clients=127.0.0.1  --jmx-manager-hostname-for-clients=127.0.0.1 --http-service-bind-address=127.0.0.1
```
Configure PDX

```shell
configure pdx --read-serialized=true --disk-store
```

```shell
start server --name=gf1-server  --server-port=10101   --locators=127.0.0.1[10001] --max-heap=1g   --initial-heap=1g  --bind-address=127.0.0.1 --hostname-for-clients=127.0.0.1  --jmx-manager-hostname-for-clients=127.0.0.1 --http-service-bind-address=127.0.0.1
```

Create region 
```shell
create region --name=Claim --type=PARTITION
```

-------------------------------------
## Start Cluster 2
In Gfsh 

Exit Gfsh

```shell
cd $GEMFIRE_HOME/bin
./gfsh
```

Start Locator
```shell
start locator --name=gf2-locator --http-service-port=0 --J=-Dgemfire.tcp-port=11111 --port=10002 --J=-Dgemfire.jmx-manager-port=1098 --max-heap=250m --initial-heap=250m --bind-address=127.0.0.1 --hostname-for-clients=127.0.0.1  --jmx-manager-hostname-for-clients=127.0.0.1 --http-service-bind-address=127.0.0.1
```
Configure PDX

```shell
configure pdx --read-serialized=true --disk-store
```

```shell
start server --name=gf2-server  --server-port=10102   --locators=127.0.0.1[10002] --max-heap=1g   --initial-heap=1g  --bind-address=127.0.0.1 --hostname-for-clients=127.0.0.1  --jmx-manager-hostname-for-clients=127.0.0.1 --http-service-bind-address=127.0.0.1
```

Create region
```shell
create region --name=Claim --type=PARTITION
```

Create region
```shell
create region --name=Member --type=PARTITION
```


contentType = application/json

```json
{
  "id":"1",
  "description":"ORDER_TEST",
  "customer":{"id":"bunny"},
  "orderStatusCode":1,
  "orderDetails":[]
}
```

# Build Docker


```shell
mvn install
cd applications/customer-order-app
mvn spring-boot:build-image

docker tag customer-order-app:0.0.2-SNAPSHOT cloudnativedata/customer-order-app:0.0.2-SNAPSHOT
docker push cloudnativedata/customer-order-app:0.0.2-SNAPSHOT
```

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.0/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#web)
* [Apache Freemarker](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#web.servlet.spring-mvc.template-engines)
* [Spring for RabbitMQ](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#messaging.amqp)
* [Cloud Stream](https://docs.spring.io/spring-cloud-stream/docs/current/reference/html/spring-cloud-stream.html#spring-cloud-stream-overview-introducing)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#actuator)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Messaging with RabbitMQ](https://spring.io/guides/gs/messaging-rabbitmq/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

