#!/bin/bash
#Shellscrpt	:	down - 下载脚本
#Tips		：	
#			1、采用source命令 或 （.） 将该文件导入文件shell脚本中，即可使用该文件定义的方法
#Author		:	frank.wu <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
#Category	: 	Net  Funtion
#Date		: 	2011/6/9
#Version	:	@version 1.0


. tplink.sh
. ../util.sh

NeedSwitchIp1(){
	downType=$1
	if [ $(cat $need_switch_ip) == "true" ];then    #需要切换ip
               	#set -x
		Running=$(GetNowProcess $downType) #当前下载进程是否为0
                if [ $Running == 0 ];then
			echo "Need swtich Ip,Threads: $Running."
			if [ $(cat $need_switch_ip) == "true" ];then
			echo changing > $need_switch_ip;	#切换ip过程中，增加一个状态，正在切换状态
								#正在切换状态 为changing
								#需要切换状态 为 true
								#不需要切换状态 为 false
                        GetNextIp				#切换ip,得到新的ip
                        echo  false > $need_switch_ip           #切换ip的标记，设为false
			fi
                fi
		#set +x
        fi
}

#下载页面
DownPage(){
	outfile=$1 	
	url=$2
	site=$3
	urllist=$4
	filesize=$5
	#ip=$(cat $current_ip)

	#wget -q -T 60 --user-agent="Baiduspider" -O ${outfile} $url
	curl -s --user-agent "Baiduspider"  --connect-timeout 60 -m 120 -o ${outfile} $url
	HandleOutFile $down_site $downType $outfile
	NeedSwitchIp1 $downType        
}

