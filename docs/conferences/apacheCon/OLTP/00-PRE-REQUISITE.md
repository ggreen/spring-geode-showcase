PRe-requisite


```shell
source cloud/GKE/local_setenv.sh
```


```shell
./cloud/GKE/gke_gemfire_setup.sh
```

```shell
gcloud beta container --project "gregoryg-playground" clusters create "cluster-1" --zone "us-east1-b" --no-enable-basic-auth --cluster-version "1.20.9-gke.701" --release-channel "regular" --machine-type "e2-highmem-16" --image-type "COS_CONTAINERD" --disk-type "pd-ssd" --disk-size "100" --metadata disable-legacy-endpoints=true --scopes "https://www.googleapis.com/auth/devstorage.read_only","https://www.googleapis.com/auth/logging.write","https://www.googleapis.com/auth/monitoring","https://www.googleapis.com/auth/servicecontrol","https://www.googleapis.com/auth/service.management.readonly","https://www.googleapis.com/auth/trace.append" --max-pods-per-node "110" --num-nodes "3" --logging=SYSTEM,WORKLOAD --monitoring=SYSTEM --enable-ip-alias --network "projects/gregoryg-playground/global/networks/default" --subnetwork "projects/gregoryg-playground/regions/us-east1/subnetworks/default" --no-enable-intra-node-visibility --default-max-pods-per-node "110" --no-enable-master-authorized-networks --addons HorizontalPodAutoscaling,HttpLoadBalancing,GcePersistentDiskCsiDriver --enable-autoupgrade --enable-autorepair --max-surge-upgrade 1 --max-unavailable-upgrade 0 --enable-shielded-nodes --node-locations "us-east1-b"
```