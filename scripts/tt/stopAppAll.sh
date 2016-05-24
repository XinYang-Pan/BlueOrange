#!/bin/bash
#ident  "%W%"
# DONE

PID_LIST=`ps -ef | grep java | grep appGroup=tradingtools | awk '{ print $2 }'`

if [ "${PID_LIST}" == "" ];then
	echo "None is runing! skip stop. "
	exit
else
	echo "PID_LIST List is ${PID_LIST}"
	kill ${PID_LIST}
	echo "All is stopped on pid=${PID_LIST}!"
fi