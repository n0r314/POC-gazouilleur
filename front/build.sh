#!/usr/bin/env bash
npm install
npm run build
docker build -t registry.gitlab.com/zacademy-rennes-promo-06/projet-final-poei-anne-laure/front:latest .
