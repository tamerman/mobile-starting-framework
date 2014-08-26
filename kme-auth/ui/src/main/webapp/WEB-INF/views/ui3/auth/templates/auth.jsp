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
