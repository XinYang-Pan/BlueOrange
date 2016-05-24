#!/bin/bash
#ident  "%W%"

APP_NAME=$1
SOE_ID=$2
SERVER_NAME=$3
REMOTE_APP_DIR=$4

if [ "${APP_NAME}" == "" ];then
	echo "APP_NAME is not specified, exiting ..."
	exit 1
fi

if [ "${SOE_ID}" == "" ];then
	echo "SOE_ID is not specified, exiting ..."
	exit 1
fi

BASEDIR=`dirname $0`
. ${BASEDIR}/env.source
. ${BASEDIR}/env.sh

if [ "${SERVER_NAME}" == "" ];then
	SERVER_NAME=${DEFAULT_REMOTE_SERVER}
	echo "SERVER_NAME is not specified, using default ${SERVER_NAME}."
fi

if [ "${REMOTE_APP_DIR}" == "" ];then
	REMOTE_APP_DIR=${APP_DIR}
	echo "REMOTE_APP_DIR is not specified, using default ${REMOTE_APP_DIR}."
fi

rm -rf ${APP_DIR}
echo "executing ..."
echo "scp -r ${SOE_ID}@${SERVER_NAME}:${REMOTE_APP_DIR} ${APP_ROOT}/"
scp -r ${SOE_ID}@${SERVER_NAME}:${REMOTE_APP_DIR} ${APP_ROOT}/

