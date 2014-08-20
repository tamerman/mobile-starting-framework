<%--
  Copyright 2011 The Kuali Foundation Licensed under the Educational Community
  License, Version 2.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
  agreed to in writing, software distributed under the License is distributed
  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied. See the License for the specific language governing
  permissions and limitations under the License.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"%>

<spring:message code="membership.title.addGroup"  	var="addGroupTabLabel"/>
<spring:message code="membership.title.removeGroup" var="removeGroupTabLabel"/>

<div class="inset-content-10px">
	<form id="theForm" name="theForm" ng-submit="submitForm()" role="form" novalidate>
			<fieldset>
				<div class="form-group">
				    <div class="list-group-item" ng-repeat="group in MembershipData.groups.group">
				    	<input class="pull-left" type="checkbox" ng-true-value="true" ng-false-value="false" ng-checked="false" ng-click="removeGroups($event,group)">
				        <a href="#/listUsers" ng-hide="group.children" ng-click="groupClick(group)">
				            <h4 class="wrap">{{group.name}}</h4>
				            <p>{{group.description}}</p>
				        </a>
				    </div>
			    </div>
	    	</fieldset>
	    	
   		    <div class="form-group pull-right">
		       	<button onclick="location.href='#/addGroup'" type="button" class="btn btn-default"><c:out value="${addGroupTabLabel}"/></button>
		       	<button type="submit" class="btn btn-kme" ng-show="MembershipData.groups.group.length > 0" ng-disable="theForm.$invalid"><c:out value="${removeGroupTabLabel}"/></button>
		    </div>
    </form>
</div>