#!/bin/sh

LANG=zh_CN.UTF-8
export LANG

basedir=`dirname $0`
cd $basedir
echo $(pwd)
cmd_java=$(whereis java | awk '{print $2}')

#cmd_java=/usr/java/jdk1.6.0_22/bin/java
classpath="$JAVA_HOME/lib/tools.jar:$JAVA_HOME/libs/dt.jar:$JAVA_HOME/jre/lib/dt.jar"

#cd /var/www/CycleTaskManager

echo $cmd_java
echo
echo $classpath
echo

for jar in $(ls $(pwd)/libs/*.jar) ; do
 classpath="${classpath}:${jar}"
done

classpath="./work/classes/:${classpath}"

echo $classpath
echo
#while(( 1 ));
#do 
	#$cmd_java -cp "${classpath}" -Xms256m -Xmx512m com.shinetech.service.Service
	#$cmd_java -cp "${classpath}" -Xms256m -Xmx512m com.shinetech.service.ExportQualifiedProductImageService
	$cmd_java -cp "${classpath}" -Xms256m -Xmx512m com.shinetech.test.UpdateImageName
#done
