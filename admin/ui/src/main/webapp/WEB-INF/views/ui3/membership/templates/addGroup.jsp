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
