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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<spring:message code="news.articledetail.samepagedisplay" var="articleDetailDisplay"/>
<div ng-controller="NewsArticleController" data-ng-init="init()">
    <div class="one">
        <div class="list-group">
            <div class="list-group-item list-header">{{currentNewsSource.title}}</div>

            <div ng-switch on="${articleDetailDisplay}">
                <div ng-switch-when="false">
                    <a href="#/article" ng-click="newsArticleClick(newsArticle)" id="{{newsArticle.articleId}}" class="list-group-item" ng-repeat="newsArticle in currentNewsSource.articles">
                        <span class="pull-right glyphicon glyphicon-chevron-right black right-decoration"></span>
                        {{newsArticle.title}}</a>
                </div>
                <div ng-switch-when="true">
                    <a ng-click="newsArticleClickForSamePageDisplay(newsArticle)" class="list-group-item" ng-repeat="newsArticle in currentNewsSource.articles">
                        <span class="pull-right glyphicon glyphicon-chevron-right black right-decoration"></span>
                        {{newsArticle.title}}
                    </a>
                </div>
            </div>

        </div>
    </div>

    <div class="two" style="display: none">
        <div class="list-group">
            <div class="list-group-item list-header">{{NewsData.currentNewsArticle.title}}</div>
            <div class="list-group-item" ng-bind-html="NewsData.currentNewsArticle.description"></div>
        </div>
    </div>
</div>
