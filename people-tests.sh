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

# Script to list all people
curl -X GET http://localhost:8080/people

# Script to update an existing person
# Replace {id} with the actual ID of the person you want to update
curl -X PUT http://localhost:8080/people/2 \
     -H "Content-Type: application/json" \
     -d '{
           "firstName": "Jane",
           "lastName": "Doe",
           "gender": "Female",
           "age": 28
         }'

# Script to delete an existing person
# Replace {id} with the actual ID of the person you want to delete
curl -X DELETE http://localhost:8080/people/2
