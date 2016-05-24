#!/bin/bash
#ident  "%W%"
# $1 APP_NAME
# DONE

APP_NAME=$1

BASEDIR=`dirname $0`
PID=`${BASEDIR}/echoPid.bash ${APP_NAME}`

if [ "${APP_NAME}" == "" ];then
	echo "no APP_NAME is not passed into this. existing ..."
	exit 1
fi

if [ "${PID}" == "" ];then
	echo "${APP_NAME} is not runing! skip stop. "
	exit 0
else
	kill ${PID}
	echo "${APP_NAME} is stopped on pid=${PID}!"
fi