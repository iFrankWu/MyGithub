/*WeekstaatResourceImpl = function ($resource) {
    return $resource('rest/weekstaten/:jaar/:weeknummer', {}, {
        get:{method:'GET', params:{}, isArray:true},
        getWeekstaat:{method:'GET', params:{jaar:'jaar', weeknummer:'weeknummer'}},
        save:{method:'PUT', params:{jaar:'jaar', weeknummer:'weeknummer'}}
    });
};
*/
app.factory("UserResource",function ($resource) {
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
})
 


app.factory("StudentResource", function ($resource) {
    return $resource('crm/student/:id/:onlyStuClass/:preOrNext/:size/:page/:sortColumn', {}, {
       // get:{method:'GET', params:{}, isArray:true},
        //getWeekstaat:{method:'GET', params:{jaar:'jaar', weeknummer:'weeknummer'}},
        getNextPage:{method:'GET',params:{id:'id',onlyStuClass:1,preOrNext:'preOrNext',size:'size',page:'page',sortColumn:'sortColumn'}},
        getPrePage:{method:'GET',params:{id:'id',onlyStuClass:1,preOrNext:'preOrNext',size:'size',page:'page',sortColumn:'sortColumn'}},
        getCurrentPage:{method:'GET',params:{id:'id',onlyStuClass:1,preOrNext:'preOrNext',size:'size',page:'page',sortColumn:'sortColumn'}},
        getTopPage:{method:'GET',params:{id:'id',onlyStuClass:1,preOrNext:'preOrNext',size:'size',page:'page',sortColumn:'sortColumn'}},
        addStudent:{method:"PUT",params:{}},
        deleteStudent:{method:"DELETE",params:{id:"id"}},
        updateStudent:{method:"PUT",params:{id:"id"}},
        getList:{method:'GET',params:{id:1,onlyStuClass:'onlyStuClass'},isArray:true}
    });
});
 
app.factory("ClazzResource",function ($resource) {
    return $resource('crm/clazz/:id/:withStudent', {}, {
        getList:{method:'GET',params:{id:'id',withStudent:'withStudent'},isArray:true},
        addClazz:{method:'PUT',params:{}},
        updateClazz:{method:"PUT",params:{id:'id'}},
        deleteClazz:{method:'DELETE',params:{id:'id'}},
        getClazz:{method:'GET',params:{id:'id'}}
    });
});

app.factory("CommonResource",function ($resource) {
    return $resource('crm/comm/', {}, {
//        getList:{method:'GET',params:{id:'id',withStudent:'withStudent'},isArray:true},
//        addClazz:{method:'PUT',params:{}},
//        updateClazz:{method:"PUT",params:{id:'id'}},
//        deleteClazz:{method:'DELETE',params:{id:'id'}},
//        getClazz:{method:'GET',params:{id:'id'}}
    	postRequest:{method:'POST',isArray:true},
    	putRequest:{method:'PUT'},
        exportData:{method:'POST',params:{exports:"exports"}}
    });
});

app.factory("SearchResource",function ($resource) {
    return $resource('crm/serach/:type/:param', {}, {
//        getList:{method:'GET',params:{id:'id',withStudent:'withStudent'},isArray:true},
//        addClazz:{method:'PUT',params:{}},
//        updateClazz:{method:"PUT",params:{id:'id'}},
//        deleteClazz:{method:'DELETE',params:{id:'id'}},
//        getClazz:{method:'GET',params:{id:'id'}}
    });
});


 