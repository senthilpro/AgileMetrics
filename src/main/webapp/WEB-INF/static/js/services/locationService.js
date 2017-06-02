angular.module('myApp')
 .factory('LocationService', ['$filter', 
  function($filter){

    var cityList = [
        {"id":1, "city":"Hyderabad" },
        {"id":2, "city":"Chennai"},
        {"id":3, "city":"Coimbatore"},
    ];

    var serviceList = [
        {"id":1, "service":"Painting" },
        {"id":2, "service":"Plumbing"},
        {"id":3, "service":"Electrician"},
    ];
    
    var locationList = [
        {"id":1, "location":"Gachibowli", "cityId": 1},
        {"id":2, "location":"Madhapur", "cityId": 1},
        {"id":3, "location":"Dilshuknagar", "cityId": 1},
        {"id":4, "location":"Banjara Hills", "cityId": 1},
        {"id":5, "location":"Lingampally", "cityId": 1},
        {"id":6, "location":"Nizampet", "cityId": 1},
        {"id":7, "location":"Parrys", "cityId": 2},
        {"id":8, "location":"Chrompet", "cityId": 2},
        {"id":9, "location":"Guindy", "cityId": 2},
        {"id":10, "location":"Mount Road", "cityId": 2},
        {"id":11, "location":"Peelamedu", "cityId": 3}, 
        {"id":12, "location":"RS Puram", "cityId": 3},
        {"id":13, "location":"Ganapathy", "cityId": 3},
        {"id":14, "location":"Saibaba colony", "cityId": 3},
    ];

    function getCityLocation(cityId){
        var locations = ($filter('filter')(locationList, {cityId: cityId.id}));
        return locations;
    }

    function getServices(){   
        return serviceList;
    }
    
    function getCity(){   
        return cityList;
    }
    
    function getCityIndex(value) {
        for(var i=0; i<cityList.length; i++) {
            if(cityList[i].city === value)
                 return i;
        }
    }
    
    function getCityId(value) {
        for(var i=0; i<cityList.length; i++) {
            if(cityList[i].city === value)
                 return cityList.id;
        }
    }
    
    function getLocationIndex(value, city) {
    	var locations = ($filter('filter')(locationList, {cityId: getCityId(city)}));
        for(var i=0; i<locations.length; i++) {
            if(locations[i].location === value)
                 return i;
        }
    }
    function getServiceIndex(value) {
        for(var i=0; i<serviceList.length; i++) {
            if(serviceList[i].service === value)
                 return i;
        }
    }
    
    return {
    	getCityLocation : getCityLocation,
    	getCity : getCity,
    	getServices: getServices,
    	getServiceIndex: getServiceIndex,
    	getLocationIndex: getLocationIndex,
    	getCityIndex: getCityIndex
    };       
}]);
