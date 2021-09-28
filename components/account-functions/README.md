


```shell script
deploy --jar=/Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-geode-showcase/components/account-functions/target/account-functions-0.0.1-SNAPSHOT.jar

```

```shell
mvn -pl components/account-functions -am package
```

gfsh

```shell
execute function --id=AccountCountInNyFunction --region=/Account
```