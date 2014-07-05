'use strict';

var app = angular.module('userControl', ['ui', 'ngResource','ngCookies'] ,function ($routeProvider) {
    console.log("Configure route provider");
  //  $locationProvider.html5Mode(true).hashPrefix('!');
    $routeProvider
//        .when("/weekstaatoverzicht", {
//            templateUrl: "partials/weekstaatoverzicht.html",
//            controller: AppController
//        })
//        .when("/weekstaat/:weeknummer", {
//            templateUrl: "partials/weekstaat.html",
//            controller: WeekstaatController
//        })
    	.when("/crm/main/",{
	    	templateUrl:"partials/main.html",
	    	controller:MainController
    	})
//        .when("/crm/students/:searchText",{
        .when("/crm/students/",{
        	templateUrl:"partials/student.list.html",
        	controller:StudentController
        })
         .when("/crm/classes",{
        	templateUrl:"partials/class.list.html",
        	controller:ClassController
        })
         .when("/crm/users",{
        	templateUrl:"partials/user.list.html",
        	controller:UserController
        })
        .when("/crm/mypage",{
        	templateUrl:"partials/mypage.html",
        	controller:UserController
        })
         .when("/crm/login",{
        	templateUrl:"partials/userLogin.html",
        	controller:UserController
        })
        .when("/", {
            redirectTo: "/crm/login"
        })
        .otherwise({
            redirectTo: "/crm/login"
        });
})
    .factory('UserResource',UserResourceImpl)
    .factory("StudentResource",StudentResourceImpl)
    .factory("ClazzResource",ClazzResourceImpl)
    .factory("CommonResource",CommonResourceImpl)
    .factory("SearchResource",SearchResourceImpl)
    .service('UserService',UserServiceImpl)
    .service("StudentService",StudentServiceImpl)
    .service("ClassService",ClazzServiceImpl)
    .service('CommonService',CommonServiceImpl)
	.service('SerachService',SerachServiceImpl);

/*Date.prototype.getWeek = function () {
    var determinedate = new Date();
    determinedate.setFullYear(this.getFullYear(), this.getMonth(), this.getDate());
    var D = determinedate.getDay();
    if (D == 0) D = 7;
    determinedate.setDate(determinedate.getDate() + (4 - D));
    var YN = determinedate.getFullYear();
    var ZBDoCY = Math.floor((determinedate.getTime() - new Date(YN, 0, 1, -6)) / 86400000);
    var WN = 1 + Math.floor(ZBDoCY / 7);
    return WN;
};*/

var FLOAT_REGEXP = /^\-?\d+((\.|\,)\d+)?$/;
app.directive('smartFloat', function() {
  return {
    require: 'ngModel',
    link: function(scope, elm, attrs, ctrl) {
      ctrl.$parsers.unshift(function(viewValue) {
        if (FLOAT_REGEXP.test(viewValue)) {
          ctrl.$setValidity('float', true);
          return parseFloat(viewValue.replace(',', '.'));
        } else {
          ctrl.$setValidity('float', false);
          return undefined;
        }
      });
    }
  };
});