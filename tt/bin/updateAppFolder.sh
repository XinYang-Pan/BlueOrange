#!/bin/bash
#ident  "%W%"

BASEDIR=`dirname $0`

. ${BASEDIR}/env.source

svn update ${WORK_ROOT}
chmod -R 755 ${WORK_ROOT}/bin