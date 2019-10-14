#!/bin/bash -e

IN=../dot-keys/crusher/crusher-app-firebase-service-account.json
OUT=server/src/main/resources/crusher-app-firebase-service-account.json.enc
PASSWORD=`heroku config:get SECRET_KEY`

echo $PASSWORD | gpg --batch --yes --passphrase-fd 0 --symmetric --cipher-algo AES256 --output $OUT $IN
