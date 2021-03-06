/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

'use strict';
angular.module('todoApp', ['ngRoute'])
    .config(['$routeProvider',  function ($routeProvider) {
        $routeProvider.when('/Home', {
            controller: 'homeCtrl',
            templateUrl: 'views/Home.html',
        }).when('/TodoList', {
            controller: 'todoListCtrl',
            templateUrl: 'views/TodoList.html',
        }).otherwise({redirectTo: '/Home'});
    }]);