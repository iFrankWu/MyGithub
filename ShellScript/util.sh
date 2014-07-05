#!/bin/bash
#Shellscrpt	:	util - 一些常用的方法
#Tips		：	
#			1、采用source命令 或 （.） 将该文件导入文件shell脚本中，即可使用该文件定义的方法
#Author		:	frank.wu <a href="mailto:wushexin@gmail.com">Frank.Wu</a>
#Category	: 	Net Funtion
#Date		: 	2011/6/9
#Version	:	@version 1.0

#生成MD5值
Md5Dir(){
        md5=$(echo -n $1 | openssl md5 |awk '{print $1}')
        temp=${md5:0:4}${md5:16:4}${md5:28:4}
        num=$((16#$temp))
        md5_dir=$(($num%128))
        echo $md5_dir
}

#aleax采集回来的文件是否有效
AlexaFileIsValid(){
	fileSize=$1
	echo true
}

#markosweb采集回来的文件是否有效
MarkoswebFileIsValid(){
	fileSize=$1
	if [ $fileSize -gt 14500 ];		#文件大于14500 说明是一个有效文件，返回0
	then
		return 0
	elif [ $fileSize -lt 14500 -a $fileSize -gt 13500 ] ;#Not Fount Page 页面
	then 
		return 1
	elif [ $fileSize -lt 6000 ];	#输入验证码
	then 
		return 2
	else				#除上述情况外，返回3
		return 3
	fi
}
#得到当前的相关进程数目
GetNowProcess(){
	if [ $# -lt 2 ];then
		echo $(ps aux | egrep "(curl).*(Baiduspider)" | grep -v grep | wc -l)
	else
		flag=$1
		ip=$2	
		echo $(ps aux | egrep "($flag).*($ip)" | grep -v 'grep' | wc -l)
	fi
}
#判断当前宿主主机是否网络畅通,可指定某个ip是否处于联网状态
NetStatus(){
	if [ -z $1 ]	#函数的参数 为ip 
        then 
                /bin/ping -c 3 baidu.com > /dev/null 2>&1 	#ping baidu.com 
                if [ $? -eq 0 ]				   	#如果执行成功，则说明网络畅通
                then 	
			echo true
                        return 0
                else						#执行失败，说明网络出现故障				
                        echo false
			return 1
                fi
        else 
                /bin/ping -c 3 -I $1 baidu.com >/dev/null 2>&1 #判断某个ip是否为联网状态
                if [ $? -eq 0 ]
                then 
			echo true
                        return 0
                else
			echo false
                        return 1
                fi
        fi
}
HandleOutFile(){
	if [ "$1" == "alexa" ];then
		AlexaFile $2 $3
	elif [ "$1" == "markosweb" ];then
		MarkoswebFile $2 $3
	fi
		
}
MarkoswebFile(){
	downType=$1
	outfile=$2
	if [ $downType == "wget" ];
	then
		WgetMarkoswebFile $outfile
	elif [ $downType == "curl" ];then
		CurlMarkoswebFile $outfile
	fi 
}
AlexaFile(){
	downType=$1
	outfile=$2
	if [ $downType == "wget" ];
	then
		WgetAlexaFile $outfile
	elif [ $downType == "curl" ];then
		CurlAlexaFile $outfile
	fi 
}
WgetMarkoswebFile(){
	outfile=$1
	if [ -e $outfile ];then         
                size=$(stat -c %s $outfile)
                echo "$outfile filesize is  $size."
                if [ $size -gt 6000 ];
                then
                        gzip -f  $outfile
                else
                        if(( $size > 0 )) ;
                        then 
                                echo "Waiting for all threads stoped.";
                                echo true > $need_switch_ip
                                echo $site >> $urllist
                                rm -f $outfile
                        else
                                echo "Not Found Page $url"
				echo $site >> $zero_byte_site
                                echo $url >> $zero_byte_url
                                rm -f $outfile
                        fi
                fi
        else
                echo "$outfile doesn't exist!"
                echo $site >> $urllist
        fi

}
CurlMarkoswebFile(){
	     outfile=$1
	     if [ -e $outfile  ];then                   #判断文件是否存在，curl下载时，出现了文件不存在的情况，故需判断
                size=$(stat -c %s $outfile)             #得到文件大小
		echo "$outfile filesize is  $size."
                MarkoswebFileIsValid $size              #判断文件
                result=$?                               #得到返回结果
                if [ $result -eq 0 ];                   #合法文件
                then
                        gzip -f $outfile                #压缩之
                elif [ $result -eq 1 ]                  #404错误文件
                then
                        echo "Not Found Page $url"
                        echo $site >> $zero_byte_site
                        echo $url >> $zero_byte_url
                        rm -f $outfile
                elif [ $result -eq 2 ];                 #如果返回的输入验证码页面，则需要切换ip
                then
                        echo "Waitting For All Threads Stopped,Threads : $(GetNowProcess)."
                        echo true > $need_switch_ip     #将切换ip标记置为ture
                        echo $site >> $urllist          #站点写入
                        rm -f $outfile
                fi
        else
		echo "$outfile doesn't exist!"
                echo $site >> $urllist
        fi
}
CurlAlexaFile(){
	   outfile=$1
	   if [ -e $outfile ];then
                size=$(stat -c %s $outfile)
                echo "$outfile filesize is  $size,"

                if [ $size -gt 40000 ];#根据测试数据，发现没有数据的站点。其大小为36615 左右
                then
                        gzip -f  $outfile
                else
                        echo "Not found Page $url"
			echo $site >> $zero_byte_site
                        echo $url >> $zero_byte_url
                        rm -f $outfile
                fi
        else
                echo "$outfile doesn't exist!"
                echo $site >> $urllist
	fi
}
WgetAlexaFile(){
	outfile=$1
	if [ -e $outfile ];then
		size=$(stat -c %s $outfile)
		echo "$outfile Size : $size"
		if [ $size -gt 40000 ];then
			gzip $outfile
		else
			echo "Not found Page $url"
			echo $site >> $zero_byte_site
                        echo $url >> $zero_byte_url
                        rm -f $outfile
		fi
	else
		echo $site >> $urllist
	fi
}
NeedSwitchIp(){
	downType=$1
	if [ $(cat $need_switch_ip) == "true" ];then    #需要切换ip
               	#set -x
		Running=$(GetNowProcess $downType $(cat $current_ip)) #当前下载进程是否为0
                if [ $Running == 0 ];then
			echo "Need swtich Ip,Threads: $Running."
			if [ $(cat $need_switch_ip) == "true" ];then
			echo changing > $need_switch_ip;	#切换ip过程中，增加一个状态，正在切换状态
								#正在切换状态 为changing
								#需要切换状态 为 true
								#不需要切换状态 为 false
                        echo $(GetNextIp) > $current_ip         #切换ip,得到新的ip
                        echo  false > $need_switch_ip           #切换ip的标记，设为false
			fi
                fi
		#set +x
        fi
}
#每当程序陷入等待时间过长，或者 拨号异常时，杀死相关进程，重新切换ip
ClearAdslProcess(){
	if [ $# -ge 3 ];then
		echo "清除进程"
		downType=$1
		adslName=$2
		adslNum=$3
		ps aux | grep $downType | grep -v grep | awk '{pringt $2}'| xargs kill -9 2>/dev/null
		ps aux | grep $adslName | grep -v grep | awk '{print $2}'| xargs kill -9 2>/dev/null
		ps aux | grep $adslNum | grep -v grep | awk '{print $2}' | xargs kill -9 2>/dev/null
	fi
}
