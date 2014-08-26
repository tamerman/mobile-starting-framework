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
