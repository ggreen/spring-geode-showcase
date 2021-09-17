#!/bin/bash


STR=`pwd`
if [[ "$STR" == *"cloud"* ]]; then
  echo "You must execute this script from the root project directory"
  exit 0
fi


#PRE_REQUISUITE

if [ -z $GEMFIRE_OPERATOR_DOWNLOAD_DIR ]
then
  echo "Please set \$GEMFIRE_OPERATOR_DOWNLOAD_DIR to the location where your download gemfire-operator-1.0.1.tgz. See http://network.pivotal.io"
  exit
fi


if [ -z $HARBOR_USER ]
then
  echo "Please set the your username and password used to login into registry.pivotal.io .profile on on the shell. Example: export \$HARBOR_USER=MYUSER; export=\$HARBOR_PASSWORD==MYPASSWORD "
  exit
fi

if [ -z $HARBOR_PASSWORD ]
then
  echo "Please set the your username and password used to login into registry.pivotal.io in .profile on on the shell. Example: export \$HARBOR_USER=MYUSER; export=\$HARBOR_PASSWORD==MYPASSWORD "
  k get p
fi

set -x #echo on


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
sleep 40
helm install gemfire-operator $GEMFIRE_OPERATOR_DOWNLOAD_DIR/gemfire-operator-1.0.1.tgz --namespace gemfire-system

