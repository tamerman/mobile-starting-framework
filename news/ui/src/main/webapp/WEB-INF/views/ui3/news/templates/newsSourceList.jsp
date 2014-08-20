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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div ng-controller="NewsController" data-ng-init="init()">
    <div class="list-group">
        <div ng-repeat="newsSource in newsSources.newsSource">
            <a href="#/sources/{{newsSource.id}}" class="list-group-item" ng-show="newsSource.children">
                <span class="pull-right glyphicon glyphicon-chevron-right black right-decoration"></span>
                {{newsSource.name}}</a>
            <a href="#/articles" class="list-group-item" ng-hide="newsSource.children" ng-click="newsSourceClick(newsSource)">
                <span class="pull-right glyphicon glyphicon-chevron-right black right-decoration"></span>
                {{newsSource.name}}</a>
        </div>
    </div>
</div>
