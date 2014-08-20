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

<div ng-controller="EventsCategoryController" data-ng-init="init()">
    <div class="list-group">
        <div ng-repeat="category in EventsData.categories.category | filter:{hasEvents:true}">
            <a href="#/viewEvents" class="list-group-item" ng-hide="category.children" ng-click="categoryClick(category)">
                <span class="pull-right glyphicon glyphicon-chevron-right black"></span>
                {{category.title}}</a>
        </div>
    </div>
</div>