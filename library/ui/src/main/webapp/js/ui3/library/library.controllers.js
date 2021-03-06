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

/**
 * Controller for the form
 */
library.controller("NoOpCtrl",
    ['$scope', function ($scope) {
        $scope.$root.kmeNavLeft = function () {
            window.history.back();
        };

        /*
         * Function to hide messages
         */
        $scope.clickHideMessages = function () {
            $scope.$parent.errors = [];
            $scope.$parent.infos = [];
            $scope.$parent.alerts = [];
            $scope.$parent.successes = [];
        };
        $scope.clickHideMessages();
    }
    ])

    .controller("LibraryEditContactCtrl",
    ['$scope', '$routeParams', '$window', 'LibraryService',
        function ($scope, $routeParams, $window, LibraryService) {

            /*
             * Function to hide messages
             */
            $scope.clickHideMessages = function () {
                $scope.$parent.errors = [];
                $scope.$parent.infos = [];
                $scope.$parent.alerts = [];
                $scope.$parent.successes = [];
            };
            $scope.clickHideMessages();

            $scope.$root.kmeNavLeft = function () {
                $scope.clickHideMessages();
                window.history.back();
            };

            LibraryService.getLibraryContactDetails($routeParams.libraryId).then(function (contactDetail) {
                $scope.contactDetail = contactDetail;
            });

            $("form#contactForm").submit(function (e) {
                $.ajax({
                    url: $(this).attr("action"),
                    type: "POST",
                    data: $(this).serialize(),
                    success: function (data, textStatus, jqXHR) {
                        $window.location = "#/viewContact/" + $scope.libraryId;
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.log("Failed to submit");
                    }
                });
                e.preventDefault(); //STOP default action
            });


        }])


    .controller("LibraryEditHoursCtrl",
    ['$scope', '$routeParams', '$window', 'LibraryService',
        function ($scope, $routeParams, $window, LibraryService) {

            /*
             * Function to hide messages
             */
            $scope.clickHideMessages = function () {
                $scope.$parent.errors = [];
                $scope.$parent.infos = [];
                $scope.$parent.alerts = [];
                $scope.$parent.successes = [];
            };
            $scope.clickHideMessages();

            $scope.$root.kmeNavLeft = function () {
                $scope.clickHideMessages();
                window.history.back();
            };

            $("form#libraryHoursForm").submit(function (e) {
                $.ajax(
                    {
                        url: $(this).attr("action"),
                        type: "POST",
                        data: $(this).serialize(),
                        success: function (data, textStatus, jqXHR) {
                            $window.location = "#/viewHours/" + $scope.libraryId
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.log("Failed to submit");
                        }
                    });
                e.preventDefault(); //STOP default action
            });

        }]
)


