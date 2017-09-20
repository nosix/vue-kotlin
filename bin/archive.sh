#!/bin/bash

CMD=`basename $0`

if [ $# -ne 1 ]; then
    echo "$CMD version" 1>&2
    exit 1
fi

version_option=-Pversion=$1

./gradlew $version_option clean

./gradlew $version_option --project-dir=vuekt-plugin publishToMavenLocal uploadArchives

./gradlew $version_option vuekt:publishToMavenLocal vuekt:uploadArchives

./gradlew $version_option vuekt-js2vue:publishToMavenLocal vuekt-js2vue:uploadArchives

# EOF