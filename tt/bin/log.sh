#!/bin/bash
#ident  "%W%"
# $1 APP_NAME
# DONE

VAR=$1

BASEDIR=`dirname $0`
. ${BASEDIR}/env.source

if [ "${VAR}" == "" ]; then
	echo "log dir is ${APP_LOG_ROOT}"
elif [ "${VAR}" == "all" ]; then
	tail -f -n100 ${APP_LOG_ROOT}/autobooking/*.log ${APP_LOG_ROOT}/optimizer/*.log ${APP_LOG_ROOT}/optimizerview/*.log ${APP_LOG_ROOT}/execution/*.log
elif [ "${VAR}" == "clean" ]; then
	rm -rf ${APP_LOG_ROOT}/{autobooking,optimizer,optimizerview,execution}
else
	APP_NAME=${VAR}
	
	. ${BASEDIR}/env.sh
	
	# 
	echo "watch ${APP_LOG_DIR}/${APP_NAME}/${APP_NAME}.log"
	tail -100f ${APP_LOG_DIR}/${APP_NAME}/${APP_NAME}.log
fi


