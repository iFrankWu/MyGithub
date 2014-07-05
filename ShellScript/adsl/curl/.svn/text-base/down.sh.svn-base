#!/bin/sh
#下载脚本
#. ../switch_ip.sh
DownPage(){
	outfile=$1 	
	url=$2
	site=$3
	urllist=$4
	filesize=$5
	ip=$(cat $current_ip)

	#wget -q -T 60 --user-agent="Baiduspider" --bind-address="$ip" -O ${outfile} $url
	curl -s --user-agent "Baiduspider" --interface $ip --connect-timeout 60 -m 120 -o ${outfile} $url
        
	HandleOutFile $down_site $downType $outfile
	NeedSwitchIp $downType	
}
