# Recreate GemFire cluster 

## step 1 - Delete the GemFire Cluster

```shell
k delete GemFireCluster gemfire1
```


## step 2 - wait for all gemfire members to be deleted  (Control^C to stop)

```shell
watch kubectl get pods
```

## step 3 - cleanup persistence volume claims

```shell
k delete pvc data-gemfire1-locator-0 data-gemfire1-locator-1  data-gemfire1-server-0 data-gemfire1-server-1 data-gemfire1-server-2
```



----------------------------------------------------
# Redeploy cluster with allow persistence transactions

## step 1 - Create GemFire cluster with addition properties such as -Dgemfire.ALLOW_PERSISTENT_TRANSACTIONS=true

```shell
k apply -f cloud/k8/data-services/exercise-scalability/03-Transactions/gemfire1-2loc-3data-allow-persistence-transactions.yml
```


## step 2 - wait for 2 locators and 3 data node gemfire members to be running  (Control^C to stop)

```shell
watch kubectl get pods
```


## step 3 - Create Account with persistence

```shell
kubectl exec -it gemfire1-locator-0 -- gfsh -e connect -e "create region --name=Account --type=PARTITION_PERSISTENT"
```

## step 4 - Create Location with persistence co-located with the Account region

```shell
kubectl exec -it gemfire1-locator-0 -- gfsh -e connect -e "create region --name=Location --type=PARTITION_PERSISTENT --colocated-with=/Account"
```


# step 5 - Verify Account and Location Region created

```shell
kubectl exec -it gemfire1-locator-0 -- gfsh -e connect -e "list region"
```

------------------------------------------------------
# Deploy spring-geode-kotlin-transaction application

## step 1 - Change to root directory of the spring-geode-kotlin-transaction app

```shell
cd ~/projects/gemfire/spring-geode-showcase/applications/spring-geode-kotlin-transaction/
```

## step 2 - Build the spring-geode-kotlin-transaction app docker image

```shell
mvn clean package spring-boot:build-image
```

## step 3 - Make the spring-geode-kotlin-transaction image available to kind

```shell
kind load docker-image spring-geode-kotlin-transaction:0.0.1-SNAPSHOT
```

## step 4 - Change to the root project directory

```shell
cd ~/projects/gemfire/spring-geode-showcase
```

## step 5 - deploy the spring-geode-kotlin-transaction in the local kubernetes cluster

```shell
k apply -f cloud/k8/apps/transactions/app-transactions.yml
```

## step 6 - Wait for the application to be running  (Control^C to stop)

```shell
watch kubectl get pods
```

## step 7 - expose listening port of the spring-geode-kotlin-transaction app

```shell
k port-forward deployment/spring-geode-kotlin-transaction 9090:8080
```


------------------------------------------------------
# Verify account and location region transactions

## step 1 - Save account and location data in a single transaction

```shell

curl -X 'POST' \
'http://localhost:9090/save' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{ "account": { "id": "ACCT-C", "name": "Account C" }, "location": { "id": "ACCT-C", "address": "123 ACCT-C Street", "city": "NYC", "stateCode": "NY", "zipCode": "02323" } }' ; echo

```

## step 2 - Read account data

```shell
curl -X 'GET' 'http://localhost:8080/findById?s=ACCT-C' -H 'accept: */*' ; echo
```


## step 3 - Verify account info rolled back when invalid location zipcode provided

```shell
curl -X 'POST' \
'http://localhost:9090/save' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{ "account": { "id": "ACCT-C", "name": "Account C-invalid" }, "location": { "id": "ACCT-C", "address": "123 ACCT-C Street-INVALID","city": "NYC","stateCode": "NY","zipCode": "INVALID"} }'  ; echo
```


## step 4 - Read data - Should not see "invalid" account data

```shell
curl -X 'GET' 'http://localhost:8080/findById?s=ACCT-C' -H 'accept: */*'  ; echo
```
