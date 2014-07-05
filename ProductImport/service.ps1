$java_home=$env:JAVA_HOME
$app_home="."
$classpath="$java_home\lib\tools.jar;$java_home\lib\dt.jar;$java_home\jre\lib\dt.jar;./work/classes/"
 

foreach ( $i in (Get-ChildItem -Name $app_home\libs\*.jar )) {
	$classpath=$classpath + ";$app_home\libs\$i"
}

Write-Host $classpath

java  -cp $classpath  -Xms256m -Xmx512m com.shinetech.service.Service
