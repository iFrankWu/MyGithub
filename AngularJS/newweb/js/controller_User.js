app.controller("UserController",function ($scope,$log, $routeParams,$location, $filter,$http,UserService,$cookieStore,CommonService,StudentService) {
	$scope.isAdmin = false;
	if($cookieStore.get("user") == null){
		//$("#logout").show();
	} else{
		var user = $cookieStore.get("user");
		if(user.type != CommonService.types.values[2]){
			$("#classLink").show();
    		$("#userLink").show();
    		$scope.isAdmin = true;
		}
		$("#logout").show();
		$("#mypageLink").show();
		$("#studentLink").show();
	}
	$scope.allMoney = function(pays){
		var money = 0.0;
		// for(var index in pays){
		if($.isEmptyObject(pays))return 0;
		for (var index = 0; index < pays.length; index++) {
			money += pays[index].payAmount;
		}
		return money;
	}
	$scope.tempname;
	$scope.getStudentNameBySid = function(sid){
		 var param = {
				 	action:'getStuNameById',
					sid:sid
			}
		  CommonService.putRequest(param,function(result){
			if(result != null){//该学生已经存在了
				$scope.tempname =  result.name;
			}
		 },function(response){
			 CommonService.unauthorizedCallBack(response);
		 });
		 return $scope.tempname;
	}
	
	$scope.payments;
	$scope.date1;
	$scope.date2;
	$scope.getPayments = function(){
		if( $scope.date1 != null || $scope.date1 != null){
			if($scope.date1 > $scope.date2){
				alert("日期设置出错");
				return;
			}
		}
		if($scope.date1 == null &&  $scope.date2 == null){//默认设置为当天
			$scope.date1 = $filter('date')(new Date(), 'yyyy-MM-dd');
			$scope.date2 = $filter('date')(new Date(), 'yyyy-MM-dd');
				//return $filter('date')(new Date(time), 'yyyy-MM-dd HH:mm:ss');
		}
		 var param = {
				 	action:'serachPaymentByUser',
					date1:$scope.date1,
					date2:$scope.date2
			}
		CommonService.postRequest(param,function(result){
			if(result != null){//该学生已经存在了
//				console.log(result);
				$scope.payments = result;
			}
		 },function(response){
		 	CommonService.unauthorizedCallBack(response);
			 // $scope.unauthorizedCallBack(response);
		 });
	}
	 
 
		$scope.username = "";
		$scope.id;
	    $scope.password = "";
	    $scope.result;
	    $scope.users = null;
	    $scope.modifys=false;
		$scope.createDate = "";
		
		$scope.cancle= function(){
			 $scope.id=null;
			 $scope.username = "";
		     $scope.password = "";
		     $scope.result;
		     $scope.users;
		     $scope.modifys=false;
			 $scope.createDate = "";
			 $scope.modifys = false;// !$scope.modifys;
		 }
		 
	    $scope.types = CommonService.types;
	    /*[
	                    {name:'超级管理员', value:'super'},
	                     {name:'管理员', value:'admin'},
	                     {name:'普通用户', value:'normal'}
	                   ];*/
	    $scope.statuses = CommonService.statuses;/*[
	                     {name:'可用', value:'useable'},
	                     {name:'禁用', value:'unuseable'}
	                   ];*/
	   
	    $scope.seek = function(value,arrays){
	    	// for(var ele in arrays){
	    	for (var ele = 0; ele < arrays.length; ele++) {
	    		if(arrays[ele].value == value){
	    			return arrays[ele].name;
	    		}
	    	}
	    }
	    $scope.currentuser;
	    if( $scope.currentuser == null){
	    	$scope.currentuser = $cookieStore.get("user");
	    }
	    $scope.login = function(){
	    	var user = {
	    			username:$scope.username,
	    			password:$scope.password
	    	}
	    	var result = UserService.login(user,function(result){
	    		if(result.isSuccess){
		    		// MainController.$inject = ['$scope','$location','UserService','$cookieStore','$locale','$http','CommonService'];
		    		 //MainController = function($scope,$location,UserService,$rootScope,$locale,$cookieStore,$http,CommonService){
		    		 //CommonService.user = result.description;
		    		 $cookieStore.put("user",result.description);
		    		 $scope.currentuser = result.description;
//		    		 MainController.$inject = ['$scope','$location','UserService','$cookieStore','$locale','$http','CommonService'];
		    		$("#logout").show();
		    		$("#mypageLink").show();
		    	
		    		 if(result.description.type==CommonService.types.values[2]){
		    			$("#classLink").hide();
		    			 $("#studentLink").show();
		    				$("#userLink").hide();
		    			$location.path("/crm/students");
		    		 }else{
		    			$("#classLink").show();
			    		$("#userLink").show();
			    		 $("#studentLink").show();
			    		 $location.path("/crm/main");//.search({'isSuccess':result.isSuccess});
		    		 }
	    		}else{
		    		alert("登录失败，详情： "+result.description);
		    	}
	    	},function(response){
	    		alert("Response status:"+response.status+", 未知错误，请联系Frank Wu(wushexin@gmail.com)");
	    	});
	    }
	    $scope.addUser = function(){
	    	var user = {
	    			username:$scope.username,
	    			password:$scope.password,
	    			type:$scope.type,
	    			status:$scope.status
	    	}
	    	UserService.addUser(user,function(result){
	    		if(result.isSuccess){
	    			alert("成功添加用户");
	    			$scope.cancle();
	    			$scope.getUserList();
	    		}else{
		    		alert("添加用户失败，详情： "+result.description);
		    	}
	    	},function(response){
	    		 CommonService.unauthorizedCallBack(response);
	    	});
	    
	    }
	    $scope.deleteUser =  function(user){
	    	var r=confirm("确定删除该用户吗?");
	    	if(r == true){
		    	UserService.deleteUser(user.id,function(result){
		    		if(result.isSuccess){
		    			alert("成功删除用户");
		    			$scope.getUserList();
		    		}else{
			    		alert("删除用户失败，详情： "+result.description);
			    	}
		    	},function(response){
		    		 CommonService.unauthorizedCallBack(response);
		    	});
	    	}
	    }
	    $scope.getUserList = function(){
	    	 UserService.getUserList(function(result){
	    		if(result != null){
//		    		console.log(result);
		    		$scope.users = result;
		    	}else{
		    		alert("Get user list is null");
		    	}
	    	},function(response){
//	    		 $scope.unauthorizedCallBack(response);
				//CommonService.unauthorizedCallBack(response);
	    		console.log(response);// just log it 
	    	});
	    
	    };
	    
// 	    $scope.$watch('confirmed1', function(newValue, oldValue) {
// //	        console.log("old value: %s and new value: %s", oldValue, newValue);
// 	    	 $scope.getUserList();
// 	    });

	    $scope.modify = function(user){
	    	$scope.username = user.username;
		    $scope.password = user.password;
		    $scope.type = user.type;
			$scope.status = user.status;
			$scope.createDate = user.createDate;
			$scope.id = user.id;
			$scope.modifys = !$scope.modifys;
			$("#type").val(user.type);
			$("#status").val(user.status);
	    }
	    $scope.updateUser = function(){
	    	var user = {
	    			id:$scope.id,
	    			username:$scope.username,
	    			password:$scope.password,
	    			type:$scope.type,
	    			status:$scope.status,
	    			createDate:$scope.createDate
	    	}
	    	UserService.updateUser($scope.id,user,function(result){
	    		if(result.isSuccess){
	    			alert("更新用户成功");
	    		    $scope.cancle();
	    			$scope.getUserList();
	    		}else{
		    		alert("更新用户失败，详情： "+result.description);
		    	}
	    	},function(response){
	    		  CommonService.unauthorizedCallBack(response);
	    	});
	    }
	    
	    $scope.formatTime = function(time){
	    	if(time == null)return null;
	    	return $filter('date')(new Date(time), 'yyyy-MM-dd HH:mm:ss');
	    }
	    
	    
	    $scope.formatDate = function(time){
	    	if(time == null)return null;
	    	return $filter('date')(new Date(time), 'yyyy-MM-dd');
	    }
	    
	    $scope.isSelected = function(v1,type,v2){
	    	var r = "";
	    	if("type"==type)
	    		 r =  (v1==v2.type?"selected":"");
	    	else
	    		r= ( v1==v2.status?"":"selected");
	    	 document.write(r);
	    }
	  $scope.doesReminder = function(doesReminder){
		  return doesReminder == true ? "已设置":"没有设置";
	  }

	  /***
		this is for return back for student
	*/
	$scope.allStudents4Refund = [];
	$scope.allClass4Refund = [];
	$scope.selectedStu;
	$scope.showSettingRefund = false;
	$scope.allRefunds ;
	$scope.selectedClass;
	$scope.refundPayways = CommonService.refundPayways;
	$scope.payment = {
				"payAmount":null,
				"sid":null,
				"uid":null,
				"payer":null,
				"receiver":null,
				"useBalance":null,
				"cashOrCard":null,
				"selectedClass":null,
				"selectedPayway":null};
	
	$scope.getStudentList4Refund = function(){
		$scope.showSettingRefund = true;
		// $scope.refundPayways = CommonService.refundPayways;
		StudentService.getStudentList("true",function(result){
			if(result!= null){
				$scope.allStudents4Refund = result;
			} 
		},function(response){
			 CommonService.unauthorizedCallBack(response);
		 });	
	}

	$scope.changeStudent = function(){
		$scope.allClass4Refund = $scope.selectedStu.clazzes;
	}
	$scope.getallRefunds = function(){
		var param = {
			"action":"getAllRefunds"
		};
	 CommonService.postRequest(param,function(result){
		  	$log.log(result);
			if(result != null){
				$scope.allRefunds =  result;
			}
		 },function(response){
		 	$log.log(response);
			 // CommonService.unauthorizedCallBack(response);
		 });
	}
	$scope.deleteRefund = function($index){
		$log.log($index);
		var r=confirm("确实删除该退款信息吗？");
		if(r == false)return;
		var param = {
			"action":"deleteRefund",
			"payId":$scope.allRefunds[$index].id,
		}
		  CommonService.putRequest(param,function(result){
			if(result != null){//该学生已经存在了
				alert("删除成功！");
				$scope.getallRefunds();
			}
		 },function(response){
			 CommonService.unauthorizedCallBack(response);
		 });
	}
	$scope.addRefund = function(){
		if($.isEmptyObject($scope.selectedStu)){
			alert("请首先选择学生");
			return;
		}
		if($.isEmptyObject($scope.selectedClass)){
			alert("请首先选择班级");
			return;
		}
		$scope.payment.sid = $scope.selectedStu.id;
		$scope.payment.useBalance = $scope.payment.payAmount;
		$scope.payment.selectedClass = $scope.selectedClass.className;
		$scope.payment.uid = $cookieStore.get("user").id,
		$scope.payment.payer = $cookieStore.get("user").username;
		$scope.payment.selectedPayway = $scope.selectedStu.name;//store the student name in column selectedPayway
		$scope.payment.receiver = $scope.selectedStu.name;
		var param = {
			"action":"addRefund",
			"payment":$scope.payment
		}
		$log.log(param);
		  CommonService.putRequest(param,function(result){
			if(result != null){//该学生已经存在了
				alert("设置成功！");
				$scope.getallRefunds();
			}
		 },function(response){
			 CommonService.unauthorizedCallBack(response);
		 });
		$scope.showSettingRefund = false;
	}
 	$scope.printRefund = function($index){

 	} 

 	$scope.date5;
	$scope.date6;
	$scope.abletosummit = false;
	$scope.exportData = function(){
		if($scope.date5 !=null &&  $scope.date6!= null){
			if($scope.date5 > $scope.date6){
				alert("请设置正确的时间区间");
				$scope.abletosummit = true;
				return;
			}
			$scope.abletosummit = false;
		}
	}

	 $scope.getColFromArray = function(array,colName) {
	 	var array = $scope.users;
				 	if(array == null)return null;
				 	var cols = [];
				 	for (var i = 0; i < array.length; i++) {
				 		cols.push(array[i][colName]);
				 	}
				 	// var cols = $.map(array)[colName];
				 	return cols;
				 }

	$scope.getallRefunds();
	$scope.getUserList();
});