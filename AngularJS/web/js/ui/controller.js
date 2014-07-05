MainController = function($scope,$location,UserService,$locale,$cookieStore,$http,CommonService){
//	 $scope.isSuccess = $location.search()['isSuccess']; 
//	 if( $scope.isSuccess == "undefined")
//		 $scope.isSuccess = true;
if($cookieStore.get("user") == null){
	$("#logout").show();
} else{
	var user = $cookieStore.get("user");
	if(user.type != 'normal'){
		$("#classLink").show();
		$("#userLink").show();
	}
	$("#logout").show();
	$("#mypageLink").show();
	$("#studentLink").show();
}
$scope.user=$cookieStore.get("user");
	 $scope.unauthorizedCallBack = function(response){
		 if(response.status == 401){
			 alert("登陆已超时，请重新登陆。");
			 $cookieStore.put("user") = null;
			 $location.path("/crm/login");
		 }else{
			 alert("未知系统错误，请重新登陆");
			 $cookieStore.put("user") = null;
			 $location.path("/crm/login");
		 }
	 }
}

ClassController = function($scope,$location,$filter,$http,ClassService,StudentService,$cookieStore,UserService,CommonService){
	if($cookieStore.get("user") == null){
		$("#logout").show();
//		alert("cookie is null"+$cookieStore.get("user"));
	} else{
		var user = $cookieStore.get("user");
		if(user.type != 'normal'){
			$("#classLink").show();
    		$("#userLink").show();
    		$("#studentLink").show();
		}
		$("#logout").show();
		$("#mypageLink").show();
	}
	
	$scope.lastIndex;
	$scope.showDetailsFun = function($index,clazz){
//		$scope.showDetails[$index] = !$scope.showDetails[$index] ;
		
		if($("#details") == null || $("#details").text() == ""){
			
		}else{
			
			$("#details").remove();
			if($scope.lastIndex == $index)return;
		}
		$scope.lastIndex = $index;
		var ele = "#clazzTable tr:eq("+($index+1)+")";
		
		var html = "<tr id='details'><td width='100%'' colspan='18'>";
		
		if(clazz.payways!=null &&  clazz.payways.length > 0 ){
			//payment history html
			var payhtml = "<fieldset style='clear:left'>";
			payhtml += "<legend>折扣信息</legend>";
			payhtml += "<table width='100%'>            			<thead>";
			payhtml +=	"<th width='25%'>付费方式</th><th width='25%'>折扣</th>";
			payhtml +=					"<th  width='25%'>创建日期</th>";
			payhtml +=					"<th  width='25%'>修改日期</th>";
			payhtml +=				"</thead>";
			payhtml +=				"<tbody>";
			// $scope.doesPaymentOverdue(student.paymentHistorys,$index);
			for(var idx in clazz.payways){
				payhtml += "<tr><td width='25%'>"+clazz.payways[idx].payway+"</td>";
				payhtml += "<td  width='25%'>"+clazz.payways[idx].discount+"</td>";
				payhtml += "<td  width='25%'>"+$scope.formatDate(clazz.payways[idx].createDate)+"</td>";
				payhtml += "<td  width='25%'>"+$scope.formatDate(clazz.payways[idx].modifyDate)+"</td>";
				payhtml += "</tr>";
			}
			payhtml += "</tbody>            	</table>";
			payhtml += "</fieldset>";
			html  += payhtml;
		}
		
		if(clazz.students != null && clazz.students.length > 0){
			var stuhtml =  "<fieldset style='clear:left'>";
			stuhtml += "					<legend>班级学生</legend>";
			stuhtml += "						<table width='100%'>";
			stuhtml += "<thead><th>姓名</th><th>年龄</th><th>联系人</th><th>联系电话</th><th>固定电话</th><th>创建日期</th><th>修改日期</th></thead>";
			stuhtml += "				<tbody>";
			
			for(var idx in clazz.students){
				stuhtml += "<tr>";
				stuhtml += "<td>"+clazz.students[idx].name+"</td>";
				stuhtml += "<td>"+clazz.students[idx].age+"</td>";
			//	stuhtml += "<td>"+clazz.students[idx].address+"</td>"; <th>地址</th>
				stuhtml += "<td>"+clazz.students[idx].contact+"</td>";
				stuhtml += "<td>"+clazz.students[idx].mobilePhone+"</td>";
				stuhtml += "<td>"+clazz.students[idx].telephone+"</td>";
				//stuhtml += "<td>"+clazz.students[idx].email+"</td>"; <th>email</th><th>客户状态</th> <th>数据来源</th>
				//stuhtml += "<td>"+clazz.students[idx].status+"</td>";
				//stuhtml += "<td>"+clazz.students[idx].dataFrom+"</td>";
	
				stuhtml += "<td>"+$scope.formatDate(clazz.students[idx].createDate)+"</td>";
				stuhtml += "<td>"+$scope.formatDate(clazz.students[idx].modifyDate)+"</td>";
				stuhtml += "</tr>";
			}
			stuhtml += "</tbody>            	</table>";
			stuhtml += "</fieldset>";
			html  += stuhtml;
		}
		
		 
		
		html = html+"</td></tr>";
//		console.log(html);
		$(ele).after(html);
	
		
		
	}
	
	$scope.unauthorizedCallBack = function(response){
		 if(response.status == 401){
			 alert("登陆已超时，请重新登陆。");
			 $cookieStore.put("user") = null;
			 $location.path("/crm/login");
		 }else{
			 alert("未知系统错误，请重新登陆");
			 $cookieStore.put("user") = null;
			 $location.path("/crm/login");
		 }
	 }
	$scope.clazzes;
	$scope.showDetails = [];
	$scope.modifys = false;
	$scope.id;
	$scope.className;
	$scope.classNotice;
	$scope.teacher;
	$scope.payAmount;
	$scope.modifyDate;
	$scope.createDate;
	$scope.students = []; /* one class's student*/
	$scope.allStudents = null; /*system's all students*/
	$scope.oldClassName;
	$scope.show = false; // show add student model
	
	  $scope.formatDate = function(time){
	    	if(time == null)return null;
	    	return $filter('date')(new Date(time), 'yyyy-MM-dd HH:mm:ss');
	    }
	  
	$scope.showAddClass = function(){
		$scope.show = !$scope.show;
		if($scope.show){
			if($scope.allStudents == null || $scope.allStudents == "undefined"){
				StudentService.getStudentList(function(result){
					if(result!= null){
						$scope.allStudents = result;
					} 
				},function(response){
					 $scope.unauthorizedCallBack(response);
				 });
			}
		}
	}
	
//	if($scope.allStudents == null){
//		StudentService.getStudentList(function(result){
//			if(result!= null){
//				$scope.allStudents = result;
//			} 
//		});
//	}
	
	 $scope.selectedStu1 = [];
	 $scope.selectedStu2 = [];
	 
	 $scope.selectAStudent = function()
	 {
		 for(var sel in $scope.selectedStu1 ){
			 for(var ele in $scope.allStudents){
		    		if($scope.allStudents[ele].name == $scope.selectedStu1[sel]){
//		    			if($scope.students == null) $scope.students = [];
		    			$scope.students.push($scope.allStudents[ele]);
		    			$scope.allStudents.splice(ele,1);
		    		}
		    	}
		 }
	 }
	 
	 $scope.removeAStudent = function()
	 {
		 for(var sel in $scope.selectedStu2 ){
			 for(var ele in $scope.students){
		    		if($scope.students[ele].name == $scope.selectedStu2[sel]){
		    			$scope.allStudents.push($scope.clazzes[ele]);
		    			$scope.students.splice(ele,1);
		    		}
		    	}
		 }
	 }
	
	if($scope.clazzes == null){
		 ClassService.getAllClassList(true,function(result){
			 if(result != null){
//		    		console.log(result);
		    		$scope.clazzes = result;
		    	}else{
		    		alert("获取班级列表失败");
		    	}
		 },function(response){
			 $scope.unauthorizedCallBack(response);
		 });
	}
	
	
	$scope.discountInfo = CommonService.discountInfo;
	$scope.payways = [];
	$scope.addPaywayPage = false;
	$scope.addPayways = function(){
		 if(!$scope.addPaywayPage){
			 $scope.addMorePaywayInfo();
			 $scope.addPaywayPage = true;
		 }else{
			 $scope.addPaywayPage = false; 
			 $scope.payways = [];
		 }
	}
	 $scope.addMorePaywayInfo = function(){
		 if($scope.payways == null){
			 $scope.payways = [];
		 }
		 $scope.payways.push({
			 payway:"",
			 discount:1,
			 createDate:null,
			 modifyDate:null
		 });
	 }
	 $scope.removePaywayHtml  = function($index){
		 $scope.payways.splice($index,1);
	 }
	$scope.addClazz = function(){
		var clazz = {
				className:$scope.className,
			  classNotice:$scope.classNotice,
		          teacher:$scope.teacher,
		        payAmount:$scope.payAmount,
	           modifyDate:$scope.modifyDate,
			   createDate:$scope.createDate,
			     students:$scope.students,
			     payways: $scope.payways
		};
		ClassService.addClazz(clazz,function(result){
			if(result.isSuccess){
				alert("增加班级成功");
				$scope.className = null;
				$scope.classNotice = null;
		        $scope.teacher = null;
		        $scope.payAmount = null;
	            $scope.modifyDate = null;
	   		    $scope.createDate = null;
		        $scope.students =null;
		        $scope.payways = [];
		        $scope.addPaywayPage = false; 
				ClassService.getAllClassList(true,function(result){
					 if(result != null){
//				    		console.log(result);
				    		$scope.clazzes = result;
				    	}else{
				    		alert("Get All class list is null");
				    	}
				 });
			  $scope.show = false;
			}else{
				alert("增加班级失败，详情： "+result.description);
			}
		},function(response){
			 $scope.unauthorizedCallBack(response);
		 });
	}
		
	$scope.modify = function(clazz){
		 	$scope.className   = clazz.className;
		    $scope.classNotice = clazz.classNotice;
	        $scope.teacher     = clazz.teacher;
	        $scope.payAmount   = clazz.payAmount;
            $scope.modifyDate  = clazz.modifyDate;
		    $scope.createDate  = clazz.createDate;
		    $scope.students    = clazz.students;
//		    $scope.oldClassName= clazz.className;
		    $scope.payways =  clazz.payways;
		    $scope.id = clazz.id;
		    $scope.modifys     = !$scope.modifys;
		    $scope.show = false;
		
		StudentService.getStudentList(function(result){
			 if(result != null){
//		    		console.log(result);
		    		//$scope.allClazzes = result;
		    		 for(var ele in  $scope.students){
		    			 for(var allEle in result){
		    				 if(result[allEle].name == $scope.students[ele].name ){
		    					 result.splice(allEle,1);
		    				 }
		    			 }
		    		 }
		    		 $scope.allStudents = result;
		    	}else{
		    		alert("Get All class list is null");
		    	}
		 },function(response){
			 $scope.unauthorizedCallBack(response);
		 });
		 
	}
	$scope.updateClazz = function(clazz){
		var clazz = {
				className:$scope.className,
			  classNotice:$scope.classNotice,
		          teacher:$scope.teacher,
		        payAmount:$scope.payAmount,
	           modifyDate:$scope.modifyDate,
			   createDate:$scope.createDate,
			     students:$scope.students,
			     payways:$scope.payways,
			     id:$scope.id
		};
		ClassService.updateClazz(  $scope.id,clazz,function(result){
			if(result.isSuccess){
				alert("更新班级成功");
				$scope.cancle();
				 ClassService.getAllClassList(true,function(result){
					 if(result != null){
//				    		console.log(result);
				    		$scope.clazzes = result;
				    	}else{
				    		alert("Get All class list is null");
				    	}
				 });
			}else{
				alert("更新班级失败，详情： "+result.description);
			}
		},function(response){
			 $scope.unauthorizedCallBack(response);
		 });
	}
	$scope.cancle = function(){
		$scope.id = null;
		$scope.className   = null;
	    $scope.classNotice = null;
        $scope.teacher     = null;
        $scope.payAmount   = 0;
        $scope.modifyDate  = null;
	    $scope.createDate  = null;
	    $scope.students    = [];
	    $scope.oldClassName= null;
		$scope.modifys     = !$scope.modifys;
		$scope.payways = [];
		$scope.show = false;
		StudentService.getStudentList(function(result){
			if(result!= null){
				$scope.allStudents = result;
			} 
		},function(response){
			 $scope.unauthorizedCallBack(response);
		 });
	}
	$scope.deleteClazz = function(clazz){
		var r=confirm("确实删除该班级吗？");
		if(r == true){
			ClassService.deleteClazz(clazz.id,function(result){
				if(result.isSuccess){
					alert("删除班级成功");
					 ClassService.getAllClassList(true,function(result){
						 if(result != null){
//					    		console.log(result);
					    		$scope.clazzes = result;
					    	}else{
					    		alert("Get All class list is null");
					    	}
					 });
				}else{
					alert("删除班级失败，详情： "+result.description);
				}
			},function(response){
				 $scope.unauthorizedCallBack(response);
			 });
		}
	}
}

StudentController = function($scope, $location,$filter,$http,StudentService,ClassService,$cookieStore,UserService,CommonService,$modal,$log){
	if($cookieStore.get("user") == null){
		$("#logout").show();
	} else{
		var user = $cookieStore.get("user");
		if(user.type != 'normal'){
			$("#classLink").show();
    		$("#userLink").show();
    		$("#studentLink").show();
		}	
		$("#logout").show();
		$("#mypageLink").show();
	}

	$scope.showDialog = function(){
		$scope.tempUrl = "";

		var modalInstance = $modal.open({
			templateUrl:'partials/student.detail.html',
			controller: function($scope,$modalInstance,$log){
				$scope.student = {};
				$scope.addPhone  = function(){
					alert("Add new phone");
				}
				$scope.addMorePhone = function(){
					alert("Add More phone");
				}
				$scope.removePhone = function($index){
					alert("remove $index phone");
				}
			}
		});
		modalInstance.result.then(function(){
			 $log.log("Modal dismissed at :"+new Date());
		},function(){
			$log.log("Modal dismissed at :"+new Date());
		});

	}

	$scope.useAdvanceSearch = false;
	$scope.lastIndex = null;
	$scope.test = function($index,student){
		if($("#details") == null || $("#details").text() == ""){
			
		}else{
			
			$("#details").remove();
			if($scope.lastIndex == $index)return;
		}
		$scope.lastIndex = $index;
		var ele = "#studentTable tr:eq("+($index+1)+")";
		
		var html = "<tr id='details'><td width='100%'' colspan='18'>";
		
		var baseinfo = "<fieldset style='clear:left'>";
		baseinfo += "<legend>基本信息</legend>";
		baseinfo += "<table width='100%'>  <thead>";
		baseinfo += "<th>生日</th><th>地址</th><th>固定电话</th><th>email</th><tbody>";
		baseinfo += "<tr>";
		baseinfo += "<td>"+$scope.formatDate(student.age)+"</td>";
		baseinfo += "<td>"+student.address+"</td>";
		baseinfo += "<td>"+student.telephone+"</td>";
		baseinfo += "<td>"+student.email+"</td>";
		baseinfo += "</tr>";
		baseinfo += "</tbody>            	</table>";
		baseinfo += "</fieldset>";
		html += baseinfo;
		if(student.paymentHistorys != null &&  student.paymentHistorys.length > 0){
			//payment history html
			var payhtml = "<fieldset style='clear:left'>";
			payhtml += "<legend>已有付款信息</legend>";
			payhtml += "<table width='100%'>            			<thead>";
			payhtml +=	"<th width='10%'>付款人</th><th width='10%'>付款金额</th><th width='10%'>收款人</th><th width='10%'>付款目的</th><th width='10%'>生效日期</th><th width='10%'>过期日期</th>";
			payhtml +=					"<th  width='10%'>是否过期</th><th  width='10%'>备注</th>";
			payhtml +=					"<th  width='10%'>付款日期</th>";
			payhtml +=					"<th  width='10%'>打印</th>";
			payhtml +=				"</thead>";
			payhtml +=				"<tbody>";
			// $scope.doesPaymentOverdue(student.paymentHistorys,$index);
			for(var idx in student.paymentHistorys){
				var payAmount = student.paymentHistorys[idx].payAmount;
				var purpose  = student.paymentHistorys[idx].purpose;
				var startDate  = student.paymentHistorys[idx].startDate;
				var endDate  = student.paymentHistorys[idx].endDate;
				var payDate  = student.paymentHistorys[idx].payDate;
				var payer  = student.paymentHistorys[idx].payer;
				var receiver  = student.paymentHistorys[idx].receiver;
				payhtml += "<tr><td>"+payer+"</td><td>"+payAmount+"</td><td>"+receiver+"</td>";
				payhtml += "<td>"+student.paymentHistorys[idx].purpose+"</td>";
				payhtml += "<td>"+student.paymentHistorys[idx].startDate+"</td>";
				payhtml += "<td>"+student.paymentHistorys[idx].endDate+"</td>";
				//payhtml += "<td ng-style=\""+$scope.doesPaymentOverdueStyle[$index]+"\">"+$scope.doesPaymentOverdue(student.paymentHistorys[idx],$index) +"</td>";
				payhtml += "<td>"+$scope.newdoesPaymentOverdue(student.paymentHistorys[idx],$index) +"</td>";
				payhtml += "<td>"+student.paymentHistorys[idx].remark+"</td>";
				payhtml += "<td>"+student.paymentHistorys[idx].payDate+"</td>";
				payhtml += "<td><button class='btn' onclick=\"return printFrame('printframe','"+payAmount+"','"+purpose+"','"+startDate+"','"+endDate+"','"+payDate+"','"+payer+"','"+receiver+"')\">打印收据</button></td>";
				payhtml += "</tr>";
			}
			payhtml += "</tbody>            	</table>";
			payhtml += "</fieldset>";
			html  += payhtml;
		}
		
		
		var commhtml =  "<fieldset style='clear:left'>";
		commhtml += "					<legend>历史跟踪记录</legend>";
		commhtml += "						<table width='100%'>";
		commhtml += "			<thead><th width='60%'>跟踪记录</th><th width='20%'>日期</th><th width='20%'></th></thead>";
		commhtml += "				<tbody>";
		if(student.communications != null && student.communications.length > 0){
			for(var idx in student.communications){
				commhtml += "<tr>";
				commhtml += "<td><textarea readonly>"+student.communications[idx].content+"</textarea></td>";
				commhtml +=	"<td>"+student.communications[idx].communicationDate+"</td>";
				commhtml += "<td></td>";
				commhtml += "</tr>";
			}
		}
		commhtml += "<tr><td><textarea id='commText'></textarea></td>";
		commhtml += "<td><input id='commDate' type='date'></input></td>";
		commhtml += "<td><button class=\"btn\" id='commCommit' onclick='addComm("+student.id+")'>提交</button></td></tr>";
		commhtml += "</tbody> </table>";
		commhtml += "</fieldset>";
		html  += commhtml;
	 
		if(student.clazzes != null && student.clazzes.length > 0){
			var classhtml = "<fieldset  style='clear:left'>";
			classhtml += "<legend>班级信息</legend>";
			classhtml += "<table width='100%'>";
			classhtml += "<thead>";
			classhtml += "<tr>";
			classhtml += "			<th>班级名字</th>";
			classhtml += "			<th width='30%'>班级通知</th>";
			classhtml += "			<th>班主任</th>";
			classhtml += "			<th>学费</th>";
			classhtml += "			<th>最后修改日期</th>";
			classhtml += "			<th>创建日期</th>";
			classhtml += "		</tr>";
			classhtml += "	</thead>";
			classhtml += "	<tbody>";
			for(var idx in student.clazzes){
				classhtml += "<tr>";
				classhtml += "<td>"+student.clazzes[idx].className+"</td>";
				classhtml += "<td width='30%''> <textarea readonly style='width:80%'> "+student.clazzes[idx].classNotice+"</textarea></td>";
				classhtml += "<td>"+student.clazzes[idx].teacher+"</td>";
				classhtml += "<td>"+student.clazzes[idx].payAmount+"</td>";
				classhtml += "<td>"+$scope.formatTime(student.clazzes[idx].modifyDate)+"</td>";
				classhtml += "<td>"+$scope.formatTime(student.clazzes[idx].createDate)+"</td>";
				classhtml += "</tr>";
			}
			classhtml += "</tbody>            	</table>";
			classhtml += "</fieldset>";
			html  += classhtml;
		}
		
		html = html+"</td></tr>";
//		console.log(html);
		$(ele).after(html);
	}
	$scope.checkStudent = function(){
		 if($scope.name== null|| $scope.mobilePhone == null ){
			 alert("请先输入学生名字和手机号");
			 return;
		 }
		 var param = {
				 	action:'doesExistStudent',
					name:$scope.name,
					mobilePhone:$scope.mobilePhone
			}
		 CommonService.putRequest(param,function(result){
			if(result.isSuccess){//该学生已经存在了
				alert(result.description);
				return ;
			}else{
				alert("该学生不存在");
			}
		 },function(response){
			 $scope.unauthorizedCallBack(response);
		 });
	 }
	 $scope.age2;
	$scope.serachByDateRang = function(){
		if($scope.age != null &&  $scope.age > $scope.age2){
			alert("日期区间设置错误");
			return;
		}
		 var param = {
				 	action:'searchStudent',
				 	name:$scope.name,
					 sex:$scope.sex,
					 date1:$scope.age,
					 date2:$scope.age2,
			     contact:$scope.contact,
			 mobilePhone:$scope.mobilePhone,
			      status:$scope.status,
			    dataFrom:$scope.dataFrom,
			    doesBill:$scope.doesBill
			}
		 CommonService.postRequest(param,function(result){
			if(result != null){//该学生已经存在了
//				console.log(result);
				for(var stuIndex in result){
		    		for(var index in result[stuIndex].paymentHistorys){
		    			var startDate = result[stuIndex].paymentHistorys[index].startDate ;
		    			result[stuIndex].paymentHistorys[index].startDate = $filter('date')(new Date(startDate), 'yyyy-MM-dd');
		    			
		    			var endDate = result[stuIndex].paymentHistorys[index].endDate ;
		    			result[stuIndex].paymentHistorys[index].endDate = $filter('date')(new Date(endDate), 'yyyy-MM-dd');
		    			
		    			var payDate = result[stuIndex].paymentHistorys[index].payDate ;
		    			result[stuIndex].paymentHistorys[index].payDate = $filter('date')(new Date(payDate), 'yyyy-MM-dd');
	//	    			return $filter('date')(new Date(time), 'yyyy-MM-dd HH:mm:ss');
		    		}
		    		
		    		for(var index in result[stuIndex].communications){
		    			var commdate = result[stuIndex].communications[index].communicationDate;
		    			if(commdate == null)
		    				result[stuIndex].communications[index].communicationDate =  $filter('date')(new Date(), 'yyyy-MM-dd');
		    			else
		    				result[stuIndex].communications[index].communicationDate =  $filter('date')(new Date(commdate), 'yyyy-MM-dd');
		    		}
	    		}
	    		$scope.students = result;
				return ;
			}
		 },function(response){
			 $scope.unauthorizedCallBack(response);
		 });
	}
	
	$scope.selectedClassToPay; //绑定选择班级的 select
	$scope.selectedPayway;  // 绑定付款方式 select
	$scope.paywaysForPay = []; // 这是一个数组
	$scope.payAmountForPay = [];//标准学费的一个数组
	$scope.discountForPay = [];//折扣数组
	$scope.paywayssForPay = []; //获得当前的付款方式，以便计算付款总额， 总额等于 学费×折扣×月份数
	
	$scope.changeClass = function (obj,$index) {
		$scope.selectedClassToPay =  JSON.parse(obj.selectedClassToPay);
		$scope.paywaysForPay[$index] = $scope.selectedClassToPay.payways;
		$scope.payAmountForPay[$index] = $scope.selectedClassToPay.payAmount;
		$scope.paymentHistorys[$index].payAmount = $scope.selectedClassToPay.payAmount;
//		console.log($scope.paywaysForPay);
	};
	
	$scope.changePayway = function (obj,$index) {
		$scope.selectedPayway=  JSON.parse(obj.selectedPayway);
		$scope.discountForPay[$index] = $scope.selectedPayway.discount;
		$scope.paywayssForPay[$index] =  $scope.selectedPayway.payway;
//		console.log($scope.discountForPay);
		$scope.paymentHistorys[$index].payAmount = $scope.shouldPayMoney($index);
		$scope.paymentHistorys[$index].pwayid = $scope.selectedPayway.id;//这个id 传给服务器
	};
	
	$scope.shouldPayMoney = function($index){
		return $scope.toDecimal($scope.payAmountForPay[$index]*$scope.discountForPay[$index]*$scope.getMultiFactor($scope.paywayssForPay[$index]));
	}
	$scope.getMultiFactor = function(payway){
		 if(payway == CommonService.discountInfo[0].name){
			 return 1;
		 }else if(payway == CommonService.discountInfo[1].name){
			 return 3;
		 }else if(payway == CommonService.discountInfo[2].name){
			 return 6;
		 }else if(payway == CommonService.discountInfo[3].name){
			 return 12;
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
	
	$scope.unauthorizedCallBack = function(response){
		 if(response.status == 401){
			 alert("登陆已超时，请重新登陆。");
			 $cookieStore.put("user") = null;
			 $location.path("/crm/login");
		 }else{
			 alert("未知系统错误，请重新登陆");
			 $cookieStore.put("user") = null;
			 $location.path("/crm/login");
		 }
	 }
	 
	// $scope.result = $location.search();//['isSuccess']; 
	 $scope.id;
	 $scope.name ;
	 $scope.sex ;
	 $scope.age ;
	 $scope.address ;
	 $scope.contact ;
	 $scope.mobilePhone ;
	 $scope.telephone ;
	 $scope.email ;
	 $scope.status ;
	 $scope.dataFrom ;
	 $scope.doesBill ;
	 $scope.createDate ;
	 $scope.modifyDate ;
	 $scope.students;
	 $scope.oldName;
	 $scope.modifys=false;
	 $scope.addpaypage = false;
	 $scope.addcomm = false;
	 $scope.show = false;
	 
	 $scope.computeAge = function(age){
		 var date =  new Date();
		 var borndate = new Date(age);
		 return parseInt((date-borndate)/(24*60*60*1000*365));// return the student age 
	 }
	 
	 $scope.printFrame = function(frameId, $index,idx)
	    {
	        var doc = $('#'+frameId).get(0).contentWindow.document;
	        var $body = $('body',doc);
	        var payment = students[$index].paymentHistorys[idx];
	        $body.html('<p style="color:red;position:relative;left:100px;top:50px;">'+payment.payer+'</p><p style="color:red;position:relative;left:100px;top:50px;">'+payment.purpose+'</p><p style="color:red;position:relative;left:100px;top:50px;">'+payment.payAmount+'</p><p style="color:red;position:relative;left:100px;top:50px;">'+payment.receiver+'</p>');

	        setTimeout(function(){
	            $('#'+frameId).get(0).contentWindow.focus();
	            $('#'+frameId).get(0).contentWindow.print();
	        }, 1000);
	    }
	 
	 $scope.searchText;
	 $scope.filterByString = function(){
		if($scope.searchText != null){
			$scope.searchText = $scope.searchText.replace("男","male");
			$scope.searchText = $scope.searchText.replace("女","female");
			$scope.searchText = $scope.searchText.replace("是","true");
			$scope.searchText = $scope.searchText.replace("否","false");
		}
	 }
	 
	 $scope.formatSex = function(sex){
		 return sex=="male"?"男":"女";
	 }
	 $scope.formatDoesBill = function(doesBill){
		 return doesBill ? "是":"否";
	 }
	 
	 $scope.showAddStudent = function(){
		 $scope.show = !$scope.show;
		 $scope.modifys = false;
		 $scope.cancle();
	 }
	 
	 
	 
	 $scope.showCommunications = function(student){
		 if(student.communications != null && student.communications.length > 0){
			 return student.communications[0].content;
		 }else{
			 return "无跟踪记录";
		 }
	 }
	 
	 $scope.doesOverdueStyle = [];
	 $scope.doesOverdue = function(student,$index){
		 if(student.paymentHistorys == null || student.paymentHistorys.length == 0){
			 return "无付款记录";
		 }
		 var overdue = new Date(2030,1,1).getTime();
		 var doesreminder = false;
		 for(var index in student.paymentHistorys){
			 if(student.paymentHistorys[index].doesNeedReminder){
				 doesreminder = true;
				 if(student.paymentHistorys[index].endDate < overdue){
					 overdue = student.paymentHistorys[index].endDate; //取最小的过期日期
				 }
				 var overdueDate = new Date(student.paymentHistorys[index].endDate) 
				 var nowDate = new Date();
				 if(overdueDate < nowDate ){
					 $scope.doesOverdueStyle[$index] = {color:'red'};
					 return "已过期:"+student.paymentHistorys[index].endDate;
				 }else{
					 $scope.doesOverdueStyle[$index] = {};
				 }
			 }
		 }
		 if(doesreminder){ // $filter('date')(new Date(), 'yyyy-MM-dd');
//			 doesOverdueStyle = {color:'red'};
			 return student.paymentHistorys[index].endDate;
//			 return $filter('date')(new Date(overdue), 'yyyy-MM-dd');
		 }
		 else
			 return "没有设置过期提醒";
		 
	 }
	 
	 $scope.doesPaymentOverdueStyle = [];
	 $scope.doesPaymentOverdue = function(paymentHistory,$index){
		 if(paymentHistory.doesNeedReminder){
			 var overdueDate = new Date(paymentHistory.endDate) 
			 var nowDate = new Date();
			 if(overdueDate < nowDate ){
				 $scope.doesPaymentOverdueStyle[$index] = {color:'red'};
				 return "已过期";
			 }else{
				 $scope.doesPaymentOverdueStyle[$index] = {};
				return  $filter('date')(new Date(paymentHistory.endDate), 'yyyy-MM-dd');//new Date( paymentHistory.endDate).toLocaleString();
			 }
		 }else{
			return "本付款信息没有设置过期提醒";
		 }
	 }
	 $scope.newdoesPaymentOverdue = function(paymentHistory,$index){
		 if(paymentHistory.doesNeedReminder){
			 var overdueDate = new Date(paymentHistory.endDate) 
			 var nowDate = new Date();
			 if(overdueDate < nowDate ){
				 return "<font color='red'>已过期</font>";
			 }else{
				 return "没有过期";
//				return  $filter('date')(new Date(paymentHistory.endDate), 'yyyy-MM-dd');//new Date( paymentHistory.endDate).toLocaleString();
			 }
		 }else{
			return "未设置过期提醒";
		 }
	 }
	 
	 $scope.allClazzes = null; /* all class from server side */
	 
	 if( $scope.allClazzes == null){
		 ClassService.getAllClassList('false',function(result){
			 if(result != null){
//		    		console.log(result);
		    		$scope.allClazzes = result;
		    	}else{
		    		alert("Get All class list is null");
		    	}
		 },function(response){
			 $scope.unauthorizedCallBack(response);
		 });
	 }
	 
	 
	 $scope.clazzes = [];
	 $scope.paymentHistorys = [];
	 $scope.communications = [];
	 $scope.showDetails = [];
	 
	 $scope.selected1 = [];
	 $scope.selected2 = [];
	 
	 $scope.selectAClass = function()
	 {
		 for(var sel in $scope.selected1 ){
			 for(var ele in $scope.allClazzes){
		    		if($scope.allClazzes[ele].className == $scope.selected1[sel]){
		    			$scope.clazzes.push($scope.allClazzes[ele]);
		    			$scope.allClazzes.splice(ele,1);
		    		}
		    	}
		 }
	 }
	 
	 $scope.removeAClass = function()
	 {
		 for(var sel in $scope.selected2 ){
			 for(var ele in $scope.clazzes){
		    		if($scope.clazzes[ele].className == $scope.selected2[sel]){
		    			$scope.allClazzes.push($scope.clazzes[ele]);
		    			$scope.clazzes.splice(ele,1);
		    		}
		    	}
		 }
	 }
	 
	 $scope.showDetailsFun = function($index){
		 $scope.showDetails[$index] = !$scope.showDetails[$index];
	 }
	 
	 $scope.addComm = function(){
		 if(!$scope.addcomm){
			 $scope.addMoreCommInfo();
			 $scope.addcomm = true;
		 }else{
			 $scope.addcomm = false; 
			 $scope.communications = [];
		 }
	 }
	 $scope.addMoreCommInfo = function(){
		 if($scope.communications == null)
			 $scope.communications = [];
		 $scope.communications.push({
			 content:null,
			 communicationDate:null
		 });
	 }
	 
	 $scope.removeCommHtml = function($index){
		 $scope.communications.splice($index,1);
	 }
	 
	 $scope.addPay = function(){
		 if(!$scope.addpaypage){
			 $scope.addMorePayInfo();
			 $scope.addpaypage = true;
		 }else{
			 $scope.addpaypage = false; 
			 $scope.paymentHistorys = [];
		 }
	 }
	 $scope.addMorePayInfo = function(){
		 if($scope.paymentHistorys == null){
			 $scope.paymentHistorys = [];
		 }
		 $scope.paymentHistorys.push({
			 payAmount:0,
			 purpose:"",
			 doesNeedReminder:true,
			 startDate:null,
			 endDate:null,
			 remark:null,
		 	 payDate:null,
		 	 pwayid:null
		 });
	 }
	 
	 $scope.removePayHtml = function($index){
		 $scope.paymentHistorys.splice($index,1);
//		 if($scope.paymentHistorys.length  == 0){
//			 
//		 }
	 }
	 
	 $scope.addStudent = function(){
		 var student = {
				name:$scope.name,
				 sex:$scope.sex,
				 age:$scope.age,
		 	 address:$scope.address,
		     contact:$scope.contact,
		 mobilePhone:$scope.mobilePhone,
		   telephone:$scope.telephone,
		       email:$scope.email,
		      status:$scope.status,
		    dataFrom:$scope.dataFrom,
		    doesBill:$scope.doesBill,
	 paymentHistorys:$scope.paymentHistorys,
	  communications:$scope.communications,
	  		 clazzes:$scope.clazzes,
	  		 uid:$cookieStore.get("user").id
		 }
		 StudentService.addStudent(student,function(result){
	    		if(result.isSuccess){
	    			alert("成功添加学生");
	    			 $scope.cancle();
	    			 $scope.getStudentList();
					 ClassService.getAllClassList('false',function(result){
						 if(result != null){
					    		$scope.allClazzes = result;
					    	}else{
					    		alert("Get All class list is null");
					    	}
					 },function(response){
						 $scope.unauthorizedCallBack(response);
					 });
					 $scope.show = false;
	    		}else{
		    		alert("添加学生失败，详情： "+result.description);
		    		 //$location.path("/crm/main").search({'isSuccess':result.isSuccess});
		    	}
	    	},function(response){
				 $scope.unauthorizedCallBack(response);
			 });
	 }
	 
	 
	 $scope.deleteStudent = function(student){
		 var r=confirm("确定删除该学生吗?");
		 if(r == true){
			 StudentService.deleteStudent(student.id,function(result){
				 if(result.isSuccess){
					 alert("删除学生成功");
					 ClassService.getAllClassList('false',function(result){
						 if(result != null){
//					    		console.log(result);
					    		$scope.allClazzes = result;
					    		 $scope.getStudentList();
					    	}else{
					    		alert("Get All class list is null");
					    	}
					 },function(response){
						 $scope.unauthorizedCallBack(response);
					 });
				 }else{
					 alert("删除学生失败，详情： "+result.description);
				 }
			 },function(response){
				 $scope.unauthorizedCallBack(response);
			 })
		 }
	 }
	  
	    $scope.formatTime = function(time){
	    	if(time == null)return null;
	    	return $filter('date')(new Date(time), 'yyyy-MM-dd HH:mm:ss');//date.toLocaleString();
	    }
	    $scope.formatDate = function(time){
	    	if(time == null)return null;
	    	return $filter('date')(new Date(time), 'yyyy-MM-dd');
	    }
	 
	   $scope.oldPaymentHistorys;
	   $scope.oldCommunications;
	   $scope.uid;
	 $scope.modify = function(student){
		 $scope.name = student.name ;
		 $scope.sex = student.sex ;
		 $scope.age = student.age;
		 $scope.address = student.address;
		 $scope.contact = student.contact;
		 $scope.mobilePhone = student.mobilePhone;
		 $scope.telephone  = student.telephone;
		 $scope.email  = student.email;
		 $scope.status = student.status;
		 $scope.dataFrom  = student.dataFrom;
		 $scope.doesBill  = student.doesBill;
		 $scope.createDate  = student.createDate;
		 $scope.oldName = student.name;
//		 $scope.paymentHistorys = student.paymentHistorys;
		 $scope.oldPaymentHistorys = student.paymentHistorys;//这是老的pay信息
//		 $scope.communications = student.communications;
		  $scope.oldCommunications = student.communications; //历史跟踪信息
		 $scope.clazzes = student.clazzes;
		 $scope.id = student.id;
		 $scope.uid = student.uid;
		 
		 if($cookieStore.get("user") == null || $cookieStore.get("user") == "undefined"){
			 alert("系统参数丢失，请重新登陆");
 			 $location.path("/crm/login");
		 }
		 if($cookieStore.get("user").type == "super" || student.uid == $cookieStore.get("user").id){
			 
		 }else{
			 $('#name').attr('readonly', true);
			 $('#mobilePhone').attr('readonly', true);
		 }
		 
		 ClassService.getAllClassList('false',function(result){
			 if(result != null){
//		    		console.log(result);
		    		 for(var ele in $scope.clazzes){
		    			 for(var allEle in result){
		    				 if(result[allEle].className == $scope.clazzes[ele].className ){
		    					 result.splice(allEle,1);
		    				 }
		    			 }
		    		 }
		    		 $scope.allClazzes = result;
		    	}else{
		    		alert("Get All class list is null");
		    	}
		 },function(response){
			 $scope.unauthorizedCallBack(response);
		 });
		 
		 $scope.modifys = !$scope.modifys;
		 $scope.show = false;
//		 console.log( $scope.allClazzes.length);
	    }
	    $scope.cancle= function(){
	    	$scope.id = null;
	    	 $scope.name = null ;
	    	 $scope.sex  = null;
	    	 $scope.age  = null ;
	    	 $scope.address = null;
	    	 $scope.contact = null;
	    	 $scope.mobilePhone= null ;
	    	 $scope.telephone= null ;
	    	 $scope.email = null;
	    	 $scope.status = null;
	    	 $scope.dataFrom = null;
	    	 $scope.doesBill = null;
	    	 $scope.createDate = null ;
	    	 $scope.modifyDate= null ;
	    	 $scope.oldName = null;
	    	 $scope.paymentHistorys = [];
			 $scope.communications = [];
			 $scope.clazzes = [];
	    	 $scope.modifys = false;
	    	 $scope.uid = null;
	    	 ClassService.getAllClassList('false',function(result){
				 if(result != null){
//			    		console.log(result);
			    		$scope.allClazzes = result;
			    	}else{
			    		alert("Get All class list is null");
			    	}
			 },function(response){
				 $scope.unauthorizedCallBack(response);
			 });
	    } 
	    
	 
	 $scope.$watch('confirmed1', function(newValue, oldValue) {
//	        console.log("old value: %s and new value: %s", oldValue, newValue);
	        $scope.getStudentList();
	    });
	 $scope.updateStudent = function(){
		 var student = {
				 	  id:$scope.id,
					name:$scope.name,
					 sex:$scope.sex,
					 age:$scope.age,
			 	 address:$scope.address,
			     contact:$scope.contact,
			 mobilePhone:$scope.mobilePhone,
			   telephone:$scope.telephone,
				   email:$scope.email,
			      status:$scope.status,
			    dataFrom:$scope.dataFrom,
			    doesBill:$scope.doesBill,
	    	  createDate:$scope.createDate,
		 paymentHistorys:$scope.paymentHistorys,
		  communications:$scope.communications,
		  			 uid:$scope.uid,
			     clazzes:$scope.clazzes 
			 }
	    	StudentService.updateStudent($scope.id,student,function(result){
	    		if(result.isSuccess){
	    			alert("更新学生成功");
	    			 $scope.modifys = false;
	    			 $scope.cancle();
	    			$scope.getStudentList();
	    		}else{
		    		alert("更新学生失败，详情： "+result.description);
		    	}
	    	},function(response){
				 $scope.unauthorizedCallBack(response);
			 });
	    }
	 $scope.getStudentList = function(){
		 StudentService.getStudentList(function(result){
    		if(result != null){
//	    		console.log(result);
	    		for(var stuIndex in result){
		    		for(var index in result[stuIndex].paymentHistorys){
		    			var startDate = result[stuIndex].paymentHistorys[index].startDate ;
		    			result[stuIndex].paymentHistorys[index].startDate = $filter('date')(new Date(startDate), 'yyyy-MM-dd');
		    			
		    			var endDate = result[stuIndex].paymentHistorys[index].endDate ;
		    			result[stuIndex].paymentHistorys[index].endDate = $filter('date')(new Date(endDate), 'yyyy-MM-dd');
		    			
		    			var payDate = result[stuIndex].paymentHistorys[index].payDate ;
		    			result[stuIndex].paymentHistorys[index].payDate = $filter('date')(new Date(payDate), 'yyyy-MM-dd');
	//	    			return $filter('date')(new Date(time), 'yyyy-MM-dd HH:mm:ss');
		    		}
		    		
		    		for(var index in result[stuIndex].communications){
		    			var commdate = result[stuIndex].communications[index].communicationDate;
		    			if(commdate == null)
		    				result[stuIndex].communications[index].communicationDate =  $filter('date')(new Date(), 'yyyy-MM-dd');
		    			else
		    				result[stuIndex].communications[index].communicationDate =  $filter('date')(new Date(commdate), 'yyyy-MM-dd');
		    		}
	    		}
	    		$scope.students = result;
	    	}else{
	    		alert("Get student list is null");
	    	}
    	},function(response){
			 $scope.unauthorizedCallBack(response);
		 });
    
    };
};
UserController = function ($scope, $routeParams,$location, $filter,$http,UserService,$cookieStore,CommonService) {
	if($cookieStore.get("user") == null){
		//$("#logout").show();
	} else{
		var user = $cookieStore.get("user");
		if(user.type != 'normal'){
			$("#classLink").show();
    		$("#userLink").show();
		}
		$("#logout").show();
		$("#mypageLink").show();
		$("#studentLink").show();
	}
	$scope.allMoney = function(pays){
		var money = 0.0;
		for(var index in pays){
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
			 $scope.unauthorizedCallBack(response);
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
			 $scope.unauthorizedCallBack(response);
		 });
	}
	$scope.unauthorizedCallBack = function(response){
		 if(response.status == 401){
			 alert("登陆已超时，请重新登陆。");
			 $cookieStore.put("user") = null;
			 $location.path("/crm/login");
		 }else{
			 alert("未知系统错误，请重新登陆");
			 $cookieStore.put("user") = null;
			 $location.path("/crm/login");
		 }
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
		 
	    $scope.types = [
	                    {name:'超级管理员', value:'super'},
	                     {name:'管理员', value:'admin'},
	                     {name:'普通用户', value:'normal'}
	                   ];
	    $scope.statuses = [
	                     {name:'可用', value:'useable'},
	                     {name:'禁用', value:'unuseable'}
	                   ];
	   
	    $scope.seek = function(value,arrays){
	    	for(var ele in arrays){
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
		    	
		    		 if(result.description.type=="normal"){
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
	    		 $scope.unauthorizedCallBack(response);
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
		    		 $scope.unauthorizedCallBack(response);
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
	    		console.log(response);// just log it 
	    	});
	    
	    };
	    
	    $scope.$watch('confirmed1', function(newValue, oldValue) {
//	        console.log("old value: %s and new value: %s", oldValue, newValue);
	    	 $scope.getUserList();
	    });
	    $scope.modify = function(user){
	    	$scope.username = user.username;
		    $scope.password = user.password;
		    $scope.type = user.type;
			$scope.status = user.status;
			$scope.createDate = user.createDate;
			$scope.id = user.id;
			$scope.modifys = !$scope.modifys;
//			var typeHtml = "";
//			for(var index in  $scope.types){
//				var t =  $scope.types[index];
//				if(t.value == user.type)
//					typeHtml = typeHtml+'<option value="'+t.value+'" selected="selected">'+t.name+'</option>';
//				else
//					typeHtml = typeHtml+'<option value="'+t.value+'">'+t.name+'</option>';	
//			 }
//			$('#type').html(typeHtml);
			$("#type").val(user.type);
//			var statusHtml = "";
//			for(var index in  $scope.statuses){
//				var t = $scope.statuses[index];
//				if(t.value == user.status)
//					statusHtml = statusHtml+'<option value="'+t.value+'" selected="selected">'+t.name+'</option>';
//				else
//					statusHtml = statusHtml+'<option value="'+t.value+'">'+t.name+'</option>';	
//			 }
//			$('#status').html(statusHtml);
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
	    		 $scope.unauthorizedCallBack(response);
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
};