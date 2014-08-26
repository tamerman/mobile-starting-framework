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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="news.title" var="msgCat_ToolTitle"/>
<spring:message code="news.expand" var="msgCat_Expand"/>
<spring:message code="news.collapse" var="msgCat_Collapse"/>
<spring:message code="news.errorMsg" var="news_errorMsg"/>
<spring:message code="news.noarticles" var="news_noArticles"/>

var news = angular.module("newsApp", ['ngRoute','ngSanitize','ui.bootstrap'],
function($routeProvider,$locationProvider) {
    $locationProvider.html5Mode(false);
    $routeProvider.
             when('/',                   {  templateUrl: '${pageContext.request.contextPath}/news/templates/newsSourceList',     controller: 'NewsController' }).
            when('/articles',                   {  templateUrl: '${pageContext.request.contextPath}/news/templates/newsSearchArticleList',  controller: 'NewsSearchController' }).
            when('/sources',            {  templateUrl: '${pageContext.request.contextPath}/news/templates/newsSourceList',     controller: 'NewsController' }).
            when('/sources/:sourceId',  {  templateUrl: '${pageContext.request.contextPath}/news/templates/newsSourceList',     controller: 'NewsController' }).
            when('/article',            {  templateUrl: '${pageContext.request.contextPath}/news/templates/newsArticleDetail',  controller: 'NewsArticleDetailController' }).
            otherwise({ redirectTo: "/" });
});

news.factory('NewsData',function() {
    return {
        newsPageTitle:null,
        newsBreadcrumbs:[],
        newsSources:null,
        currentNewsSource:null,
        currentNewsArticle:null,
        errors:null,
        infos:null,
        alerts:null,
        successes:null
    };
});


news.controller("NewsController", function($scope,$http,$routeParams,$templateCache,$location,$sce,$log,NewsData) {

    $scope.init = function() {
        $scope.NewsData = NewsData;
        NewsData.errors = [];
     	NewsData.alerts = [];
     	NewsData.infos = [];
     	NewsData.successes = [];
        $scope.newsMsgExpand = "<c:out value="${msgCat_Expand}"/>";
        $scope.newsMsgCollapse = "<c:out value="${msgCat_Collapse}"/>";
        $scope.newsMsgError = "<c:out value="${news_errorMsg}"/>";
        $scope.newsMsgNoArticles = "<c:out value="${news_noArticles}"/>";
        $scope.newsPageTitle = "<c:out value="${msgCat_ToolTitle}"/>";

        $scope.sourceId = $routeParams.sourceId && $routeParams.sourceId || 0;

        NewsData.newsPageTitle = $scope.newsPageTitle;
        $scope.loadSources($scope.sourceId);

        var menuItems = "{\"menus\": ["+
            "{ \"url\":\"/news\" , \"label\":\"News Home\" },"+
            <%--"{ \"url\":\"/preferences\" , \"label\":\"Preferences\" }"+--%>
            "]}";

        $scope.menuItems = eval ("(" + menuItems + ")");
    }

    $scope.kmeNavLeft = function() {
        // send to home screen.
        NewsData.errors = [];
     	NewsData.alerts = [];
     	NewsData.infos = [];
     	NewsData.successes = [];
        window.history.back();
    }
    
    $scope.clickHideErrors = function() {     
   		NewsData.errors = [];
	}
	$scope.clickHideAlerts = function() {     
       	NewsData.alerts = [];
	}
	$scope.clickHideInfos = function() {     
       	NewsData.infos = [];
	}
	$scope.clickHideSuccesses = function() {     
       	NewsData.successes = [];
	}

    $scope.loadSources = function(parentId) {
        if( 'undefined' === typeof parentId ) {
            parentId = 0;
        }
        $http({
            method: 'GET',
            url: '<c:out value="${pageContext.request.contextPath}"/>/services/news/sources/getAllActive/'+parentId+'?compact=true&_type=json'
        }).success(function(data, status, headers, config) {
                if( status != 200 ) {
                    NewsData.errors = eval('({"error":[{"id":'+status+',"name":"Unable to load News Feeds"}]})');
                }
//                NewsData.newsSources = data;
                $scope.newsSources = data;
                if( NewsData.newsBreadcrumbs[NewsData.newsBreadcrumbs.length-1] != parentId ) {
                    NewsData.newsBreadcrumbs.push(parentId);
                }
            }).error(function(data, status, headers, config) {
                NewsData.errors = eval('({"error":[{"id":'+status+',"name":"Unable to load News Feeds"}]})');
            });
    }

    $scope.newsSourceClick = function(obj) {
        if( 'undefined' === typeof obj ) {
            NewsData.errors = eval('({"error":[{"id":5000,"name":"Failed to find news source."}]})');
            $log.error("No news source object passed to newsSourceClick.");
        } else {
            NewsData.currentNewsSource = obj;
        }
    }
});

news.controller("NewsArticleController", function($scope,$http,$routeParams,$templateCache,$location,$sce,$log,NewsData) {

    $scope.init = function() {
        $scope.NewsData = NewsData;
        NewsData.errors = [];
     	NewsData.alerts = [];
     	NewsData.infos = [];
     	NewsData.successes = [];
        $scope.newsMsgExpand = "<c:out value="${msgCat_Expand}"/>";
        $scope.newsMsgCollapse = "<c:out value="${msgCat_Collapse}"/>";
        $scope.newsMsgError = "<c:out value="${news_errorMsg}"/>";
        $scope.newsMsgNoArticles = "<c:out value="${news_noArticles}"/>";
        $scope.newsPageTitle = "<c:out value="${msgCat_ToolTitle}"/>";

        $scope.currentNewsSource = NewsData.currentNewsSource;

        $scope.loadArticles($scope.currentNewsSource.id);
    }

    $scope.loadArticles = function(sourceId) {
        if( 'undefined' === typeof sourceId ) {
            // Reroute people back to the news sources
            NewsData.errors = eval('({"error":[{"id":5200,"name":"Could not find source to load articles from."}]})');
        } else {
            $http({
                method: 'GET',
                url: '<c:out value="${pageContext.request.contextPath}"/>/services/news/sources/getNewsSource/'+sourceId+'?_type=json'
            }).success(function(data, status, headers, config) {
                if( status != 200 ) {
                    NewsData.errors = eval('({"error":[{"id":'+status+',"name":"Unable to load News Feeds"}]})');
                }
                NewsData.currentNewsSource = data.newsSource;
                $scope.currentNewsSource = data.newsSource;
                NewsData.newsPageTitle = $scope.currentNewsSource.title;
            }).error(function(data, status, headers, config) {
                NewsData.errors = eval('({"error":[{"id":'+status+',"name":"Unable to load News Feeds"}]})');
            });
        }
    }

    $scope.newsArticleClick = function(obj) {
        if( 'undefined' === typeof obj ) {
            NewsData.errors = eval('({"error":[{"id":5000,"name":"Failed to find news article."}]})');
            $log.error("No news article object passed to newsArticleClick.");
        } else {
            NewsData.currentNewsArticle = obj;
            NewsData.newsPageTitle = $scope.currentNewsSource.title;
        }
    }

    $scope.newsArticleClickForSamePageDisplay = function(obj) {
        if( 'undefined' === typeof obj ) {
            NewsData.errors = eval('({"error":[{"id":5000,"name":"Failed to find news article."}]})');
            $log.error("No news article object passed to newsArticleClick.");
        } else {
            NewsData.currentNewsArticle = obj;
            NewsData.newsPageTitle = $scope.currentNewsSource.title;
        }
        $('.one').hide();
        $('.two').show();
    }
    
    $scope.clickHideErrors = function() {     
   		NewsData.errors = [];
	}
	$scope.clickHideAlerts = function() {     
       	NewsData.alerts = [];
	}
	$scope.clickHideInfos = function() {     
       	NewsData.infos = [];
	}
	$scope.clickHideSuccesses = function() {     
       	NewsData.successes = [];
	}

});

news.controller("NewsArticleDetailController", function($scope,$http,$templateCache,$location,$sce,$log,NewsData) {
    $scope.init = function() {
        $scope.NewsData = NewsData;
        NewsData.errors = [];
     	NewsData.alerts = [];
     	NewsData.infos = [];
     	NewsData.successes = [];
        $scope.newsMsgExpand = "<c:out value="${msgCat_Expand}"/>";
        $scope.newsMsgCollapse = "<c:out value="${msgCat_Collapse}"/>";
        $scope.newsMsgError = "<c:out value="${news_errorMsg}"/>";
        $scope.newsMsgNoArticles = "<c:out value="${news_noArticles}"/>";
        $scope.newsPageTitle = "<c:out value="${msgCat_ToolTitle}"/>";

        $scope.currentNewsArticle = NewsData.currentNewsArticle;
    }
});

news.controller("NewsSearchController", function($scope,$http,$routeParams,$templateCache,$location,$sce,$log,NewsData) {

    $scope.init = function() {
    console.log("init called ");
        $scope.NewsData = NewsData;
        NewsData.errors = [];
     	NewsData.alerts = [];
     	NewsData.infos = [];
     	NewsData.successes = [];
        $scope.newsMsgExpand = "<c:out value="${msgCat_Expand}"/>";
        $scope.newsMsgCollapse = "<c:out value="${msgCat_Collapse}"/>";
        $scope.newsMsgError = "<c:out value="${news_errorMsg}"/>";
        $scope.newsMsgNoArticles = "<c:out value="${news_noArticles}"/>";
        $scope.newsPageTitle = "<c:out value="${msgCat_ToolTitle}"/>";
        $scope.sourceId = $routeParams.sourceId && $routeParams.sourceId || 0;
        $scope.currentNewsSource = NewsData.currentNewsSource;
        $scope.tempobj={};
        $scope.tempobj.showSearchResult = 0;
        $scope.loadArticles($scope.currentNewsSource.id);
    }

    $scope.loadArticles = function(sourceId) {
        if( 'undefined' === typeof sourceId ) {
            // Reroute people back to the news sources
            NewsData.errors = eval('({"error":[{"id":5200,"name":"Could not find source to load articles from."}]})');
        } else {
            $http({
                method: 'GET',
                url: '<c:out value="${pageContext.request.contextPath}"/>/services/news/sources/getNewsSource/'+sourceId+'?_type=json'
            }).success(function(data, status, headers, config) {
                if( status != 200 ) {
                    NewsData.errors = eval('({"error":[{"id":'+status+',"name":"Unable to load News Feeds"}]})');
                }
                NewsData.currentNewsSource = data.newsSource;
                $scope.currentNewsSource = data.newsSource;
                NewsData.newsPageTitle = $scope.currentNewsSource.title;
            }).error(function(data, status, headers, config) {
                NewsData.errors = eval('({"error":[{"id":'+status+',"name":"Unable to load News Feeds"}]})');
            });
        }
    }
    $scope.loadCachedResults = function() {
        $log.debug("Entered loadCachedResults.");
        if(sessionStorage && sessionStorage.getItem("people_search_results") != null ) {
            $log.debug("Session storage is available in the browser.");
            var cachedData = JSON.parse( sessionStorage.getItem( "people_search_results" ) );
            NewsData.searchUsed = sessionStorage.getItem("people_search_type");
            NewsData.cachedSearch = cachedData;
            NewsData.searchResults = cachedData.searchResults;
            NewsData.subGroupDNs = JSON.parse(sessionStorage.getItem("list_group_DNs"));
            if( NewsData.searchResults.people[0].displayName == "Error" ) {
                NewsData.simpleResultsPage = false;
                NewsData.advancedResultsPage = false;
            } else {
                NewsData.simpleResultsPage = true;
                NewsData.advancedResultsPage = false;
            }
            if( NewsData.searchUsed == 2 ) {
                NewsData.showSimpleSearch = false;
                NewsData.showAdvancedSearch = true;
                NewsData.advancedResultsPage = true;
                NewsData.showAdvancedSearchForm = false;
                $("#searchHide").html("${resultsHide}");
                $("#searchType").html("${searchTypeSimple}");
            } else {
                NewsData.showSimpleSearch = true;
                NewsData.showAdvancedSearch = false;
            }
            $scope.simpleText = NewsData.searchResults.searchCriteria.searchText;
            $scope.firstNameText = NewsData.searchResults.searchCriteria.firstName;
            $scope.lastNameText = NewsData.searchResults.searchCriteria.lastName;
            $scope.matching = NewsData.searchResults.searchCriteria.exactness;
            $scope.checkExact();
            $scope.status = NewsData.searchResults.searchCriteria.status;
            $scope.locationSelect = NewsData.searchResults.searchCriteria.location;
            $scope.usernameText = NewsData.searchResults.searchCriteria.username;
        } else {
            $log.debug("Session storage is unavailable in the browser or no data found.");
            NewsData.simpleResultsPage = false;
            NewsData.advancedResultsPage = false;
            NewsData.searchUsed = 0;
        }
        $log.debug("Leaving loadCachedResults");
    }

	$scope.simpleSubmit = function() {
	    console.log("news data"+JSON.stringify(NewsData));
	    console.log("NewsData.currentNewsSource--"+JSON.stringify(NewsData.currentNewsSource));
	    console.log("simpleText--"+$scope.simpleText);
	    console.log("showSearchResult--"+$scope.tempobj.showSearchResult);
        NewsData.searchUsed = 1;
        $scope.tempobj.showSearchResult = 1;
        console.log("showSearchResult--"+$scope.tempobj.showSearchResult);
		NewsData.searchedFor = $scope.simpleText;
    }
   
    $scope.searchFilter = function(article) {
    	var returnval=false;
    	if(article.title.indexOf($scope.simpleText) == -1 ? false : true) 
    	{
    		returnval= true;	
    	}
    	if( article.description.indexOf($scope.simpleText) == -1 ? false : true)
    	{
    	    returnval= true;
    	}
    	return returnval;
    }

    $scope.newsArticleClick = function(obj) {
        if( 'undefined' === typeof obj ) {
            NewsData.errors = eval('({"error":[{"id":5000,"name":"Failed to find news article."}]})');
            $log.error("No news article object passed to newsArticleClick.");
        } else {
            NewsData.currentNewsArticle = obj;
            NewsData.newsPageTitle = $scope.currentNewsSource.title;
        }
    }

    $scope.newsArticleClickForSamePageDisplay = function(obj) {
        if( 'undefined' === typeof obj ) {
            NewsData.errors = eval('({"error":[{"id":5000,"name":"Failed to find news article."}]})');
            $log.error("No news article object passed to newsArticleClick.");
        } else {
            NewsData.currentNewsArticle = obj;
            NewsData.newsPageTitle = $scope.currentNewsSource.title;
        }
        $('.one').hide();
        $('.two').show();
    }
    
    $scope.clickHideErrors = function() {     
   		NewsData.errors = [];
	}
	$scope.clickHideAlerts = function() {     
       	NewsData.alerts = [];
	}
	$scope.clickHideInfos = function() {     
       	NewsData.infos = [];
	}
	$scope.clickHideSuccesses = function() {     
       	NewsData.successes = [];
	}
});