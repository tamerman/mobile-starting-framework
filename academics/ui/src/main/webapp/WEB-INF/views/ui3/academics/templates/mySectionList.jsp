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
<div ng-controller="MyScheduleListController" data-ng-init="init()">
    <div class="alert alert-danger" ng-repeat="error in myScheduleData.errors.error" ng-click="clickHideErrors()">{{error.name}}</div>
    <div class="list-group">
        <a href="#/sectionDetail" class="list-group-item" ng-click="mySectionClick(thisSection)" ng-repeat="thisSection in mySchedule.section | orderBy:number">
            <span class="pull-right glyphicon glyphicon-chevron-right black right-decoration"></span>
            <span class="soc-section-header">{{thisSection.subjectId}} {{thisSection.catalogNumber}} {{thisSection.number}} {{thisSection.type}}</span>
            <p ng-show="thisSection.courseTitle">{{thisSection.courseTitle}}</p>
            <p ng-show="thisSection.classTopic">{{thisSection.classTopic}}</p>
            <span class="academics-grade" ng-show="thisSection.grade">{{thisSection.grade}}</span>
            <div ng-if="sectionWaitList(thisSection)">
                <span class="soc-section-left">Status</span>
                <span class="soc-section-right orange">Wait List / Position: {{thisSection.waitTotal}}</span>
            </div>
            <my-section-list-meeting ng-repeat="thisMeeting in thisSection.meeting | orderBy:number | limitTo:sectionMeetingDisplayLimit"></my-section-list-meeting>
        </a>
    </div>
</div>
