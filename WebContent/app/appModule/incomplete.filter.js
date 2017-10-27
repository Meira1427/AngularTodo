angular.module('todoModule')
	.filter('incomplete' , function(){
		return function(list, showAll){
			if(showAll) {
				return list;
			}
			else {
				var filtered = [];
				list.forEach(function(item, index, array){
					if(!item.completed) {
						filtered.push(item);
					}
				});
				return filtered;
			}
		};
	});