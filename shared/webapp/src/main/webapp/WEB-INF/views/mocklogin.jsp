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

<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme"  uri="http://kuali.org/mobility" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="shared.login" var="msgCat_Login"/>
<spring:message code="login.mockLogin" var="msgCat_ToolTitle"/>



<kme:page title="${msgCat_ToolTitle}" id="mockuser" backButton="true" homeButton="true" backButtonURL="home">
  <script type="text/javascript" src="js/md5.js"></script>
  <script type="text/javascript" src="js/sha1.js"></script>
	<kme:content>
		<form:form action="${pageContext.request.contextPath}/mocklogin" commandName="mockuser" data-ajax="false" method="post">
			<kme:listView id="mockLoginForm" filter="false" dataTheme="c" dataInset="false">
	 			<kme:listItem>
					<label for="userId"><spring:message code="login.username" /></label>
					<form:input path="userId" /><br/>
					<form:errors path="userId" />
				</kme:listItem>
				<kme:listItem>
	            	<label for="password"><spring:message code="login.password" /></label>
	            	<form:input path="password" type="password" id="password" /><br/>
	            	<form:errors path="password" />
				</kme:listItem>
				<kme:listItem>
					<fieldset class="ui-grid-a">
	    				<div class="ui-block-a"><a data-theme="c" href="${pageContext.request.contextPath}/home" data-role="button"><spring:message code="shared.cancel" /></a></div>
						<div class="ui-block-b"><input data-theme="a" class="submit" type="submit" value="${msgCat_Login}" onclick="calMD5()" /></div>
						<!-- By default MD5 hashing encryption is applied on password field.
						     calSHA1() method can be used if we need to apply SHA-1 hashing encryption.
						     In that case, we need to use onclick=calSHA1()
						-->
					</fieldset>
				</kme:listItem>
			</kme:listView>		  
		</form:form>
	</kme:content>

	<script type="text/javascript">
            function calMD5(str)
            {
                var password =document.getElementById("password").value;
                var hash =hex_md5(password);
                document.getElementById("password").value=hash;
            }
            function calSHA1(str)
            {
                 var password =document.getElementById("password").value;
                 var hash =calcSHA1(password);
                 document.getElementById("password").value=hash;
            }
    </script>
</kme:page>


