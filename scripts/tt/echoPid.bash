#!/bin/bash
#ident  "%W%"

APP_NAME=$1

PID=`ps -ef | grep java | grep "appGroup=tradingtools " | grep "appName=${APP_NAME} " | awk '{ print $2 }'`

echo ${PID}
