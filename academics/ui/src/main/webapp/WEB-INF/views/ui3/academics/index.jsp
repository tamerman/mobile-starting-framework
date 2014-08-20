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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="kme3" uri="http://kuali.org/mobility/v3.0"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="academics.title" var="title"/>

<kme3:page title="${title}" toolName="academics" ngAppName="academicsApp" ngControllerName="academicsController" ngInitFunction="init" backFunction="kmeNavLeft" cssFilename="academics" jsFilename="academics">
    <div class="list-group">
        <a ng-href="{{academicsOption.path}}" ng-repeat="academicsOption in academicsOptions" class="list-group-item home-tool-item ng-cloak">
            <span class="home-tool-icon pull-left" style="background-image: url('${pageContext.request.contextPath}{{academicsOption.image}}');"></span>
            <h3>{{academicsOption.name}}</h3>
            <p ng-show="academicsOption.description">{{academicsOption.description}}</p>
        </a>
    </div>

</kme3:page>