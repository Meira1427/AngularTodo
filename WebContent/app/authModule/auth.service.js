angular.module('authModule')
  .factory('authService', function($http, $cookies) {
    var service = {};

    var saveToken = function(user) {
      // Store the user's id and email in cookies
    		$cookies.put('uid', user.id);
    		$cookies.put('email', user.email);
    }

    service.getToken = function() {
      // Return an object with id and email properties,
      // the values are the values of the cookies
    		var answer = {
    			id: $cookies.get('uid'),
    			email: $cookies.get('email')
    		}
    		return answer;
    }

    var removeToken = function() {
      // Remove both the id and email cookies
    		$cookies.remove('uid');
    		$cookies.remove('email');
    }

    service.login = function(user) {
      // Use the auth/login route to authenticate the user
      // On success, use saveToken to store the users id/email
    		return $http({
				method : 'POST',
				url : 'api/auth/login',
				headers : {
						'Content-Type' : 'application/json'
					},
				data : user
				})
				.then(function(resp){
					saveToken(resp.data);
					return resp;
				});
    		}

    service.register = function(user) {
      // Use the auth/register route to create and authenticate the user
      // On success, use saveToken to store the users id/email
    		return $http({
    				method : 'POST',
    				url : 'api/auth/register',
    				headers : {
    					'Content-Type' : 'application/json'
    					},
    					data : user
    				})
    				.then(function(resp){
    					saveToken(resp.data);
    					return resp;
    				});
    	}

    service.logout = function() {
      // Use the auth/logout route to remove the users session
      // On success, use removeToken to remove the id and email cookies
    		return $http({
    					method : 'POST',
    					url : 'api/auth/logout'
    				})
    				.then(function(resp){
    					removeToken();
    					return resp;
    				});
    		}

    return service;
  })