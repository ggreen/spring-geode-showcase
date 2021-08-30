
# Create GemFire Cluster

## step 1 - Change directory to where the example Spring Boot applications

cd ~/projects/gemfire/spring-geode-showcase

## step 2 - Create a GemFire Cluster (1 Locator 1 Data Node)

kubectl apply -f cloud/k8/data-services/exercise1/gemfire1.yml

# ---------------------------------------
# Build Application Docker Image

## step 1 - change directory to build the first Java application 

cd ~/projects/gemfire/spring-geode-showcase/applications/spring-geode-showcase/

## step 2 build the docker image spring-geode-showcase:0.0.1-SNAPSHOT

mvn spring-boot:build-image

## step 3 Make the docker image available to the local Kind Kubernetes cluster

kind load docker-image spring-geode-showcase:0.0.1-SNAPSHOT

# ---------------------------------------
# Setup the configuration and GemFire cluster 

## step 1 - change directory to the root project directory

cd ~/projects/gemfire/spring-geode-showcase/

## step 2 - deployment configuration settings for the application

kubectl apply -f cloud/k8/config-maps.yml

## step 3 login into the GemFire cluster

kubectl exec -it gemfire1-locator-0 -- gfsh

# step 4 - Perform the following commands in Gfsh to connect, create a region and exit

connect
create region --name=Account --type=PARTITION
exit

# ---------------------------------------
# Deploy applications

## step 1 - Deploy the definitions located in cloud/k8/apps/app.yml

kubectl apply -f cloud/k8/apps

## step 2 - use the watch command util the application spring-geode-showcase pod state is ready 

watch kubectl get pods

## step 3 - Expose the spring-geode-showcase to be accessed using port 8080

kubectl port-forward deployment/spring-geode-showcase 8080:8080

## step 4 - Open a new shell -> click (+)

## step 5 - Write account data

curl -X 'POST' \
'http://localhost:8080/save' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"id": "1",
"name": "Acct 1"
}'  ; echo

## step 6 - Read account data

curl -X 'GET' 'http://localhost:8080/findById?s=1' \
-H 'accept: */*'  ; echo


# --------------------
# K8 Auto Healing


# step 1 - Delete/Kill the cache server data node

k delete pod gemfire1-server-0

# step 2 - watch the kubernetes platform recreate the deleted server

watch kubectl get pods


# step 3 - Try to Read account, it will be empty because the data is not persisted 
# It also did not have any redundant copies
# Note: there will be an Error if this is executed before pod is recreated) or null once restarted

curl -X 'GET' 'http://localhost:8080/findById?s=1' \
-H 'accept: */*'  ; echo


## step 4 -  re-write data

curl -X 'POST' \
'http://localhost:8080/save' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"id": "1",
"name": "Acct 1"
}'  ; echo

## step 5 -  Read data

curl -X 'GET' 'http://localhost:8080/findById?s=1' \
-H 'accept: */*'  ; echo

