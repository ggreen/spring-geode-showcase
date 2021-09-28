#-----------------------
# Postgres 

kubectl create namespace cert-manager

helm repo add jetstack https://charts.jetstack.io

helm repo update

helm install cert-manager jetstack/cert-manager --namespace cert-manager  --version v1.0.2 --set installCRDs=true

kubectl create secret docker-registry image-pull-secret --docker-server=registry.pivotal.io --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD

# Install Postgres

mkdir -p ~/dataServices/postgres
curl -o postgres-for-kubernetes-v1.2.0.tar.gz https://cloud-native-data.s3.amazonaws.com/postgres-for-kubernetes-v1.2.0.tar.gz
tar xvf postgres-for-kubernetes-v1.2.0.tar.gz


cd ~/dataServices/postgres/postgres-for-kubernetes-v1.2.0
docker load -i ./images/postgres-instance
docker load -i ./images/postgres-operator
docker images "postgres-*"
export HELM_EXPERIMENTAL_OCI=1
helm registry login registry.pivotal.io --username=$HARBOR_USER --password=$HARBOR_PASSWORD

helm chart pull registry.pivotal.io/tanzu-sql-postgres/postgres-operator-chart:v1.2.0
helm chart export registry.pivotal.io/tanzu-sql-postgres/postgres-operator-chart:v1.2.0  --destination=/tmp/

kubectl create secret docker-registry regsecret --docker-server=https://registry.pivotal.io --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD

helm install --wait postgres-operator /tmp/postgres-operator/
sleep 30s

cd ~/projects/rabbitmq/tanzu-rabbitmq-event-streaming-showcase/
kubectl apply -f cloud/k8/data-services/postgres
sleep 2m
## -----------------------

# Postgres

kubectl exec -it postgres-0 -- psql -c "ALTER USER postgres PASSWORD 'CHANGEME'"
kubectl exec -it postgres-0 -- psql -c "CREATE TABLE ACCOUNTS (ACCT_ID  VARCHAR(50)  NOT NULL, ACCT_NM  VARCHAR(50) NOT NULL, PRIMARY KEY(ACCT_ID));"
