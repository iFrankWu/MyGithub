#!/bin/sh
#filename down_gzip.sh
# download file and if filesize greater than size ,and gzip file ,else switch ip.
#导入ip切换脚本
#. ../switch_ip.sh

. down.sh
#set -x

#每当程序陷入等待时间过长，或者 拨号异常时，杀死相关进程，重新切换ip
ClearProcess(){
        ps aux | grep curl | grep -v grep | awk '{pringt $2}'| xargs kill -9 2>/dev/null
        ps aux | grep ppp1 | grep -v grep | awk '{print $2}'| xargs kill -9 2>/dev/null
        ps aux | grep adsl1 | grep -v grep | awk '{print $2}' | xargs kill -9 2>/dev/null
}

if [ $# -lt 1 ];then
	cfg_file=markosweb.config;#alexa.config
else
	cfg_file=$1
fi

eval `cat $cfg_file`
[ -z $number ] && export number=1
[ -z $adsl_name  ] && export adsl_name=ppp${number}
[ -z $adslNum ] && export adslNum=adsl${number}
[ -z $ip_log  ] && export ip_log=/tmp/${number}_ip.log
[ -z $zero_byte_site ] && export zero_byte_site=result/zero_byte_site
[ -z $zero_byte_url  ] && export zero_byte_url=result/zero_byte_url
[ -z $times ] && export times=20
[ -z $current_ip ] && export current_ip=/tmp/${number}_current_ip
[ -z $need_switch_ip ] && export need_switch_ip=/tmp/${number}_need_switch_ip
[ -z $threads ] && threads=10
[ -z $urllist ] && export urllist=../../listac
[ -z $downType ] && export downType=curl
[ -z $down_site ] && export down_site=alexa


[ ! -d result ] && mkdir result
touch $need_switch_ip
touch $current_ip
touch $ip_log
touch $zero_byte_site
touch $zero_byte_url
touch $log

echo false > $need_switch_ip
echo "Start download time $(date)" >> $log

i=$start
max=$end
#j=0

. ../../util.sh
. ../switch_ip.sh	#由于程序里使用了一些自动设置默认值功能，因为仅当配置文件读取完毕，才导入切换ip脚本
ClearProcess
echo $(GetNextIp) > $current_ip

#cat $urllist | while read site 
sed -n ${start},$(wc -l $urllist | awk '{print $1}')p $urllist | while read site
do
	
        #let j="$j+1"
        #[ $j -lt $i ] && continue
	
	while [ "$(cat $need_switch_ip)" != "false" ];do
		ip=$(cat $current_ip)
		Running=$(ps aux | egrep "(curl).*(Baiduspider).*($ip)" | grep -v 'grep' | wc -l)
		echo "Threads: $Running need_switch_ip:"$(cat $need_switch_ip)
                sleep 2
        done
	
	if(($i%20 == 0))
	then
		NetStatus $(cat $current_ip) 
		if [ $? -ne 0 ]; then
			echo "Net ip just change before ,sleep 1 "
			NetStatus $(cat $current_ip) > /dev/null
			[ $? -ne 0 ] && sleep 1
			NetStatus $(cat $current_ip) > /dev/null
			if [ $? -ne 0 ]
			then 
				ClearProcess
				echo changing > $need_switch_ip
				echo $(GetNextIp)>$current_ip
				echo false > $need_switch_ip
			fi
		fi
	fi


	url=${gather_site}${site}
	md5_dir=$(Md5Dir $url)
	outdir=$output/$md5_dir/
	[ ! -d $outdir ] && mkdir -p  $outdir

	outfile=${outdir}${site}".html"
	
	DownPage $outfile $url $site $urllist &
	
	let i="(($i+1))"
	sed -i "s/start=$(($i-1))/start=$i/" $cfg_file
	
	#进程控制
	k=1
	Running=$(GetNowProcess curl $(cat $current_ip))
	while [ $Running -gt $threads ];do
		echo "Threads :${Running} >= ${threads},Sleep 2 Seconds,Waitting Times $k";
		k="$(($k+1))"
		if (( $k > 100 ));
		then
			ClearProcess
			echo changing > $need_switch_ip
			echo $(GetNextIp) > $current_ip
			echo false > $need_switch_ip
			break
		fi
		sleep 2
		Running=$(GetNowProcess curl $(cat $current_ip))
	done

	#[ $i -gt $max  ] && (echo "Download overed $(date)" >> $log ; break)
done
