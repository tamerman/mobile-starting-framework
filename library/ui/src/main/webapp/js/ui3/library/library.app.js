/*
 * The MIT License
 * Copyright (c) 2011 Kuali Mobility Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

'use strict';
var library = angular.module("libraryApp", ['ngSanitize', 'ui.bootstrap', 'ngRoute']);

/**
 * Configure routes for the Grades tool
 */
library.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
            when('/home', { 'templateUrl': 'library/home'}).
            when('/viewHours', { 'templateUrl': 'library/viewHours' }).
            when('/viewHours/:libraryId', { 'templateUrl': function (routeParams) {
                return 'library/viewHours/' + routeParams.libraryId;
            }}).
            when('/editHours/:libraryId', { 'templateUrl': function (routeParams) {
                return 'library/editHours/' + routeParams.libraryId;
            }}).
            when('/viewContact', { 'templateUrl': 'library/viewContact' }).
            when('/viewContact/:libraryId', { 'templateUrl': function (routeParams) {
                return 'library/viewContact/' + routeParams.libraryId;
            }}).
            when('/editContact/:libraryId', { 'templateUrl': function (routeParams) {
                return 'library/editContact/' + routeParams.libraryId;
            }}).
            otherwise({ redirectTo: '/home' });
    }])

    .run(function ($rootScope, $templateCache) {
        $rootScope.$on('$viewContentLoaded', function () {
            $templateCache.removeAll();
        });
    });
