#!/bin/bash

#登录
#echo "start ..i login magento system..."
#curl -L -s -c ck.txt -d "form_key=WwqUREkAHelYRgSf&login%5Busername%5D=admin&login%5Bpassword%5D=1qaz2wsx" -o login.html http://192.168.2.231/magento/admin
#curl -L -s -c ck.txt -d "form_key=WwqUREkAHelYRgSf&login%5Busername%5D=admin&login%5Bpassword%5D=admin555" -o login.html http://192.168.2.219/magento/admin


#echo "login success";
#import页面的入口url
import_url=$(cat login.html | gawk 'match($0,"href=\"(.*admin/import[^\"]+)",array) {print array[1]}');

#echo "import index url : "$import_url;
curl -L -s -b ck.txt -o import.index.html $import_url


#取出表单信息
eval `cat import.index.html | gawk 'match($0,"<form id=\"edit_form\" action=\"([^\"]+)\".*?name=\"form_key\".*?value=\"([^\"]+)\"",array) {print "action_url="array[1];print "form_key="array[2]}'`

#action_url 提交路径的url
#form_key 提交参数之一
#echo "action_url:"$action_url;
#echo "form_key:"$form_key;
post_url=$action_url"?form_key=$form_key"
#echo $post_url;
echo "start check the csv file data"
curl -s -b ck.txt -F import_file=@data/simple_import.csv -F behavior=append -F entity=catalog_product -F form_key=$form_key -o check_result.txt $post_url

check_result_form_key=$(cat check_result.txt | gawk 'match($0,"string.16..\"([^\"]+)",array) {print array[1]}');
import_url=$(cat check_result.txt | gawk 'match($0,"startImport..([^,]+)",array) {print array[1]}');
#echo $check_result_form_key;
#echo $import_url;
temp=$(echo $import_url | sed 's/\\//g');
length=${#temp};
url=${temp:0:$length-1}
#import_entity=$url"?form_key=$check_result_form_key"
import_entity=$url"?form_key=$form_key"

if [ "$length" -lt "1" ]
then 
	echo "an error occurs at check data, details:"
	cat check_result.txt
	exit 1
fi
echo "data check success"


#echo $import_entity
echo "import the product data"
curl -s -b ck.txt -F form_key=$form_key -F behavior=append -F entity=catalog_product -o statuss.txt $import_entity

statuss=$(cat statuss.txt | grep successfully)
len=${#statuss}
if [ "$len" -gt "1" ]
then 
	echo "product data success import over"
	rm -f statuss.txt check_result.txt ck.txt login.html import.index.html
	exit 0;
else
	echo "an error occurs at import product data,detail:"
	cat statuss.txt
	exit 1;
fi
