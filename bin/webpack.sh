#!/bin/bash

CMD=`basename $0`

if [ $# -ne 1 ]; then
    echo "$CMD guide|single-file" 1>&2
    exit 1
fi

if [ ! `which node` ]; then
    echo "Please set PATH to node command." 1>&2
    exit 1
fi

cd $1/build

node node_modules/webpack/bin/webpack.js --config webpack.config.js

# EOF