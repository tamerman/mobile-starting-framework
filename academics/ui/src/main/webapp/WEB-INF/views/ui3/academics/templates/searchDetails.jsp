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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="academics.term" var="msgAcademicsTerm"/>
<spring:message code="academics.schoolCollege" var="msgAcademicsSchoolCollege"/>
<spring:message code="academics.subject" var="msgAcademicsSubject"/>
<spring:message code="academics.course" var="msgAcademicsCourse"/>
<spring:message code="academics.instructor" var="msgAcademicsInstructor"/>
<div ng-init="init()" class="inset-content-10px">
    <div class="alert alert-danger" ng-repeat="error in SearchData.errors.error" ng-click="clickHideErrors()">{{error.name}}</div>
    <form>
		<div class="form-group">
			<label for="termSelect">${msgAcademicsTerm}:</label>
            <select class="form-control" id="termSelect" name="termSelect" ng-model="searchCriteria.termId" ng-change="termChange()" ng-options="term.id as term.description for term in terms" >
            </select>
		</div>
		<div class="form-group">
			<label for="schoolSelect">${msgAcademicsSchoolCollege}:</label>
            <select class="form-control" id="schoolSelect" name="schoolSelect" ng-model="searchCriteria.careerId" ng-change="schoolChange()" ng-options="school.id as school.description for school in schools">
            </select>
		</div>
		<div class="form-group">
			<label for="subjectSelect">${msgAcademicsSubject}:</label>
            <select class="form-control" id="subjectSelect" name="subjectSelect" ng-model="searchCriteria.subjectId" ng-change="subjectChange()" ng-options="subject.id as subject.description for subject in subjects">
            </select>
		</div>
		<div class="form-group">
			<label for="courseNumber">${msgAcademicsCourse} #</label>
            <input id="courseNumber" type="text" class="form-control" ng-model="searchCriteria.catalogNumber">
        </div>
		<div class="form-group">
			<label for="instructorName">${msgAcademicsInstructor}:</label>
            <input id="instructorName" type="text" class="form-control" ng-model="searchCriteria.instructor">
		</div>
        <div class="form-group colHolder">
            <div class="col1">
                <label for="oneLevel">
                    <input id="oneLevel" type="checkbox" ng-model="oneLevel" ng-disabled="searchCriteria.catalogNumber.length>0" ng-checked="!(searchCriteria.catalogNumber.length>0) && oneLevel">&nbsp;100 Level</label>
                <span class="hiddenSeparator"></span>
                <label for="twoLevel">
                    <input id="twoLevel" type="checkbox" ng-model="twoLevel" ng-disabled="searchCriteria.catalogNumber.length>0" ng-checked="!(searchCriteria.catalogNumber.length>0) && twoLevel">&nbsp;200 Level</label>
                <span class="hiddenSeparator"></span>
                <label for="threeLevel">
                    <input id="threeLevel" type="checkbox" ng-model="threeLevel" ng-disabled="searchCriteria.catalogNumber.length>0" ng-checked="!(searchCriteria.catalogNumber.length>0) && threeLevel">&nbsp;300 Level</label>
            </div>
            <div class="col2">
                <label for="fourLevel">
                    <input id="fourLevel" type="checkbox" ng-model="fourLevel" ng-disabled="searchCriteria.catalogNumber.length>0" ng-checked="!(searchCriteria.catalogNumber.length>0) && fourLevel">&nbsp;400 Level</label>
                <span class="hiddenSeparator"></span>
                <label for="fiveLevelPlus">
                    <input id="fiveLevelPlus" type="checkbox" ng-model="fiveLevelPlus" ng-disabled="searchCriteria.catalogNumber.length>0" ng-checked="!(searchCriteria.catalogNumber.length>0) && fiveLevelPlus">&nbsp;500+ Level</label>
            </div>
        </div>
		<div class="form-group">
            <label for="openClasses">
                <input id="openClasses" type="checkbox" ng-model="searchCriteria.showOpen"  ng-true-value="on" ng-false-value="off" >&nbsp;Show Open Classes Only</label>
        </div>
        <button type="submit" class="btn btn-kme" ng-disabled="!( searchCriteria.careerId!='ALL' || searchCriteria.subjectId!='ALL' || !!searchCriteria.catalogNumber || !!searchCriteria.instructor || oneLevel || twoLevel || threeLevel || fourLevel || fiveLevelPlus)" ng-click="courseSearch()">Search</button>
    </form>
</div>