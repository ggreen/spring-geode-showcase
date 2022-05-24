```shell
create region --name=Account --type=PARTITION
```

```shell
create region --name=Location --type=PARTITION
```

Start application

```shell
java -jar applications/account-location-event-service/target/account-location-event-service-0.0.1-SNAPSHOT.jar

```