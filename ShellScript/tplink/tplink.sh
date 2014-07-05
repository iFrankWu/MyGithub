#!/bin/bash
#Shellscrpt	:	tplink - 获得某路由器的下一个有效ip,外部调用方法 GetNextIp 即可
#Tips		：	
#			1、采用source命令 或 （.） 将该文件导入文件shell脚本中，即可使用该文件定义的方法
#			2、如果程序运行中，出现类似syntax error in expression (error token is "1307549448-86400000")错误，可去检查
#			$tplink_ip.log 文件，该文件默认为 /tmp/tplink_ip.log ,该文件中是否有异常数据，正确格式应该为 ip 时间戳
#Author		:	frank.wu <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
#Category	: 	Net Funtion
#Date		: 	2011/6/8
#Version	:	@version 1.0


#set -x 	#可用于打印脚本执行的过程
[ -z $ip_tplink ] && ip_tplink=192.168.2.16 	#读取路由器ip,如果父shell没有传值给它，默认为2.16路由器
[ -z $user_tplink ] && user_tplink=admin	#路由器的用户名
[ -z $pass_tplink ] && pass_tplink=608		#路由器的密码
[ -z $user_adsl ] && user_adsl=*902adsl8	#adsl账号
[ -z $pass_adsl ] && pass_adsl=Hello123World	#adsl密码


#判断路由器是否网络畅通
TplinkStatus(){
	/bin/ping -c 3 baidu.com > /dev/null 2>&1
	if [ $? -eq 0 ]
	then 
		echo true
	else
		echo false
	fi
}
#获得路由器的当前ip值，如果ip=0.0.0.0,那么输出false,否则输出当前ip
GetIp(){
	ip=$(curl -s --basic -u "${user_tplink}:${pass_tplink}" -k "http://$ip_tplink/userRpm/StatusRpm.htm" | grep -P "var\s+wanPara\s+=([^;]*);"|grep day | awk -F ',' '{print $3}' | awk -F '"' '{print $2}')  >/dev/null 2>&1
	[ "$ip" == "0.0.0.0" -o -z "$ip" ] && echo false || echo $ip
}
#路由器拨号
TplinkUp(){
curl -s --basic -u "${user_tplink}:${pass_tplink}" -k  "http://$ip_tplink/userRpm/PPPoECfgRpm.htm?wantype=2&acc=$user_adsl&psw=$pass_adsl&VnetPap=0&linktype=4&waittime2=0&Connect=%C1%AC+%BD%D3" > /dev/null 2>&1
}
#路由器断网
TplinkDown(){
	curl -s --basic -u "$user_tplink:$pass_tplink" -k "http://$ip_tplink/userRpm/PPPoECfgRpm.htm?wantype=2&acc=$user_adsl&psw=$pass_adsl&VnetPap=0&linktype=4&waittime2=0&Disconnect=%B6%CF+%CF%DF" > /dev/null 2>&1
}

#ip检查,判断ip是否在为有效ip,判断规则：当该ip在某段时间内，只出现过一次为有效
CheckIp(){
        [ -z $ip_log ] && ip_log=/tmp/tplink_ip.log    	#ip存放文件
        interval=$((24*60*60*1000))             	#时间间隔
        temp_ip=$1
        timestmp=$(date +%s)    			#得到当前时间戳
        lasttime=$(grep -P "$temp_ip\s" $ip_log | awk '{print $2}')	#查找当前ip是否在ip缓存文件中是否存在，若存在，找出上次出现的时间戳
								#如果不采用正则，那么grep匹配 114.249.209.2 会将 114.249.209.2* 都匹配出来，结果造成程序出错

        if [ -z "$lasttime" ];then  			#此ip还没有出现过
                echo "$temp_ip" "$timestmp" >> $ip_log	#将此ip和当前时间戳追加到ip缓存文件
                echo true;				#输出true
        else
                value="$(($timestmp-$lasttime-$interval))"	#计算ip上次出现的时间戳与当前的时间戳之差，是否在指定的时间间隔内
                (( $value > 0 )) && (sed -i "s/$lasttime/$timestmp/" $ip_log ; echo true )|| echo false		#如果不在时间间隔内，那么修改缓存文件中ip对应的时间戳,输出true.否则为false
        fi
}
#切换ip
ChangeIp(){
	ClearTplink 	#先清除路由器相关信息
	TplinkDown	#先断网
	TplinkUp	#后拨号
	ip=$(GetIp)	#得到当前的路由器的ip
	echo $ip	#输出ip
}
#清除所有的路由器拨号 断网 等相关进程
ClearTplink(){
	ps aux | grep $ip_tplink | grep -v 'grep' | awk '{print $2}'| xargs kill -9  2>/dev/null 
}
#得到下一个有效ip,该方法为外部使用接口
GetNextIp(){
	while((1));do
		ip=$(ChangeIp)
		while [ "$ip" == "false" ];do
			ip=$(ChangeIp)
		done
		if [ "$(CheckIp $ip)" == "true" ];then 
			ClearTplink
			echo $ip
			break
		fi
	done
}


#while (( 1 ));do GetNextIp;done
