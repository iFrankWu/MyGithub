#!/bin/sh
#Shellscript	:	switch_ip 切换ip，GetNextIp 方法用于获得下一个新的ip.此版用于adsl拨号。
#Tips
#Author		:	<a href="mailto:wushexin@gmail.com">Frank.Wu</a>
#Catagory	:	Net Function
#Date		:	2011/6/8
#Version	:	@version 1.0
#set -x
[ -z $number ] && number=0				#网卡序号
[ -z $adslName ] && adslName=ppp${number}		#adsl拨号网卡名
[ -z $adslNum ] && adslNum=adsl${number}		#对应adsl拨号的驱动名
[ -z $ip_log ] && ip_log=/tmp/${number}_ip.log    	#ip存放文件0
[ -z $times ] && times=15				#切换ip时，最多判断次数

#获得当前adsl拨号状态
IsRunning(){
	#adsl-status: Link is up and running on interface ppp0
	status=$(/sbin/adsl-status /etc/sysconfig/network-scripts/ifcfg-$adslName | grep "up and running");
	[ -z "$status" ] && echo false || echo true
}

#得到当前ip
GetIp(){
     /sbin/adsl-status /etc/sysconfig/network-scripts/ifcfg-$adslName | grep  inet | awk '{print $2}'
}

#adsl拨号连接
AdslUp(){
     /sbin/adsl-start /etc/sysconfig/network-scripts/ifcfg-$adslName
}

#adsl断号
AdslDown(){
	/sbin/adsl-stop /etc/sysconfig/network-scripts/ifcfg-$adslName
}

#ip检查
CheckIp(){
	interval=$((24*60*60*1000))		#时间间隔
	temp_ip=$1
	timestmp=$(date +%s)			#得到时间戳
	lasttime=$(grep -P "$temp_ip\s" $ip_log | awk '{print $2}')
	

	if [ -z "$lasttime" ];then  		#此ip还没有出现过
		echo "$temp_ip" "$timestmp" >> $ip_log
		echo true;
	else	
		value="$(($timestmp-$lasttime-$interval))"
		(( $value > 0 )) && (sed -i "s/$lasttime/$timestmp/" $ip_log ; echo true )|| echo false
	fi
}
#当前adsl是否为down状态
IsDown(){
	isDown=$(/sbin/adsl-status /etc/sysconfig/network-scripts/ifcfg-$adslName | grep adsl-status | grep down | awk '{print $2}')
	[ -z $isDown ] && echo false || echo true
}
ClearAdsl(){
	ps aux | egrep "($adslName|$adslNum)" | grep -v grep | awk '{print $2}'| xargs kill -9 
}
#切换ip
ChangeIp(){
	if [ "$(IsDown)" == "false" ];	#首先判断是否该网卡是down状态
	then 
		AdslDown		#不是down状态，则关闭该网卡
		sleep 1		#考虑到关闭时间，故等待其一小段时间
	fi
	while [ "$(IsDown)" == "false" ]; 	#如果网卡不能够正确关闭，可能是拨号出现异常，故强制杀死该网卡相关进程
	do
		ClearAdsl		#清除进程
		sleep 1
	done
	AdslUp				#拨号上网
	time=1
	while [ $time -lt $times ]
	do 
		let time="(($time+1))"
		if [ "$(IsRunning)" == "true" ];then	#确定已经拨号成功，获取当前网卡ip
			ip=$(GetIp)
			break;
		fi
		sleep 1	
	done
	[ -z "$ip" ] && echo false || echo $ip		#输出结果
}

#得到下一个有效ip
GetNextIp(){
	while (( 1 ));do
		ip=$(ChangeIp)			#切换ip
		while [ "$ip" == "false" ];do	
			ip=$(ChangeIp)		#如果ip切换没有成功，继续切换
		done
		flag=$(CheckIp $ip)		#检查ip是否有效
		if [ "$flag" == "true" ];then
			echo $ip
			break;
		fi
	done
}

#while (( 1 ));do GetNextIp;done
