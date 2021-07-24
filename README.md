# spring-geode-showcase


# edge-computing-vital-signs-showcase


AMI: 8 GB RAM, 30 GB disk, 2 CPU

![poc.png](docs/images/poc.png)

chmod 400 aws-ohio-playground.pem
ssh -i "/Users/devtools/paaS/AWS/secrets/aws-ohio-playground.pem"  ec2-user@ec2-3-128-179-174.us-east-2.compute.amazonaws.com

sudo yum install java

sudo yum install docker

curl -Lo ./kind https://kind.sigs.k8s.io/dl/v0.11.1/kind-linux-amd64
chmod +x ./kind
sudo mv ./kind /usr/bin/kind


vi k8-1wnode.yaml


```yaml
kind: Cluster
apiVersion: kind.x-k8s.io/v1alpha4
nodes:
- role: control-plane
- role: worker

```

sudo systemctl start docker

sudo kind create cluster  --config k8-1wnode.yaml

sudo yum install  kubectl



**kubectl**

    curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
    sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
    alias k='kubectl'

    sudo cp -r /root/.kube /$HOME/.kube
    sudo chown -R $USER $HOME/.kube


**Instal Helm**

    curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/master/scripts/get-helm-3
    chmod 700 get_helm.sh
    ./get_helm.sh

**Cert Manager Install**

kubectl create namespace cert-manager
helm repo add jetstack https://charts.jetstack.io
helm repo update
helm install cert-manager jetstack/cert-manager --namespace cert-manager  --version v1.0.2 --set installCRDs=true


**GemFire**
kubectl config current-context
kubectl create namespace gemfire-system

export HARBOR_USER=
export HARBOR_PASSWORD=


kubectl create secret docker-registry image-pull-secret --namespace=gemfire-system --docker-server=registry.pivotal.io --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD
kubectl create secret docker-registry image-pull-secret --docker-server=registry.pivotal.io --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD


kubectl create rolebinding psp-gemfire --clusterrole=psp:vmware-system-privileged --serviceaccount=gemfire-system:default


wget https://cloud-native-data.s3.amazonaws.com/gemfire-operator-1.0.1.tgz
helm install gemfire-operator gemfire-operator-1.0.1.tgz --namespace gemfire-system


vi gemfire1.yaml
```
apiVersion: gemfire.tanzu.vmware.com/v1
kind: GemFireCluster
metadata:
 name: gemfire1
spec:     
  image: registry.pivotal.io/tanzu-gemfire-for-kubernetes/gemfire-k8s:1.0.0
  locators:           
    replicas: 1                   
  servers:
    replicas: 2     
```

k apply -f ./gemfire1.yaml

sudo yum install git

----
sudo yum remove java
curl -O https://download.java.net/java/GA/jdk11/13/GPL/openjdk-11.0.1_linux-x64_bin.tar.gz
tar zxvf openjdk-11.0.1_linux-x64_bin.tar.gz
sudo mv jdk-11.0.1 /usr/local/

sudo vi /etc/profile.d/jdk11.sh

# create new
export JAVA_HOME=/usr/local/jdk-11.0.1
export PATH=$PATH:$JAVA_HOME/bin

source /etc/profile.d/jdk11.sh


git clone https://github.com/Tanzu-Solutions-Engineering/edge-computing-vital-signs-showcase.git
cd ~

wget https://apache.claz.org/maven/maven-3/3.8.1/binaries/apache-maven-3.8.1-bin.tar.gz
tar xvf apache-maven-3.8.1-bin.tar.gz
export PATH=$PATH:$HOME/apache-maven-3.8.1/bin

git clone https://github.com/Tanzu-Solutions-Engineering/edge-computing-vital-signs-showcase.git
git clone https://github.com/ggreen/spring-geode-showcase.git



sudo groupadd docker
sudo usermod -aG docker ${USER}
sudo su -s ${USER}


cd applications/spring-geode-showcase/
mvn spring-boot:build-image


kind load docker-image spring-geode-showcase:0.0.1-SNAPSHOT


curl http://169.254.169.254/latest/meta-data/public-hostname
ec2-3-128-179-174.us-east-2.compute.amazonaws.com


java -jar applications/spring-geode-showcase/target/spring-geode-showcase-0.0.1-SNAPSHOT.jar --server.port-80

