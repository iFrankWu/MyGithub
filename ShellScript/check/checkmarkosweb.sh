#!/bin/bash


#从文件名中得到域名
GetDomain(){
        echo $1 | awk -F '/' '{print $NF}'| awk -F '.html' '{print $1}'
}

#检查wget下载的文件
WgetMarkoswebFile(){
        outfile=$1
        if [ -e $outfile ];then         
                size=$(stat -c %s $outfile)
                if [ $size -gt 6000 ];
                then
                        echo $outfile >> overList       #将文件输入已经下载完得文件列表中
                else
                        if(( $size > 0 )) ;
                        then 
                                echo $(GetDomain $outfile) >> reloadFile #需要输入验证码，该文件需要重现下载
                        else
                                echo $(GetDomain $outfile) >> 404File #将文件输入到404列表中，以在数据库中标明状态
                        fi
                        rm -f $outfile
                fi
        else
               #echo "$outfile doesn't exist!"
                echo $site >> reloadFile
        fi

}
#检查curl下载的文件
CurlMarkoswebFile(){
        outfile=$1
        if [ -e $outfile  ];then                   #判断文件是否存在，curl下载时，出现了文件不存在的情况，故需判断
                size=$(stat -c %s $outfile)             #得到文件大小
                if [ $size -gt 15000 ];then
                        echo $outfile >> overList       #将文件输入已经下载完得文件列表中
                else
                        #filetype=$(file $outfile | awk '{print $2}')
                        #if [ "$filetype" == "HTML" ];then
			if [ $size -gt 12000 ];then
                                echo $(GetDomain $outfile) >> 404File #将文件输入到404列表中，以在数据库中标明状态
                        else
                                echo $(GetDomain $outfile) >> reloadFile #需要输入验证码，该文件需要重现下载
                        fi
                        rm -f $outfile
                fi
        else
                #echo "$outfile doesn't exist!"
                echo $(GetDomain $outfile) >> reloadFile        #文件不存在时，该文件得重新下载
        fi
}

downtype=curl

if [ "$downtype" == "curl" ];then
	while read file
	do
		CurlMarkoswebFile $file
	done
else
	while read file
	do
		WgetMarkoswebFile $file
	done
fi
