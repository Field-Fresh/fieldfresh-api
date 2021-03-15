#!/bin/bash

aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 059394117896.dkr.ecr.us-east-1.amazonaws.com &&
docker pull 059394117896.dkr.ecr.us-east-1.amazonaws.com/fieldfresh-api && docker-compose up