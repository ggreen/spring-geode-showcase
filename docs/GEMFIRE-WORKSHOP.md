

------
sudo systemctl start docker
docker ps
sudo kind create cluster  --config k8-1wnode.yaml
sudo cp -r /root/.kube /$HOME/.kube
sudo chown -R $USER $HOME/.kube
kubectl create namespace cert-manager

helm repo add jetstack https://charts.jetstack.io
helm repo update
helm install cert-manager jetstack/cert-manager --namespace cert-manager  --version v1.0.2 --set installCRDs=true
kubectl create namespace gemfire-system

kubectl create secret docker-registry image-pull-secret --namespace=gemfire-system --docker-server=registry.pivotal.io --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD
kubectl create secret docker-registry image-pull-secret --docker-server=registry.pivotal.io --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD
kubectl create rolebinding psp-gemfire --clusterrole=psp:vmware-system-privileged --serviceaccount=gemfire-system:default
helm install gemfire-operator ~/data-services/gemfire-operator-1.0.1.tgz --namespace gemfire-system

cd ~/projects/gemfire/spring-geode-showcase

k apply -f cloud/k8/data-services/exercise1/gemfire1.yml

---

cd ~/projects/gemfire/spring-geode-showcase/applications/spring-geode-showcase/
mvn spring-boot:build-image


kind load docker-image spring-geode-showcase:0.0.1-SNAPSHOT
cd ../..
kubectl apply -f cloud/k8/config-maps.yml

kubectl apply -f cloud/k8/apps


**Instal k9s**
wget https://github.com/derailed/k9s/releases/download/v0.24.14/k9s_Linux_x86_64.tar.gz
tar xvf k9s_Linux_x86_64.tar.gz
sudo cp ./k9s /usr/local/bin/


*Shell into locator*
gfsh
connect

create region --name=Account --type=PARTITION


sudo /usr/local/bin/kubectl port-forward YOUR-POD-ID

curl http://169.254.169.254/latest/meta-data/public-hostname
ec2-3-128-179-174.us-east-2.compute.amazonaws.com


------


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
curl -X 'GET' \
  'http://localhost:8080/findById?s=1' \
  -H 'accept: */*'
```


```shell script
curl -X 'DELETE' \
  'http://localhost:8080/deleteById/1' \
  -H 'accept: */*'
  
```
