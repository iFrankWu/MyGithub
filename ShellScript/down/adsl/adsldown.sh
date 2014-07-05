#!/bin/bash

# 下载脚本，根据unix编程风格之简单原则编写下载程序
# download file and if filesize greater than size ,and gzip file ,else switch ip.
#导入ip切换脚本



if [ $# -lt 1 ];then
	cfg_file=../config/markosweb.adsl;#alexa.config
else
	cfg_file=$1
fi

eval `cat $cfg_file`
[ -z $number ] && export number=1					#adsl链路
[ -z $adsl_name  ] && export adsl_name=ppp$number			#adsl网卡
[ -z $adslNum ] && export adslNum=adsl$number				#adsl驱动
[ -z $ip_log  ] && export ip_log=result/ip_${number}.log		#切换的ip文件
[ -z $times ] && export times=20					#切换ip时，指定等待次数
[ -z $threads ] && threads=10						#容许下载的线程数
[ -z $urllist ] && export urllist=../../listac				#当前使用的站点列表文件
[ -z $downType ] && export downType=wget				#下载类型 wget/curl
[ -z $down_site ] && export down_site=alexa				#当前下载的站点 alexa/markosweb/google
[ -z $gather_site ] && gather_site=http://www.alexa.com/siteinfo/
[ -z $log ] && log=result/info.log
[ -z $ipfrequency ] && ipfrequency=10

[ ! -d result ] && mkdir result		#result文件 不存在，则新建该文件
touch $ip_log
touch $log
echo "Start download time $(date)" >> $log

i=$start
max=$end

. switch_ip.sh		#由于程序里使用了一些自动设置默认值功能，因为仅当配置文件读取完毕，才导入切换ip脚本
. ../util.sh

#清空当前关于adls拨号的进程，得到下一次Ip
GetNextIps(){
	ClearAdslProcess $downType $adsl_name $adslNum	#清空进程
	echo $(GetNextIp)			#得到下一个有效ip
}

#每当程序陷入等待时间过长，或者 拨号异常时，杀死相关进程，重新切换ip
ClearAdslProcess(){
	if [ $# -ge 3 ];then
		downType=$1
		adslName=$2
		adslNum=$3
		ps aux | grep $downType | grep -v grep | awk '{pringt $2}'| xargs kill -9 2>/dev/null
		ps aux | grep $adslName | grep -v grep | awk '{print $2}'| xargs kill -9 2>/dev/null
		ps aux | grep $adslNum | grep -v grep | awk '{print $2}' | xargs kill -9 2>/dev/null
	fi
}

ip=$(GetNextIps)


sed -n ${start},$(wc -l $urllist | awk '{print $1}')p $urllist | while read site
do
	if(($i%${ipfrequency} == 0));then	#每下载20个文件切换ip
		Running=$(GetNowProcess $downType $ip)
		while [ $Running -gt 0 ];do
			#echo "Threads :${Running} >= 0,Sleep 2 Seconds,Waitting All Threads Stops.Waitting Times $k";
			k="$(($k+1))"
			if (( $k > 100 ));
			then
				ClearAdslProcess $downType $adsl_name $adslNum	#清空进程
				break
			fi
			sleep 2
			Running=$(GetNowProcess $downType $ip)
		done 
		if(($i%200 == 0));then
			ClearAdslProcess $downType $adsl_name $adslNum
		fi
		ip=$(GetNextIp)
	fi


	url=${gather_site}${site}
	md5_dir=$(Md5Dir $url)
	outdir=$output/$md5_dir/
	[ ! -d $outdir ] && mkdir -p $outdir

	outfile=${outdir}${site}".html.gz"
	
	DownPage $outfile $url $ip &
	
	let i="(($i+1))"
	sed -i "s/start=$(($i-1))/start=$i/" $cfg_file
	
	
	#进程控制
	k=1
	Running=$(GetNowProcess $downType $ip)
	while [ $Running -gt $threads ];do
		#echo "Threads :${Running} >= ${threads},Sleep 2 Seconds,Waitting Times $k";
		k="$(($k+1))"
		if (( $k > 100 ));
		then
			ip=$(GetNextIps)
			break
		fi
		sleep 2
		Running=$(GetNowProcess $downType $ip)
	done

	#[ $i -gt $max  ] && (echo "Download overed $(date)" >> $log ; break)
done
