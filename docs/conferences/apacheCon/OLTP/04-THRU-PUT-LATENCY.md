PRe-requisite


```shell
source cloud/GKE/local_setenv.sh
```


```shell
./cloud/GKE/gke_gemfire_setup.sh
```



```shell
kubectl apply -f cloud/k8/data-services/oltp/gemfire-g1.yml -n perftest
```

```shell
kubectl exec -it gemfire1-locator-0 -- gfsh -e connect -e "create region --name=test --type=PARTITION" -n perftest
```

```shell
k port-forward gemfire1-locator-0 7071:7070 -n perftest
```

```shell
k apply -f cloud/k8/apps/geode-perf-test/put -n perftest
```

```shell
k logs -f jobs/geode-perf-test-put-01 -c apache-geode-perf-test-put-01 -c apache-geode-perf-test-put-02 -c apache-geode-perf-test-put-03 -n perftest
```

```shell
k logs -f jobs/geode-perf-test-put-01  -n perftest
```

-----------
Cleanup

```shell
k delete -f cloud/k8/apps/geode-perf-test/put  -n perftest
```

```shell
kubectl exec -it gemfire1-locator-0 -- gfsh -e connect -e "destroy region --name=test" -n perftest
```
