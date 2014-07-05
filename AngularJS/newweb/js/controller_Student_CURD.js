app.controller("StudentCURDController",function($scope,$modalInstance,$log,StudentService,ClassService,$cookieStore,UserService,CommonService,student,modifys){
				$scope.student = student;//{"accountBalance":2000};
				$scope.selected1 = [];
				$scope.selected2 = [];
				$scope.payways ;
				$scope.modifys = modifys;
				$scope.addcomm = false;
				$scope.allClazzes = [];//allClazzes;
				$scope.otherTelephones = [];//存放其他联系电话的数组，其初始化，由otherTelephone用分割符“，”生成。
				$scope.addpaypage = false;
				$scope.initialPaymentHistorySize = 0;
				$scope.initialCommentSize = 0;
				$scope.isAdmin = false;
				 function init(){
				 	$log.log("init the modalInstance");
				 	$scope.payways = CommonService.payways;
				 	 ClassService.getAllClassList('false',function(result){
						 if(result != null){
					    		$scope.allClazzes = result;
					    		initClassState();
					    		initPaymentState();
					    	}else{
					    		$log.log("Get All class list is null");
					    	}

					 },function(response){
						  CommonService.unauthorizedCallBack(response);
					 });
					 var user = $cookieStore.get("user");
					 if(user.type != CommonService.types.values[2]){
					 	$scope.isAdmin = true;
					 }
				}
				function initClassState(){
					if(!$.isEmptyObject(student)){
					 	$scope.otherTelephones = $scope.student["otherTelephone"].split(",");
					 	for (var sel = 0; sel < $scope.student.clazzes.length; sel++) {
					 		// $log.log($scope.student["clazzes"][sel]);
							 // for(var ele in $scope.allClazzes){
							 for (var ele = 0; ele < $scope.allClazzes.length; ele++) {
							 		if($scope.allClazzes[ele]["className"] == $scope.student.clazzes[sel]["className"]){
									    		$scope.allClazzes.splice(ele,1);
									    }
									}
						 }
					  }else{
					  	$scope.student = {};
					  }
				}
				function initPaymentState(){
					if(!$.isEmptyObject(student)){
						if(!$.isEmptyObject(student['paymentHistorys'])){
							$scope.initialPaymentHistorySize = student['paymentHistorys'].length;
							for (var i = 0; i < student['paymentHistorys'].length; i++) {
								
								$scope.initChangeClass(student['paymentHistorys'][i],i);
								// $scope.initPayment(student['paymentHistorys'][i],i);
								$scope.initChangePayway(student['paymentHistorys'][i],i);
							};
						}
						if(!$.isEmptyObject(student['communications'])){
							$scope.initialCommentSize = student['communications'].length;
						}
					}
				}
					
				 init();

				 $scope.checkStudent = function(){
					 if($scope.student.name== null || $scope.student.mobilePhone == null ){
						 alert("请先输入学生名字和手机号");
						 return;
					 }
					 var param = {
							 	action:'doesExistStudent',
								name:$scope.student.name,
								mobilePhone:$scope.student.mobilePhone
						}
					 CommonService.putRequest(param,function(result){
						if(result.isSuccess){//该学生已经存在了
							alert(result.description);
							return ;
						}else{
							alert("该学生不存在");
						}
					 },function(response){
						  CommonService.unauthorizedCallBack(response);
					 });
				 }

				$scope.addPhone  = function(){
					$scope.otherTelephones.push("Input first phone number here");
					$log.log($scope.otherTelephones);
				}
				$scope.addMorePhone = function(){
					$scope.otherTelephones.push("Input new phone number here");
				}
				$scope.removePhone = function(index){
					 var r=confirm("确定删除该联系电话吗?");
		 			if(r == false)return;
					$scope.otherTelephones.splice(index,1);
				}

				$scope.addComm = function(){
					if(!$scope.addcomm){
						 $scope.addMoreCommInfo();
						 $scope.addcomm = true;
					 }else{
						 $scope.addcomm = false; 
						 $scope.student.communications = [];
					 }
				}
				$scope.addMoreCommInfo = function(){
					 if($scope.student.communications == null)
						 $scope.student.communications = [];
					 	 $scope.student.communications.push({
							 content:null,
							 communicationDate:null,
							 sid:$cookieStore.get("user").id,
							 username:$cookieStore.get("user").username
					 });
				}
				 $scope.removeCommHtml = function($index){
				 	 var r=confirm("确定删除该跟踪记录吗?");
		 			if(r == false)return;
		 			$scope.student.communications.splice($index,1);
	 			}

	 			 $scope.addPay = function(){
					 if(!$scope.addpaypage){
						 $scope.addMorePayInfo();
						 $scope.addpaypage = true;
					 }else{
						 $scope.addpaypage = false; 
						 $scope.student.paymentHistorys = [];
					 }
				 }
				 $scope.addMorePayInfo = function(){
					 if($scope.student.paymentHistorys == null){
						 $scope.student.paymentHistorys = [];
					 }
					 $scope.student.paymentHistorys.push({
						 payAmount:0,
						 purpose:"",
						 cashOrCard:null,
						 doesNeedReminder:true,
						 startDate:null,
						 endDate:null,
						 remark:null,
						 useBalance:0,
					 	 payDate:null,
					 	 pwayid:null,
					 	 selectedClass:null,
					 	 selectedPayway:null
					 });
				 }
				 
				 $scope.removePayHtml = function($index){
				 	 var r=confirm("确定删除该付款信息吗?");
		 			if(r == false)return;
					 $scope.student.paymentHistorys.splice($index,1);
				 }

				// $scope.selectedClassToPay; //绑定选择班级的 select
				// $scope.selectedPayway;  // 绑定付款方式 select
				$scope.paywaysForPay = []; // 这是一个数组
				$scope.payAmountForPay = [];//标准学费的一个数组
				$scope.discountForPay = [];//折扣数组
				$scope.paywayssForPay = []; //获得当前的付款方式，以便计算付款总额， 总额等于 学费×折扣×月份数
				$scope.useBalances = [];
				
				$scope.changeClass = function (paymentHistory,$index) {
					$log.log(paymentHistory);
					// $scope.selectedClassToPay =  JSON.parse(obj.selectedClassToPay);
					var selectedClass = $scope.getObjectFromArrayByColValue($scope.student.clazzes,"className",paymentHistory.selectedClass);
					$scope.paywaysForPay[$index] = selectedClass.payways;
					$scope.payAmountForPay[$index] = selectedClass.payAmount;
					paymentHistory.payAmount = selectedClass.payAmount;
					if(!$.isEmptyObject($scope.student) && $scope.student.id!= null){
						var param = {
								 	action:'getDiscountForStudentByClass',
									sid:$scope.student.id,
									cid:selectedClass.id
							}
						$scope.getDiscountForStudentByClass(param,$index);
					}
				}
				$scope.golableDiscount = false;
				$scope.getDiscountForStudentByClass = function(param,$index){
					CommonService.putRequest(param,function(result){
							$log.log("Retrun discount = "+result.discount);
							if(result.discount >0 && result.discount <= 1){
								$scope.discountForPay[$index] = result.discount;
								$scope.golableDiscount = true;
							}else{
								$scope.golableDiscount = false;
							}
			
						 },function(response){
							  CommonService.unauthorizedCallBack(response);
						 });
				}
				$scope.changePayway = function (paymentHistory,$index) {
					var selectedPayway = $scope.getObjectFromArrayByColValue($scope.paywaysForPay[$index],"payway",paymentHistory.selectedPayway);
					if(!$scope.golableDiscount){
					 	$scope.discountForPay[$index] = selectedPayway.discount;
					}
				 	$log.log("Last discount= "+$scope.discountForPay[$index]);	
					$scope.paywayssForPay[$index] =  selectedPayway.payway;
					paymentHistory.payAmount = $scope.shouldPayMoney($index);
					paymentHistory.discount = $scope.discountForPay[$index];
					paymentHistory.pwayid = selectedPayway.id;//这个id 传给服务器
				};
				
				 $scope.checkfloat = function(obj) {
				    var reg = new RegExp("^[0-9]+(\.\[0-9]+)?$");  // ^-\d*\.{0,1}\d+$
			       if(!reg.test(obj.value)){
			           alert("请输入数字!");
			           obj.value = 0;
			       }
			    } 
				$scope.checkBalance = function($index){
					if($scope.useBalances[$index] == "" || $scope.useBalances[$index] == null)return;
					var reg = new RegExp("^[0-9]+(\.\[0-9]+)?$");  // ^-\d*\.{0,1}\d+$
			       if(!reg.test($scope.useBalances[$index])){
			           alert("请输入数字!");
			           $scope.useBalances[$index] = 0;
			       }else{
			       		var rest = $scope.student.accountBalance - $scope.useBalances.sum();
			       		if( rest >= 0){
			       				//if(rest < 0) rest = 0;
			       			  //alert("可使用余额为"+rest +", 请重新填入余额值。");
			         		 // $scope.useBalances[$index] = 0;
			       		}else{
			       			 alert("您使用的余额值不能够小于0.");
			         		  $scope.useBalances[$index] = 0;
			       		}
			       }
				}
				$scope.initChangeClass = function (paymentHistory,$index) {
					// $log.log(paymentHistory);
					// $scope.selectedClassToPay =  JSON.parse(obj.selectedClassToPay);
					var selectedClass = $scope.getObjectFromArrayByColValue($scope.student.clazzes,"className",paymentHistory.selectedClass);
					$scope.paywaysForPay[$index] = selectedClass.payways;
					$scope.payAmountForPay[$index] = selectedClass.payAmount;
					// paymentHistory.payAmount = selectedClass.payAmount;
				}
				$scope.initChangePayway = function(paymentHistory,$index){
						$scope.discountForPay[$index] = paymentHistory.discount;
						$scope.paywayssForPay[$index] =  paymentHistory.selectedPayway;
						// paymentHistory.payAmount = $scope.shouldPayMoney($index);
				}
				$scope.shouldPayMoney = function($index){
					return $scope.toDecimal($scope.payAmountForPay[$index]*$scope.discountForPay[$index]*$scope.getMultiFactor($scope.paywayssForPay[$index]));
				}
				$scope.getMultiFactor = function(payway){
					 if(payway == CommonService.discountInfo.values[0]){
						 return 1;
					 }else if(payway == CommonService.discountInfo.values[1]){
						 return 3;
					 }else if(payway == CommonService.discountInfo.values[2]){
						 return 6;
					 }else if(payway == CommonService.discountInfo.values[3]){
						 return 12;
					 }else{
					 	return 1;
					 }
				}
				 $scope.toDecimal = function(x) {  
			          var f = parseFloat(x);  
			          if (isNaN(f)) {  
			              return;  
			          }  
			          f = Math.round(x*100)/100;  
			          return f;  
			      }
			   
				 
				 $scope.selectAClass = function()
				 {

				 	 if($scope.student["clazzes"] == null){
						 $scope.student["clazzes"] = [];
					 }
					 // for(var sel in $scope.$$childTail.selected1 ){
					 for (var sel = 0; sel < $scope.$$childTail.selected1.length; sel++) {
						 // for(var ele in $scope.allClazzes){
						 for (var ele = 0; ele < $scope.allClazzes.length; ele++) {
					    		if($scope.allClazzes[ele].className == $scope.$$childTail.selected1[sel]){
					    			$scope.student.clazzes.push($scope.allClazzes[ele]);
					    			$scope.allClazzes.splice(ele,1);
					    		}
					    	}
					 }
				 }
				 
				 $scope.removeAClass = function()
				 {
					 // for(var sel in $scope.$$childTail.selected2 ){
						//  for(var ele in $scope.student.clazzes){

					 for (var sel = 0; sel < $scope.$$childTail.selected2.length; sel++) {
						 // for(var ele in $scope.allClazzes){
						 for (var ele = 0; ele < $scope.student.clazzes.length; ele++) {
					    		if($scope.student.clazzes[ele].className == $scope.$$childTail.selected2[sel]){
					    			 var r= confirm("确定删除该班级吗?");
		 							if(r == false)continue;
					    			$scope.allClazzes.push($scope.student.clazzes[ele]);
					    			$scope.student.clazzes.splice(ele,1);
					    		}
					    	}
					 }
				 } 
				 $scope.getColFromArray = function(array,colName) {
				 	if(array == null)return null;
				 	var cols = [];
				 	for (var i = 0; i < array.length; i++) {
				 		cols.push(array[i][colName]);
				 	}
				 	// var cols = $.map(array)[colName];
				 	return cols;
				 }
				 $scope.getObjectFromArrayByColValue = function(array,colName,colValue){
				 	for (var i = 0; i < array.length; i++) {
				 		if(array[i][colName] == colValue)
				 			return array[i];
				 	}
				 	return null;
				 }
				 $scope.checkStudentField = function(student){
				 	if(student['name'] == null){
				 		alert("请输入学生姓名");
				 		return false;
				 	}
				 	if(student['mobilePhone'] == null){
				 		alert("请输入联系电话");
				 		return false;
				 	}
				 	return true;
				 }
				 $scope.submit = function(){
					$scope.student["otherTelephone"] = $scope.otherTelephones.toString();
					if($scope.useBalances.length > 0 && !$.isEmptyObject(student.paymentHistorys)){
						for (var i = 0; i < student.paymentHistorys.length; i++) {
							if(isNaN($scope.useBalances[i])) continue;
							// student.paymentHistorys[i+$scope.initialPaymentHistorySize-2]["useBalance"] = $scope.useBalances[i];
							student.paymentHistorys[i]["useBalance"] = $scope.useBalances[i];
						};
					}	
					// JSON.stringify(j);
					// $log.log(JSON.stringify($scope.student));
					if($scope.checkStudentField($scope.student)){
						$modalInstance.close($scope.student);
					}
				}
				$scope.cancel = function(){
					$log.log("Click cancel");
					$modalInstance.dismiss('cancel');
				}
			}
);