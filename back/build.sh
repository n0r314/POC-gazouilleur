#!/usr/bin/env bash
mvn clean package
docker image build -t registry.gitlab.com/zacademy-rennes-promo-06/projet-final-poei-anne-laure/back:latest .
