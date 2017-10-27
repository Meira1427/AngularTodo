angular.module('todoModule')
	.component('todo', {
		templateUrl: 'app/appModule/todoList/todoList.component.html',
		controller: function(todoService, $filter){
			var vm = this;
			
			vm.selected = null;
			vm.editTodo = null;
			
			vm.list = [];
			
			var getAll = function(){
				todoService.index()
				.then(function(res){
					console.log(res.data);
					vm.list = res.data;
				})
				.catch(function(err){
					console.log(error);
				});
			}
			
			getAll();
			
			vm.addTask = function(task) {
				todoService.create(task)
				.then(function(res){
					getAll();
				})
				.catch(function(error){
					console.log(error);
				});
			};
			
			vm.getTotalIncomplete = function(){
				var incompleteOnly = $filter('incomplete')(vm.list, false);
				return incompleteOnly.length;
			};
			
			vm.weightOfIncomplete = function(){
				var num = vm.getTotalIncomplete();
				if(num < 5) {
					return 'verygood';
				}
				else if(num < 10) {
					return 'good';
				}
				else {
					return 'warning';
				}
			};
			
			vm.displayTodo = function(task) {
				vm.selected = task;
			}
			
			vm.displayTable = function() {
				vm.editTodo = null;
				vm.selected = null;
			}
			
			vm.setEditTodo = function() {
				vm.editTodo = angular.copy(vm.selected);
			}
			
			vm.cancelEdit = function() {
				vm.editTodo = null;
			}
			
			vm.updateTodo = function(task) {
				console.log("In updateTodo and calling update");
				todoService.update(task)
				.then(function(res){
					getAll();
				})
				.catch(function(error){
					console.log(error);
				});
				vm.editTodo = null;
				vm.selected = null;
			}
			
			vm.deleteTodo = function(id){
				todoService.destroy(id)
				.then(function(res){
					getAll();
				})
				.catch(function(error){
					console.log(error);
				});
			}
		},
		
		controllerAs: 'vm'
	
	});