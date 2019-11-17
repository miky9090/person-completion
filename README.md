
# Person Completion Services
The application works with or without docker.
## Using the application *without* docker
### Requirements
You will need java 9 in order to run the 2 applications.
### Running the applications
You can run the following commands for each of the main modules ([DocumentValidator](https://github.com/miky9090/person-completion/tree/master/DocumentValidator "DocumentValidator"), [PersonValidator](https://github.com/miky9090/person-completion/tree/master/PersonValidator "PersonValidator")):

    mvn clean package
    
Then go to the target directory and run 
`java -jar person-validator-0.0.1-SNAPSHOT.jar`
`java -jar document-validator-0.0.1-SNAPSHOT.jar`

Person validator will run on port 8080.
Document validator will run on port  9090.

In order to see the application working, go to

[localhost:8080](http://localhost:8080)

[localhost:9090](http://localhost:9090)

You will see a Swagger UI, where you can browser the API documentation.

## Using the application *with* docker
### Requirements
In order to run the application using docker, you will need to expose docker daemon on TCP without TLS.
On Windows, open docker desktop and find the general settings.

Make sure to check 

 - [X] Expose daemon on tcp://localhost:2375 without TLS

### Running the applications
Run the following command in the directory of each application ([DocumentValidator](https://github.com/miky9090/person-completion/tree/master/DocumentValidator "DocumentValidator"), [PersonValidator](https://github.com/miky9090/person-completion/tree/master/PersonValidator "PersonValidator"), [service-discovery](https://github.com/miky9090/person-completion/tree/master/service-discovery "service-discovery")):

    mvn clean install dockerfile:build 

After building the docker images, you can run them by using the provided [docker-compose.yml](https://github.com/miky9090/person-completion/blob/master/compose/docker-compose.yml "docker-compose.yml") file.
In order to run the images, go to the compose folder and run the following command:

    docker-compose up

This will find the 3 docker images, and will run them.

Person validator will run on port 8080.
Document validator will run on port  9090.
Service discovery will run on port 8761.

In order to see the application working, go to

[localhost:8080](http://localhost:8080)

[localhost:9090](http://localhost:9090)

[localhost:8761](http://localhost:8761)
