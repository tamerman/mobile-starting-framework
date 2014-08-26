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
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<div ng-controller="MembershipDetailController" data-ng-init="init()" class="inset-content-10px">
     <form id="theForm" name="theForm" ng-submit="submitForm()" novalidate>
		<fieldset>
			<div class="form-group">					
			    <div class="list-group-item" ng-repeat="user in MembershipData.groupUsers.user">
			    	<input class="pull-left" type="checkbox" ng-model="MembershipData.modifiedGroupUserKeys[$index]" 
			    		ng-true-value="true" ng-false-value="false" ng-checked="false" ng-click="removeGroupUsers($event,user)">{{user.displayName}}
				</div>
			</div>	    
	    </fieldset>		               
        <div class="form-group pull-right">
        	<button onclick="location.href='${pageContext.request.contextPath}/membership'" type="button" class="btn btn-default">Back</button>
           	<button onclick="location.href='#/addGroupUser'" type="button" class="btn btn-default">Add User</button>
           	<button type="submit" class="btn btn-kme" ng-show="MembershipData.groupUsers.user.length > 0" ng-disable="theForm.$invalid">Remove</button>
        </div>	
	</form>
</div>