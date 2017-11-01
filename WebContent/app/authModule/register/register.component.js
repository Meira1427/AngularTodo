angular.module('authModule')
	.component('register', {
		templateUrl: 'app/authModule/register/register.component.html',
		
		controller: function(authService, $location){
			var vm = this;
			
			vm.register = function(user) {
				console.log("in register function");
				console.log(user);
				authService.register(user)
				.then(function(response){
					$location.path('/todo')
				})
				.catch(function(error){
					console.log(error)
				});
			}

			
		},
		
		controllerAs: 'vm'
	});