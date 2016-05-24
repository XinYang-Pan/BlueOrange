#!/bin/bash
#ident  "%W%"
# $1 APP_NAME $2 APP_ENV
# DONE

APP_NAME=$1
COB_VALIDATION=$2

STATUS=

BASEDIR=`dirname $0`
. ${BASEDIR}/env.source
. ${BASEDIR}/app.env
. ${APP_DIR}/scripts/app_env.source

# COB SERVER VALIDATION
if [ "${COB_VALIDATION}" == "Y" ] && [ "${OPT_PRIMARY}" == "" ];then
	echo "COB_VALIDATION=Y, ${APP_NAME} in COB server. Server=${HOSTNAME}. Skipping the start process..exit"
    exit 0
fi

# create log dir if needed
if [ ! -d "${APP_LOG_DIR}" ]; then
	mkdir -p ${APP_LOG_DIR}
fi

# CLASSPATH
CLASSPATH=${APP_DIR}/properties/${ENV}:${APP_DIR}/properties/common:${APP_DIR}/bin/*

# VARS - Java Program Arguments
VARS=${VARS}

# JAVA_OPTS - Java vm Arguments
JAVA_OPTS="-DappName=${APP_NAME} -DappGroup=tradingtools -Denv=${ENV} -DappLogDir=${APP_LOG_DIR} ${JAVA_OPTS} "

PID=`${BASEDIR}/echoPid.bash ${APP_NAME}`
CONSOLE_LOG=${APP_LOG_DIR}/${APP_NAME}_console.log

if [ "${PID}" == "" ];then
	java ${JAVA_OPTS} -cp ${CLASSPATH} ${MAIN_CLASS} ${VARS} >> ${CONSOLE_LOG} 2>&1 &
	STATUS=$?
	exit ${STATUS}
else
	echo "${APP_NAME} is already running on pid=${PID}! skipping start!"
	exit 1
fi
