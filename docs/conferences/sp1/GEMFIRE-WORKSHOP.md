

------

# Pre-Workshop Setup

cd ~/projects/gemfire/spring-geode-showcase

git pull

./cloud/k8/setup.sh



# Cluster Setup

kubectl apply -f cloud/k8/data-services/sp1/exercise1/gemfire1.yml

---



# Build Application Docker Image

```shell
cd ~/projects/gemfire/spring-geode-showcase/applications/spring-geode-showcase/

mvn spring-boot:build-image

kind load docker-image spring-geode-showcase:0.0.1-SNAPSHOT
```

## Deploy application
```shell
cd ~/projects/gemfire/spring-geode-showcase/

kubectl apply -f cloud/k8/config-maps.yml
```

## Configure GemFire 
```shell script
kubectl exec -it gemfire1-locator-0 -- gfsh
```

In Gfsh

```shell script
connect

create region --name=Account --type=PARTITION

exit
```


Deploy applications
```shell
kubectl apply -f cloud/k8/apps
kubectl get pods
```


kubectl port-forward deployment/spring-geode-showcase 8080:8080


------

One new shell -> click (+) 

## Data Access

```shell script
curl -X 'POST' \
  'http://localhost:8080/save' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "1",
  "name": "Acct 1"
}'
```


```shell script
curl -X 'GET' 'http://localhost:8080/findById?s=1' \
  -H 'accept: */*'
```


--------------------
**K8 Auto Healing**

```shell script
k delete pod gemfire1-server-0
watch kubectl get pods
```


Error (before pod is recreated) or null once restarted
```shell
curl -X 'GET' 'http://localhost:8080/findById?s=1' \
-H 'accept: */*'
```

Repost data

```shell script
curl -X 'POST' \
'http://localhost:8080/save' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"id": "1",
"name": "Acct 1"
}'
```

```shell script
curl -X 'GET' 'http://localhost:8080/findById?s=1' \
-H 'accept: */*'

```
------------------

## GemFire Persistence


```shell script
kubectl exec -it gemfire1-locator-0 -- gfsh
```

```shell script
connect

destroy region --name=/Account

create region --name=Account --type=PARTITION_PERSISTENT

exit
```

The following returns Null Value
```shell script
curl -X 'GET' 'http://localhost:8080/findById?s=1' \
-H 'accept: */*'
```

Save data
```shell script
curl -X 'POST' \
'http://localhost:8080/save' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"id": "1",
"name": "Acct 1"
}'
```

```shell script
curl -X 'GET' 'http://localhost:8080/findById?s=1' \
-H 'accept: */*'
```


```shell script
k delete pod gemfire1-server-0
watch kubectl get pods
```

Wait for gemfire1-server-0 in running stats

```shell script
curl -X 'GET' 'http://localhost:8080/findById?s=1' \
-H 'accept: */*'
```

----------------

# Scalability

## Scale Locator

```shell
cd ~/projects/gemfire/spring-geode-showcase

k apply -f cloud/k8/data-services/sp1/exercise-scalability/01-locator-scale/gemfire1-2loc-1data.yml

watch kubectl get pods
```

Open new shell
```shell
for i in $(seq 1 2000);
do
curl -X 'GET' 'http://localhost:8080/findById?s=1' \
-H 'accept: */*'
sleep 1s
done
```

```shell
k delete pod gemfire1-locator-0

watch kubectl get pods
```

Look for errors/Kill loop


## Scale Data

```shell
cd ~/projects/gemfire/spring-geode-showcase

k apply -f cloud/k8/data-services/sp1/exercise-scalability/02-datanode-scale/gemfire1-2loc-3data.yml

watch kubectl get pods
```

```shell
kubectl exec -it gemfire1-locator-0 -- gfsh
```

```shell script
connect

destroy region --name=/Account

create region --name=Account --type=PARTITION_REDUNDANT --startup-recovery-delay=1000

exit
```

```shell script
curl -X 'POST' \
  'http://localhost:8080/save' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "1",
  "name": "Acct 1"
}'
```


```shell script
curl -X 'GET' 'http://localhost:8080/findById?s=1' \
  -H 'accept: */*'
```

k delete pod gemfire1-server-0

```shell script
curl -X 'GET' 'http://localhost:8080/findById?s=1' \
  -H 'accept: */*'
```

Wait for recovery

```shell
watch kubectl get pods
```

```shell
kubectl delete pod gemfire1-server-1
```

```shell script
curl -X 'GET' 'http://localhost:8080/findById?s=1' -H 'accept: */*'
```

```shell
kubectl delete pod gemfire1-server-2
```

```shell script
curl -X 'GET' 'http://localhost:8080/findById?s=1' \
  -H 'accept: */*'
```



# Transactions

Delete Cluster 

```shell
k delete GemFireCluster gemfire1

watch kubectl get pods
```

Redeploy cluster with allow persistence transactions
```shell
k apply -f cloud/k8/data-services/sp1/exercise-scalability/03-Transactions/gemfire1-2loc-3data-allow-persistence-transactions.yml

watch kubectl get pods
```

```shell
kubectl exec -it gemfire1-locator-0 -- gfsh -e connect -e "destroy region --name=/Location"

kubectl exec -it gemfire1-locator-0 -- gfsh -e connect -e "destroy region --name=/Account"

kubectl exec -it gemfire1-locator-0 -- gfsh -e connect -e "create region --name=Account --type=PARTITION_PERSISTENT"

kubectl exec -it gemfire1-locator-0 -- gfsh -e connect -e "create region --name=Location --type=PARTITION_PERSISTENT --colocated-with=/Account"
```

Deploy spring-geode-kotlin-transaction application

```shell
cd ~/projects/gemfire/spring-geode-showcase/applications/spring-geode-kotlin-transaction/

mvn clean package spring-boot:build-image

kind load docker-image spring-geode-kotlin-transaction:0.0.1-SNAPSHOT

cd ~/projects/gemfire/spring-geode-showcase

k apply -f cloud/k8/apps/transactions/app-transactions.yml

watch kubectl get pods
```
```shell
k port-forward deployment/spring-geode-kotlin-transaction 9090:8080
```


In another shell

```shell
curl -X 'POST' \
  'http://localhost:9090/save' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "account": {
    "id": "ACCT-C",
    "name": "Account C"
  },
  "location": {
    "id": "ACCT-C",
    "address": "123 ACCT-C Street",
    "city": "NYC",
    "stateCode": "NY",
    "zipCode": "02323"
  }
}'
```

```shell script
curl -X 'GET' 'http://localhost:8080/findById?s=ACCT-C' -H 'accept: */*'
```

````shell
curl -X 'POST' \
'http://localhost:9090/save' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"account": {
"id": "ACCT-C",
"name": "Account C-invalid"
},
"location": {
"id": "ACCT-C",
"address": "123 ACCT-C Street-INVALID",
"city": "NYC",
"stateCode": "NY",
"zipCode": "INVALID"
}
}'
````

Should not see "invalid" data

```shell script
curl -X 'GET' 'http://localhost:8080/findById?s=ACCT-C' -H 'accept: */*'
```


-----------------------

# WAN Replication


```shell
k apply -f cloud/k8/data-services/sp1/exercise-scalability/04-WAN/gemfire2-2loc-3data.yml
```

Wait for pods (2 Locators gemfire2-locator(0-1) and 2 Data node gemfire2-server(0-2) to be ready
```shell
watch kubectl get pods
```

```shell
kubectl exec -it gemfire1-locator-0 -- gfsh -e connect -e "create gateway-receiver"
```
```shell
kubectl exec -it gemfire2-locator-0 -- gfsh -e connect  -e "create gateway-sender --id=Account_Sender_to_1 --parallel=true  --remote-distributed-system-id=1 --enable-persistence=true --enable-batch-conflation=true" 

kubectl exec -it gemfire2-locator-0 -- gfsh -e connect -e "create region --name=Account --type=PARTITION_PERSISTENT --gateway-sender-id=Account_Sender_to_1"

kubectl exec -it gemfire2-locator-0 -- gfsh -e connect -e "create region --name=Location --type=PARTITION_PERSISTENT --colocated-with=/Account  --gateway-sender-id=Account_Sender_to_1"
```



```shell
k apply -f cloud/k8/apps/wan/app-transactions-wan2.yml

```

Wait for spring-geode-kotlin-transaction-wan2
```shell
watch kubectl get pods
```

```shell
k apply -f cloud/k8/apps/wan/app-wan2.yml
```

```shell
kubectl port-forward deployment/spring-geode-kotlin-transaction-wan2 9290:8080
```



Empty

```shell script
curl -X 'GET' 'http://localhost:8080/findById?s=ACCT-WAN' \
  -H 'accept: */*'
```



```shell script
curl -X 'POST' \
  'http://localhost:9290/save' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
-d '{
"account": {
"id": "WAN",
"name": "Account WAN"
},
"location": {
"id": "WAN",
"address": "123 WAN Street-WAN",
"city": "NYC",
"stateCode": "NY",
"zipCode": "55555"
}
}'
```


```shell script
curl -X 'GET' 'http://localhost:8080/findById?s=WAN' \
  -H 'accept: */*'
```