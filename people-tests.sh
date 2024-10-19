#!/bin/bash

# Script to add a person using the REST API

curl -X POST http://localhost:8080/people \
     -H "Content-Type: application/json" \
     -d '{
           "firstName": "John",
           "lastName": "Doe",
           "gender": "Male",
           "age": 30
         }'
