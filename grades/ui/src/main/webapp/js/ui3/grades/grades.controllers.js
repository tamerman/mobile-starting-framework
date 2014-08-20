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

/**
 * Controller for the form
 */
grades.controller("FormCtrl",
    ['$scope', '$window','GradesSession',
        function($scope, $window, GradesSession) {

            /*
             * Function to hide messages
             */
            $scope.clickHideMessages = function(){
                $scope.$parent.errors = [];
                $scope.$parent.infos = [];
                $scope.$parent.alerts = [];
                $scope.$parent.successes = [];
            };
            $scope.clickHideMessages();

            $scope.$root.kmeNavLeft = function() {
                $scope.clickHideMessages();
                window.history.back();
            };
             /**
             * Defaults for date fields
             */
            var year = new Date().getFullYear();

            GradesSession.startDate = new Date(year, 0, 1);
            GradesSession.endDate = new Date(year, 11, 31);
            $scope.gradesSession = GradesSession;


            $scope.open = function(flag, $event) {
                $event.preventDefault();
                $event.stopPropagation();

                if('start' === flag){
                    $scope.endDateOpen = false;
                    $scope.startDateOpen = true;
                }
                else if ('end' === flag){
                    $scope.startDateOpen = false;
                    $scope.endDateOpen = true;
                }
            };

            /**
             * Handler for submit click
             */
            $scope.submit = function(){
                $window.location ="#/results";
            };
        }]);

/**
 * Controller for the results partial
 */
grades.controller("ResultsCtrl",
    ['$scope', '$window','GradesSession', 'GradesService',
        function($scope,$location,GradesSession,GradesService) {

            /*
             * Function to hide messages
             */
            $scope.clickHideMessages = function(){
                $scope.$parent.errors = [];
                $scope.$parent.infos = [];
                $scope.$parent.alerts = [];
                $scope.$parent.successes = [];
            };
            $scope.clickHideMessages();

            $scope.$root.kmeNavLeft = function() {
                $scope.clickHideMessages();
                window.history.back();
            };
            $scope.$parent.infos = [{'name' : msgCat_Loading}];

            GradesService.getGrades(
                    moment(GradesSession.startDate).format("YYYY-MM-DD"),
                    moment(GradesSession.endDate).format("YYYY-MM-DD")
                ).then(
                // Success
                function(results){
                    $scope.clickHideMessages();
                    $scope.serverSuccess = true;
                    $scope.results = results;
                },
                // Failed
                function(error){
                    $scope.clickHideMessages();
                    $scope.$parent.errors = [{'name' : msgCat_Error}];
                });


        }]
);
