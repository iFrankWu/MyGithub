/*WeekstaatResourceImpl = function ($resource) {
    return $resource('rest/weekstaten/:jaar/:weeknummer', {}, {
        get:{method:'GET', params:{}, isArray:true},
        getWeekstaat:{method:'GET', params:{jaar:'jaar', weeknummer:'weeknummer'}},
        save:{method:'PUT', params:{jaar:'jaar', weeknummer:'weeknummer'}}
    });
};
*/
UserResourceImpl = function ($resource) {
    return $resource('crm/user/:id/:passoword', {}, {
       // get:{method:'GET', params:{}, isArray:true},
        //getWeekstaat:{method:'GET', params:{jaar:'jaar', weeknummer:'weeknummer'}},
        //save:{method:'PUT', params:{jaar:'jaar', weeknummer:'weeknummer'}},
        login:{method:'POST',params:{}},
        logout:{method:'GET',params:{id:'id'}},
        getList:{method:'GET',params:{},isArray:true},
        addUser:{method:"PUT",params:{}},
        deleteUser:{method:"DELETE",params:{id:'id'}},
        updateUser:{method:"PUT",params:{id:"id"}}
    });
};
 


StudentResourceImpl = function ($resource) {
    return $resource('crm/student/:id', {}, {
       // get:{method:'GET', params:{}, isArray:true},
        //getWeekstaat:{method:'GET', params:{jaar:'jaar', weeknummer:'weeknummer'}},
        addStudent:{method:"PUT",params:{}},
        deleteStudent:{method:"DELETE",params:{id:"id"}},
        updateStudent:{method:"PUT",params:{id:"id"}},
        getList:{method:'GET',params:{},isArray:true}
    });
};
 
ClazzResourceImpl = function ($resource) {
    return $resource('crm/clazz/:id/:withStudent', {}, {
        getList:{method:'GET',params:{id:'id',withStudent:'withStudent'},isArray:true},
        addClazz:{method:'PUT',params:{}},
        updateClazz:{method:"PUT",params:{id:'id'}},
        deleteClazz:{method:'DELETE',params:{id:'id'}},
        getClazz:{method:'GET',params:{id:'id'}}
    });
};

CommonResourceImpl = function ($resource) {
    return $resource('crm/comm/', {}, {
//        getList:{method:'GET',params:{id:'id',withStudent:'withStudent'},isArray:true},
//        addClazz:{method:'PUT',params:{}},
//        updateClazz:{method:"PUT",params:{id:'id'}},
//        deleteClazz:{method:'DELETE',params:{id:'id'}},
//        getClazz:{method:'GET',params:{id:'id'}}
    	postRequest:{method:'POST',isArray:true},
    	putRequest:{method:'PUT'}
    });
};

SearchResourceImpl = function ($resource) {
    return $resource('crm/serach/:type/:param', {}, {
//        getList:{method:'GET',params:{id:'id',withStudent:'withStudent'},isArray:true},
//        addClazz:{method:'PUT',params:{}},
//        updateClazz:{method:"PUT",params:{id:'id'}},
//        deleteClazz:{method:'DELETE',params:{id:'id'}},
//        getClazz:{method:'GET',params:{id:'id'}}
    });
};


 