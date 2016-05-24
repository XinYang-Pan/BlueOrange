#!/bin/bash
#ident  "%W%"
# $1 SKIP_TEST
# DONE

SKIP_TEST=$1
STATUS=0

BASEDIR=`dirname $0`
. ${BASEDIR}/env.source

# jar
${BASEDIR}/updateAndInstallJar.sh CommonService

STATUS=$?
if [ ${STATUS} -ne 0 ]; then
	exit ${STATUS}
fi
	
# server
# ${BASEDIR}/updateAndInstallApp.sh autobooking ${SKIP_TEST}
# ${BASEDIR}/updateAndInstallApp.sh execution ${SKIP_TEST}
# ${BASEDIR}/updateAndInstallApp.sh optimizer ${SKIP_TEST}
# ${BASEDIR}/updateAndInstallApp.sh optimizerview ${SKIP_TEST}

for APP_NAME in ${DEFAULT_APP_LIST}
do
	${BASEDIR}/updateAndInstallApp.sh ${APP_NAME} ${SKIP_TEST}

	STATUS=$?
	if [ ${STATUS} -ne 0 ]; then
		exit ${STATUS}
	fi
	
done