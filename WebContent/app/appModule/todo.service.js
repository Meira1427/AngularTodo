angular.module('todoModule')
	.factory('todoService', function($http, $filter){
		var service = {};
		
		service.index = function(){
			return $http({
				method : 'GET',
				url : 'api/users/1/todos'
			})
		};
		
		service.create = function(task) {
			console.log(task);
			task.completed = false;
			return $http({
				method : 'POST',
				url : 'api/users/1/todos',
				headers : {
					'Content-Type' : 'application/json'
				},
				data : task
			})
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
			return $http({
				method : 'PUT',
				url : 'api/users/1/todos/' + tid,
				headers : {
					'Content-Type' : 'application/json'
				},
				data : task
			})
		}
		
		service.destroy = function(id) {
			console.log("id is: " + id);
			return $http({
				method : 'DELETE',
				url : 'api/users/1/todos/' + id
			})
		}
		
		return service;
	});