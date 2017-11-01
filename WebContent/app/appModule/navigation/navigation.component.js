angular.module('todoModule')
	.component('navigation', {
		templateUrl: 'app/appModule/navigation/navigation.component.html',
		
		controller: function(authService){
			var vm = this;
			
			vm.checkUser = function(){
				var temp = authService.getToken();
//				console.log("checking user");
				if(isNaN(temp.id)) {
					return false;
				}
				else {
					return true;
				}
			}
			
		},
		
		controllerAs: 'vm'
	});