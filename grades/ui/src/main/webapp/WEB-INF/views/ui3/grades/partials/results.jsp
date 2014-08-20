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
<spring:message code="grades.webserviceLoading" var="msgCat_Loading" />
<spring:message code="grades.webserviceError" var="msgCat_Error" />



<div class="container" ng-show="serverSuccess" style="margin-top: 20px;">

    <div class="row hidden-sm hidden-xs">
        <div class="col-md-2 col-md-offset-4">
            <b><spring:message code="grades.participationMark" /></b>
        </div>
        <div class="col-md-2">
            <b><spring:message code="grades.examMark" /></b>
        </div>
        <div class="col-md-2">
            <b><spring:message code="grades.finalMark" /></b>
        </div>
    </div>

    <div class="row" ng-repeat="result in results">

        <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
            <b>{{result.moduleName}}</b>
        </div>

        <div class="col-xs-12 col-sm-12 col-md-2 col-lg-2">
            <!-- Participation Mark -->
            <div class="col-xs-6 col-sm-6 hidden-md hidden-lg">
                <b><spring:message code="grades.participationMark" /></b>
            </div>
            <div class="col-xs-6 col-sm-6 col-md-12 col-lg-12">
                {{result.participationMark}}
                <i>{{result.participationMarkComment}}</i>
            </div>
        </div>

        <div class="col-xs-12 col-sm-12 col-md-2 col-lg-2">
            <!-- Participation Mark -->
            <div class="col-xs-6 col-sm-6 hidden-md hidden-lg">
                <b><spring:message code="grades.examMark" /></b>
            </div>
            <div class="col-xs-6 col-sm-6 col-md-12 col-lg-12">
                {{result.examMark}}
                <i>{{result.examMarkComment}}</i>
            </div>
        </div>

        <!-- Final Mark -->
        <div class="col-xs-12 col-sm-12 col-md-2 col-lg-2">
            <div class="col-xs-6 col-sm-6 hidden-md hidden-lg">
                <b><spring:message code="grades.finalMark" /></b>
            </div>
            <div class="col-xs-6 col-sm-6 col-md-12 col-lg-12">
                {{result.finalMark}}
                <i>{{result.finalMarkComment}}</i>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var msgCat_Loading = "${msgCat_Loading}";
    var msgCat_Error = "${msgCat_Error}";
</script>