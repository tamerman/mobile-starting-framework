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
<div class="soc-section-header list-group-item">
    <p class="soc-section-header-line1">{{myScheduleData.myCurrentSection.subjectId}} {{myScheduleData.myCurrentSection.catalogNumber}} {{myScheduleData.myCurrentSection.number}} {{myScheduleData.myCurrentSection.type}}</p>
    <p class="soc-section-header-line2" ng-show="myScheduleData.myCurrentSection.courseTitle">{{myScheduleData.myCurrentSection.courseTitle}}</p>
    <p class="soc-section-header-line2" ng-show="myScheduleData.myCurrentSection.classTopic">{{myScheduleData.myCurrentSection.classTopic}}</p>
    <span class="academics-grade" ng-show="myScheduleData.myCurrentSection.grade">{{myScheduleData.myCurrentSection.grade}}</span>
</div>