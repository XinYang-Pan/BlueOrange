#!/bin/bash
#ident  "%W%"

SOE_ID=$1
SERVER_NAME=$2
REMOTE_WORK_ROOT=$3

if [ "${SOE_ID}" == "" ];then
	echo "SOE_ID is not specified, exiting ..."
	exit 1
fi

BASEDIR=`dirname $0`
. ${BASEDIR}/env.source

if [ "${SERVER_NAME}" == "" ];then
	SERVER_NAME=${DEFAULT_REMOTE_SERVER}
	echo "SERVER_NAME is not specified, using default ${SERVER_NAME}."
fi

if [ "${REMOTE_APP_DIR}" == "" ];then
	REMOTE_WORK_ROOT=${WORK_ROOT}
	echo "REMOTE_WORK_ROOT is not specified, using default ${REMOTE_WORK_ROOT}."
fi

rm -rf ${WORK_ROOT}/{bin,conf}
echo "executing ..."
echo "scp -r ${SOE_ID}@${SERVER_NAME}:${REMOTE_WORK_ROOT}/{bin,conf} ${WORK_ROOT}/"
scp -r ${SOE_ID}@${SERVER_NAME}:${REMOTE_WORK_ROOT}/{bin,conf} ${WORK_ROOT}/
