#!/bin/bash

#ssh root@192.168.2.231 '/opt/lampp/bin/php /opt/lampp/htdocs/magento/shell/indexer.php reindexall'

#登录
#登录模块放到主程序中去执行

#echo "start ... login magento system..."
#curl -L -s -c ck.txt -d "form_key=WwqUREkAHelYRgSf&login%5Busername%5D=admin&login%5Bpassword%5D=1qaz2wsx" -o login.html http://192.168.2.231/magento/admin
#curl -L -s -c ck.txt -d "form_key=WwqUREkAHelYRgSf&login%5Busername%5D=admin&login%5Bpassword%5D=admin555" -o login.html http://192.168.2.219/magento/admin

#echo "login success";
#index 页面的入口url
index_url=$(cat login.html| grep "Index Management" | gawk 'match($0,"<a href=\"([^\" ]+process/list/[^\"]+)",array) {print array[1]}');

#echo "index index url : "$index_url;
curl -L -s -b ck.txt -o index.index.html $index_url

post_url=$(cat index.index.html | grep massReindex | awk -F '"url":' '{print $3}'| awk -F "\"" '{print $2}' | sed 's/\\//g');
#取出表单信息

form_key=$(cat index.index.html | grep "var FORM_KEY" | awk -F "'" '{print $2}');

#action_url 提交路径的url
#form_key 提交参数之一
#echo "action_url:"$action_url;
#echo "form_key:"$form_key;
#echo $post_url;
curl -s -b ck.txt -d "form_key=$form_key&process=8%2C7%2C5%2C6%2C1%2C4%2C2%2C3%2C9&massaction_prepare_key=process" -o index_result.txt $post_url
rm -f index.index.html login.html index_result.txt ck.txt 
