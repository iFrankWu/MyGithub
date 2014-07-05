#!/bin/sh
#filename down_gzip.sh
# download file and if filesize greater than size ,and gzip file ,else switch ip.
#导入ip切换脚本


#set -x
if [ $# -lt 1 ];then
	cfg_file=markosweb.config;#alexa.config
else
	cfg_file=$1
fi

eval `cat $cfg_file`
[ -z $number ] && export number=1
[ -z $adsl_name  ] && export adsl_name=ppp$number
[ -z $adslNum ] && export adslNum=adsl$number
[ -z $ip_log  ] && export ip_log=result/${number}_ip.log
[ -z $zero_byte_site ] && export zero_byte_site=result/zero_byte_site
[ -z $zero_byte_url  ] && export zero_byte_url=result/zero_byte_url
[ -z $times ] && export times=20
[ -z $current_ip ] && export current_ip=result/${number}_current_ip
[ -z $need_switch_ip ] && export need_switch_ip=result/${number}_need_switch_ip
[ -z $threads ] && threads=10
[ -z $urllist ] && export urllist=../../listac
[ -z $downType ] && export downType=wget
[ -z $down_site ] && export down_site=alexa

[ ! -d result ] && mkdir result
touch $need_switch_ip
touch $current_ip
touch $ip_log
touch $zero_byte_site
touch $zero_byte_url
touch $log
echo "Start download time $(date)" >> $log
echo false > $need_switch_ip

i=$start
max=$end
#j=0

. ../switch_ip.sh	#由于程序里使用了一些自动设置默认值功能，因为仅当配置文件读取完毕，才导入切换ip脚本

. ../../util.sh
. down.sh

ClearAdslProcess $downType $adsl_name $adslNum

echo $(GetNextIp) > $current_ip

#cat $urllist | while read site 
sed -n ${start},$(wc -l $urllist | awk '{print $1}')p $urllist | while read site
do
	
        #let j="$j+1"
        #[ $j -lt $i ] && continue
	
	while [ "$(cat $need_switch_ip)" != "false" ];do
		Running=$(GetNowProcess $downType $(cat $current_ip))
		echo "Threads: $Running Need Switch Ip: "$(cat $need_switch_ip).
                sleep 2
        done
	
	if(($i%20 == 0))
	then
		NetStatus $(cat $current_ip) 
		if [ $? -ne 0 ]; then
			echo "Net ip just change before ,sleep 1 "
			sleep 1
			NetStatus $(cat $current_ip) >/dev/null
			[ $? -ne 0 ] && sleep 1
			NetStatus $(cat $current_ip) >/dev/null
			if [ $? -ne 0 ]
			then 
				ClearAdslProcess $downType $adsl_name $adslNum
				echo changing > $need_switch_ip
				echo $(GetNextIp)>$current_ip
				echo false > $need_switch_ip
			fi
		fi
	fi


	url=${gather_site}${site}
	md5_dir=$(Md5Dir $url)
	outdir=$output/$md5_dir/
	[ ! -d $outdir ] && mkdir $outdir

	outfile=${outdir}${site}".html"
	
	DownPage $outfile $url $site &
	
	let i="(($i+1))"
	sed -i "s/start=$(($i-1))/start=$i/" $cfg_file
	
	#进程控制
	k=1
	Running=$(GetNowProcess $downType $(cat $current_ip))
	while [ $Running -gt $threads ];do
		echo "Threads :${Running} >= ${threads},Sleep 2 Seconds,Waitting Times $k";
		k="$(($k+1))"
		if (( $k > 100 ));
		then
			ClearAdslProcess $downType $adsl_name $adslNum
			echo changing > $need_switch_ip
			echo $(GetNextIp) > $current_ip
			echo false > $need_switch_ip
			break
		fi
		sleep 2
		Running=$(GetNowProcess $downType $(cat $current_ip))
	done

	#[ $i -gt $max  ] && (echo "Download overed $(date)" >> $log ; break)
done
