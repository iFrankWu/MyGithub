#!/bin/bash
#检查下载的压缩流文件的大小  markosweb 为deflate文件

#从文件名中得到域名
GetDomain(){
        echo $1 | awk -F '/' '{print $NF}'| awk -F '.html' '{print $1}'
}

AlexaFile(){
	outfile=$1
	if [ -e $outfile ];then
		size=$(stat -c %s $outfile)
                if [ $size -gt 10000 ];		#根据测试数据，发现没有数据的站点。其大小为36615 左右
                then
                	echo $outfile >> overList       #将文件输入已经下载完得文件列表中
		else
                        echo $(GetDomain $outfile) >> 404File #将文件输入到404列表中，以在数据库中标明状态
			rm -f $outfile
                fi
        else
		echo $(GetDomain $outfile) >> reloadFile        #文件不存在时，该文件得重新下载
	fi
}

while read file
do
	AlexaFile $file
done

