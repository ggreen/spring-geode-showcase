# Getting Started

This example application illustrates connecting to multiple GemFire cluster with different security manager implementations.


A custom implementation of the [AuthInitialize](https://gemfire.docs.pivotal.io/apidocs/tgf-915/org/apache/geode/security/AuthInitialize.html) is needed to determine which credential is needed to authenticate to a cluster. The [getCredentials](https://gemfire.docs.pivotal.io/apidocs/tgf-915/org/apache/geode/security/AuthInitialize.html#getCredentials-java.util.Properties-org.apache.geode.distributed.DistributedMember-boolean-) method is passed the [DistributedMember](https://gemfire.docs.pivotal.io/apidocs/tgf-915/org/apache/geode/distributed/DistributedMember.html). You can use the member information such as the [getId()](https://gemfire.docs.pivotal.io/apidocs/tgf-915/org/apache/geode/distributed/DistributedMember.html#getId--) to determine which credential to return.


See the [MultiClusterAuthInit reference implementation](https://github.com/ggreen/spring-geode-showcase/blob/master/applications/multi-cluster-demo-app/src/main/java/com/vmware/gemfire/multi/cluster/controller/security/MultiClusterAuthInit.java). Also note that the [GfSecuritiesEnvSpringLocator](https://github.com/ggreen/spring-geode-showcase/blob/master/applications/multi-cluster-demo-app/src/main/java/com/vmware/gemfire/multi/cluster/controller/security/GfSecuritiesEnvSpringLocator.java) acts as a bridge between Spring and GemFire to provide the [MultiClusterAuthInit](ttps://github.com/ggreen/spring-geode-showcase/blob/master/applications/multi-cluster-demo-app/src/main/java/com/vmware/gemfire/multi/cluster/controller/security/MultiClusterAuthInit.java) with the [Spring Environment](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/env/Environment.html) object. 




Use Case


GemFire Spring client  multi-cluster connections pattern


- Connect two GemFireCluster 
- Can be hosted in TAS or Kubernetes
- Support multiple credentials
- Use Spring Boot and Spring Data GemFire
- Multiple regions in difference clusters
    - claims region (in cluster 1) 
    - member region (cluster 2)
      Should [follow example](https://docs.spring.io/spring-boot-data-geode-build/current/reference/html5/#cloudfoundry-cloudcache-multiinstance-using)


Test Scenario

- Insert claim in the cluster 1
- Test insert payment in cluster 2


# Start GemFire 


See [GemFire getting started](https://docs.vmware.com/en/VMware-Tanzu-GemFire/9.10/tgf/GUID-getting_started-installation-obtain_gemfire_maven.html)

Start Gfsh

```shell
cd $GEMFIRE_HOME/bin
./gfsh
```


------------------------------------

Generate Encrypted Password

```shell
java -classpath applications/libs/nyla.solutions.core-2.0.0.jar -DCRYPTION_KEY=PIVOTAL  nyla.solutions.core.util.Cryption MYPASSWORD
```


-------------------------------------
## Start Cluster 1 
In Gfsh

Start Locator 
```shell
start locator --name=gf1-locator --J="-DCRYPTION_KEY=PIVOTAL" --J="-Dconfig.properties=/Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-geode-showcase/deployments/local/secret/config/gemfire-one-users.properties" --J=-Dgemfire.security-manager=io.pivotal.dataTx.geode.security.UserSecurityManager  --classpath=/Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-geode-showcase/applications/libs/* --enable-cluster-configuration=true --connect=false --port=10001 --J=-Dgemfire.jmx-manager-port=1099 --max-heap=250m --initial-heap=250m --bind-address=127.0.0.1 --hostname-for-clients=127.0.0.1  --jmx-manager-hostname-for-clients=127.0.0.1 --http-service-bind-address=127.0.0.1
```
Configure PDX

```shell
configure pdx --read-serialized=true --disk-store
```

```shell
start server --name=gf1-server --use-cluster-configuration=true --J="-DCRYPTION_KEY=PIVOTAL"  --J="-Dconfig.properties=/Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-geode-showcase/deployments/local/secret/config/gemfire-one-users.properties" --user=admin --password="admin" --J=-Dgemfire.security-manager=io.pivotal.dataTx.geode.security.UserSecurityManager  --classpath=/Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-geode-showcase/applications/libs/*  --server-port=10101   --locators=127.0.0.1[10001] --max-heap=1g   --initial-heap=1g  --bind-address=127.0.0.1 --hostname-for-clients=127.0.0.1  --jmx-manager-hostname-for-clients=127.0.0.1 --http-service-bind-address=127.0.0.1
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
start locator --name=gf2-locator --J="-DCRYPTION_KEY=PIVOTAL" --J="-Dconfig.properties=/Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-geode-showcase/deployments/local/secret/config/gemfire-two-users.properties" --J=-Dgemfire.security-manager=io.pivotal.dataTx.geode.security.UserSecurityManager  --classpath=/Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-geode-showcase/applications/libs/* --enable-cluster-configuration=true --connect=false  --http-service-port=0 --J=-Dgemfire.tcp-port=11111 --port=10002 --J=-Dgemfire.jmx-manager-port=1098 --max-heap=250m --initial-heap=250m --bind-address=127.0.0.1 --hostname-for-clients=127.0.0.1  --jmx-manager-hostname-for-clients=127.0.0.1 --http-service-bind-address=127.0.0.1 
```
Configure PDX

```shell
configure pdx --read-serialized=true --disk-store
```

```shell
start server --name=gf2-server --use-cluster-configuration=true --J="-DCRYPTION_KEY=PIVOTAL"  --J="-Dconfig.properties=/Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-geode-showcase/deployments/local/secret/config/gemfire-two-users.properties" --user=admin2 --password="admin" --J=-Dgemfire.security-manager=io.pivotal.dataTx.geode.security.UserSecurityManager  --classpath=/Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-geode-showcase/applications/libs/*  --server-port=10102   --locators=127.0.0.1[10002] --max-heap=1g   --initial-heap=1g  --bind-address=127.0.0.1 --hostname-for-clients=127.0.0.1  --jmx-manager-hostname-for-clients=127.0.0.1 --http-service-bind-address=127.0.0.1
```

Create region
```shell
create region --name=Claim --type=PARTITION
```

Create region
```shell
create region --name=Member --type=PARTITION
```
--------


Connect to GF 1

```shell
connect --locator=127.0.0.1[10001]
list clients
```

Connect to GF 2

```shell
connect --locator=127.0.0.1[10002]
list clients
```

----------

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
cd applications/multi-cluster-demo-app
mvn spring-boot:build-image

docker tag multi-cluster-demo-app:0.0.1-SNAPSHOT cloudnativedata/multi-cluster-demo-app:0.0.1-SNAPSHOT
docker push cloudnativedata/multi-cluster-demo-app:0.0.1-SNAPSHOT
```

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.0/maven-plugin/reference/html/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#web)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#actuator)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Messaging with RabbitMQ](https://spring.io/guides/gs/messaging-rabbitmq/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

