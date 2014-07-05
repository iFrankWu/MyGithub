#!/bin/bash

#得到当前的相关进程数目
GetNowProcess(){
	downTypes=$1
	if [ $# -lt 2 ];then
		echo $(ps aux | egrep "($downTypes).*(Baiduspider)" | grep -v grep | wc -l)
	else
		ip=$2	
		echo $(ps aux | egrep "($downTypes).*($ip)" | grep -v 'grep' | wc -l)
	fi
}

#生成MD5值
Md5Dir(){
        md5=$(echo -n $1 | openssl md5 |awk '{print $1}')
        temp=${md5:0:4}${md5:16:4}${md5:28:4}
        num=$((16#$temp))
        md5_dir=$(($num%128))
        echo $md5_dir
}
#下载页面
DownPage(){
	outfile=$1 	
	url=$2
	ip=$3
	if [ $downType == "wget" ];then
		wget -q -T 60 --user-agent="Baiduspider" --header='Accept-Encoding:gzip,deflate'  --bind-address="$ip" -O ${outfile} $url
	else
		curl -s --user-agent "Baiduspider"  -H "Accept-Encoding: gzip,deflate" --interface $ip --connect-timeout 60 -m 120 -o ${outfile} $url
        fi
	echo $outfile
}

