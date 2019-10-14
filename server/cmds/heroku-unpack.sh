#!/bin/bash -e

IN=src/main/resources/crusher-app-firebase-service-account.json.enc
OUT=src/main/resources/crusher-app-firebase-service-account.json
PASSWORD=`heroku config:get SECRET_KEY`

echo $PASSWORD | gpg --batch --yes --passphrase-fd 0 --output $OUT --decrypt $IN
