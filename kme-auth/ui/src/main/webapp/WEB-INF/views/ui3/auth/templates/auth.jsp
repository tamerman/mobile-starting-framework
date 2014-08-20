<%--
Copyright 2014-2014 The Kuali Foundation Licensed under the Educational
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
<div>
    <notification-list></notification-list>
    <div class="panel panel-default">
        <div class="panel-body">
            <form role="form" ng-submit="doLogin()">
                <div class="form-group">
                    <label for="loginName"><spring:message code="kme-auth.login.name.label"/></label>
                    <input type="text" class="form-control" id="loginName" placeholder="<spring:message code="kme-auth.login.name.label"/>" ng-model="loginName">
                </div>
                <div class="form-group">
                    <label for="password"><spring:message code="kme-auth.login.password.label"/></label>
                    <input type="password" class="form-control" id="password" placeholder="<spring:message code="kme-auth.login.password.label"/>" ng-model="password">
                </div>
                <button type="submit" class="btn btn-default"><spring:message code="kme-auth.login.button"/></button>
            </form>
        </div>
    </div>
</div>
