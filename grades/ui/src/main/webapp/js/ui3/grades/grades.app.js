/*
  The MIT License (MIT)
  
  Copyright (C) 2014 by Kuali Foundation

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:
 
  The above copyright notice and this permission notice shall be included in

  all copies or substantial portions of the Software.
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
*/

//  Copyright 2014 The Kuali Foundation Licensed under the Educational Community
//  License, Version 2.0 (the "License"); you may not use this file except in
//  compliance with the License. You may obtain a copy of the License at
//  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
//  agreed to in writing, software distributed under the License is distributed
//  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
//  express or implied. See the License for the specific language governing
//  permissions and limitations under the License.

'use strict';
var grades = angular.module("gradesApp", ['ngSanitize','ui.bootstrap', 'ngRoute']);

/**
 * Configure routes for the Grades tool
 */
grades.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/index', 		    { templateUrl: 'grades/partials/form',	    controller: 'FormCtrl' }).
            when('/results', 		{ templateUrl: 'grades/partials/results',	controller: 'ResultsCtrl' }).
            otherwise({ redirectTo: '/index' });
    }]);

/**
 * Directive to test that a date is after another date
 */
grades.directive('after', [function() {
    return {
        require: 'ngModel',
        link: function(scope, elem, attrs, ngModel) {
            var targetElement = attrs.after;
            scope.$watch(attrs.ngModel, function() {

                function isValid(value){
                    var target = scope;
                    for(var idx = 0 ; idx < targetElement.split('.').length; idx++){
                        target = target[targetElement.split('.')[idx]];
                    }
                    return moment(value).isAfter(moment(target));
                }

                //For DOM -> model validation
                ngModel.$parsers.unshift(function(value) {
                    var valid = isValid(value);
                    ngModel.$setValidity('after', valid);
                    return value;
                });

                //For model -> DOM validation
                ngModel.$formatters.unshift(function(value) {
                    ngModel.$setValidity('after', isValid(value));
                    return value;
                });

            });
        }
    }
}]);


/**
 * Session object for grades
 */
grades.factory("GradesSession",[ function(){
    return {
        'startDate' : "",
        'endDate' : "",
        'clear' : function(){
            this.startDate = "";
            this.endDate = "";
        }
    }
}]);

