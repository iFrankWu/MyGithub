#!/bin/bash

#登录
function login(){
	user=$1
	pass=$2
	login_url=$3
	curl -L -s -c ck.txt -d "form_key=WwqUREkAHelYRgSf&login%5Busername%5D=$user&login%5Bpassword%5D=$pass" -o login.html $login_url
}
