#!/bin/bash -e

IN=src/main/resources/crusher-app-firebase-service-account.json.enc
OUT=src/main/resources/crusher-app-firebase-service-account.json

echo $SECRET_KEY | gpg --batch --yes --passphrase-fd 0 --output $OUT --decrypt $IN
