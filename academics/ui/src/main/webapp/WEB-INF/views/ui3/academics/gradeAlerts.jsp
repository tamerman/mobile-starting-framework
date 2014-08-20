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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme3" uri="http://kuali.org/mobility/v3.0" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="academics.gradeAlerts" var="msgGradeAlertsTitle"/>
<spring:message code="academics.gradeAlerts.label" var="msgGradeAlertsLabel"/>
<spring:message code="academics.gradeAlerts.msg" var="msgGradeAlertsMsg"/>
<spring:message code="academics.gradeAlerts.warning" var="msgGradeAlertsWarning"/>
<spring:message code="academics.gradeAlerts.notice1" var="msgGradeAlertsNotice1"/>
<spring:message code="academics.gradeAlerts.notice2" var="msgGradeAlertsNotice2"/>
<spring:message code="academics.gradeAlerts.notice3" var="msgGradeAlertsNotice3"/>
<spring:message code="academics.gradeAlerts.android" var="msgGradeAlertsAndroid"/>
<spring:message code="academics.gradeAlerts.androidUrl" var="msgGradeAlertsAndroidUrl"/>
<spring:message code="academics.gradeAlerts.ios" var="msgGradeAlertsIos"/>
<spring:message code="academics.gradeAlerts.iosUrl" var="msgGradeAlertsIosUrl"/>
<spring:message code="academics.gradeAlerts.msgOn" var="msgGradeAlertsMsgOn"/>
<spring:message code="academics.gradeAlerts.msgOff" var="msgGradeAlertsMsgOff"/>
<spring:message code="academics.gradeAlerts.testButton" var="msgAcademicsGradeAlertsTestButton"/>
<spring:message code="academics.gradeAlerts.testMsg0" var="testMsg0"/>
<spring:message code="academics.gradeAlerts.testMsg1" var="testMsg1"/>
<spring:message code="academics.gradeAlerts.testMsg2" var="testMsg2"/>
<spring:message code="academics.gradeAlerts.testMsg12" var="testMsg12"/>
<spring:message code="academics.gradeAlerts.androidSupportUrl" var="msgGradeAlertsAndroidSupportUrl"/>
<spring:message code="academics.gradeAlerts.iosSupportUrl" var="msgGradeAlertsIosSupportUrl"/>

<kme3:page title="${msgGradeAlertsTitle}" toolName="academics" ngAppName="gradeAlertsApp"
           ngControllerName="GradeAlertsController" ngInitFunction="init" backFunction="kmeNavLeft"
           cssFilename="academics" jsFilename="errorsHandler,gradeAlerts">
    <div class="http-error-messages" http-error-messages></div>
    <div id="theContentArea" class="main-view" ng-view="">
        <div class="list-group">
            <div class="list-group-item">
                <p>${msgGradeAlertsMsg}</p>
                <p>&nbsp;</p>

                    <div style="text-align:center">
                        <span>${msgGradeAlertsLabel} </span>
                        <span ng-if="gradeAlertOpt=='on'"><em>${msgGradeAlertsMsgOn}</em></span>
                        <span ng-if="gradeAlertOpt=='off'"><em>${msgGradeAlertsMsgOff}</em></span>
                        <br/>
                        <button type="button" id="gradeAlertBt" class="btn btn-primary" ng-model="gradeAlertOpt"
                                btn-checkbox
                                btn-checkbox-true="'on'" btn-checkbox-false="'off'"
                                ng-change="update()">
                            {{gradeAlertOpt == 'on' && 'Disable' || 'Enable'}}
                        </button>
                    </div>
                    <br/>
                    <span id="updateResult">{{updateResult}}</span>
                    
                    <div ng-show="isNative">
                        <div style="text-align:center">
                            <button ng-show="gradeAlertOpt == 'on'" type="submit" class="btn btn-kme" style="width:inherit"
                                    ng-click="gradeAlertTest()">${msgAcademicsGradeAlertsTestButton}</button>
                        </div>
                        <br/>

                        <p ng-show="gradeAlertOpt == 'on'"><span id="testResult0" ng-show="testResult=='0'">${testMsg0}</span>
                            <span id="testResult1" ng-show="testResult=='1'">${testMsg1}</span>
                            <span id="testResult2" ng-show="testResult=='2'">${testMsg2}</span>
                            <span id="testResult12" ng-show="testResult=='1' || testResult=='2'">
                                <a href="<c:out value="${msgGradeAlertsAndroidSupportUrl}"/>"> ${msgGradeAlertsAndroid} </a>${msgGradeAlertsNotice2}
                                <a href="<c:out value="${msgGradeAlertsIosSupportUrl}"/>"> ${msgGradeAlertsIos} </a>${testMsg12}</span>
                        </p>
                    </div>

                <div><em>
                    <p><c:out value="${msgGradeAlertsWarning}"/></p><br>
                    <p><c:out value="${msgGradeAlertsNotice1}"/>
                    <a href="<c:out value="${msgGradeAlertsAndroidUrl}"/>">${msgGradeAlertsAndroid}</a> ${msgGradeAlertsNotice2}
                    <a href="<c:out value="${msgGradeAlertsIosUrl}"/>">${msgGradeAlertsIos}</a> ${msgGradeAlertsNotice3}</p>
                </em></div>

            </div>
        </div>
    </div>

</kme3:page>


