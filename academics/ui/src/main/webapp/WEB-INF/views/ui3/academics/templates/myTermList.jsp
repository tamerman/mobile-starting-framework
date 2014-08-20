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
<div ng-controller="MyScheduleController">
    <div class="alert alert-danger" ng-repeat="error in myScheduleData.errors.error" ng-click="clickHideErrors()">{{error.name}}</div>
    <div class="list-group">
        <div ng-repeat="thisTerm in myScheduleData.myTerms.term">
            <div class="list-group-item" ng-hide="thisTerm.academicCareer">{{myScheduleData.notEnrolled}} {{thisTerm.description}}</div>
            <my-career-list ng-repeat="thisCareer in thisTerm.academicCareer"></my-career-list>
        </div>
    </div>
</div>