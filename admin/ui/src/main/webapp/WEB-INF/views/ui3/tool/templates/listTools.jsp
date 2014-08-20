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

<spring:message code="common.add"  	var="addLabel"/>
<spring:message code="common.remove" var="removeLabel"/>

<div class="inset-content-10px">
	<form id="theForm" name="theForm" ng-submit="submitForm()" role="form" novalidate>
			<fieldset>
				<div class="form-group">
				    <div class="list-group-item" ng-repeat="tool in ToolData.tools.tool">
				    	<input class="pull-left" type="checkbox" ng-true-value="true" ng-false-value="false" ng-checked="false" ng-click="removeTools($event,tool)">
				        <a href="#/modifyTool" ng-hide="tool.children" ng-click="toolClick(tool)">
				            <h4 class="wrap">{{tool.title}}</h4>
				            <p>{{tool.description}}</p>
				        </a>
				    </div>
			    </div>
	    	</fieldset>
	    	
   		    <div class="form-group pull-right">
		       	<button onclick="location.href='#/addTool'" type="button" class="btn btn-default"><c:out value="${addLabel}"/></button>
		       	<button type="submit" class="btn btn-kme" ng-show="ToolData.tools.tool.length > 0" ng-disable="theForm.$invalid"><c:out value="${removeLabel}"/></button>
		    </div>
    </form>
</div>