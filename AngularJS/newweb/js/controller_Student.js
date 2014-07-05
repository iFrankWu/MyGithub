app.controller("StudentController",function($scope,$compile,$location,$filter,$http,StudentService,ClassService,$cookieStore,UserService,CommonService,$modal,$log){
	$scope.userName;
	if($cookieStore.get("user") == null){
		$("#logout").show();
	} else{
		var user = $cookieStore.get("user");
		$scope.userName =user.username;
		if(user.type != CommonService.types.values[2]){
			$("#classLink").show();
    		$("#userLink").show();
    		$("#studentLink").show();
		}	
		$("#logout").show();
		$("#mypageLink").show();
	}

	$scope.updateStudentDialog = function(student){
		$scope.tempUrl = "";
		$scope.student = student;
		$scope.modifys  = true;
		var modalInstance = $modal.open({
			templateUrl:'partials/student.detail.html',
			windowClass:'mystudent-dialog',
			controller:'StudentCURDController',
			resolve:{
				student:function(){
					return $scope.student;
				},
				modifys : function(){
					return $scope.modifys;
				}
			}
		});
		modalInstance.result.then(function(student){
			 $log.log("Modal dismissed at :"+new Date());
			 $log.log(student);
			  StudentService.updateStudent(student['id'],student,function(result){
	    		if(result.isSuccess){
	    			alert("更新学生成功");
	    			 $scope.modifys = false;
	    			 $scope.cancle();
	    			// $scope.getStudentList();
	    			$scope.getTopPage(1);
	    		}else{
		    		alert("更新学生失败，详情： "+result.description);
		    	}
	    	},function(response){
				 CommonService.unauthorizedCallBack(response);
			 });
		},function(){
			$log.log("Modal dismissed at :"+new Date());
		});
	}

	$scope.addStudentDialog = function(){
		$scope.tempUrl = "";
		$scope.student = {};
		$scope.modifys = false;
		var modalInstance = $modal.open({
			templateUrl:'partials/student.detail.html',
			windowClass: 'mystudent-dialog',
			controller: 'StudentCURDController',
			resolve:{
				student:function(){
					return $scope.student;
				},
				modifys : function(){
					return $scope.modifys;
				}
			}
		});
		modalInstance.result.then(function(student){
			 $log.log("Modal dismissed at :"+new Date());
			 student["uid"] = $cookieStore.get("user").id
			  StudentService.addStudent(student,function(result){
	    		if(result.isSuccess){
	    			alert("成功添加学生");
	    			 $scope.cancle();
	    			 // $scope.getStudentList();
	    			 $scope.getTopPage(1);
					 ClassService.getAllClassList('false',function(result){
						 if(result != null){
					    		$scope.allClazzes = result;
					    	}else{
					    		alert("Get All class list is null");
					    	}
					 },function(response){
						 CommonService.unauthorizedCallBack(response);
					 });
					 $scope.show = false;
	    		}else{
		    		alert("添加学生失败，详情： "+result.description);
		    		 //$location.path("/crm/main").search({'isSuccess':result.isSuccess});
		    	}
	    	},function(response){
				  CommonService.unauthorizedCallBack(response);
			 });
		},function(){
			$log.log("Modal dismissed at :"+new Date());
		});

	}

	$scope.useAdvanceSearch = false;
	$scope.lastIndex = null;
	$scope.users = null;
	if($scope.users == null){
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
	}
	$scope.getUserNameById = function(uid){
			for (var i = 0; i < $scope.users.length; i++) {
				if($scope.users[i]['id'] == uid ){
					return $scope.users[i]['username'];
				}
			};
	}
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
		baseinfo += "<th>创建人</th><th>年龄</th><th>地址</th><th>固定电话</th><th>email</th><th>qq</th><th>学校</th><th>联系电话</th><th>余额</th><th>备注</th><tbody>";
		baseinfo += "<tr>";
		baseinfo += "<td>"+$scope.getUserNameById(student.uid)+"</td>";
		baseinfo += "<td>"+$scope.formatDate(student.age)+"</td>";
		baseinfo += "<td>"+student.address+"</td>";
		baseinfo += "<td>"+student.telephone+"</td>";
		baseinfo += "<td>"+student.email+"</td>";
		baseinfo += "<td>"+student.qq+"</td>";
		baseinfo += "<td>"+student.school+"</td>";
		baseinfo += "<td>"+student.otherTelephone+"</td>";
		baseinfo += "<td>"+student.accountBalance+"</td>";
		baseinfo += "<td>"+student.remark+"</td>";
		baseinfo += "</tr>";
		baseinfo += "</tbody>            	</table>";
		baseinfo += "</fieldset>";
		html += baseinfo;
		if(student.paymentHistorys != null &&  student.paymentHistorys.length > 0){
			//payment history html
			var payhtml = "<fieldset style='clear:left'>";
			payhtml += "<legend>已有付款信息</legend>";
			payhtml += "<table width='100%'>            			<thead>";
			payhtml +=	"<th>总金额</th><th>余额支付</th><th >其他方式支付</th><th>折扣</th><th>收款人</th><th>付款目的</th><th >班级</th> <th >方式</th>  <th >生效日期</th><th >过期日期</th>";
			payhtml +=					"<th  >过期</th><th >备注</th>";
			payhtml +=					"<th >日期</th>";
			payhtml +=					"<th  >打印</th>";
			payhtml +=				"</thead>";
			payhtml +=				"<tbody>";
			// $scope.doesPaymentOverdue(student.paymentHistorys,$index);
			for (var idx = 0; idx < student.paymentHistorys.length; idx++) {
				var payAmount = student.paymentHistorys[idx].payAmount;
				var purpose  = student.paymentHistorys[idx].purpose;
				var startDate  = student.paymentHistorys[idx].startDate;
				var endDate  = student.paymentHistorys[idx].endDate;
				var payDate  = student.paymentHistorys[idx].payDate;
				var payer  = student.paymentHistorys[idx].payer;
				var receiver  = student.paymentHistorys[idx].receiver;
				payhtml += "<tr><td>"+payAmount+"</td>";
			
				payhtml +=  "<td>"+student.paymentHistorys[idx].useBalance+"</td>";
				payhtml += "<td>"+(payAmount-student.paymentHistorys[idx].useBalance)+"("+student.paymentHistorys[idx].cashOrCard+")</td>";
				payhtml +=  "<td>"+student.paymentHistorys[idx].discount+"</td>";
				payhtml +=  "<td>"+receiver+"</td>";
				payhtml += "<td>"+student.paymentHistorys[idx].purpose+"</td>";

				payhtml += "<td>"+student.paymentHistorys[idx].selectedClass+"</td>";
				payhtml += "<td>"+student.paymentHistorys[idx].selectedPayway+"</td>";
				

				payhtml += "<td>"+student.paymentHistorys[idx].startDate+"</td>";
				payhtml += "<td>"+student.paymentHistorys[idx].endDate+"</td>";
				//payhtml += "<td ng-style=\""+$scope.doesPaymentOverdueStyle[$index]+"\">"+$scope.doesPaymentOverdue(student.paymentHistorys[idx],$index) +"</td>";
				payhtml += "<td>"+$scope.newdoesPaymentOverdue(student.paymentHistorys[idx],$index) +"</td>";
				payhtml += "<td>"+student.paymentHistorys[idx].remark+"</td>";
				payhtml += "<td>"+student.paymentHistorys[idx].payDate+"</td>";
				payhtml += "<td><button class='btn' ng-click=\"printFrame('printframe','"+payAmount+"','"+purpose+"','"+startDate+"','"+endDate+"','"+payDate+"','"+payer+"','"+receiver+"','"+student.paymentHistorys[idx].cashOrCard+"')\">打印收据</button></td>";
				// payhtml += "<td><button class='btn' ng-click=\"printFrame('printframe',student.paymentHistorys[idx])\">打印收据</button></td>";
				payhtml += "</tr>";
			}
			payhtml += "</tbody>            	</table>";
			payhtml += "</fieldset>";
			html  += payhtml;
		}
		
		
		var commhtml =  "<fieldset style='clear:left'>";
		commhtml += "					<legend>历史跟踪记录</legend>";
		commhtml += "						<table width='100%'>";
		commhtml += "			<thead><th width='60%'>跟踪记录</th><th width='20%'>日期</th><th width='20%'>联系人</th></thead>";
		commhtml += "				<tbody>";
		if(student.communications != null && student.communications.length > 0){
			// for(var idx in student.communications){
			for (var idx= 0; idx < student.communications.length; idx++) {
				commhtml += "<tr>";
				commhtml += "<td><textarea readonly>"+student.communications[idx].content+"</textarea></td>";
				commhtml +=	"<td>"+student.communications[idx].communicationDate+"</td>";
				commhtml += "<td>"+student.communications[idx].username+"</td>";
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
			for (var idx = 0; idx < student.clazzes.length; idx++) {
			// 	student.clazzes[i]
			// };
			// for(var idx in student.clazzes){
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
		$compile($("#details").contents())($scope);
		// $scope.$apply(student);
		// $ap
		
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
			  CommonService.unauthorizedCallBack(response);
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
				// for(var stuIndex in result){
				for (var stuIndex = 0; stuIndex < result.length; stuIndex++) {	 
		    		// for(var index in result[stuIndex].paymentHistorys){
		    		for (var index = 0; index < result[stuIndex].paymentHistorys.length; index++) {
		    			var startDate = result[stuIndex].paymentHistorys[index].startDate ;
		    			result[stuIndex].paymentHistorys[index].startDate = $filter('date')(new Date(startDate), 'yyyy-MM-dd');
		    			
		    			var endDate = result[stuIndex].paymentHistorys[index].endDate ;
		    			result[stuIndex].paymentHistorys[index].endDate = $filter('date')(new Date(endDate), 'yyyy-MM-dd');
		    			
		    			var payDate = result[stuIndex].paymentHistorys[index].payDate ;
		    			result[stuIndex].paymentHistorys[index].payDate = $filter('date')(new Date(payDate), 'yyyy-MM-dd');
	//	    			return $filter('date')(new Date(time), 'yyyy-MM-dd HH:mm:ss');
		    		}
		    		
		    		// for(var index in result[stuIndex].communications){
		    		for (var index = 0; index < result[stuIndex].communications; index++) {
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
			  CommonService.unauthorizedCallBack(response);
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
	 	if($.isEmptyObject(student.paymentHistorys)) return "无付款记录";
		 // if(student.paymentHistorys == null || student.paymentHistorys.length == 0){
			 // return "无付款记录";
		 // }
		 var overdue = new Date(2030,1,1).getTime();
		 var doesreminder = false;
		 // for(var index in student.paymentHistorys){
		 for (var index = 0; index < student.paymentHistorys.length; index++) {
		 	 
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
		
			 if(doesreminder){ // $filter('date')(new Date(), 'yyyy-MM-dd');
		//			 doesOverdueStyle = {color:'red'};
					 return student.paymentHistorys[index].endDate;
		//			 return $filter('date')(new Date(overdue), 'yyyy-MM-dd');
				 }
				 else
					 return "没有设置过期提醒";
			 }
		 
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
			  CommonService.unauthorizedCallBack(response);
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
		 // for(var sel in $scope.selected1 ){
		 for (var sel = 0; sel < $scope.selected1.length; sel++) {
			 // for(var ele in $scope.allClazzes){
			 for (var ele = 0; ele < $scope.allClazzes.length; ele++) {
			   		if($scope.allClazzes[ele].className == $scope.selected1[sel]){
		    			$scope.clazzes.push($scope.allClazzes[ele]);
		    			$scope.allClazzes.splice(ele,1);
		    		}
		    	}
		 }
	 }
	 
	 $scope.removeAClass = function()
	 {
		 // for(var sel in $scope.selected2 ){
		 for (var sel = 0; sel < $scope.selected2.length; sel++) {
		 	 for (var ele = 0; ele < $scope.clazzes.length; ele++) {
			 // for(var ele in $scope.clazzes){
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
	    			 // $scope.getStudentList();
	    			 $scope.getTopPage(1);
					 ClassService.getAllClassList('false',function(result){
						 if(result != null){
					    		$scope.allClazzes = result;
					    	}else{
					    		alert("Get All class list is null");
					    	}
					 },function(response){
						 CommonService.unauthorizedCallBack(response);
					 });
					 $scope.show = false;
	    		}else{
		    		alert("添加学生失败，详情： "+result.description);
		    		 //$location.path("/crm/main").search({'isSuccess':result.isSuccess});
		    	}
	    	},function(response){
				  CommonService.unauthorizedCallBack(response);
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
					    		 // $scope.getStudentList();
					    		 $scope.getTopPage(1);
					    	}else{
					    		alert("Get All class list is null");
					    	}
					 },function(response){
						 CommonService.unauthorizedCallBack(response);
					 });
				 }else{
					 alert("删除学生失败，详情： "+result.description);
				 }
			 },function(response){
				  CommonService.unauthorizedCallBack(response);
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
		    		 // for(var ele in $scope.clazzes){
		    		 for (var ele = 0; ele < $scope.clazzes.length; ele++) {
		    		  
		    			 // for(var allEle in result){
		    			 for (var allEle = 0; allEle < result.length; allEle++) {
		    		 
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
			  CommonService.unauthorizedCallBack(response);
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
				  CommonService.unauthorizedCallBack(response);
			 });
	    } 
	    
	 
// 	 $scope.$watch('confirmed1', function(newValue, oldValue) {
// //	        console.log("old value: %s and new value: %s", oldValue, newValue);
// 	        $scope.getStudentList();
// 	    });
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
	    			// $scope.getStudentList();
	    			$scope.getTopPage(1);
	    		}else{
		    		alert("更新学生失败，详情： "+result.description);
		    	}
	    	},function(response){
				 CommonService.unauthorizedCallBack(response);
			 });
	    }
	 $scope.getStudentList = function(){
		 StudentService.getStudentList("false",function(result){
    		if(result != null){
//	    		console.log(result);
	    		// for(var stuIndex in result){
	    		for (var stuIndex = 0; stuIndex < result.length; stuIndex++) {
	    		 
		    		// for(var index in result[stuIndex].paymentHistorys){
		    		for (var index = 0; index < result[stuIndex].paymentHistorys.length; index++) {
		    			
		    			var startDate = result[stuIndex].paymentHistorys[index].startDate ;
		    			result[stuIndex].paymentHistorys[index].startDate = $filter('date')(new Date(startDate), 'yyyy-MM-dd');
		    			
		    			var endDate = result[stuIndex].paymentHistorys[index].endDate ;
		    			result[stuIndex].paymentHistorys[index].endDate = $filter('date')(new Date(endDate), 'yyyy-MM-dd');
		    			
		    			var payDate = result[stuIndex].paymentHistorys[index].payDate ;
		    			result[stuIndex].paymentHistorys[index].payDate = $filter('date')(new Date(payDate), 'yyyy-MM-dd');
	//	    			return $filter('date')(new Date(time), 'yyyy-MM-dd HH:mm:ss');
		    		}
		    		for (var index = 0; index < result[stuIndex].communications.length; index++) {		    		 
		    		// for(var index in result[stuIndex].communications){
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
			 CommonService.unauthorizedCallBack(response);
		 });
    
    };



    /**
		Handle page
    */
    $scope.handleReturnStudentList = function(result){
    	for (var stuIndex = 0; stuIndex < result.length; stuIndex++) {
	    		 
		    		// for(var index in result[stuIndex].paymentHistorys){
		    		for (var index = 0; index < result[stuIndex].paymentHistorys.length; index++) {
		    			
		    			var startDate = result[stuIndex].paymentHistorys[index].startDate ;
		    			result[stuIndex].paymentHistorys[index].startDate = $filter('date')(new Date(startDate), 'yyyy-MM-dd');
		    			
		    			var endDate = result[stuIndex].paymentHistorys[index].endDate ;
		    			result[stuIndex].paymentHistorys[index].endDate = $filter('date')(new Date(endDate), 'yyyy-MM-dd');
		    			
		    			var payDate = result[stuIndex].paymentHistorys[index].payDate ;
		    			result[stuIndex].paymentHistorys[index].payDate = $filter('date')(new Date(payDate), 'yyyy-MM-dd');
	//	    			return $filter('date')(new Date(time), 'yyyy-MM-dd HH:mm:ss');
		    		}
		    		for (var index = 0; index < result[stuIndex].communications.length; index++) {		    		 
		    		// for(var index in result[stuIndex].communications){
		    			var commdate = result[stuIndex].communications[index].communicationDate;
		    			if(commdate == null)
		    				result[stuIndex].communications[index].communicationDate =  $filter('date')(new Date(), 'yyyy-MM-dd');
		    			else
		    				result[stuIndex].communications[index].communicationDate =  $filter('date')(new Date(commdate), 'yyyy-MM-dd');
		    		}
	    		}
	    		return result;
    }
    $scope.maxId = 1;
	$scope.minId = 1;
	$scope.size=10;
	$scope.sortColumn = "id";	
	$scope.gopage=0;
	$scope.currentPage = 0;
	$scope.allRecordNumber=10;
	$scope.getPrePage = function(pages){
		$("#showPageTr").css("display","");
		if($scope.currentPage <= 1){
			alert("已到第一页");
			return;
		}
		StudentService.getPrePage($scope.maxId,$scope.size,pages,$scope.sortColumn,function(result){
			if(result != null){
				$scope.students = $scope.handleReturnStudentList(result.studentList);;
				$scope.maxId = result.maxId;
				$scope.minId = result.minId;
				$scope.allRecordNumber = result.allRecordNumber;
				$scope.currentPage = $scope.currentPage -1;
				$scope.showPreNextPage();
				$scope.getPageNumber();
			}else{
				alert("获取学生失败");
			}
		},function(response){
			 CommonService.unauthorizedCallBack(response);
		});
	}
	$scope.getCurrentPage = function(pages){
		$("#showPageTr").css("display","");
		if($scope.maxId == null )return;
		StudentService.getCurrentPage($scope.maxId,$scope.size,pages,$scope.sortColumn,function(result){
			if(result != null){
				$scope.students = $scope.handleReturnStudentList(result.studentList);;
				$scope.maxId = result.maxId;
				$scope.minId = result.minId;
				$scope.allRecordNumber = result.allRecordNumber;
				$scope.showPreNextPage();
				$scope.getPageNumber();
			}else{
				alert("获取学生失败");
			}
		},function(response){
			 CommonService.unauthorizedCallBack(response);
		});
	}
	
	$scope.pageSizeChange = function(){
		if($scope.size <= 0){
			alert("每页显示记录数必须大于等于1，请设置正确的值");
			$scope.size = 1;
			return;
		}
		$scope.getCurrentPage(1);
	}
	$scope.showPreNextPage = function(){
		if($scope.currentPage <= 1 ){
			$("#prepage").css("display","none");
		}else{
			$("#prepage").css("display","");
		}
		if($scope.currentPage == $scope.getPageNumber() ){
			$("#nextpage").css("display","none");
		}else{
			$("#nextpage").css("display","");
		}
	}
	$scope.getNextPage = function(pages){
		$("#showPageTr").css("display","");
		if($scope.currentPage >= $scope.getPageNumber()){
			alert("已到最后一页");
			return;
		}
		StudentService.getNextPage($scope.minId,$scope.size,pages,$scope.sortColumn,function(result){
			$scope.students = $scope.handleReturnStudentList(result.studentList);;
			$scope.maxId = result.maxId;
			$scope.minId = result.minId;
			$scope.allRecordNumber = result.allRecordNumber;
			$scope.currentPage = $scope.currentPage +1;
			$scope.showPreNextPage();
			$scope.getPageNumber();
		},function(response){
			 CommonService.unauthorizedCallBack(response);
		});
	}	
	$scope.getTopPage = function(pages){
		$("#showPageTr").css("display","");
		StudentService.getTopPage($scope.maxId,$scope.size,pages,$scope.sortColumn,function(result){
			$scope.students = $scope.handleReturnStudentList(result.studentList);;
			$scope.maxId = result.maxId;
			$scope.minId = result.minId;
			$scope.allRecordNumber = result.allRecordNumber;
			$scope.currentPage = $scope.currentPage +1;
			$scope.showPreNextPage();
			$scope.getPageNumber();
		},function(response){
			 CommonService.unauthorizedCallBack(response);
		});
	}	
	$scope.paginationUI = function(){
		var html = "";
		$("pagination").html(html);
	}
	$scope.getPageNumber = function(){ 
		if($scope.allRecordNumber == null || $scope.allRecordNumber == 0){
			return 0;
		}
		//console.log("$scope.allRecordNumber%$scope.size:"+($scope.allRecordNumber%$scope.size));
		if($scope.allRecordNumber%$scope.size == 0){
			
			return $scope.allRecordNumber/$scope.size;
		}
		return Math.floor($scope.allRecordNumber/$scope.size)+1;
	}
	$scope.goPage = function(){
		$("#showPageTr").css("display","");
		if($scope.gopage != 0){
			var pages = 1;
			if($scope.gopage > $scope.getPageNumber() ){
				alert("输入的页码值必须小于最大页数 "+$scope.getPageNumber()+" ，请输入正确的页码值");
				return;
			}else if( $scope.gopage <= 0){
				alert("输入的页码值必须大于 0 ，请输入正确的页码值");return;
			}
			if(Math.abs($scope.gopage-$scope.currentPage) != 0){
				pages =  Math.abs($scope.gopage-$scope.currentPage);
			}
		}
		if($scope.currentPage >  $scope.gopage){
			$scope.getPrePage(pages);
			$scope.currentPage = $scope.gopage+1;
		}else if($scope.currentPage ==  $scope.gopage){
			return;
		}else {
			$scope.getNextPage(pages);
			$scope.currentPage = $scope.gopage-1;
		}
//		$scope.currentPage = $scope.gopage;
	}
	 //init page
	 $scope.getTopPage(1);


	 //print receiver
	  $scope.printFrame = function(frameId, payAmount,purpose,startDate,endDate,payDate,payer,receiver,payway)
	    {
	    	// $log.log(paymenthistory);
	    	//  payAmount,purpose,startDate,endDate,payDate,payer,receiver
	    	//alert(payAmount+":"+payAmount+":"+purpose+":"+startDate+":"+endDate+":"+payDate+":"+payer+":"+receiver);
	         var doc = $('#'+frameId).get(0).contentWindow.document;
	        var $body = $('body',doc);
			var html =  '<p style="color:red;position:absolute;left:420px;top:25px;">'+payDate+'</p>';
			html +=  '<p style="color:red;position:absolute;left:200px;top:45px;">'+payer+'</p>';
			html += '<p style="color:red;position:absolute;left:150px;top:90px;">'+purpose+', 支付方式：'+payway+'('+startDate+'~'+endDate+')</p>';
			// html += '<p style="color:red;position:absolute;left:150px;top:110px;">'+purpose+'('+startDate+'~'+endDate+')</p>';
			//html += '<p style="color:red;position:absolute;left:350px;top:90px;"></p>';
			html += '<p style="color:red;position:absolute;left:150px;top:140px;">'+numtochinese(payAmount)+'</p>';
			html += '<p style="color:red;position:absolute;left:440px;top:140px;">'+payAmount+'</p>';
			html += '<p style="color:red;position:absolute;left:170px;top:230px;">'+receiver+'</p>';
	        $body.html(html);
	        setTimeout(function(){
	            $('#'+frameId).get(0).contentWindow.focus();
	            $('#'+frameId).get(0).contentWindow.print();
	        }, 1000); 
	        return false;
	    }
	    $(function(){
	    	$("tbody > tr:odd").addClass("odd");
	    	$("tbody > tr:even").addClass("even");
	    })
});