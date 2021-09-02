# Scale Locator for resiliency

## step 1 - start from the root project directory

```shell
cd ~/projects/gemfire/spring-geode-showcase
```


## step 2 - apply configuration to add one additional locator

```shell
k apply -f cloud/k8/data-services/exercise-scalability/01-locator-scale/gemfire1-2loc-1data.yml
```

## step 3 - wait for gemfire1-locator-1 state to be ready and running (control^C to stop)

```shell
watch kubectl get pods
```


## step 4 - Open a new shell -> click (+)

## step 5 - Read data in a loop to check resiliency due to step 6

```shell
for i in $(seq 1 2000);
do
curl -X 'GET' 'http://localhost:8080/findById?s=1'  -H 'accept: */*'  ; echo
sleep 1s
done
```



# step 6 - Delete or kill the first locator  (may take several seconds)

Note: Click on a different shell (not the one executing the for loop)

```shell
k delete pod gemfire1-locator-0
```

# step 7 - Watch for locator to be recreated (control^C to stop)
See the loop shell from step 5 (should not see any errors)  (Control^C to stop)

```shell
watch kubectl get pods
```


-------------------------------------------
# Scale Data Node/Cache Server

## step 1 - start from the root project directory

```shell
cd ~/projects/gemfire/spring-geode-showcase
```

## step 2 - apply configuration to add two additional data node/cache server

```shell
k apply -f cloud/k8/data-services/exercise-scalability/02-datanode-scale/gemfire1-2loc-3data.yml
```


## step 3 - wait for the addition gemfire1-server (1-2) states to be ready and running (control^C to stop)

```shell
watch kubectl get pods
```


-------------------------------------------
# Add  Redundancy to region for In-Memory Fault tolerance

## step 1 - start GemFire command line interface

```shell
kubectl exec -it gemfire1-locator-0 -- gfsh
```


## step 2 - In gfsh, connect to cluster, recreate region for Redundancy

```shell
connect
destroy region --name=/Account
create region --name=Account --type=PARTITION_REDUNDANT --startup-recovery-delay=1000
exit
```


## step 3 - write data using the account-service

```shell
curl -X 'POST' \
'http://localhost:8080/save' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{ "id": "1", "name": "Acct 1" }'  ; echo
```


## step 4 - Read data

```shell
curl -X 'GET' 'http://localhost:8080/findById?s=1' -H 'accept: */*'  ; echo
```


-------------------------------------------
# Verify In-Memory Fault tolerance

## step 1 - delete/kill a cache server (may take several seconds)

```shell
k delete pod gemfire1-server-0
```

## step 2 - verify no data lost (may take a couple of seconds while data re-balancing occurs)

```shell
curl -X 'GET' 'http://localhost:8080/findById?s=1' -H 'accept: */*'  ; echo
```

## step 3 - Wait killed data-node to recovery  (Control^C to stop)

```shell
watch kubectl get pods
```

## step 4 - delete/kill another cache server (may take several seconds)

```shell
kubectl delete pod gemfire1-server-1
```

## step 5 - verify no data lost (may take a couple of seconds while data re-balancing occurs)

```shell
curl -X 'GET' 'http://localhost:8080/findById?s=1' -H 'accept: */*' ; echo
```

## step 6 - Wait killed data-node to recovery  (Control^C to stop)

```shell
watch kubectl get pods
```

## step 7 - delete/kill another cache server (may take several seconds)

```shell
kubectl delete pod gemfire1-server-2
```

## step 8 - verify no data lost

```shell
curl -X 'GET' 'http://localhost:8080/findById?s=1' -H 'accept: */*'  ; echo
```
