#!/bin/bash
#ident  "%W%"
# $1 APP_NAME
# DONE

APP_NAME=$1

BASEDIR=`dirname $0`

BASEDIR=`dirname $0`
. ${BASEDIR}/env.source
. ${BASEDIR}/app.env
. ${APP_DIR}/scripts/app_env.source

${BASEDIR}/startApp.sh $@

if [ $? == "0" ]; then
	tail -0f ${APP_LOG_DIR}/${APP_NAME}_console.log &
fi
