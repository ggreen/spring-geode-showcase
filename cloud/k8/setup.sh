#!/bin/bash
cd ~
sudo kind create cluster  --config k8-1wnode.yaml

sudo cp -r /root/.kube /$HOME/.kube

sudo chown -R $USER $HOME/.kube


# Set GemFire Pre-Requisite

kubectl create namespace cert-manager

helm repo add jetstack https://charts.jetstack.io

helm repo update

helm install cert-manager jetstack/cert-manager --namespace cert-manager  --version v1.0.2 --set installCRDs=true

kubectl create namespace gemfire-system

kubectl create secret docker-registry image-pull-secret --namespace=gemfire-system --docker-server=registry.pivotal.io --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD

kubectl create secret docker-registry image-pull-secret --docker-server=registry.pivotal.io --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD

kubectl create rolebinding psp-gemfire --clusterrole=psp:vmware-system-privileged --serviceaccount=gemfire-system:default


# Install the GemFire Operator

helm install gemfire-operator ~/data-services/gemfire-operator-1.0.1.tgz --namespace gemfire-system


# Create GemFire Cluster
cd ~/projects/gemfire/spring-geode-showcase

git pull

kubectl apply -f cloud/k8/data-services/exercise1/gemfire1.yml