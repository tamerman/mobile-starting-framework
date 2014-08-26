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
