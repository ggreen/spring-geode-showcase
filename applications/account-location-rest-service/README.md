```shell
cd applications/account-location-rest-service
mvn  spring-boot:build-image

```




```shell script
docker tag account-location-rest-service:0.0.1-SNAPSHOT nyla/account-location-rest-service:0.0.1-SNAPSHOT 
docker login
docker push nyla/account-location-rest-service:0.0.1-SNAPSHOT

```