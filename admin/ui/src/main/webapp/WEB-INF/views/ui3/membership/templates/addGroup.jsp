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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="inset-content-10px">
	<form id="theForm" name="theForm" ng-submit="submitForm(theForm.$valid)" novalidate>
		<fieldset>
			<div class="form-group" ng-class="{ 'has-error' : theForm.groupName.$invalid && theForm.groupName.$dirty && !theForm.groupName.$focused }">
	            <label for="groupName">Group Name:</label>
	            <input class="form-control" id="groupName" name="groupName" type="text" value="" ng-model="groupName" required group-name-validate />
	            <p ng-show="theForm.groupName.$invalid && theForm.groupName.$dirty && !theForm.groupName.$focused" class="help-block">Group name must be capital separated by - and should start with letter</p>
	        </div>
	        
			<div class="form-group" ng-class="{ 'has-error' : theForm.groupDescription.$invalid && theForm.groupDescription.$dirty && !theForm.groupDescription.$focused }">
	            <label for="groupDescription">Group Description:</label>
	            <textarea class="form-control" rows="3" id="groupDescription" name="groupDescription"  ng-model="groupDescription" maxlength="255" ></textarea>
	            <p ng-show="theForm.groupDescription.$invalid && theForm.groupDescription.$dirty && !theForm.groupDescription.$focused" class="help-block">Please enter a valid group name.</p>
	        </div>
			
			<div class="form-group pull-right">
	           	<button onclick="location.href='${pageContext.request.contextPath}/membership'" type="button" class="btn btn-default">Cancel</button>
	           	<button type="submit" class="btn btn-kme" ng-disable="theForm.$invalid">Submit</button>
	        </div>	
		
		</fieldset>
	</form>
</div>
