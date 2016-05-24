#!/bin/bash
#ident  "%W%"
# $1 APP_NAME $2 APP_ENV
# DONE

VAR=$1

BASEDIR=`dirname $0`
. ${BASEDIR}/env.source

# 
echo "start all of apps - ${DEFAULT_APP_LIST}"
for APP_NAME in ${DEFAULT_APP_LIST}
do
	${BASEDIR}/startApp.sh ${VAR}
	sleep 20s
done
