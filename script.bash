#!/bin/bash

    cat $1 | 
    #tr '[:upper:]' '[:lower:]' |
    awk -F "[\*§'\",. 0-9\(?\):_-;\[\]!><{}~=]" '{for(i=1;i<=NF;i++){ {print $i} } }' |
    awk 'length($0)>3' |sort |sed 's/[[:space:]]*$//'| uniq -c | sort -n > $2 
 