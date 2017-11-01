angular.module('todoModule')
	.factory('todoService', function($http, $filter, $cookies, $location, $rootScope, authService){
		var service = {};
		
		var checkLogin = function(){
			var userIdPass = authService.getToken();
			if(userIdPass == null) {
				return null;
			}
			else {
				return userIdPass.id;
			}
		};
		
		service.index = function(){
			var uid = checkLogin();
			if(isNaN(uid)) {
				$location.path('/login');
			}
			else {
				return $http({
					method : 'GET',
					url : 'api/users/' + uid + '/todos'
				})
			}
		};
		
		service.create = function(task) {
			console.log(task);
			task.completed = false;
			var uid = checkLogin();
			if(isNaN(uid)) {
				$location.path('/login');
			}
			else {
				return $http({
					method : 'POST',
					url : 'api/users/' + uid + '/todos',
					headers : {
						'Content-Type' : 'application/json'
					},
					data : task
				})
				.then(function(resp){
					$rootScope.$broadcast('newItem', {
						task : resp.data
					});
					return resp;
				}) 
			}
		}
	
		service.update = function(task) {
			console.log("Entering Update");
			console.log(task.completed);
			if(task.completed) {
				console.log("Setting Complete Date");
				task.completeDate = $filter('date')(Date.now(), 'MM/dd/yyyy');
			}
			else {
				console.log("Changing to Empty String");
				task.completeDate = '';
			}
			var tid = task.id;
			var uid = checkLogin();
			if(isNaN(uid)) {
				$location.path('/login');
			}
			else {
				return $http({
					method : 'PUT',
					url : 'api/users/' + uid + '/todos/' + tid,
					headers : {
						'Content-Type' : 'application/json'
					},
					data : task
				})
			}
		}
		
		service.destroy = function(id) {
			var uid = checkLogin();
			if(isNaN(uid)) {
				$location.path('/login');
			}
			else {
				return $http({
					method : 'DELETE',
					url : 'api/users/' + uid + '/todos/' + id
				})
			}
		}
		
		return service;
	});