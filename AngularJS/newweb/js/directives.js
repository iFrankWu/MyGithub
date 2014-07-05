app.directive("addskills",function($http,$compile,$interpolate){
	return {
		restrict:'A',
		template:'Skills<button ng-click="addOneSkill()">+</button><li ng-repeat="skill in newSkill track by $index"><input type="text" ng-model="newSkill[$index]"></input><button ng-click="deleteSkill($index)">x</button></li>',
		scope:{
			newSkill:'=',
			getNewSkills:'&'	
		},
		link : function(scope,ele,attr){
			console.log("Run into directive.....");
			//scope.skills =[];
			//scope.newSkill =[];
			var init = function(){
				if(scope.newSkill != null && scope.newSkill.length > 0){
					for(var i=0;i<scope.newSkill.length;i++){
			//			scope.skills.push({});
					}
				}else{
					scope.newSkill = [];
				}
				console.log("Init ...."+scope.skills);
			}
			init();
			scope.addOneSkill = function(){
			//	scope.skills.push({});
				scope.newSkill.push("New Skill");
				scope.getNewSkills({skills:scope.newSkill});
			
			}
			scope.deleteSkill = function(index){
				scope.newSkill.splice(index,1);
			//	scope.skills.splice(index,1);
				scope.getNewSkills({skills:scope.newSkill});
			
			}
		}
		
	}
});
/*
app.directive('myConfigform', function($http, $compile, $interpolate) {    
	return {      
		restrict: 'E',     
		compile: function(element) {     
			 var buildMyForm = function(data) {     
			     var template = $interpolate('<form role="form">{{form}}</form>');  
			     var formMarkup = '<input type="text" ng-model="newEmp.skill"> <button ng-click="add()">+</button>';     
			     return template({form: formMarkup});       
		 };
	         element.replaceWith(buildMyForm());      
	         return function(scope, element) {        
			  $compile(element.contents())(scope); 
	         };  
		    } 
	   };
	});*/
