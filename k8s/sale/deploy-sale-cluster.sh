#!/bin/bash

set -e

echo "Criando cluster kind sale-cluster..."
kind create cluster --name sale-cluster || echo "Cluster já existe. Continuando..."

echo "Aplicando Postgres..."
kubectl apply -f postgres/

echo "Aplicando Redis..."
kubectl apply -f redis/

echo "Aplicando aplicação Sale..."
kubectl apply -f app/

echo "Aplicando Prometheus..."
kubectl apply -f prometheus/

echo "Todos os recursos foram aplicados com sucesso!"

echo "Fazendo Pull da imagem do sales-api para o cluster!"
kind load docker-image sales-api:latest --name sale-cluster

echo "Verificando pods..."
kubectl get pods -A

echo "Você pode usar 'kubectl logs -f <pod-name>' para ver logs específicos."

