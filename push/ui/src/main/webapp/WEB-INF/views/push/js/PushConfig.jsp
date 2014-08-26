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
