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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div ng-controller="MyScheduleSectionDetailController" data-ng-init="init()">
    <div class="alert alert-danger" ng-repeat="error in myScheduleData.errors.error" ng-click="clickHideErrors()">{{error.name}}</div>
    <div class="tab-view list-group">
        <my-section-detail-header></my-section-detail-header>
        <my-section-detail-body ng-show="socShowDetailBody" ng-init="socShowDetailBody=true"></my-section-detail-body>
        <soc-section-detail-description ng-show="socShowDetailDescription" ng-init="socShowDetailDescription=false"></soc-section-detail-description>
        <soc-section-detail-extra ng-show="socShowDetailExtra" ng-init="socShowDetailExtra=false"></soc-section-detail-extra>
    </div>
    <soc-section-detail-nav></soc-section-detail-nav>
</div>
