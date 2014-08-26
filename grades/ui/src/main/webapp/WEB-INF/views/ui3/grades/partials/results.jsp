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