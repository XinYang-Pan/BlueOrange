#!/bin/bash
#ident  "%W%"
# $1 APP_NAME $2 APP_ENV
# DONE

APP_NAME=$1
APP_ENV=$2

BASEDIR=`dirname $0`
. ${BASEDIR}/env.source
. ${BASEDIR}/env.sh

# 
${APP_DIR}/scripts/start_${APP_NAME}.bash ${APP_ENV}


#PID=`ps -ef | grep java | grep appName=${APP_NAME} | awk '{ print $2 }'`

#if [ "${PID}" == "" ];then
#	java ${JAVA_OPTS} -cp ${CLASSPATH} ${MAIN_CLASS} ${VARS} | tee -a ${APP_LOG_DIR}/${APP_NAME}_console.log &
#	echo "${APP_NAME} is started. APP_LOG_DIR=${APP_LOG_DIR}"
#else
#	echo "${APP_NAME} is already running on pid=${PID}! skip start!"
#fi
