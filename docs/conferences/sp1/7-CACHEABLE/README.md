# Setup Postgres


--------------------------
# Build Application Docker Image

## step 1 - change directory to build the first Java application 

```shell
cd ~/projects/gemfire/spring-geode-showcase/applications/spring-geode-showcase/
```

## step 2 build the docker image account-rest-service:0.0.1-SNAPSHOT

```shell
mvn -pl components/account-domain install
cd applications/account-jdbc-caching-rest-service
mvn  spring-boot:build-image
```

## step 3 Make the docker image available to the local Kind Kubernetes cluster

```shell
kind load docker-image account-jdbc-caching-rest-service:0.0.1-SNAPSHOT
```

--------------------------
# Setup the configuration and GemFire cluster 

## step 1 - change directory to the root project directory

```shell
cd ~/projects/gemfire/spring-geode-showcase/
```

## step 2 login into the GemFire cluster

```shell
kubectl exec -it gemfire1-locator-0 -- gfsh -e connect -e "create region --name=AccountCache --entry-time-to-live-expiration=1000 --enable-statistics=true --type=PARTITION"
```

--------------------------
# Deploy applications

## step 1 - Deploy the definitions located in cloud/k8/apps/app.yml

```shell
kubectl apply -f cloud/k8/apps/cacheable
```

## step 2 - use the watch command util the application spring-geode-showcase pod state is ready   (Control^C to stop)

```shell
watch kubectl get pods
```

## step 3 - Expose the spring-geode-showcase to be accessed using port 8080

```shell
kubectl port-forward deployment/account-jdbc-caching-rest-service 9020:8080
```

## step 4 - Open a new shell -> click (+)

## step 5 - Write account data

```shell
curl -X 'POST' \
'http://localhost:9020/accounts' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{ "id": "1", "name": "Acct 1" }'  ; echo

```

## step 6 - Read account data

```shell
curl -X 'GET' 'http://localhost:9020/accounts/1' -H 'accept: */*'  ; echo
```


```shell
k logs -f deployment/account-jdbc-caching-rest-service
```