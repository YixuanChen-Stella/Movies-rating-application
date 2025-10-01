#!/bin/bash

terraform init

terraform apply -auto-approve

chmod 400 deployer-key.pem

echo "Terraform applied successfully! Use the following command to SSH into WebApp:"
echo "ssh -i deployer-key.pem ubuntu@$(terraform output -raw webapp_public_ip)"
