#!/bin/bash
#ident  "%W%"
# $1 ACTION
# DONE

ACTION=$1

BASEDIR=`dirname $0`
. ${BASEDIR}/env.source

if [ "${ACTION}" == "clean" ]; then
	echo "rm -rf ${SVN_WORKING_DIR}"
	rm -rf ${SVN_WORKING_DIR}
# elif [ "${ACTION}" == "" ]; then
else
	echo "	clean"
fi


