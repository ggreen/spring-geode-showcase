PRe-requisite


```shell
source cloud/GKE/local_setenv.sh
```


```shell
./cloud/GKE/gke_gemfire_setup.sh
```



```shell
kubectl apply -f cloud/k8/data-services/oltp/gemfire-g1.yml
```

```shell
kubectl exec -it gemfire1-locator-0 -- gfsh -e connect -e "create region --name=test --type=PARTITION"
```

```shell
k port-forward gemfire1-locator-0 7071:7070
```

```shell
k apply -f cloud/k8/apps/geode-perf-test/put
```

```shell
k logs -f jobs/geode-perf-test-put-01 -c apache-geode-perf-test-put-01 -c apache-geode-perf-test-put-02 -c apache-geode-perf-test-put-03
```

```shell
k logs -f jobs/geode-perf-test-put-01 
```

-----------
Cleanup

```shell
k delete -f cloud/k8/apps/geode-perf-test/put
```

```shell
kubectl exec -it gemfire1-locator-0 -- gfsh -e connect -e "destroy region --name=test"
```
