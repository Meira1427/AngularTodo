angular.module('authModule', ['ngCookies', 'ngRoute'])
	.config(function($routeProvider){
		$routeProvider
		.when('/register', {
			template: '<register></register>'
		})
		.when('/login', {
			template: '<login></login>'
		})
});