PRe-requisite


```shell
source cloud/GKE/local_setenv.sh
```




```shell
gcloud beta container --project "gregoryg-playground" clusters create "cluster-1" --zone "us-east1-b" --no-enable-basic-auth --cluster-version "1.20.9-gke.701" --release-channel "regular" --machine-type "e2-highmem-16" --image-type "COS_CONTAINERD" --disk-type "pd-ssd" --disk-size "100" --metadata disable-legacy-endpoints=true --scopes "https://www.googleapis.com/auth/devstorage.read_only","https://www.googleapis.com/auth/logging.write","https://www.googleapis.com/auth/monitoring","https://www.googleapis.com/auth/servicecontrol","https://www.googleapis.com/auth/service.management.readonly","https://www.googleapis.com/auth/trace.append" --max-pods-per-node "110" --num-nodes "5" --logging=SYSTEM,WORKLOAD --monitoring=SYSTEM --enable-ip-alias --network "projects/gregoryg-playground/global/networks/default" --subnetwork "projects/gregoryg-playground/regions/us-east1/subnetworks/default" --no-enable-intra-node-visibility --default-max-pods-per-node "110" --no-enable-master-authorized-networks --addons HorizontalPodAutoscaling,HttpLoadBalancing,GcePersistentDiskCsiDriver --enable-autoupgrade --enable-autorepair --max-surge-upgrade 1 --max-unavailable-upgrade 0 --enable-shielded-nodes --node-locations "us-east1-b"
```

```shell
GEODE_HOME=/Users/devtools/repositories/IMDG/geode/apache-geode-1.13.1

$GEODE_HOME/bin/gfsh -e "connect --user=admin --password=admin" -e "destroy region --name=/Location"

$GEODE_HOME/bin/gfsh -e "connect --user=admin --password=admin" -e "destroy region --name=/Account"

```

```shell
cd /Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-geode-showcase
./cloud/GKE/gke_gemfire_setup.sh
```

PVC Cleanup if needed
```shell
k delete GemFireCluster gemfire1 gemfire2

k delete pvc data-gemfire1-locator-0 data-gemfire1-locator-1 data-gemfire1-server-0 data-gemfire1-server-1 data-gemfire1-server-2 data-gemfire2-server-0 data-gemfire2-server-1 data-gemfire2-server-2 data-gemfire2-locator-0 data-gemfire2-locator-1
```


```shell
cd /Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-geode-showcase
k create namespace perftest
k apply -n perftest -f  cloud/k8/data-services/oltp/gemfire-g1.yml
```

Check port-forward

```shell
ps -ef | port-forward
```


```shell
kubectl apply -f cloud/k8/data-services/oltp/gemfire-g1.yml -n perftest
```

