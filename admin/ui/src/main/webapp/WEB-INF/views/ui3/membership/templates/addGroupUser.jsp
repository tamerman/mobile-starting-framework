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
<div ng-controller="MembershipAddUserController" data-ng-init="init()" class="inset-content-10px">
	<form id="theForm" name="theForm" ng-submit="submitForm()" novalidate>        	
        <fieldset>
	       	<div class="form-group">	
			    <div class="list-group-item" ng-repeat="user in MembershipData.allUsers.user">	    	
			    	<input type="checkbox" class="pull-left" ng-model="MembershipData.modifiedGroupUserKeys[$index]" 
				    	ng-true-value="true" ng-false-value="false" ng-checked="false" ng-click="addGroupUsers($event,user)"/>{{user.displayName}}        
			    </div>	      
			</div>
	    </fieldset>
	    <div class="form-group pull-right">
	       	<button onclick="location.href='#/listUsers'" type="button" class="btn btn-default">Back</button>
	       	<button type="submit" class="btn btn-kme" ng-show="MembershipData.allUsers.user.length > 0" ng-disable="theForm.$invalid">Submit</button>
	    </div>	        
   	</form>
</div>