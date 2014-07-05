CommonServiceImpl = function(CommonResource) {
    return {
        userId: "",
        user:null,
        discountInfo:[
	                     {name:'月付', value:'month'},
	                     {name:'季付', value:'season'},
	                     {name:'半年付', value:'halfyear'},
	                     {name:'年付', value:'year'},
	                   ],
	     postRequest:function(params,callback,failurecallback){
	    	 CommonResource.postRequest(params,callback,failurecallback);
	     },
	     putRequest:function(params,callback,failurecallback){
	    	 CommonResource.putRequest(params,callback,failurecallback);
	     }
    }
}; 

ClazzServiceImpl = function(ClazzResource){
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
};

StudentServiceImpl = function(StudentResource){
	return {
		getStudentList: function(callback,failurecallback){
			StudentResource.getList(callback,failurecallback);
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
};
UserServiceImpl = function(UserResource){
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
};

SerachServiceImpl = function(SearchResource){
	return {
		  login : function(user,callback,failurecallback){
			  UserResource.login(user,callback,failurecallback);
	       }
	      
	}
};