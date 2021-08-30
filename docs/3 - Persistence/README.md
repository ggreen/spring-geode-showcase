# -----------------------------------------------------
# Create persistent region

## step 1 - start the GemFire command line
kubectl exec -it gemfire1-locator-0 -- gfsh


## step 2 - Execute in gfsh to connect, delete previous region and create as persistent

connect
destroy region --name=/Account
create region --name=Account --type=PARTITION_PERSISTENT
exit


## step 3 - The account service will return a not-found empty null value

curl -X 'GET' 'http://localhost:8080/findById?s=1' \
-H 'accept: */*' ; echo


## step 4 - write data 


curl -X 'POST' \
'http://localhost:8080/save' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"id": "1",
"name": "Acct 1"
}'  ; echo

## step 4 - read data

curl -X 'GET' 'http://localhost:8080/findById?s=1' \
-H 'accept: */*'  ; echo


# -----------------------------------------------------
# 
## step 1 - remote cache server
k delete pod gemfire1-server-0

# step 2 - Wait for gemfire1-server-0 to be in a running stats
watch kubectl get pods



## step 3 - read data - that will be reloaded from disk
curl -X 'GET' 'http://localhost:8080/findById?s=1' \
-H 'accept: */*'  ; echo