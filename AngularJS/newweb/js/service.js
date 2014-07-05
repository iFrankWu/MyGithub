app.service("CommonService",function(CommonResource,$cookieStore, $location) {
    return {
        userId: "",
        user:null,
        payways:{
                "type":"select",
                "name":"Job Title",
                "value":"-Select One-",
                "values":["现金","刷卡","转账"] //"-Select One-",
        },
         refundPayways:{
                "type":"select",
                "name":"Job Title",
                "value":"-Select One-",
                "values":["存入账户","现金","刷卡","转账"]//"-Select One-",
        },
         types :{
         	 "type":"select",
              "name":"类型",
         	"value":"-Select One-",
         	"values":["超级管理员","管理员","普通用户"]//"-Select One-",
         } ,
	    statuses :{
	    	 "type":"select",
                "name":"状态",
	   		 "value":"-Select One-",
         	 "values":["可用","禁用"]//"-Select One-",
         },
         discountInfo:{
         	 "type":"select",
                "name":"付款方式",
	   		 "value":"-Select One-",
         	 "values":["月付","季付","半年付","年付"]//"-Select One-",
         },
        // discountInfo:[
	       //               {name:'月付', value:'month'},
	       //               {name:'季付', value:'season'},
	       //               {name:'半年付', value:'halfyear'},
	       //               {name:'年付', value:'year'},
	       //             ],

	        getColFromArray:function(array,colName) {
				 	if(array == null)return null;
				 	var cols = [];
				 	for (var i = 0; i < array.length; i++) {
				 		cols.push(array[i][colName]);
				 	}
				 	// var cols = $.map(array)[colName];
				 	return cols;
				 },
			getObjectFromArrayByColValue:function(array,colName,colValue){
				 	for (var i = 0; i < array.length; i++) {
				 		if(array[i][colName] == colValue)
				 			return array[i];
				 	}
				 	return null;
				 },

	     postRequest:function(params,callback,failurecallback){
	    	 CommonResource.postRequest(params,callback,failurecallback);
	     },
	       exportData:function(data){
				CommonResource.exportData({exports:"exports"},data,callback,failurecallback);
		    },
	     putRequest:function(params,callback,failurecallback){
	    	 CommonResource.putRequest(params,callback,failurecallback);
	     },
	     unauthorizedCallBack:function(response){
			 if(response.status == 401){
				 alert("登陆已超时，请重新登陆。");
				 $cookieStore.put("user",null);
				 $location.path("/crm/login");
			 }else{
				 alert("未知系统错误，请重新登陆");
				 $cookieStore.put("user",null);
				 $location.path("/crm/login");
			 }
		 }
    }
}); 



app.service("ClassService",function(ClazzResource){
	return {
		getAllClassList: function(withStudents,callback,failurecallback){
			ClazzResource.getList({id:1,withStudent:withStudents},callback,failurecallback);
		},
		addClazz : function(clazz,callback,failurecallback){
			ClazzResource.addClazz(clazz,callback,failurecallback);
		},
		updateClazz:function(ids,clazz,callback,failurecallback){
			ClazzResource.updateClazz({id:ids},clazz,callback,failurecallback);
		},
		deleteClazz:function(ids,callback,failurecallback){
			ClazzResource.deleteClazz({id:ids},callback,failurecallback);
		}
		
	}
});

app.service("StudentService",function(StudentResource){
	return {
		getNextPage:function(startId,sizes,pages,sortColumns,callback,failurecallback){
			if(startId == null) startId = 0;
    	   StudentResource.getNextPage({id:startId,preOrNext:'Next',size:sizes,page:pages,sortColumn:sortColumns},callback,failurecallback);
	    },
	    getPrePage:function(startId,sizes,pages,sortColumns,callback,failurecallback){
			if(startId == null) startId = 0;
    	   StudentResource.getPrePage({id:startId,preOrNext:'Pre',size:sizes,page:pages,sortColumn:sortColumns},callback,failurecallback);
	    },
	    getCurrentPage:function(startId,sizes,pages,sortColumns,callback,failurecallback){
			if(startId == null) startId = 0;
    	   StudentResource.getCurrentPage({id:startId,preOrNext:'Current',size:sizes,page:pages,sortColumn:sortColumns},callback,failurecallback);
	    },
	    getTopPage:function(startId,sizes,pages,sortColumns,callback,failurecallback){
			if(startId == null) startId = 1;
    	   StudentResource.getTopPage({id:startId,preOrNext:'Top',size:sizes,page:pages,sortColumn:sortColumns},callback,failurecallback);
	    },
		getStudentList: function(onlyStuClass,callback,failurecallback){
			StudentResource.getList({onlyStuClass:onlyStuClass},callback,failurecallback);
		},
		addStudent : function(student,callback,failurecallback){
			StudentResource.addStudent(student,callback,failurecallback);
		},
		updateStudent:function(ids,student,callback,failurecallback){
			StudentResource.updateStudent({id:ids},student,callback,failurecallback);
		},
		deleteStudent:function(ids,callback,failurecallback){
			StudentResource.deleteStudent({id:ids},callback,failurecallback);
		}
		
	}
});
app.service("UserService",function(UserResource){
	return {
		  login : function(user,callback,failurecallback){
			  UserResource.login(user,callback,failurecallback);
	       },
	       logout : function(ids,callback){
				  UserResource.logout({id:ids},callback);
		       },
	       getUserList : function(callback,failurecallback){
	    	   UserResource.getList(callback,failurecallback);
		    },
		    addUser : function(user,callback,failurecallback){
		    	UserResource.addUser(user,callback,failurecallback);
		    },
		    deleteUser:	function(ids,callback,failurecallback){
		    	UserResource.deleteUser({id:ids},callback,failurecallback);
		    },
		    updateUser:function(ids,user,callback,failurecallback){
		    	UserResource.updateUser({id:ids},user,callback,failurecallback);
		    }
	}
});

app.service("SerachService",function(SearchResource){
	return {
		  login : function(user,callback,failurecallback){
			  UserResource.login(user,callback,failurecallback);
	       }
	      
	}
});