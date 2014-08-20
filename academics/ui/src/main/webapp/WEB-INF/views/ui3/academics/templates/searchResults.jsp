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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div ng-init="init()">
    <div class="alert alert-danger" ng-repeat="error in SearchData.errors.error" ng-click="clickHideErrors()">{{error.name}}</div>

    <div>
        <div ng-repeat="thisMessage in searchResult.messages">
            <div class="alert alert-danger" ng-if="thisMessage.type=='ERROR'"><p>{{thisMessage.description}}</p></div>
            <div class="alert alert-warning" ng-if="thisMessage.type=='ALERT'"><p>{{thisMessage.description}}</p></div>
            <div class="alert alert-info" ng-if="thisMessage.type=='OTHER'"><p>{{thisMessage.description}}</p></div>
        </div>
    </div>
    <div class="list-group">
        <div ng-click="classDetails(thisSection)" class="list-group-item"
             ng-repeat="thisSection in searchResult.section">
            <span class="pull-right glyphicon glyphicon-chevron-right black right-decoration"></span>
            <strong>{{thisSection.subjectId}} {{thisSection.catalogNumber}} {{thisSection.number}}
                {{thisSection.type}}</strong>

            <p ng-show="thisSection.courseTitle">{{thisSection.courseTitle}}</p>

            <p ng-show="thisSection.classTopic">{{thisSection.classTopic}}</p>
            <span ng-if="sectionOpen(thisSection)" class="pull-right green">{{thisSection.availableSeats}} {{thisSection.enrollmentStatus}}</span>
            <span ng-if="sectionClosed(thisSection)" class="pull-right red">{{thisSection.enrollmentStatus}}</span>
            <span ng-if="sectionWaitList(thisSection)" class="pull-right orange">{{thisSection.enrollmentStatus}}</span>

            <soc-search-result-meeting></soc-search-result-meeting>
        </div>
    </div>
</div>