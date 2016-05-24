#!/bin/bash
#ident  "%W%"
# $1 app_name $2 app_zip_file
# DONE

APP_NAME=$1
ZIP_FILE=$2

BASEDIR=`dirname $0`
. ${BASEDIR}/env.source

APP_DIR=${APP_ROOT}/${APP_NAME}

#########################################
# remove old app folder
#########################################
rm -rf ${APP_DIR}
mkdir -p ${APP_DIR}

#########################################
# back up zip file
#########################################
ZIP_DIR=${WORK_ROOT}/zip
if [ ! -d "${ZIP_DIR}" ]; then
	mkdir -p ${ZIP_DIR}
fi
if [ ! -d "${ZIP_DIR}/his" ]; then
	mkdir -p ${ZIP_DIR}/his
fi
ZIP_FILE_NAME=`basename ${ZIP_FILE}`
if [ -f "${ZIP_DIR}/${ZIP_FILE_NAME}" ]; then
	mv ${ZIP_DIR}/${ZIP_FILE_NAME} ${ZIP_DIR}/his/${ZIP_FILE_NAME}.`date +%Y-%m-%d_%T`
fi
mv ${ZIP_FILE} ${ZIP_DIR}/${ZIP_FILE_NAME}

#########################################
# install new version of app
#########################################
unzip ${ZIP_DIR}/${ZIP_FILE_NAME} -d ${APP_DIR}

#########################################
# create app info file
#########################################
echo "APP_NAME=${APP_NAME}" >> ${APP_DIR}/app_info
echo "APP_SVN_URL=${APP_SVN_URL}" >> ${APP_DIR}/app_info
echo "MAKING_TIME=`date`" >> ${APP_DIR}/app_info
echo "" >> ${APP_DIR}/app_info

