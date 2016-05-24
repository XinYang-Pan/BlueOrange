#!/bin/bash
#ident  "%W%"
# $1 app_name
# DONE

APP_NAME=$1

BASEDIR=`dirname $0`
. ${BASEDIR}/env.source

${BASEDIR}/stopApp.sh ${APP_NAME}

#########################################
# remove old app folder
#########################################
rm -rf ${APP_ROOT}/${APP_NAME}
