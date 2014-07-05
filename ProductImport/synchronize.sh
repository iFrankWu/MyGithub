#!/bin/sh

LANG=zh_CN.UTF-8
export LANG

basedir=`dirname $0`
cd $basedir
echo $(pwd)
cmd_java=$(whereis java | awk '{print $2}')

 
classpath="$JAVA_HOME/lib/tools.jar:$JAVA_HOME/libs/dt.jar:$JAVA_HOME/jre/lib/dt.jar"



for jar in $(ls $(pwd)/libs/*.jar) ; do
 classpath="${classpath}:${jar}"
done

classpath="./work/classes/:${classpath}"

 
$cmd_java -cp "${classpath}" -Xms256m -Xmx512m com.shinetech.synchronize.GenericOnlineInfo;
 