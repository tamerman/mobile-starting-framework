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

var sharedprefs = angular.module("sharedPreferenceApp", ['ngSanitize','ui.bootstrap']);

sharedprefs.controller("SharedPreferencesController", function($scope,$http,$window, $templateCache,$location,$sce,$log) {

	var checkClass = "ui-check-char";
	var checkCharacter = "✓";

    $scope.init = function() {
//    	$scope.username = "${username}";
    	$scope.username = "${cookie.currentNetworkId.value}";
        $scope.labToolTitle = "<c:out value="${sendersToolTitle}"/>";
        $scope.pageTitle = $scope.labToolTitle;
        $scope.loadPreferences();
        
        var menuItems = "{\"menus\": ["+
            <%--"{ \"url\":\"/push\" , \"label\":\"Push Notifications\" },"+ --%>
            <%--"{ \"url\":\"/push/history\" , \"label\":\"Push History\" },"+--%>
            <%--"{ \"url\":\"/devices\" , \"label\":\"Push Devices\" },"+--%>
            <%--"{ \"url\":\"/pushsenders\" , \"label\":\"Push Senders\" },"+--%>
            <%--"{ \"url\":\"/pushprefs\" , \"label\":\"Push Preferences\" },"+             --%>
            <%--"{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }"+--%>
            "]}";
        $scope.menuItems = eval ("(" + menuItems + ")");
    };

    $scope.kmeNavLeft = function() {
        window.history.back();
    };

	$scope.login = function(){
		console.log("Login");
		window.location = "mocklogin";
	};

	$scope.loadPreferences = function(){
		if($scope.username != ""){		
			var url = "${pageContext.request.contextPath}/pushprefs/get?username=" + $scope.username;
			$http({
			    method: 'GET',
			    url: url
			}).success(function (data, status, headers, config) {
			    // TODO
				console.log("SUCCESS" );
				var prefs =	data;		
				var length = prefs.preferences.length;
				for(var i = 0; i < length; i++){
					var senderId = prefs.preferences[i].pushSenderID;
					$scope["pushEnabled_"+senderId] = prefs.preferences[i].enabled;
				}
			}).error(function (data, status, headers, config) {
			    // TODO
				console.log("ERROR" + status);
			});
		}
	};

	$scope.updatePreference = function(id, enabled){
		if($scope.username != ""){
			var url = '${pageContext.request.contextPath}/pushprefs/save';
			console.log(url);

			var postData = {
				'username' : $scope.username,
				'senderId' : id,
				'enabled': enabled
			};
			$http({
			    method: 'POST',
			    url: url,
				data : postData
			}).success(function (data, status, headers, config) {
			    // TODO
				console.log("SUCCESS" );
			}).error(function (data, status, headers, config) {
			    // TODO
				console.log("ERROR" + status);
			});
		}
	};

	$scope.toggle = function(id){

		if($scope.username != ""){
			var isEnabled = $scope["pushEnabled_"+id]
			$scope["pushEnabled_"+id] = !isEnabled;
			if(isEnabled){
				console.log("Disable " + id );
				$scope.updatePreference(id, false);
			}else {
				console.log("Enable " + id );
				$scope.updatePreference(id, true);
			}
		}else{
			console.log("No Username set. Can't save preferences.");
		}
	};

	$scope.installClick = function(){
		var apps = "";
		$.get("${pageContext.request.contextPath}/testdata/nativeMobileAppURL.json", function(jdata) {	
	    apps = jQuery.parseJSON(jdata);
		if (/iPhone|iPad|iPod|Apple/i.test(navigator.userAgent)){
			window.location = apps.iPhoneURL;
		}else if (/Blackberry|RIM\sTablet/i.test(navigator.userAgent)){
		     window.location = apps.BlackberryURL;
		}else if (/Android/i.test(navigator.userAgent)){
			window.location = apps.AndroidURL;
	   	}
		});
	};

});