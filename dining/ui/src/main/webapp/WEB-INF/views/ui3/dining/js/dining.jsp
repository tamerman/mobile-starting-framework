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
<spring:message code="dining.noDataAvailable" var="msgNoDataAvailable"/>
<spring:message code="dining.campusLabel" var="msgCampusLabel"/>
<spring:message code="dining.title" var="diningToolTitle"/>
<spring:message code="dining.useMaps" var="useMaps"/>

var dining = angular.module("diningApp", ['ngRoute','ngSanitize','ui.bootstrap'],
function($routeProvider,$locationProvider) {
    $locationProvider.html5Mode(false);
    $routeProvider.
        when('/',               {  templateUrl: '${pageContext.request.contextPath}/dining/templates/diningHallList',     controller: 'DiningController' }).
        when('/menuList',       {  templateUrl: '${pageContext.request.contextPath}/dining/templates/menuList',    controller: 'DiningHallController' }).
        when('/menu',           {  templateUrl: '${pageContext.request.contextPath}/dining/templates/menu',    controller: 'DiningMenuController' }).
        otherwise({ redirectTo: "/" });
});


dining.factory('DiningData',function() {
    return {
        diningHalls:null,
        diningHallGroups:null,
        currentDiningHall:null,
        currentMenu:null,
        errors:null,
        infos:null,
        alerts:null,
        successes:null,
        configUseMaps:<c:out value="${useMaps}"/>,
        diningNoInfo:"<c:out value="${msgNoDataAvailable}"/>",
        diningCampusLabel:"<c:out value="${msgCampusLabel}"/>",
        diningToolTitle:"<c:out value="${diningToolTitle}"/>",
        pageTitle:"<c:out value="${diningToolTitle}"/>"
    };
});

dining.directive('diningCategory', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/dining/templates/menuCategory'
    }
});
dining.directive('diningMenuItem', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/dining/templates/menuItem'
    }
});
dining.directive('notificationList', function() {
    return {
        restrict:'E',
        transclude:true,
        replace:true,
        templateUrl: '${pageContext.request.contextPath}/dining/templates/notificationList'
    }
});

dining.controller("DiningController", function($scope,$http,$routeParams,$templateCache,$location,$sce,$log,$modal,DiningData) {
  $scope.init = function() {
      $scope.DiningData = DiningData;
      DiningData.errors = [];
      DiningData.alerts = [];
      DiningData.infos = [];
      DiningData.successes = [];
      DiningData.pageTitle = DiningData.diningToolTitle;

      $scope.loadDiningHallsWithGroup();

      var menuItems = "{\"menus\": ["+
              "{ \"url\":\"/dining\" , \"label\":\"<c:out value="${msgDiningTitle}"/>\" },"+
              <%--"{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }"+--%>
              "]}";

      $scope.menuItems = eval ("(" + menuItems + ")");
  }

  $scope.kmeNavLeft = function() {
      // send to home screen.
      DiningData.errors = [];
      DiningData.alerts = [];
      DiningData.infos = [];
      DiningData.successes = [];
      window.history.back();
  }

    $scope.loadDiningHallsWithGroup = function() {
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/dining/diningHallGroups?_type=json'
        }).success(function(data, status, headers, config) {
                    if( status != 200 ) {
                        DiningData.errors = eval('({"error":[{"id":'+status+',"name":"'+DiningData.diningNoInfo+'"}]})');
                    }
                    DiningData.diningHallGroups = data;

                }).error(function(data, status, headers, config) {
                    DiningData.errors = eval('({"error":[{"id":'+status+',"name":"'+DiningData.diningNoInfo+'"}]})');
                });
    }

  $scope.diningHallClick = function(obj, path) {
      if( 'undefined' === typeof obj ) {
          DiningData.errors = eval('({"error":[{"id":5000,"name":"Failed to find news source."}]})');
          $log.error("No news source object passed to newsSourceClick.");
      } else {
          DiningData.currentDiningHall = obj;          
      }
  }
  
	$scope.clickHideErrors = function() {     
   		DiningData.errors = [];
	}
	$scope.clickHideAlerts = function() {     
       	DiningData.alerts = [];
	}
	$scope.clickHideInfos = function() {     
       	DiningData.infos = [];
	}
	$scope.clickHideSuccesses = function() {     
       	DiningData.successes = [];
	}
});

dining.controller("DiningHallController", function($scope,$http,$routeParams,$templateCache,$location,$sce,$log,$modal,DiningData) {
    $scope.init = function() {
        $scope.DiningData = DiningData;
        DiningData.errors = [];
      	DiningData.alerts = [];
      	DiningData.infos = [];
      	DiningData.successes = [];
        DiningData.pageTitle = DiningData.currentDiningHall.name;

        var menuItems = "{\"menus\": ["+
                "{ \"url\":\"/dining\" , \"label\":\"<c:out value="${msgDiningTitle}"/>\" },"+
                <%--"{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }"+--%>
                "]}";

        $scope.menuItems = eval ("(" + menuItems + ")");
    }
    
    $scope.mapViewClick = function(obj) {
    	if( 'undefined' === typeof obj ) {
            DiningData.errors = eval('({"error":[{"id":5000,"name":"'+LabData.labNoInfo+'"}]})');
            $log.error("No lab group object passed to labDetailClick.");
        } else {
            if( 'undefined' === typeof (obj.latitude) ) {
                window.location='<c:out value="${pageContext.request.contextPath}"/>/maps#/' + obj.address.city + '/' + obj.address.postalCode + '/' + obj.address.state + '/' + obj.address.street1 + '/' + obj.address.street2;
            } else {
                window.location='<c:out value="${pageContext.request.contextPath}"/>/maps#/' + obj.latitude + '/' + obj.longitude;
            }
        }
    }

    $scope.diningMenuClick = function(obj) {
        if( 'undefined' === typeof obj ) {
            DiningData.errors = eval('({"error":[{"id":5000,"name":"'+DiningData.diningNoInfo+'"}]})');
            $log.error("No menu object passed to diningMenuClick.");
        } else if( 'undefined' === typeof obj.category ) {
            DiningData.errors = eval('({"error":[{"id":5000,"name":"'+DiningData.diningNoInfo+'"}]})');
            $log.error("No items are contained within that menu.");
        } else {
            DiningData.currentMenu = obj;
        }
    }
    
    $scope.clickHideErrors = function() {     
   		DiningData.errors = [];
	}
	$scope.clickHideAlerts = function() {     
       	DiningData.alerts = [];
	}
	$scope.clickHideInfos = function() {     
       	DiningData.infos = [];
	}
	$scope.clickHideSuccesses = function() {     
       	DiningData.successes = [];
	}
});

dining.controller("DiningMenuController", function($scope,$http,$routeParams,$templateCache,$location,$sce,$log,$modal,DiningData) {
    $scope.init = function() {
        $scope.DiningData = DiningData;
        DiningData.errors = [];
      	DiningData.alerts = [];
      	DiningData.infos = [];
      	DiningData.successes = [];
        DiningData.pageTitle = DiningData.currentDiningHall.name + " - "+ DiningData.currentMenu.name;
    }

    $scope.open = function () {
        var modalInstance = $modal.open({
            templateUrl: '${pageContext.request.contextPath}/dining/templates/menuItemAttributeLegend',
            controller: LegendInstanceCtrl
        });

        modalInstance.result.then(function (selectedItem) {
            <%--$scope.selected = selectedItem;--%>
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };
    
    $scope.clickHideErrors = function() {     
   		DiningData.errors = [];
	}
	$scope.clickHideAlerts = function() {     
       	DiningData.alerts = [];
	}
	$scope.clickHideInfos = function() {     
       	DiningData.infos = [];
	}
	$scope.clickHideSuccesses = function() {     
       	DiningData.successes = [];
	}

});

var LegendInstanceCtrl = function ($scope, $modalInstance) {

    $scope.ok = function () {
        $modalInstance.dismiss('cancel');
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};
