app.controller("ClassController",function($scope,$log,$location,$filter,$http,ClassService,StudentService,$cookieStore,UserService,CommonService){
	if($cookieStore.get("user") == null){
		$("#logout").show();
//		alert("cookie is null"+$cookieStore.get("user"));
	} else{
		var user = $cookieStore.get("user");
		if(user.type != CommonService.types.values[2]){
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
			// for(var idx in clazz.payways){
			for (var idx = 0; idx < clazz.payways.length; idx++) {
				
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
			
			// for(var idx in clazz.students){
			for (var idx = 0; idx < clazz.students.length; idx++) {
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
				StudentService.getStudentList("true",function(result){
					if(result!= null){
						$scope.allStudents = result;
					} 
				},function(response){
					  CommonService.unauthorizedCallBack(response);
				 });
			}
		}
	}
	

	
	 $scope.selectedStu1 = [];
	 $scope.selectedStu2 = [];
	 
	 $scope.selectAStudent = function()
	 {
		 	for (var sel = 0; sel < $scope.selectedStu1.length; sel++) {
			 for (var ele = 0; ele < $scope.allStudents.length; ele++) {
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
		 // for(var sel in $scope.selectedStu2 ){
		 	for (var sel = 0; sel < $scope.selectedStu2.length; sel++) {
		 		 for (var ele = 0; ele < $scope.students.length; ele++) {
			 // for(var ele in $scope.students){
		    		if($scope.students[ele].name == $scope.selectedStu2[sel]){
		    			var r=confirm("确定班级中删除该学生吗?");
		 				if(r == false)continue;
		    			$scope.allStudents.push($scope.students[ele]);
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
			  CommonService.unauthorizedCallBack(response);
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
	 	 var r=confirm("确定删除该折扣信息吗?");
		 if(r == false)return;
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
			 CommonService.unauthorizedCallBack(response);
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
		
		StudentService.getStudentList("true",function(result){
			 if(result != null){
//		    		console.log(result);
		    		//$scope.allClazzes = result;
		    		 // for(var ele in  $scope.students){
		    		 for (var ele = 0; ele < $scope.students.length; ele++) {
		    		 	// $scope.students[i]
		    		 // };
		    		 	for (var allEle = 0; allEle< result.length; allEle++) {
		    			 // for(var allEle in result){
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
			 CommonService.unauthorizedCallBack(response);
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
			 CommonService.unauthorizedCallBack(response);
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
		StudentService.getStudentList("true",function(result){
			if(result!= null){
				$scope.allStudents = result;
			} 
		},function(response){
			 CommonService.unauthorizedCallBack(response);
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
				  CommonService.unauthorizedCallBack(response);
			 });
		}
	}

	/***
		this is for discount for student
	*/
	$scope.allStudents4Discount = [];
	$scope.allClass4Discount = [];
	$scope.selectedStu;
	$scope.showSettingDiscount = false;
	$scope.allSettedDiscount ;

	
	$scope.getStudentList4Discount = function(){
		$scope.showSettingDiscount = true;
		StudentService.getStudentList("true",function(result){
			if(result!= null){
				$scope.allStudents4Discount = result;
			} 
		},function(response){
			 CommonService.unauthorizedCallBack(response);
		 });	
	}

	$scope.changeStudent = function(){
		$scope.allClass4Discount = $scope.selectedStu.clazzes;
	}
	$scope.getAllSettedDiscount = function(){
		var param = {
			"action":"getSettedDiscount"
		};
	 CommonService.postRequest(param,function(result){
		  	// $log.log(result);
			if(result != null){
				$scope.allSettedDiscount =  result;
			}
		 },function(response){
			 CommonService.unauthorizedCallBack(response);
		 });
	}
	$scope.deleteDiscount = function($index){
		// $log.log($index);
		 var r=confirm("确定删除该折扣信息吗?");
		 if(r == false)return;
		var param = {
			"action":"deleteSettedDiscount",
			"sid":$scope.allSettedDiscount[$index].sid,
			"cid":$scope.allSettedDiscount[$index].cid
		}
		  CommonService.putRequest(param,function(result){
			if(result != null){//该学生已经存在了
				alert("折扣删除成功！");
				$scope.getAllSettedDiscount();
			}
		 },function(response){
			 CommonService.unauthorizedCallBack(response);
		 });
	}
	$scope.seettingDiscunt = function(){
		if($.isEmptyObject($scope.selectedStu)){
			alert("请首先选择学生");
			return;
		}
		if($.isEmptyObject($scope.selectedClass)){
			alert("请首先选择班级");
			return;
		}
		var param = {
			"action":"settingDiscount",
			"sid":$scope.selectedStu.id,
			"cid":$scope.selectedClass.id,
			"discount":$scope.discount
		}
		// $log.log(param);
		  CommonService.putRequest(param,function(result){
			if(result != null){//该学生已经存在了
				alert("折扣设置成功！");
				$scope.getAllSettedDiscount();
			}
		 },function(response){
			 CommonService.unauthorizedCallBack(response);
		 });
		$scope.showSettingDiscount = false;
	}
	$scope.getAllSettedDiscount();
});