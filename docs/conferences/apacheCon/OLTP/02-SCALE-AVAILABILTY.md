
# Create GemFire Cluster

```shell
cd /Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-geode-showcase
```

## step 1 - Change directory to where the example Spring Boot applications

```shell
kubectl apply -f cloud/k8/data-services/oltp/gemfire-1loc-1server.yml
```

## step 3 login into the GemFire cluster

```shell
kubectl exec -it gemfire1-locator-0 -- gfsh -e "connect" -e "create region --name=Account --type=PARTITION_PERSISTENT"
```


--------------------------
# Deploy applications

## step 1 - Deploy the definitions located in cloud/k8/apps/app.yml

```shell
kubectl apply -f cloud/k8/apps
```

## step 2 - use the watch command util the application spring-geode-showcase pod state is ready   (Control^C to stop)

```shell
watch kubectl get pods
```

## step 3 - Expose the spring-geode-showcase to be accessed using port 8080

```shell
kubectl port-forward deployment/account-rest-service 8080:8080
```

## step 5 - Write account data

```shell
curl -X 'POST' \
'http://localhost:8080/save' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{ "id": "1", "name": "Acct 1" }'  ; echo

```

## step 6 - Read account data

```shell
curl -X 'GET' 'http://localhost:8080/findById?s=1' -H 'accept: */*'  ; echo
```


--------------------------
# K8 Auto Healing

## step 1 - Delete/Kill the cache server data node (may take several seconds)

```shell
k delete pod gemfire1-server-0
```

## step 2 - watch the kubernetes platform recreate the deleted server (Control^C to stop)

```shell
watch kubectl get pods
```


## step 3 - Try to Read account, it will be empty because the data is persisted 

```shell
curl -X 'GET' 'http://localhost:8080/findById?s=1' -H 'accept: */*'  ; echo
```
--------------

## s apply configuration to add one additional locator

```shell
k apply -f cloud/k8/data-services/oltp/gemfire1-2loc-1data.yml
```

## step 3 - wait for gemfire1-locator-1 state to be ready and running (control^C to stop)

```shell
watch kubectl get pods
```

## step 5 - Read data in a loop to check resiliency due to step 6

```shell
for i in $(seq 1 2000);
do
curl -X 'GET' 'http://localhost:8080/findById?s=1'  -H 'accept: */*'  ; echo
sleep 1s
done
```

```shell
k delete pod gemfire1-locator-0
```
See the loop shell from step 5 (should not see any errors)  (Control^C to stop)

```shell
watch kubectl get pods
```



-------------------------------------------
# Scale Data Node/Cache Server

##  apply configuration to add two additional data node/cache server

```shell
k apply -f cloud/k8/data-services/oltp/gemfire1-2loc-3data.yml
```
##  wait for the addition gemfire1-server (1-2) states to be ready and running (control^C to stop)

```shell
watch kubectl get pods
```
