## StartUp
For executing the Spring Boot commands navigate to the mikro-service directory you want to interact with.
The most important Spring Boot commands are:

* `mvn clean install` 
  - deletes everything installed and cleanly installs all dependencies again (also runs tests)
* `mvn package` 
  - build the executable .jar for the service (also runs tests)
* `mvn spring-boot:run`
  - runs the application as a service inside the command line

## Tests
If you want to run just the tests:

* `mvn test`

## Deployment
After a succesfully merged Pull Request on the master Travis automatically starts a new deployment and restarts the docker-compose.



# Installation Notes

## Firewall Config
    ssh local@invisible.f4.htw-berlin.de
    local@invisible:/home$ su
    Password:
    root@invisible:/home# cd
    root@invisible:~# ls
    docker-compose.yml  dump  firewall-disable.sh  firewall.sh  mongo-volume
    root@invisible:~# vi firewall.sh
    root@invisible:~# ./firewall-disable.sh
    root@invisible:~# ./firewall.sh
