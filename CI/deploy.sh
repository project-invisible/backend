#!/bin/bash

if [ $TRAVIS_BRANCH == 'master' ] ; then
  echo "--- SSH"
  ssh -o StrictHostKeyChecking=no travis@invisible.f4.htw-berlin.de "pwd  ; exit"
  echo "--- Building docker file"
  docker build -f ../database/Dockerfile -t invisible/database .
  docker build -f ../service-discovery/Dockerfile -t invisible/service-discovery .
  # docker build -f ../database/Dockerfile -t database .

  echo "--- docker images ---"
  docker images

  docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD
  docker push invisible/database
  docker push invisible/service-discovery

  scp -o StrictHostKeyChecking=no docker-compose.yml travis@invisible.f4.htw-berlin.de:~
  
  echo "+++++ stopping docker containers"
  docker-compose -f docker-compose.yml down
  echo "+++++ pulling and starting docker containers"
  docker-compose -f docker-compose.yml up -d
else
  echo "Not deploying, since this branch isn't master"
fi
