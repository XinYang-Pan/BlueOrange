#!/bin/bash
#ident  "%W%"

BASEDIR=`dirname $0`
. ${BASEDIR}/app_env.source

if [ "${APP_NAME}" == "" ];then
	echo "no APP_NAME is not passed into this. existing ..."
	exit 0
fi

PID=`ps -ef | grep java | grep "appName=${APP_NAME} " | awk '{ print $2 }'`

if [ "${PID}" == "" ];then
	echo "${APP_NAME} is not runing! skip stop. "
	exit
else
	kill ${PID}
	echo "${APP_NAME} is stopped on pid=${PID}!"
fi