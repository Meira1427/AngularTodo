angular.module('todoModule', ['ngRoute'])
.config(function($routeProvider){
	$routeProvider
	.when('/', {
		template: '<home></home>'
	})
	.when('/todo', {
		template: '<todo></todo>'
	})
	.when('/todo/:id', {
		template: '<todo></todo>'
	})
	.when('/home', {
		template: '<home></home>'
	})
	.when('/about', {
		template: '<about></about>'
	})
	.when('/contact', {
		template: '<contact></contact>'
	})
	.when('/notfound', {
		template: '<not-found></not-found>'
	})
	.otherwise({
		template: '<not-found></not-found>'
	})
});
