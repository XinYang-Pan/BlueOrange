#!/bin/bash
#ident  "%W%"
# input APP_NAME(from export) APP_ENV(from export)
# output APP_ROOT APP_LOG_ROOT APP_DIR APP_LOG_DIR JAVA_OPTS CLASSPATH
# DONE

############
# Global Variable
############
# creating APP_LOG_ROOT if not exist
if [ ! -d "${APP_LOG_ROOT}" ]; then
	echo "${APP_LOG_ROOT} does not exist. creating ..."
	mkdir -p ${APP_LOG_ROOT};
fi

# setting APP_ENV
if [ "${APP_ENV}" == "" ]; then
	echo "APP_ENV is not set, using default APP_ENV - DEFAULT_ENV=${DEFAULT_ENV}!"
	if [ "${DEFAULT_ENV}" == "" ]; then
		echo "DEFAULT_ENV is empty. exiting ..."
		exit 0;
	else 
		APP_ENV=${DEFAULT_ENV}
	fi
fi

############
# Variables based on Global Variable
############

# app level dir
export APP_DIR=${APP_ROOT}/${APP_NAME}
if [ ! -d "${APP_DIR}" ]; then
	echo "${APP_DIR} does not exist. creating ..."
	mkdir -p ${APP_DIR};
fi
export APP_LOG_DIR=${APP_LOG_ROOT}
if [ ! -d "${APP_LOG_DIR}" ]; then
	echo "${APP_LOG_DIR} does not exist. creating ..."
	mkdir -p ${APP_LOG_DIR};
fi
