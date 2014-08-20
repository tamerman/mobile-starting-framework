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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kme3" uri="http://kuali.org/mobility/v3.0"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="mdot.aboutLabel" var="msgCat_ToolTitle"/>

<kme3:page title="${msgCat_ToolTitle}" toolName="home" ngAppName="aboutApp" ngControllerName="AboutController" jsFilename="about" ngInitFunction="init">
    <div id="theContentArea" class="main-view" ng-view="">
        <div class="list-group">
            <div class="list-group-item">
            <p>Copyright &copy; 2014 the Kuali Foundation</p>
            <c:if test="${not empty institutionVersion}">
                <p>Version ${institutionVersion}</p>
            </c:if>
            <p><strong>Powered by:</strong><p>
            <p>Kuali Mobility version ${kmeVersion}</p>
            <p><strong>Maintained by:</strong></p>
            <p>The <a href="mailto:mobility.collab@kuali.org">Kuali Mobility development team</a></p>
            </div>
        </div>
    </div>

</kme3:page>