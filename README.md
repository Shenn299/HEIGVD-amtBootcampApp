# HEIGVD-AMT-PROJECT1

## Etudiants:
Fabien Franchini  

Sébastien Henneberger    


## Objecive of the lab (feature 1)
Autodeploy a very simple Java EE app in application server WildFly

## Goal of the app (feature 1)
Display a welcome message in a nice template HTML (create landing page)

## Informations about Docker image
We use the jboss/wildfly Docker image available in Docker Hub
https://hub.docker.com/r/jboss/wildfly/

## Instruction about running Docker
Go in the `topology` directory and execute `docker-compose up` command  

The application server will start

## Instruction for the URL
Once the application server is started, go to this URL: http://192.168.99.100:9090/LandingPageApp   

If you use "Docker for Mac/Windows/Linux" version, go to <http://localhost:9090/LandingPageApp>
