#!/bin/bash
#ident  "%W%"

BASEDIR=`dirname $0`
HOME_DIR=`readlink -f ${BASEDIR}/..`
. ${BASEDIR}/app_env.source

if [ "$1" != "" ];then
	echo "env is passed, overriding with - $1"
	ENV=$1
fi

# create log dir if needed
if [ ! -d "${APP_LOG_DIR}" ]; then
	mkdir -p ${APP_LOG_DIR}
fi
# CLASSPATH
CLASSPATH=${HOME_DIR}/properties/${ENV}:${HOME_DIR}/properties/common:${HOME_DIR}/bin/*
# VARS - Java Program Arguments
VARS=${VARS}
# JAVA_OPTS - Java vm Arguments
JAVA_OPTS="-DappName=${APP_NAME} -DappGroup=tradingtools -Denv=${ENV} -DappLogDir=${APP_LOG_DIR} ${JAVA_OPTS} "

PID=`ps -ef | grep java | grep "appName=${APP_NAME} " | awk '{ print $2 }'`

if [ "${PID}" == "" ];then
	java ${JAVA_OPTS} -cp ${CLASSPATH} ${MAIN_CLASS} ${VARS} 2>&1 | tee -a ${APP_LOG_DIR}/${APP_NAME}_console.log &
	echo "${APP_NAME} is started. APP_LOG_DIR=${APP_LOG_DIR}"
else
	echo "${APP_NAME} is already running on pid=${PID}! skipping start!"
fi
