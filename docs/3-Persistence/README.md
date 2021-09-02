----------------------------------------------
# Create persistent region

## step 1 - start the GemFire command line

```shell
kubectl exec -it gemfire1-locator-0 -- gfsh
```


## step 2 - Execute in gfsh to connect, delete previous region and create as persistent (may take several seconds)

```shell
connect
destroy region --name=/Account
create region --name=Account --type=PARTITION_PERSISTENT
exit
```


## step 3 - The account service will return a not-found empty null value

```shell
curl -X 'GET' 'http://localhost:8080/findById?s=1' -H 'accept: */*' ; echo
```


## step 4 - write data 

```shell
curl -X 'POST' \
'http://localhost:8080/save' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{ "id": "1", "name": "Acct 1" }'  ; echo
```

## step 4 - read data

```shell
curl -X 'GET' 'http://localhost:8080/findById?s=1' -H 'accept: */*'  ; echo
```


----------------------------------------------
# Fault Tolerance with Persistent
## step 1 - delete cache server (may take several seconds)

```shell
k delete pod gemfire1-server-0
```

# step 2 - Wait for gemfire1-server-0 to be in a running stats (control^C to stop)

```shell
watch kubectl get pods
```



## step 3 - read data - that will be reloaded from disk

```shell
curl -X 'GET' 'http://localhost:8080/findById?s=1' -H 'accept: */*'  ; echo
```
