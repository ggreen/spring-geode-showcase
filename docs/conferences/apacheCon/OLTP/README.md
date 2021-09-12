```shell
cd /Users/devtools/repositories/IMDG/geode/apache-geode-devOps
```

```shell
./startLocator.sh
```

```shell
./startDataNode.sh
```

List members

```shell
./listMemberStatus.sh 
```

```shell
./gfsh.sh
```

```shell
connect --user=admin
```


In Gfsh

```shell
create region --name=Account --type=PARTITION_PERSISTENT
```

```shell
create region --name=Location --type=PARTITION_PERSISTENT --colocated-with=/Account
```

Start Account Location REST App
```shell
java -Dspring.profiles.active=local -jar applications/account-location-rest-service/target/account-location-rest-service-0.0.1-SNAPSHOT.jar
```

Start Account REST App

```shell
java -Dspring.profiles.active=local -jar  applications/account-rest-service/target/account-rest-service-0.0.1-SNAPSHOT.jar
```

Save Account and Location with a committed transaction
```shell
curl -X 'POST' \
  'http://localhost:8081/save' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "account": {
    "id": "1",
    "name": "Account 1"
  },
  "location": {
    "id": "1",
    "address": "Account Location Address",
    "city": "MyCity",
    "stateCode": "NY",
    "zipCode": "21322"
  }
}'
```

Get account Expected: {"id":"1","name":"Account 1"}

```shell
curl -X 'GET' \
  'http://localhost:8080/accounts/1' \
  -H 'accept: */*';echo
```



Expected Internal Server Error with a transaction Rollback

```shell
curl -X 'POST' \
  'http://localhost:8081/save' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "account": {
    "id": "1",
    "name": "Invalid Location ZIP Rolled Back"
  },
  "location": {
    "id": "1",
    "address": "231112321321~@#####$#$#",
    "city": "21~@#####$#$#",
    "stateCode": "21~@#####$#$#",
    "zipCode": "INVALID"
  }
}'
```

Get account Expected: {"id":"1","name":"Account 1"}

```shell
curl -X 'GET' \
  'http://localhost:8080/accounts/1' \
  -H 'accept: */*';echo
```

Security, account cannot write the Location

In gfsh

```shell
disconnect
connect --user=account
query --query="select * from /Account"

put --key=delete --value={id="delete",name="delete"} --region=/Account
remove --key=delete --region=/Account

```

Will not have READ access to Location region
```shell
query --query="select * from /Location"

```

Will not have WRITE access to Location region
```shell
put --key=delete --value={id="delete"} --region=/Location
```
