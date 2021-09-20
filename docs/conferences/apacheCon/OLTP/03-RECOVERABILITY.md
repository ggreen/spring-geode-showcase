# Create Another GemFire Cluster with Cluster Gateway WAN replication

## step 1 - Apply configuration to create addition GemFire cluster

```shell
k apply -f cloud/k8/data-services/oltp/gemfire2-2loc-3data.yml
```


## step 2 - step Wait for pods (2 Locators gemfire2-locator(0-1) and 2 Data node gemfire2-server(0-2) to be ready  (Control^C to stop)

```shell
watch kubectl get pods
```

## step 3 - Create Gateway receiver to cluster 1 

```shell
kubectl exec -it gemfire1-locator-0 -- gfsh -e connect -e "create gateway-receiver"
```

## step 4 - Gateway sender from cluster 2 to cluster 1 for account region

```shell
kubectl exec -it gemfire2-locator-0 -- gfsh -e connect  -e "create gateway-sender --id=Account_Sender_to_1 --parallel=true  --remote-distributed-system-id=1 --enable-persistence=true --enable-batch-conflation=true"
```

## step 5 - Create Account region to use the Gateway sender from cluster 2 to cluster 1

```shell
kubectl exec -it gemfire2-locator-0 -- gfsh -e connect -e "create region --name=Account --type=PARTITION_PERSISTENT --gateway-sender-id=Account_Sender_to_1"
```

## step 6 - Create Location region to use the Gateway sender from cluster 2 to cluster 1

```shell
kubectl exec -it gemfire2-locator-0 -- gfsh -e connect -e "create region --name=Location --type=PARTITION_PERSISTENT --colocated-with=/Account  --gateway-sender-id=Account_Sender_to_1"
```


----------------------------------------------------------
# Deployment Application to access GemFire Cluster 2

## step 1 - deploy spring-geode-kotlin-transaction app to use GemFire cluster 2

```shell
k apply -f cloud/k8/apps/wan/app-transactions-wan2.yml
```

## step 2 - Wait for spring-geode-kotlin-transaction-wan2 to be running  (Control^C to stop)

```shell
watch kubectl get pods
```

## step 3 - Deploy spring-geode-showcase application to use GemFire cluster 2

```shell
k apply -f cloud/k8/apps/wan/app-wan2.yml
```

## step 4 - Export port of the spring-geode-kotlin-transaction-wan2 app

```shell
kubectl port-forward deployment/account-location-rest-service-wan2 9290:8080
```


----------------------------------------------------------
# Verify WAN replication

## step 1 - Read from spring-geode-showcase connect to clusrer 1
Response should be Empty/null

```shell
curl -X 'GET' 'http://localhost:8080/findById?s=ACCT-WAN' -H 'accept: */*'  ; echo
```


## step 2 - Write data into cluster 2 using a transaction

```shell
curl -X 'POST' \
'http://localhost:9290/save' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{ "account": { "id": "WAN", "name": "Account WAN" }, "location": { "id": "WAN", "address": "123 WAN Street-WAN", "city": "NYC", "stateCode": "NY","zipCode": "55555"} }'  ; echo
```

## step 3 - Read the WAN replicated data from cluster 1

```shell
curl -X 'GET' 'http://localhost:8080/findById?s=WAN' -H 'accept: */*'  ; echo
```
