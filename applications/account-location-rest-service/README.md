
In Gfsh 

```shell
create region --name=Account --type=PARTITION
```

```shell
create region --name=Location --type=PARTITION
```

--------------


```shell
cd applications/account-location-rest-service
mvn  spring-boot:build-image

```




```shell script
docker tag account-location-rest-service:0.0.1-SNAPSHOT nyla/account-location-rest-service:0.0.1-SNAPSHOT 
docker login
docker push nyla/account-location-rest-service:0.0.1-SNAPSHOT

```


```shell
java -jar applications/account-location-rest-service/target/account-location-rest-service-0.0.1-SNAPSHOT.jar --spring.profiles.action=local

```