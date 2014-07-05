#!/bin/bash

#ssh root@192.168.2.231 '/opt/lampp/bin/php /opt/lampp/htdocs/magento/shell/indexer.php reindexall'

#增加一条打折规则
function addrule(){
	add_rules=$(cat price.index.html | grep "Add New Rule"  | awk -F \' '{print $4}');
	curl -b ck.txt -s -L -o add_rules.html $add_rules
	form_key=$(cat add_rules.html | grep "name=\"form_key\"" | awk -F 'value="' '{print $2}'|awk -F '"' '{print $1}');
	save_url=$(cat add_rules.html | grep promo_catalog/save | awk -F "\"" '{print $4}');
	name=$1
	description=$2
	from_date=$3
	discount=$4
	echo $name $description $from_date $discount	
	curl -b ck.txt -s -L -o save.html -d "form_key=$form_key&auto_apply=&name=$name&description=$description&is_active=1&website_ids%5B%5D=1&customer_group_ids%5B%5D=0&customer_group_ids%5B%5D=1&customer_group_ids%5B%5D=2&customer_group_ids%5B%5D=3&from_date=$from_date&to_date=&sort_order=0&rule%5Bconditions%5D%5B1%5D%5Btype%5D=catalogrule%2Frule_condition_combine&rule%5Bconditions%5D%5B1%5D%5Baggregator%5D=all&rule%5Bconditions%5D%5B1%5D%5Bvalue%5D=1&rule%5Bconditions%5D%5B1--1%5D%5Btype%5D=catalogrule%2Frule_condition_product&rule%5Bconditions%5D%5B1--1%5D%5Battribute%5D=discount&rule%5Bconditions%5D%5B1--1%5D%5Boperator%5D=%3D%3D&rule%5Bconditions%5D%5B1--1%5D%5Bvalue%5D=$discount&rule%5Bconditions%5D%5B1%5D%5Bnew_child%5D=&simple_action=by_percent&discount_amount=$discount&stop_rules_processing=1" $save_url
}
function apply_rules(){
	apply_url=$(cat price.index.html | grep "Apply Rules" | awk -F \' '{print $2}')
	curl -b ck.txt -s -L -o apply.html $apply_url
}
function delete_rule(){
	curl -L -s -b ck.txt -o price.index.html $price_index_url
	cat price.index.html | grep edit/id | awk -F '"' '{print $2}' | while read edit_url ;do 
		curl -L -s -b ck.txt -o edit_url.html $edit_url;
		rule_id=$(cat edit_url.html | grep "name=\"rule_id\"" | awk -F 'value="' '{print $2}'|awk -F '"' '{print $1}');	
		delete_url=$(cat edit_url.html | grep "Delete Rule" | awk -F \' '{print $8}')
		curl -b ck.txt -s -L -o delete.html $delete_url
	done
	apply_rules
}

#登录
#echo "start ... login magento system..."
#curl -L -s -c ck.txt -d "form_key=WwqUREkAHelYRgSf&login%5Busername%5D=admin&login%5Bpassword%5D=1qaz2wsx" -o login.html http://192.168.2.231/magento/admin
#curl -L -s -c ck.txt -d "form_key=WwqUREkAHelYRgSf&login%5Busername%5D=admin&login%5Bpassword%5D=admin555" -o login.html http://192.168.2.219/magento/admin
#echo "login success";
#index 页面的入口url
price_index_url=$(cat login.html| grep "Catalog Price Rules" | awk -F "href=\"" '{print $2}'| awk -F "\"" '{print $1}');

#echo "price index url : "$price_index_url;

delete_rule

#exit;
curl -L -s -b ck.txt -o price.index.html $price_index_url

cat shell/add_price_rules.conf | while read line;do
	#addrule 70off 70off 11/22/11 70
	eval `echo $line | awk '{print "p_name="$1,"p_desc="$2,"p_f_date="$3,"p_discount="$4}'`
	addrule $p_name $p_desc $p_f_date $p_discount
done
apply_rules

rm -f edit_url.html price.index.html ck.txt save.html add_rules.html login.html delete.html apply.html
