#!/bin/bash
#ident  "%W%"
# DONE


PID=`ps -ef | grep java | grep appGroup=tradingtools | awk '{ print $2 }'`

if [ "${PID}" == "" ];then
	echo "None is runing! skip stop. "
	exit
else
	echo "PID List is ${PID}"
	kill ${PID}
	echo "All is stopped on pid=${PID}!"
fi