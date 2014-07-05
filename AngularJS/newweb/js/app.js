'use strict';
var app = angular.module('StudentApp',['ui.bootstrap','ngRoute',"ngResource","ngCookies"]);

app.config(function($routeProvider){
    console.log("Configure route provider");
	$routeProvider
	.when("/crm/main/",{
	    	templateUrl:"partials/main.html",
	    	controller:"MainController"
    	})
//        .when("/crm/students/:searchText",{
        .when("/crm/students/",{
        	templateUrl:"partials/student.list.html",
        	controller:"StudentController"
        })
         .when("/crm/classes",{
        	templateUrl:"partials/class.list.html",
        	controller:"ClassController"
        })
         .when("/crm/users",{
        	templateUrl:"partials/user.list.html",
        	controller:"UserController"
        })
        .when("/crm/mypage",{
        	templateUrl:"partials/mypage.html",
        	controller:"UserController"
        })
         .when("/crm/login",{
        	templateUrl:"partials/userLogin.html",
        	controller:"UserController"
        })
        .when("/", {
            redirectTo: "/crm/login"
        })
        .otherwise({
            redirectTo: "/crm/login"
        });

});
