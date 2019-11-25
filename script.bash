#!/bin/bash

    cat $1 | 
    #tr '[:upper:]' '[:lower:]' |
    awk -F "[^A-Za-z]" '{for(i=1;i<=NF;i++){ {print tolower($i)} } }' |
    awk 'length($0)>3' |sort |sed 's/[[:space:]]*$//'| uniq -c | sort -n > $2 
 