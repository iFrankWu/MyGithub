#!/bin/sh

if [ $# -lt 1 ]; then
	echo "Please Set The Login Host";
	echo "Example:sh $0 231"
	exit 1;
fi

if [ "$1" == "231" ];then 
	user=admin
	pass=admin555
	login_url=http://192.168.2.231/magento/admin
 	database=magento
	dbhost=192.168.2.231
	dbuser=root
	dbpass=root
	svn up
	svn up src/com/shinetech/dress/tag/
	ant
elif [ "$1" == "219" ];then
	user=admin
	pass=admin555
	login_url=http://192.168.2.219/magento/admin
 	database=magento160
	dbhost=192.168.2.231
	dbuser=dress2
	dbpass=dress2
	svn up
	svn up src/com/shinetech/dress/tag/
	ant
	
elif [ "$1" == "ok" ];then
	user=admin
        pass=admin902
        login_url=http://www.9dresses.com/admin    
        database=9dressesmall
        dbhost=localhost
	dbuser=Sjk-user
	dbpass=mnbhjkoiu890
else
	echo "error";
	exit;
fi





LANG=zh_CN.UTF-8
export LANG

basedir=`dirname $0`
cd $basedir
echo $(pwd)
cmd_java=$(whereis java | awk '{print $2}')

 
classpath="$JAVA_HOME/lib/tools.jar:$JAVA_HOME/libs/dt.jar:$JAVA_HOME/jre/lib/dt.jar"



for jar in $(ls $(pwd)/libs/*.jar) ; do
 classpath="${classpath}:${jar}"
done

classpath="./work/classes/:${classpath}"

 
$cmd_java -cp "${classpath}" -Xms256m -Xmx512m com.shinetech.action.ImportOtherCategory;
 

showroom=$(cat assets/now_use_showroom.txt | tail -1 | awk -F '=' '{print $2}')
cat $showroom | awk -F '\t' '{print $2}' | tr '[:upper:]' '[:lower:]' |  while read line;do  keyword=$(echo $line | sed  s/"'"/"''"/g);urlword=$(echo $line |tr ' ' '-'|sed s/"'"//g); echo "insert into catalogsearch_query(query_text, num_results, popularity, redirect, synonym_for, store_id, display_in_terms, is_active, is_processed, updated_at) values('$keyword',0,1,'http://www.9dresses.com/c/${urlword}.html','',1,1,1,0, now());" >> /tmp/showroom.query.sql; done


if [ "$1" == "231" ];then 
	scp /tmp/showroom.query.sql root@192.168.2.231:/tmp
	ssh root@192.168.2.231 '/opt/lampp/bin/mysql -uroot -proot  magento < /tmp/showroom.query.sql'
elif [ "$1" == "ok" ];then
	/usr/local/mysql/bin/mysql -uSjk-user -p 9dressesmall < /tmp/showroom.sql
fi



#echo "SELECT value FROM `catalog_category_entity_varchar` where attribute_id = 33 and entity_id > 2" | /usr/local/mysql/bin/mysql -uSjk-user -p 9dressesmall
 


#showroom=$(cat assets/now_use_showroom.txt | tail -1 | awk -F '=' '{print $2}')
#cat $showroom | awk -F '\t' '{print $2}' | tr '[:upper:]' '[:lower:]' |  while read line;do  keyword=$(echo $line | sed  s/"'"/"''"/g);urlword=$(echo $line |tr ' ' '-'|sed s/"'"//g); echo "insert into catalogsearch_query(query_text, num_results, popularity, redirect, synonym_for, store_id, display_in_terms, is_active, is_processed, updated_at) values('$keyword',0,1,'http://www.9dresses.com/c/${urlword}.html','',1,1,1,0, now());" >> /tmp/showroom.query.sql; done
#/usr/local/mysql/bin/mysql -uSjk-user -p 9dressesmall < /tmp/showroom.sql
 