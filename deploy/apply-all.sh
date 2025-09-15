# deploy/apply-all.sh
#!/bin/bash
kubectl apply -f ../pos-api/k8s/
kubectl apply -f ../inventory-api/k8s/
kubectl apply -f ../sale-api/k8s/
kubectl apply -f ../payment-api/k8s/
