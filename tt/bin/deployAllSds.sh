#!/bin/bash
#ident  "%W%"
# $1 SKIP_TEST
# DONE

SKIP_TEST=$1
STATUS=0

BASEDIR=`dirname $0`
. ${BASEDIR}/env.source

# jar
${BASEDIR}/updateAndInstallJar.sh ttl-commons

STATUS=$?
if [ ${STATUS} -ne 0 ]; then
	exit ${STATUS}
fi
	
# server
${BASEDIR}/updateAndInstallApp.sh static-data-service ${SKIP_TEST}
