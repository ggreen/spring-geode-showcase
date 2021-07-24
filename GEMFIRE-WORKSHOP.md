Building Faster Cloud-Native Applications at Scale with Tanzu GemFire (Back to top)

Instructors: Wayne Lund, Gregory Green, Charlie Black

Brief: Tanzu GemFire enables fast access to application data with strong consistency and high availability. Learn more about Spring Data and Spring Boot abstractions, and Tanzu Gemfire in this workshop.

Abstract:
This workshop explores the use of Tanzu GemFire for fast access to application data with strong consistency and high availability. You’ll walk away from this interactive, hands-on session with a better understanding of:

1 - How developers can use the Tanzu GemFire Operator to quickly instantiate GemFire
2 - How availability of data can be maintained in light of node/pod failures and or whole site failures.
3 - How to take advantage of Tanzu GemFire availability build resilience into your applications
4 - How to build, run, and test your applications at scale
Usage of Spring Data and Spring Boot abstractions for Tanzu GemFire
How developers can configure applications to enable Tanzu GemFire for caching based on design patterns widely used in Cloud-Native Applications
How to configure GemFire persistence for High Availability and Fault Tolerant
How to setup multiple WAN replication clusters for active-active for active passive GemFire clusters

Prerequisites
Basic Java development skills, including the ability to run Apache Maven commands.
Basic understanding of Spring Data, Spring Caching, Spring Boot and Spring Test.
Familiarity with running basic OS commands from a command prompt.

Hardware Requirements
A modern-spec windows/linux/mac laptop or desktop

Technical Requirements
IDE of choice (Spring Tools Suite or IntelliJ)
JDK 8 or 11 installed
GitHub account
Maven/Gradle installed
Internet access (high-speed connection recommended)

Resources
Need to brush up on Spring Boot or Spring Data? These will get you going:

What is the Spring Framework Really All About? (video, 10 min)
Intro to Spring Boot
Intro to Spring Data (video, 30 min)
Spring Boot documentation
Spring Data documentation


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

k apply -f ./gemfire1.yaml
251  k get pods
252  history | grep rm
253  k get pods
254  docker ps
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
