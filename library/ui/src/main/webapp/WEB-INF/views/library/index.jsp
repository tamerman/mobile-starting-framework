<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:message code="library.title" var="msgCat_Title" />
<kme:page  title="${msgCat_Title}"
	id="library-ui" 
	backButton="true" 
	homeButton="true" 
	cssFilename="library">
	<kme:content>
		 <kme:listView dataDividerTheme="b" filter="false" id="menu">
			<kme:listItem hideDataIcon="true">
			  <a href="${pageContext.request.contextPath}/library/viewHours" style="background-image: url('${pageContext.request.contextPath}/images/academics/browse-classes.png');">
					<h3><spring:message code="library.viewHours" /></h3>
					<p class="wrap"><spring:message code="library.viewHours.subtitle" /></p>
			  </a>
			</kme:listItem>
			<kme:listItem hideDataIcon="true">
				<a href="${pageContext.request.contextPath}/library/viewContact" style="background-image: url('${pageContext.request.contextPath}/images/academics/browse-classes.png');">
					<h3><spring:message code="library.contact" /></h3>
					<p class="wrap"><spring:message code="library.contact.subtitle" /></p>
				</a>
			</kme:listItem>
		</kme:listView>
	</kme:content>
</kme:page>
