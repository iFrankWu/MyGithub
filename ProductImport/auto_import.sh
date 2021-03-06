#!/bin/sh

############################################
######### 检查输入参数，并开始计时 #########
############################################
date
if [ $# -lt 1 ]; then
	echo "Please Set The Login Host";
	echo "Example:sh $0 231"
	exit 1;
fi

############################################
####根据不同的环境，删除对应的产品数据 #####
############################################
if [ "$1" == "219" ];then 
	user=admin
	pass=admin555
	login_url=http://192.168.2.219/magento/admin
	echo "now_use_magento_database=219" > shell/user.conf
	#一、还原环境
	#事先删除商城的媒体库,若为219时 开启，因为219空间满
	#ssh root@192.168.2.219 'rm -rf /opt/lampp/htdocs/magento/media/catalog/;rm -rf /mnt/wedding/product/;mkdir -p /opt/lampp/htdocs/magento/media/catalog/  /mnt/wedding/product/; ln -s /mnt/wedding/product/ /opt/lampp/htdocs/magento/media/catalog/product'
	ssh root@192.168.2.219  'rm -rf /opt/lampp/htdocs/magento/media/catalog/';
	database=magento160
	dbhost=192.168.2.219
	echo ".......are you sure update 219."
#	exit 1;
elif [ "$1" == "231" ];then
	user=admin
	pass=1qaz2wsx
	login_url=http://192.168.2.231/magento/admin
	echo "now_use_magento_database=231" > shell/user.conf
	ssh root@192.168.2.231 'rm -rf /opt/lampp/htdocs/wordpress/magento/media/catalog/'
	database=magento
	dbhost=192.168.2.231
else
	echo "Error parameters.plase input 231 or 219."
	exit 1;
fi

#加载登录模块
. shell/login.sh

#echo $user $pass $login_url
#login $user $pass $login_url
#exit 1;

############################################
###更新svn源代码，并编译java类文件 #########
############################################
#svn 更新
svn up
svn up src/com/shinetech/dress/tag/

sleep 3

#编译java文件
ant
sleep 3

LANG=zh_CN.UTF-8
export LANG
basedir=`dirname $0`
cd $basedir
cmd_java=$(whereis java | awk '{print $2}')
classpath="$JAVA_HOME/lib/tools.jar:$JAVA_HOME/libs/dt.jar:$JAVA_HOME/jre/lib/dt.jar"
for jar in $(ls $(pwd)/libs/*.jar) ; do
 classpath="${classpath}:${jar}"
done
classpath="./work/classes/:${classpath}"


############################################
### 用sql truncate 语句 提高清空表的速度 ###
############################################

echo "use "$database > /tmp/init_product.sql

cat shell/magento160.sql >> /tmp/init_product.sql

scp /tmp/init_product.sql root@${dbhost}:/tmp
ssh root@${dbhost} '/opt/lampp/bin/mysql -uroot -proot < /tmp/init_product.sql'

##############################################
#######清空本地数据库的产品描述等信息#########
##############################################
#echo "init local db,reset product name,market price,real price,decription and etc. "
#$cmd_java -cp ${classpath} -Xms256m -Xmx512m com.shinetech.init.InitProductInfoHandle;

#1、删除分类
echo "delete all categorys."
$cmd_java -cp ${classpath} -Xms256m -Xmx512m com.shinetech.init.DeleteCategory;

#2、删除属性集
echo "delete attribute set."
$cmd_java -cp ${classpath} -Xms256m -Xmx512m com.shinetech.init.DeleteAttributeSet

#3、删除属性,由于这里要建立相关的属性集，故暂不删除
echo "delete attribute."
$cmd_java -cp ${classpath} -Xms256m -Xmx512m com.shinetech.init.DeleteAttribute;


#4、删除商城所有产品数据
echo "delete all prodcuts in magento."
$cmd_java -cp ${classpath} -Xms256m -Xmx512m com.shinetech.init.DeleteAllProduct;


#5、这里需要索引一次商城数据库,即导入数据前，确保数据库的索引表中数据为空
echo "index magento the first."
login $user $pass $login_url
sh shell/index.sh > shell/index.log
#exit;
#二、导入简单产品数据和相关产品
#1、导入属性和其他属性，但是这里还是不能够自动化呀，因为这里需要建立属性集，所以为了自动化，属性不删除，就用以前的属性集
$cmd_java -cp ${classpath} -Xms256m -Xmx512m com.shinetech.action.ImportAttribute;
$cmd_java -cp ${classpath} -Xms256m -Xmx512m com.shinetech.action.ImportOtherAttribute;

#2、导入属性集
$cmd_java -cp ${classpath} -Xms256m -Xmx512m com.shinetech.action.ImportAttributeSet;

#3、导入分类
echo "import the 14 commom category."
$cmd_java -cp ${classpath} -Xms256m -Xmx512m com.shinetech.action.ImportCategory; >/dev/null

#exit;
#4、生产产品的csv文件 
echo "gerneric the simple product csv file."
$cmd_java -cp "${classpath}" -Xms256m -Xmx512m com.shinetech.proxy.GenericSimpleProductProxy;

#5、导入商城
echo "import simple product csv file to magneto."
login $user $pass $login_url
sh shell/import.sh 

#6、此时需要重新索引一次数据库
echo "reindex magento data."
login $user $pass $login_url
sh shell/index.sh > shell/index.log

#7、为各个分类的产品排序
echo "sort catalog products."
$cmd_java -cp ${classpath} -Xms256m -Xmx512m com.shinetech.action.UpdateCatalogProductSort; 

#8、导入相关产品
echo "import related products."
$cmd_java -cp ${classpath} -Xms256m -Xmx512m com.shinetech.action.ImportRelatedProduct;

#三、导入衬裙部分#
#1、导入衬裙分类
echo "import petticoat category."
$cmd_java -cp ${classpath} -Xms256m -Xmx512m com.shinetech.action.ImportPetticoatCategory;

#2、生成衬裙csv文件
echo "generic petticoat product csv file."
$cmd_java -cp "${classpath}" -Xms256m -Xmx512m com.shinetech.proxy.GenericPetticoatProxy;

#3、导入衬裙产品
echo "import petticoat product csv file."
login $user $pass $login_url
sh shell/import.sh

#4、为产品导入衬裙
echo "import petticoat for products."
$cmd_java -cp ${classpath} -Xms256m -Xmx512m com.shinetech.action.ImportPetticoat4Prodcut;


#更新打折规则,先删除后增加
echo "update price rules."
login $user $pass $login_url
sh shell/add_price_rules.sh 

#索引一次
echo "reindex magento data the 3th time."
login $user $pass $login_url
sh shell/index.sh > shell/index.log

echo  "not import showroom products...."
#exit;
#导入展厅的产品
echo "import showroom products."
$cmd_java -cp ${classpath} -Xms256m -Xmx512m com.shinetech.action.ImportOtherCategory; >/dev/null

echo "reindex magento data the 4th time."
login $user $pass $login_url
sh shell/index.sh > shell/index.log

echo "it's ok!";
date
exit 0;
