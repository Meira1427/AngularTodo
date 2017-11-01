angular.module('todoModule', ['ngRoute', 'ngCookies', 'authModule'])
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
	.when('/register', {
		template: '<register></register>'
	})
	.when('/login', {
		template: '<login></login>'
	})
	.when('/notfound', {
		template: '<not-found></not-found>'
	})
	.otherwise({
		template: '<not-found></not-found>'
	})
});
