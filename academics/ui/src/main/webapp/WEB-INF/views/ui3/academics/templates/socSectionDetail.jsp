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
<div ng-controller="SocSectionDetailController" data-ng-init="init()">
    <div class="alert alert-danger" ng-repeat="error in SocData.errors.error" ng-click="clickHideErrors()">{{error.name}}</div>
    <div class="tab-view list-group">
        <soc-section-detail-header></soc-section-detail-header>
        <soc-section-detail-body ng-show="socShowDetailBody" ng-init="socShowDetailBody=true"></soc-section-detail-body>
        <soc-section-detail-description ng-show="socShowDetailDescription" ng-init="socShowDetailDescription=false"></soc-section-detail-description>
        <soc-section-detail-extra ng-show="socShowDetailExtra" ng-init="socShowDetailExtra=false"></soc-section-detail-extra>
    </div>
    <soc-section-detail-nav></soc-section-detail-nav>
</div>
