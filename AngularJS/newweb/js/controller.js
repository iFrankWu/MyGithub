app.controller('MainController',function($scope,$location,UserService,$locale,$cookieStore,$http,CommonService,$rootScope){
	$scope.userName;
	var user = $cookieStore.get("user");
	if(user != null){
		$scope.userName =user.username;
	}
	$rootScope.$on('$routeChangeSuccess', function(evt, cur, prev) {
    //alert($location.path());
		if($location.path() == "/crm/login"){
			 $("#logout").hide();
			 $("#mypageLink").hide();
			 $("#studentLink").hide();
			 $("#classLink").hide();
			 $("#userLink").hide();
		}else{
			if($cookieStore.get("user") == null){
			// $("#logout").show();
			} else{
				var user = $cookieStore.get("user");
				$scope.userName =user.username;
				if(user.type != CommonService.types.values[2]){
					$("#classLink").show();
					$("#userLink").show();
				}
				$("#logout").show();
				$("#mypageLink").show();
				$("#studentLink").show();
			}
		}
	})
});

