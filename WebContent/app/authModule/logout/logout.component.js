angular.module('authModule')
	.component('logout', {
		templateUrl: 'app/authModule/logout/logout.component.html',

		controller: function(authService, $location){
			var vm = this;
			
			vm.logout = function(user) {
				console.log("in logout function");
				authService.logout()
				.then(function(response){
					$location.path('/')
				})
				.catch(function(error){
					console.log(error);
				});
			}
			
		},
		
		controllerAs: 'vm'
	});