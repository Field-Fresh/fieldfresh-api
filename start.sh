#!/bin/bash

docker login -u AWS -p $(aws ecr get-login-password --region us-east-1) 059394117896.dkr.ecr.us-east-1.amazonaws.com &&
docker pull 059394117896.dkr.ecr.us-east-1.amazonaws.com/fieldfresh-api && docker-compose up