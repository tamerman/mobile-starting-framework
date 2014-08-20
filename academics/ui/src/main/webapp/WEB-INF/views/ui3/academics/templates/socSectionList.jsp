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
<div ng-controller="SocSectionController" data-ng-init="init()">
    <div class="alert alert-danger" ng-repeat="error in SocData.errors.error" ng-click="clickHideErrors()">{{error.name}}</div>
    <div class="list-group">
        <a href="#/sectionDetail" class="list-group-item" ng-click="socSectionClick(thisSection)" ng-repeat="thisSection in SocData.socCurrentCatalogNumber.section | orderBy:number">
            <span class="pull-right glyphicon glyphicon-chevron-right black right-decoration"></span>
            <strong>Section {{thisSection.number}} {{thisSection.typeDescription}}</strong><br/>
            <span ng-if="sectionOpen(thisSection)" class="pull-right green">{{thisSection.availableSeats}} {{thisSection.enrollmentStatus}}</span>
            <span ng-if="sectionClosed(thisSection)" class="pull-right red">{{thisSection.enrollmentStatus}}</span>
            <span ng-if="sectionWaitList(thisSection)" class="pull-right orange">{{thisSection.enrollmentStatus}}</span>
            <p ng-hide="thisSection.meeting && thisSection.meeting.length>0">TBA</p>
            <soc-section-list-meeting ng-repeat="thisMeeting in thisSection.meeting | limitTo:sectionMeetingDisplayLimit | orderBy:number"></soc-section-list-meeting>
        </a>
    </div>
</div>
