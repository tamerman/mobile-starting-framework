<%--
Copyright 2014 The Kuali Foundation Licensed under the Educational
Community License, Version 2.0 (the "License"); you may not use this file
except in compliance with the License. You may obtain a copy of the License
at http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
agreed to in writing, software distributed under the License is distributed
on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied. See the License for the specific language governing
permissions and limitations under the License.
--%>
        <%@ page language="java" contentType="application/javascript; charset=UTF-8" pageEncoding="UTF-8"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
        <spring:message code="membership.title" var="msgCat_ToolTitle"/>

        var membership = angular.module("membershipApp", ['ngRoute', 'ngSanitize', 'ui.bootstrap'],
                function ($routeProvider, $locationProvider) {
                    $locationProvider.html5Mode(false);
                    $routeProvider.
                            when('/', 				{  templateUrl: '${pageContext.request.contextPath}/membership/templates/listGroups', controller: 'MembershipController' }).
                            when('/listUsers', 		{  templateUrl: '${pageContext.request.contextPath}/membership/templates/listUsers', controller: 'MembershipDetailController' }).
                            when('/addGroup', 		{  templateUrl: '${pageContext.request.contextPath}/membership/templates/addGroup', controller: 'MembershipAddController' }).
                            when('/addGroupUser', 	{  templateUrl: '${pageContext.request.contextPath}/membership/templates/addGroupUser', controller: 'MembershipAddUserController' }).
                            otherwise({ redirectTo: "/" });
                });

        membership.factory('MembershipData', function () {
            return {
                groups: null,
                modifiedGroups: null,
                currentGroup: null,
                groupUsers: null,
                modifiedGroupUsers:null,
                modifiedGroupUserKeys:[],
                allUsers:null,                
                currentUser: null,
                errors:null,
        		infos:null,
        		alerts:null,
        		successes:null,
                membershipPageTitle: null
            };
        });
        
        membership.directive('notificationList', function() {
		    return {
		        restrict:'E',
		        transclude:true,
		        replace:true,
		        templateUrl: '${pageContext.request.contextPath}/membership/templates/notificationList'
		    }
		});
		
		membership.directive('groupNameValidate', function() {
			return {
		        require: 'ngModel',
		        link: function(scope, elm, attrs, ctrl) {
		            ctrl.$parsers.unshift(function(viewValue) {
		                
		                scope.groupName = (viewValue && /[A-Z][-][A-Z]/.test(viewValue)) ? 'valid' : undefined;
		
		                if(scope.groupName) {
		                    ctrl.$setValidity('grp', true);
		                    return viewValue;
		                } else {
		                    ctrl.$setValidity('grp', false);                    
		                    return undefined;
		                }
		
		            });
		        }
		    };
		});
        

        membership.controller("MembershipController", function ($scope, $http, $routeParams, $templateCache, $location, $sce, $log, MembershipData) {

            $scope.init = function () {
                $scope.MembershipData = MembershipData;
                MembershipData.errors = [];
		      	MembershipData.alerts = [];
		      	MembershipData.infos = [];
		      	MembershipData.successes = [];                
                $scope.msgCat_ToolTitle = "<c:out value="${msgCat_ToolTitle}"/>";
                $scope.membershipPageTitle = "<c:out value="${msgCat_ToolTitle}"/>";
                MembershipData.eventsPageTitle = $scope.membershipPageTitle;

                $scope.loadGroups();

                var menuItems = "{\"menus\": [" +
                        "{ \"url\":\"/membership\" , \"label\":\"Membership Home\" }," +
                        <%--"{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }" +--%>
                        "]}";

                $scope.menuItems = eval("(" + menuItems + ")");
            }


            $scope.kmeNavLeft = function () {
                // send to home screen.
                MembershipData.errors = [];
                MembershipData.alerts = [];
                MembershipData.infos = [];
                MembershipData.successes = [];
                window.history.back();
            }
            
            $scope.manageGroupUser = function(userGroup, user, add) {
            	var results = MembershipData.modifiedGroupUsers.user; 
				name = name.toUpperCase();
				for (index = 0; index < userGroup.length; ++index) {
			        entry = userGroup[index];
			        if (entry && entry.loginName && entry.loginName.indexOf(user.loginName) !== -1) {
			        	if(add == true){			        		
			            	results.push(entry);
			            } else {
			            	results.splice(results.indexOf(entry), 1);
			            }
			        }
			    }
			    MembershipData.modifiedGroupUsers.user = results;
			}
			
			$scope.manageGroups = function(groups, group, remove) {
            	var results = MembershipData.modifiedGroups.group; 
				for (index = 0; index < groups.length; ++index) {
			        entry = groups[index];
			        if (entry && entry.name && entry.name.indexOf(group.name) !== -1) {
			        	if(remove == true){			        		
			            	results.push(entry);
			            } else {
			            	results.splice(results.indexOf(entry), 1);
			            }
			        }
			    }
			    MembershipData.modifiedGroups.group = results;
			}
            
            $scope.isActive = function (viewLocation) {
                var active = (viewLocation === $location.path());
                return active;
            };

            $scope.loadGroups = function () {                
                $http({
                    method: 'GET',
                    url: '<c:out value="${pageContext.request.contextPath}"/>/services/membership/groups?_type=json'
                }).success(function (data, status, headers, config) {
                    if (status != 200) {
                        MembershipData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load Groups"}]})');
                    }
                    if (data.group.length == 0){
                        MembershipData.infos = eval('({"info":[{"id":' + status + ',"name":"Group is empty"}]})');
                    }
                    MembershipData.groups = data;
                    MembershipData.modifiedGroups = angular.copy(data);
                    MembershipData.modifiedGroups.group = [];
                    $scope.membershipPageTitle = "<c:out value="${msgCat_ToolTitle}"/>";
                    MembershipData.membershipPageTitle = $scope.membershipPageTitle;

                }).error(function (data, status, headers, config) {
                    MembershipData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load Groups"}]})');
                });
            }
            
            $scope.submitForm = function () {

                MembershipData.errors = [];
                MembershipData.alerts = [];
                MembershipData.infos = [];
                MembershipData.successes = [];
                
                $scope.submitted = true;

                //if (isValid) {
                    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";

                    var url = "<c:out value="${pageContext.request.contextPath}"/>/services/membership/removeGroups";
                    $http({
                        method: 'POST',
                        url: url,
                        data: MembershipData.modifiedGroups,
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                    }).success(function (data, status, headers, config) {
                        // TODO
                        console.log("SUCCESS");
                        MembershipData.successes = eval('({"success":[{"id": " + status + ","name": "Group removed" }]})');
                        MembershipData.error = false;
                        $scope.loadGroups();
                    }).error(function (data, status, headers, config) {
                        // TODO
                        console.log("ERROR" + status);
                        MembershipData.errors = eval('({"error":[{"id": " + status + ","name": $scope.noInfoError }]})');
                        MembershipData.error = true;
                    });
                //}
            }
            
            $scope.removeGroups = function ($event,group) {
            	var checkbox = $event.target;
            	$scope.manageGroups(MembershipData.groups.group, group, checkbox.checked);
            	console.log(MembershipData.modifiedGroups);
            	console.log(checkbox.checked);
            }

            $scope.groupClick = function (obj) {
                if ('undefined' === typeof obj) {
                    MembershipData.errors = eval('({"error":[{"id":5000,"name":"Failed to find group data."}]})');
                } else {
                    MembershipData.currentGroup = obj;
                }
            }
            
            $scope.clickHideErrors = function() {     
		   		MembershipData.errors = [];
			}
			$scope.clickHideAlerts = function() {     
		       	MembershipData.alerts = [];
			}
			$scope.clickHideInfos = function() {     
		       	MembershipData.infos = [];
			}
			$scope.clickHideSuccesses = function() {     
		       	MembershipData.successes = [];
			}
        });


        membership.controller("MembershipAddController", function ($scope, $http, $routeParams, $templateCache, $location, $sce, $log, MembershipData) {
            $scope.init = function () {
                $scope.MembershipData = MembershipData;
                MembershipData.errors = [];
		      	MembershipData.alerts = [];
		      	MembershipData.infos = [];
		      	MembershipData.successes = [];
                //$scope.eventCategory = "<c:out value="${eventCategory}"/>";
                //$scope.eventsPageTitle = "<c:out value="${eventCategory}"/>";
                //EventsData.eventsPageTitle = $scope.eventsPageTitle;
            }
            
            $scope.submitForm = function (isValid) {

                MembershipData.errors = [];
                MembershipData.alerts = [];
                MembershipData.infos = [];
                MembershipData.successes = [];
                
                $scope.submitted = true;

                if (isValid) {
                    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
                    var data = {
	                    groupName : $scope.groupName,
	                    groupDescription : $scope.groupDescription,
	                    userAgent : '${header['User-Agent']}'
					};
					
                    console.log(data);

                    var url = "<c:out value="${pageContext.request.contextPath}"/>/services/membership/addGroup";
                    $http({
                        method: 'POST',
                        url: url,
                        data: data,
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                    }).success(function (data, status, headers, config) {
                        // TODO
                        console.log("SUCCESS");
                        MembershipData.successes = eval('({"success":[{"id": " + status + ","name": "Group inserted" }]})');
                        MembershipData.error = false;
                    }).error(function (data, status, headers, config) {
                        // TODO
                        console.log("ERROR" + status);
                        MembershipData.errors = eval('({"error":[{"id": " + status + ","name": $scope.noInfoError }]})');
                        MembershipData.error = true;
                    });
                }
            }
            
        });
        
        membership.controller("MembershipAddUserController", function ($scope, $http, $routeParams, $templateCache, $location, $sce, $log, MembershipData) {
            $scope.init = function () {
                $scope.MembershipData = MembershipData;
                MembershipData.errors = [];
		      	MembershipData.alerts = [];
		      	MembershipData.infos = [];
		      	MembershipData.successes = [];
                //$scope.eventCategory = "<c:out value="${eventCategory}"/>";
                //$scope.eventsPageTitle = "<c:out value="${eventCategory}"/>";
                //EventsData.eventsPageTitle = $scope.eventsPageTitle;
                
                 $scope.loadUsers();
            }
            
            $scope.loadUsers = function () {
                $http({
                    method: 'GET',
                    url: '<c:out value="${pageContext.request.contextPath}"/>/services/membership/getAllExceptGroup/' + MembershipData.currentGroup.id + '?_type=json'
                }).success(function (data, status, headers, config) {
	                      if (status != 200) {
	                          MembershipData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load Category Feeds"}]})');
	                      }
	                      if (data.user.length == 0){
		                      MembershipData.infos = eval('({"info":[{"id":' + status + ',"name":"No users to add"}]})');
		                  }
	                      MembershipData.allUsers = data;
	                      MembershipData.modifiedGroupUsers = angular.copy(data);
	                      MembershipData.modifiedGroupUsers.user = [];      
	                      // $scope.membershipPageTitle = "<c:out value="${eventCategory}"/>";
	                      // MembershipData.membershipPageTitle = MembershipData.membershipPageTitle;

            		}).error(function (data, status, headers, config) {
                            MembershipData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load Group Users"}]})');
                		});
            }
                        
            $scope.addGroupUsers = function ($event,user) {
            	var checkbox = $event.target;
            	$scope.manageGroupUser(MembershipData.allUsers.user, user, checkbox.checked);
            	console.log(MembershipData.modifiedGroupUsers);
            	console.log(checkbox.checked);
            }
            
            $scope.submitForm = function () {

                MembershipData.errors = [];
                MembershipData.alerts = [];
                MembershipData.infos = [];
                MembershipData.successes = [];
                
                $scope.submitted = true;

                //if (isValid) {
                    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
                    
                    var data = {
	                    group : MembershipData.currentGroup,
	                    users : MembershipData.modifiedGroupUsers.user,
	                    userAgent : '${header['User-Agent']}'
					};
					
                    console.log(data);

                    var url = "<c:out value="${pageContext.request.contextPath}"/>/services/membership/addUserToGroup";
                    $http({
                        method: 'POST',
                        url: url,
                        data: data,
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                    }).success(function (data, status, headers, config) {
                        // TODO
                        console.log("SUCCESS");
                        MembershipData.successes = eval('({"success":[{"id": " + status + ","name": "Group Modified" }]})');
                        MembershipData.error = false;
                    }).error(function (data, status, headers, config) {
                        // TODO
                        console.log("ERROR" + status);
                        MembershipData.errors = eval('({"error":[{"id": " + status + ","name": $scope.noInfoError }]})');
                        MembershipData.error = true;
                    });
                //}
            }
        });


        membership.controller("MembershipDetailController", function ($scope, $http, $routeParams, $templateCache, $location, $sce, $log, MembershipData) {
            $scope.init = function () {
                $scope.MembershipData = MembershipData;
                MembershipData.errors = [];
		      	MembershipData.alerts = [];
		      	MembershipData.infos = [];
		      	MembershipData.successes = [];                
                $scope.currentGroup = MembershipData.currentGroup;
                
                $scope.loadGroupUsers();
            }
			
			$scope.loadGroupUsers = function () {
                $http({
                    method: 'GET',
                    url: '<c:out value="${pageContext.request.contextPath}"/>/services/membership/userByGroup/' + MembershipData.currentGroup.id + '?_type=json'
                }).success(function (data, status, headers, config) {
	                      if (status != 200) {
	                          MembershipData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load Category Feeds"}]})');
	                      }
	                      if (data.user.length == 0){
                        	MembershipData.infos = eval('({"info":[{"id":' + status + ',"name":"Group has no users"}]})');
                    	  }
	                      MembershipData.groupUsers = data;
	                      MembershipData.modifiedGroupUsers = angular.copy(data);
	                      MembershipData.modifiedGroupUsers.user = [];      
	                      // $scope.membershipPageTitle = "<c:out value="${eventCategory}"/>";
	                      // MembershipData.membershipPageTitle = MembershipData.membershipPageTitle;

            		}).error(function (data, status, headers, config) {
                            MembershipData.errors = eval('({"error":[{"id":' + status + ',"name":"Unable to load Group Users"}]})');
                		});
            }
            
            $scope.removeGroupUsers = function ($event,user) {
            	var checkbox = $event.target;
            	$scope.manageGroupUser(MembershipData.groupUsers.user, user, checkbox.checked);
            	console.log(MembershipData.modifiedGroupUsers);
            	console.log(checkbox.checked);
            }
            
            $scope.submitForm = function () {

                MembershipData.errors = [];
                MembershipData.alerts = [];
                MembershipData.infos = [];
                MembershipData.successes = [];
                
                $scope.submitted = true;

                //if (isValid) {
                    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
                    
                    var data = {
	                    group : MembershipData.currentGroup,
	                    users : MembershipData.modifiedGroupUsers.user,
	                    userAgent : '${header['User-Agent']}'
					};
					
                    console.log(data);

                    var url = "<c:out value="${pageContext.request.contextPath}"/>/services/membership/removeUserFromGroup";
                    $http({
                        method: 'POST',
                        url: url,
                        data: data,
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                    }).success(function (data, status, headers, config) {
                        // TODO
                        console.log("SUCCESS");
                        MembershipData.successes = eval('({"success":[{"id": " + status + ","name": "Group Modified" }]})');
                        MembershipData.error = false;
                        $scope.loadGroupUsers();
                    }).error(function (data, status, headers, config) {
                        // TODO
                        console.log("ERROR" + status);
                        MembershipData.errors = eval('({"error":[{"id": " + status + ","name": $scope.noInfoError }]})');
                        MembershipData.error = true;
                    });
                //}
            }
        });
