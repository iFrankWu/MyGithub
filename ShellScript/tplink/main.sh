#!/bin/bash
#Shellscrpt	:	main - 启动下载站点页面的脚本入口
#Tips		：	
#			1、采用source命令 或 （.） 将该文件导入文件shell脚本中，即可使用该文件定义的方法
#Author		:	frank.wu <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
#Category	: 	Net  Funtion
#Date		: 	2011/6/9

#Version	:	@version 1.0

#set -x
#清除相关进程

if [ $# -lt 1 ];then
	cfg_file=markosweb.config;#alexa.config
else
	cfg_file=$1
fi

ClearProcess(){
	ps aux | egrep "curl" | grep -v grep | awk '{print $2}' | xargs kill -9 2>/dev/null
	ps aux | egrep "down.sh" | grep -v grep | awk '{print $2}'| xargs kill -9 2>/dev/null
}
eval `cat $cfg_file`

[ -z $ip_log ] && export ip_log=result/tplink_ip.log
[ -z $zero_byte_site ] && export zero_byte_site=result/zero_byte_site
[ -z $zero_byte_url ] && export zero_byte_url=result/zero_byte_url
[ -z $times ] && export times=20
[ -z $nedd_switch_ip ] && export need_switch_ip=result/need_switch_ip
[ -z $current_ip ] && export current_ip=result/current_ip

. tplink.sh
. ../util.sh
. down.sh


[ ! -d result ] && mkdir result
touch $need_switch_ip
touch $current_ip
touch $ip_log
touch $zero_byte_site
touch $zero_byte_url

echo false > $need_switch_ip


echo "Start download time $(date)" >> $log	#记录起始时间

i=$start	#开始值
max=$end	#结束值
#j=0		#循环控制变量

ClearProcess	#清除相关进程
GetNextIp	#获得一个有效ip

#cat $urllist | while read site 	#循环读取站点文件中的站点
sed -n ${start},$(wc -l $urllist | awk '{print $1}')p $urllist | while read site
do
	
        #let j="$j+1"
        #[ $j -lt $i ] && continue	#定位到起始位置，主要考虑到程序终止后，再次启动时会接着上次那个地方下载，而不会重复下载
	
	while [ "$(cat $need_switch_ip)" != "false" ];do		#如果需要切换ip,等待ip切换完毕	
		echo "Threads: $(GetNowProcess)  need_switch_ip: "$(cat $need_switch_ip)  #打印提示语句
                sleep 2	
        done
	
	if (($i%20 == 0))					#没下载20个网页，检查下网络状态
	then	
		echo "Net Status: "$(NetStatus)
		if [ "$(NetStatus)" == "false" ]; then		#当前不能够联网
			echo "Net ip just change before ,sleep 1 "
			sleep 1					#可能因为切换ip，造成短暂的网络不稳定，故等待1秒，再判断是否为联网状态
			if [ "$(NetStatus)" == "false" ];	#再次判断当前是否为联网状态
			then
				sleep 1				#再睡眠一秒
				if [ "$(NetStatus)" == "false" ]; #还是不能够联网
                        	then 
                                	echo "The Net status is $(NetStatus) ,Force kill all process,and restart.";
                                	ClearProcess		#清除相关清除
                                	echo changing > $need_switch_ip
					GetNextIp		#再次获取有效ip
                                	echo false > $need_switch_ip	#切换ip的标准置为false
                        	fi	
	        	fi
		fi
	fi


	url=${gather_site}${site}				#生成下载页面的url
	md5_dir=$(Md5Dir $url)					#生成下载页面的文件存放目录
	outdir=$output/$md5_dir/	
	[  -d $outdir ] ||  mkdir -p  $outdir			#当存放目录不存在时，创建改目录

	outfile=${outdir}${site}".html"				#输出文件的格式
	
	DownPage $outfile $url $site $urllist $filesize  &	#启动下载进程
	
	let i="(($i+1))"
	sed -i "s/start=$(($i-1))/start=$i/" $cfg_file		#将文件中的start值增一
	
	#进程控制
	k=1

	while [ $(GetNowProcess) -gt $threads ];do
		echo "Threads :$(GetNowProcess) >= ${threads},Sleep 2 Seconds,Waitting Times $k.";
		k="$(( $k+1 ))"		#这里设置等待次数，暂时认为如果进程等待5分钟，还没有结束，强制杀死相关进程
		if (($k > 150));
		then
			ClearProcess
			echo changing > $need_switch_ip
			GetNextIp
			echo false > $need_switch_ip
			break
		fi
		sleep 2
	done

	#[ $i -gt $max  ] && (echo "Download overed $(date)" >> $log ; break)
done
