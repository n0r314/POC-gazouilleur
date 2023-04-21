#!/usr/bin/env bash

cd "./back" || exit 1
./build.sh

cd "../front" || exit 2
./build.sh

cd "../"