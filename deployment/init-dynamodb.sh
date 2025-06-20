#!/bin/bash

aws --endpoint-url=http://localhost:8000 dynamodb create-table \
    --table-name mueblesStats \
    --attribute-definitions \
        AttributeName=hash,AttributeType=S \
        AttributeName=timeStamp,AttributeType=S \
    --key-schema \
        AttributeName=hash,KeyType=HASH \
        AttributeName=timeStamp,KeyType=RANGE \
    --provisioned-throughput \
        ReadCapacityUnits=5,WriteCapacityUnits=5

echo "Tabla mueblesStats creada exitosamente"