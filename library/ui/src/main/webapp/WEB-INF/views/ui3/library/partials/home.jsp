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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div ng-controller="NoOpCtrl">
    <div class="list-group" id="menu">
      <a href="#/viewHours" class="list-group-item" style="background-image: url('${pageContext.request.contextPath}/images/academics/browse-classes.png');">
        <h4 class="list-group-item-heading"><spring:message code="library.viewHours" /></h4>
        <p class="list-group-item-text"><spring:message code="library.viewHours.subtitle" /></p>
      </a>
        <a href="#/viewContact" class="list-group-item" style="background-image: url('${pageContext.request.contextPath}/images/academics/browse-classes.png');">
        <h4 class="list-group-item-heading"><spring:message code="library.contact" /></h4>
        <p class="list-group-item-text"><spring:message code="library.contact.subtitle" /></p>
      </a>
    </div>
</div>
