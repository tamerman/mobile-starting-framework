<%--

    The MIT License
    Copyright (c) 2011 Kuali Mobility Team

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

--%>
<%@ page language="java" contentType="application/javascript; charset=UTF-8" pageEncoding="UTF-8"%>

angular.module('xx-http-error-handling', [])
        .config(function ($provide, $httpProvider, $compileProvider) {
            var elementsList = $();

            // this message will appear for a defined amount of time and then vanish again
            var showMessage = function (content, cl, time) {
                $('<div/>')
                        .addClass(cl)
                        .hide()
                        .fadeIn('fast')
                        .delay(time)
                        .fadeOut('fast', function () {
                            $(this).remove();
                        })
                        .appendTo(elementsList)
                        .text(content);
            };

            // push function to the responseInterceptors which will intercept
            // the http responses of the whole application
            $httpProvider.interceptors.push(function ($timeout, $q) {
                return {
                    // optional method
                    'request': function(config) {
                        // do something on success
                        return config || $q.when(config);
                    },

                    // optional method
                    'requestError': function(rejection) {
                        console.log("requestError rejection: " + JSON.stringify(rejection));
                        // do something on error
                        if (canRecover(rejection)) {
                            return responseOrNewPromise
                        }
                        return $q.reject(rejection);
                    },


                    'response': function(response) {
                        // do something on success
                        //console.log("response: " + JSON.stringify(response));
                        return response || $q.when(response);
                    },

                    'responseError': function(rejection) {
                        // do something on error
                        console.log("responseError rejection: " + JSON.stringify(rejection));
                        console.log("responseError rejection.status: " + rejection.status );

                        switch (rejection.status) {
                            case 0: //  302 handled by broswer
                                showMessage("Not Authenticated, please back to Home page.", 'xx-http-error-message', 60000);
                                break;
                            case 400: // if the status is 400 we return the error
                                showMessage(rejection.data, 'xx-http-error-message', 6000);
                                // if we have found validation error messages we will loop through
                                // and display them
                                if (rejection.data.errors.length > 0) {
                                    for (var i = 0; i < rejection.data.errors.length; i++) {
                                        showMessage(rejection.data.errors[i],
                                                'xx-http-error-validation-message', 6000);
                                    }
                                }
                                break;
                            case 404:
                                showMessage('404 The requested resource is not available.',
                                        'xx-http-error-message', 60000);
                                break;
                            case 500: // if the status is 500 we return an internal server error message
                                showMessage('500 Internal Server Error ',
                                        'xx-http-error-message', 60000);
                                break;
                            default: // for all other errors we display a default error message
                                showMessage('Error ' + rejection.status + ': ' + rejection.data.message,
                                        'xx-http-error-message', 60000);
                        }

                        //if (canRecover(rejection)) {
                        //    return responseOrNewPromise
                        //}
                        return $q.reject(rejection);
                    }
                }
            });

            // this will display the message if there was a http return status
            $compileProvider.directive('httpErrorMessages', function () {
                return {
                    link: function (scope, element, attrs) {
                        elementsList.push($(element));
                    }
                };
            });
        });