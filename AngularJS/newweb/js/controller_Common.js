app.controller("CommomController",function($scope,$compile,$location,$filter,$http,StudentService,ClassService,$cookieStore,UserService,CommonService,$modal,$log){
	$scope.userName;
	if($cookieStore.get("user") == null){
	 
	} else{
		var user = $cookieStore.get("user");
		$scope.userName =user.username;
	}
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
	    // $(function(){
	    // 	$("tbody > tr:odd").addClass("odd");
	    // 	$("tbody > tr:even").addClass("even");
	    // })
	     function logout(){
	    	$.ajax({
	   			  type: "GET",
	   			//  contentType: "application/json;charset=UTF-8",
	   			  url: "crm/user/1", //{"action":"searchStudent","doesBill":true}
	   			  //data: "{\"action\":\"addCommunicate\",\"sid\":"+sid+",\"content\":\""+text+"\",\"commDate\":\""+date+"\"}",
	   			  success: function(result){
	   				document.cookie="user" + "=" + null;
	   				if(result.isSuccess){
		    			alert("成功退出系统");
		    			$("#classLink").hide();
		    			$("#userLink").hide();
		    			$("#studentLink").hide();
		    			$("#logout").hide();
		    			$("#mypageLink").hide();
		    			location.href = "/";
		    		}else{
			    		alert("退出登录失败，详情： "+result.description);
			    	}
	   			  },
	   			  error:function(result){
	   				alert("增加失败，详情： ："+result);
	   			  }
	   			  //dataType: 'json'
			});
	    }

});