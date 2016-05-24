#!/bin/bash
#ident  "%W%"

APP_NAME=$1

BASEDIR=`dirname $0`
PID=`${BASEDIR}/echoPid.bash ${APP_NAME}`

if [ "${PID}" == "" ];then
	echo "${APP_NAME} is not running."
else
	echo "${APP_NAME} is running on pid=${PID}!"
	exit 1
fi
