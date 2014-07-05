#!/bin/sh
#下载脚本
#. ../switch_ip.sh
DownPage(){
	outfile=$1 	
	export url=$2
	export site=$3
	ip=$(cat $current_ip)

	wget -q -T 60 --user-agent="Baiduspider" --bind-address="$ip" -O ${outfile} $url
	#curl -s --user-agent "Baiduspider" --interface $ip --connect-timeout 60 -m 120 -o ${outfile} $url
	#if [ $? -eq 0 ];then	
		HandleOutFile $down_site $downType $outfile
		NeedSwitchIp $downType	
	#fi
}
