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
	cfg_file=../config/markosweb.tplink;	#alexa.config
else
	cfg_file=$1
fi


eval `cat $cfg_file`

[ -z $ip_log ] && export ip_log=result/tplink_ip.log
[ -z $times ] && export times=20
[ -z $threads ] && threads=10						#容许下载的线程数
[ -z $urllist ] && export urllist=../../listac				#当前使用的站点列表文件
[ -z $downType ] && export downType=wget				#下载类型 wget/curl
[ -z $down_site ] && export down_site=alexa				#当前下载的站点 alexa/markosweb/google
[ -z $gather_site ] && gather_site=http://www.alexa.com/siteinfo/
[ -z $log ] && log=result/info.log
[ -z $ipfrequency ] && ipfrequency=10
. tplink.sh
. ../util.sh


[ ! -d result ] && mkdir result
touch $ip_log
touch $log


#清空当前关于adls拨号的进程，得到下一次Ip
GetNextIps(){
	ClearProcess $downType				#清空进程
	GetNextIp > /dev/null				#得到下一个有效ip
}
ClearProcess(){
	ps aux | grep $1 | grep -v grep | awk '{print $2}' | xargs kill -9 2>/dev/null
	ps aux | egrep "curl" | grep -v grep | awk '{print $2}' | xargs kill -9 2>/dev/null
}


echo "Start download time $(date)" >> $log	#记录起始时间

i=$start	#开始值
max=$end	#结束值
 
GetNextIps > /dev/null	#获得一个有效ip

sed -n ${start},$(wc -l $urllist | awk '{print $1}')p $urllist | while read site
do
	if (($i%${ipfrequency} == 0))					#没下载20个网页，检查下网络状态
	then	
		Running=$(GetNowProcess $downType)
		while [ $Running -gt 0 ];do
			k="$(($k+1))"
			if (( $k > 100 ));
			then
				ClearProcess $downType		#清空进程
				break
			fi
			sleep 2
			Running=$(GetNowProcess $downType)
		done 
		GetNextIp > /dev/null
	fi


	url=${gather_site}${site}				#生成下载页面的url
	md5_dir=$(Md5Dir $url)					#生成下载页面的文件存放目录
	outdir=$output/$md5_dir/	
	[  -d $outdir ] ||  mkdir -p  $outdir			#当存放目录不存在时，创建改目录

	outfile=${outdir}${site}".html.gz"				#输出文件的格式
	DownPage $outfile $url eth0  &		#启动下载进程
	let i="(($i+1))"
	sed -i "s/start=$(($i-1))/start=$i/" $cfg_file		#将文件中的start值增一
	
	#进程控制
	k=1

	while [ $(GetNowProcess $downType) -gt $threads ];do
		#echo "Threads :$(GetNowProcess $downType) >= ${threads},Sleep 2 Seconds,Waitting Times $k.";
		k="$(( $k+1 ))"		#这里设置等待次数，暂时认为如果进程等待5分钟，还没有结束，强制杀死相关进程
		if (($k > 150));
		then
			GetNextIps >/dev/null
			break
		fi
		sleep 2
	done

	#[ $i -gt $max  ] && (echo "Download overed $(date)" >> $log ; break)
done

