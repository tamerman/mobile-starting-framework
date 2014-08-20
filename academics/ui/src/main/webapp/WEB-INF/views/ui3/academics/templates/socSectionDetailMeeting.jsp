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
<div>
    <div ng-show="thisMeeting.startDate && thisMeeting.endDate">
        <span class="soc-section-left">Start/End</span>
        <span class="soc-section-right">{{thisMeeting.startDate}}<span ng-show="thisMeeting.endDate"> - </span>{{thisMeeting.endDate}}</span>
    </div>
    <div>
        <span class="soc-section-left">Time</span>
        <span class="soc-section-right">{{thisMeeting.days}} <span ng-show="thisMeeting.times!=thisMeeting.days">{{thisMeeting.times}}</span></span>
    </div>
    <div>
        <span class="soc-section-left">Location</span>
        <span class="soc-section-right">{{thisMeeting.location}}</span>
    </div>
    <div>
        <span class="soc-section-left">Instructor</span>
        <span class="soc-section-right"><p ng-repeat="thisInstructor in thisMeeting.instructor">{{thisInstructor}}</p></span>
    </div>
    <div ng-if="thisMeeting.topic">
        <span class="soc-section-left">Topic</span>
        <span class="soc-section-right">{{thisMeeting.topic}}</span>
    </div>
</div>
