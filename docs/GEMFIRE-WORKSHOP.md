

------

# Pre-Workshop Setup

```shell
# Create K8 Cluster 1 worker node
sudo kind create cluster  --config k8-1wnode.yaml
sudo cp -r /root/.kube /$HOME/.kube
sudo chown -R $USER $HOME/.kube


# Set GemFire Pre-Requisite 
 
kubectl create namespace cert-manager
helm repo add jetstack https://charts.jetstack.io
helm repo update
helm install cert-manager jetstack/cert-manager --namespace cert-manager  --version v1.0.2 --set installCRDs=true
kubectl create namespace gemfire-system
kubectl create secret docker-registry image-pull-secret --namespace=gemfire-system --docker-server=registry.pivotal.io --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD
kubectl create secret docker-registry image-pull-secret --docker-server=registry.pivotal.io --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD
kubectl create rolebinding psp-gemfire --clusterrole=psp:vmware-system-privileged --serviceaccount=gemfire-system:default


# Install the GemFire Operator
helm install gemfire-operator ~/data-services/gemfire-operator-1.0.1.tgz --namespace gemfire-system


# Create GemFire Cluster
cd ~/projects/gemfire/spring-geode-showcase
git pull
kubectl apply -f cloud/k8/data-services/exercise1/gemfire1.yml
```
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


sudo /usr/local/bin/kubectl port-forward deployment/spring-geode-showcase 8080:8080


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
k apply -f cloud/k8/data-services/exercise-scalability/01-locator-scale/gemfire1-2loc-1data.yml
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
k apply -f cloud/k8/data-services/exercise-scalability/02-datanode-scale/gemfire1-2loc-3data.yml
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



# Consistency Check

Delete Cluster 

```shell
k delete GemFireCluster gemfire1
watch kubectl get pods
```

Redeploy cluster with allow persistence transactions
```shell
k apply -f cloud/k8/data-services/exercise-scalability/03-Transactions/gemfire1-2loc-3data-allow-persistence-transactions.yml
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

-----------------------

# WAN Replication

```shell
k delete -f cloud/k8/data-services/exercise-scalability/02-datanode-scale/gemfire1-2loc-3data.yml
```

Wait for pods to terminate
```shell
watch kubectl get pods
```


```shell
k apply -f cloud/k8/data-services/exercise-scalability/03-WAN/gemfire1-2loc-3data.yml
```

Wait for pods to be ready
```shell
watch kubectl get pods
```

```shell
k apply -f cloud/k8/data-services/exercise-scalability/03-WAN/gemfire2-2loc-3data.yml
```

Wait for pods to be ready
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


```shell
k apply -f cloud/k8/apps/wan/app-wan2.yml
```
```shell
kubectl port-forward deployment/spring-geode-kotlin-transaction-wan2 9090:8080
```

```shell
kubectl port-forward deployment/spring-geode-showcase-wan2  9092:8080
```



Empty

```shell script
curl -X 'GET' 'http://localhost:9092/findById?s=ACCT-WAN' \
  -H 'accept: */*'
```



```shell script
curl -X 'POST' \
  'http://localhost:9092/save' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "ACCT-WAN",
  "name": "Acct ACCT-WAN"
}'
```


```shell script
curl -X 'GET' 'http://localhost:8080/findById?s=WAN' \
  -H 'accept: */*'
```