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
