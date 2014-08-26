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

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme3" uri="http://kuali.org/mobility/v3.0"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:message code="feedback.title" 	var="msgCat_ToolTitle"/>

<kme3:page title="${msgCat_ToolTitle}" toolName="feedback" ngAppName="feedbackApp" ngControllerName="FeedbackController" ngInitFunction="init" backFunction="kmeNavLeft" cssFilename="feedback" jsFilename="feedback">

<div ng-view="" class="main-view inset-content-10px">

	<div class="alert alert-danger" ng-click="clickHideErrors()" ng-repeat="thisError in errors.error">{{thisError.name}}</div>
	<div class="alert alert-success" ng-click="clickHideSuccesses()" ng-repeat="thisSuccess in successes.success">{{thisSuccess.name}}</div>
	<div class="alert alert-info" ng-click="clickHideInfos()" ng-repeat="thisInfo in infos.info">{{thisInfo.name}}</div>
	<div class="alert alert-warning" ng-click="clickHideAlerts()" ng-repeat="thisAlert in alerts.alert">{{thisAlert.name}}</div>

	<form id="theForm" name="theForm" ng-submit="submitForm(theForm.$valid)" novalidate>

            <fieldset>
                <div class="form-group">
                    <label for="service"><spring:message code="feedback.subject" />:</label>
                    <select class="form-control" id="service" name="service" ng-model="service" ng-init="service='General Feedback'">
                        <c:forEach var="subject" items="${subjectList}" varStatus="status">
                            <option value="${subject.key}">${subject.value}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="deviceType"><spring:message code="feedback.devicetype" /></label>
                    <select class="form-control" id="deviceType" name="deviceType" ng-model="deviceType">
	                    <c:forEach var="devicetype" items="${deviceTypes}" varStatus="status">
							<spring:message code="${devicetype.value}" var="deviceLabel"/>
                        	<option value="${devicetype.key}">${deviceLabel}</option>
		                </c:forEach>
                    </select>
                    <form:errors path="deviceType"/>
                </div> 
                <div class="form-group" ng-class="{ 'has-error' : theForm.noteText.$invalid && (theForm.noteText.$dirty || submitted) && !theForm.noteText.$focused }">
                    <label for="noteText"><spring:message code="feedback.message" /></label>
                    <textarea class="form-control" id="noteText" name="noteText" rows="3" ng-model="noteText" required maxlength="2000" placeholder="Feedback" ng-focus></textarea>
                    <p ng-show="theForm.noteText.$invalid && (theForm.noteText.$dirty || submitted) && !theForm.noteText.$focused" class="help-block">Please type some feedback into the input box.</p>
                </div>
                <div class="form-group" ng-class="{ 'has-error' : theForm.email.$invalid && theForm.email.$dirty && !theForm.email.$focused }">
                    <label for="email"><spring:message code="feedback.youremail" /></label>
                    <input class="form-control" id="email" name="email" type="email" value="" ng-model="email" placeholder="E-mail Address" ng-focus />
                    <p ng-show="theForm.email.$invalid && theForm.email.$dirty && !theForm.email.$focused" class="help-block">Please enter a valid email.</p>
                </div>
            </fieldset>
            
            <div class="form-group pull-right">
            	<button onclick="location.href='${pageContext.request.contextPath}/home'" type="button" class="btn btn-default"><spring:message code="feedback.cancel" /></button>
            	<button type="submit" class="btn btn-kme" ng-disable="theForm.$invalid"><spring:message code="feedback.submit" /></button>
            </div>	
	
	</form>
	
</div>

    <script type="text/javascript">
        function checkMaxLength(textareaName, maxLength, infoDiv){
            currentLengthInTextarea = $("textarea[name="+ textareaName +"]").val().length;
            $("#"+infoDiv).text(parseInt(maxLength) - parseInt(currentLengthInTextarea));

            if (currentLengthInTextarea > (maxLength)) {
                $("textarea[name="+ textareaName +"]").val($("textarea[name="+ textareaName +"]").val().slice(0, maxLength));
                $("#"+infoDiv).text(0);
            }
        }

        $(function(){
            var maxLength =$("textarea#noteText").attr("maxlength");
            $('textarea[name="noteText"]').after("<div><span id='remLengForOther'>" + maxLength + "</span> characters left</div>");
            $('textarea[name="noteText"]').bind("keyup change", function(){checkMaxLength("noteText", maxLength, "remLengForOther"); } );
        });
    </script>

</kme3:page>