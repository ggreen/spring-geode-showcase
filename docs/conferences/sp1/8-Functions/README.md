
Build function

```shell
mvn -pl components/account-functions -am package
```

Add -c <specific-container> as needed
```shell
kubectl cp components/account-functions/target/account-functions-0.0.1-SNAPSHOT.jar gemfire1-locator-0:/data/account-functions-0.0.1-SNAPSHOT.jar
```

```shell
kubectl exec -it gemfire1-locator-0 -- gfsh -e connect -e "deploy --jar=/data/account-functions-0.0.1-SNAPSHOT.jar"
```

```shell
kubectl exec -it gemfire1-locator-0 -- gfsh -e connect -e "list functions"
```

```shell script
kubectl exec -it gemfire1-locator-0 -- gfsh -e connect -e "execute function --id=AccountCountInNyFunction --region=/Account"
```
