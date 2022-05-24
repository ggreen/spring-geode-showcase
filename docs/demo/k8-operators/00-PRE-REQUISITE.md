PRe-requisite


PVC Cleanup if needed
```shell
k delete GemFireCluster gemfire1 gemfire2

k delete pvc data-gemfire1-locator-0 data-gemfire1-locator-1 data-gemfire1-server-0 data-gemfire1-server-1 data-gemfire1-server-2 data-gemfire2-server-0 data-gemfire2-server-1 data-gemfire2-server-2 data-gemfire2-locator-0 data-gemfire2-locator-1
```


Install Operator

```shell
cd /Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-geode-showcase
./cloud/k8/data-services/gemfire/gf-k8-setup-operator.sh
```


```shell
cd /Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-geode-showcase
k create namespace perftest
```

Check port-forward

```shell
ps -ef | grep port-forward
```


Create perf test cluster

```shell
kubectl create secret docker-registry regsecret --docker-server=https://registry.pivotal.io --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD  -n perftest
```

```shell
kubectl create secret docker-registry image-pull-secret --docker-server=registry.pivotal.io --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD  -n perftest
```

```shell
kubectl apply -f  cloud/k8/data-services/gemfire/gemfire-perftest.yml -n perftest
```


```shell
k get pods -n perftest
```