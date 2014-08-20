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

<%@ page language="java" contentType="text/javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>

/**
 * Contructors
 */
function KMEPushConfig(){};

/**
 * Returns the application Id
 */
KMEPushConfig.prototype.getApplicationId = function (){
<c:if test="${empty applicationId }">
  return undefined;
</c:if>
<c:if test="${not empty applicationId }">
  return "${applicationId}";
</c:if>
};


/**
 * Returns the application registration URL
 */
KMEPushConfig.prototype.getRegistrationUrl = function(){
<c:if test="${empty registrationUrl }">
  return undefined;
</c:if>
<c:if test="${not empty registrationUrl }">
  return "${registrationUrl}";
</c:if>
};

/**
 * Returns the application port if available
 */
KMEPushConfig.prototype.getPort = function(){
<c:if test="${empty port }">
  return undefined;
</c:if>
<c:if test="${not empty port }">
  return ${port};
</c:if>
};


/** Add KMEPushConfig to window.kme.KMEPushConfig */
window.kme = window.kme || {};
window.kme.pushConfig = window.kme.pushConfig || new KMEPushConfig();
