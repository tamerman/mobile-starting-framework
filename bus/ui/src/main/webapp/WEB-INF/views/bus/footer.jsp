<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>

<kme:tabBar id="tabNav" showIcons="true" footer="true">
	<c:forEach items="${taborder}" var="tabName">
		<c:set var="isSelected" value="false"/>
		<c:if test="${pageName == tabName}">
			<c:set var="isSelected" value="true"/>
		</c:if>
		<c:choose>
			<c:when test="${tabName == 'map'}">
	<kme:tabBarItem url="${pageContext.request.contextPath}/bus${urlMap[tabName]}" selected="${isSelected}">${mapLabel}</kme:tabBarItem>
			</c:when>
			<c:when test="${tabName == 'stops'}">
	<kme:tabBarItem url="${pageContext.request.contextPath}/bus${urlMap[tabName]}" selected="${isSelected}">${stopsLabel}</kme:tabBarItem>
			</c:when>
			<c:when test="${tabName == 'favorites'}">
	<kme:tabBarItem url="${pageContext.request.contextPath}/bus${urlMap[tabName]}" selected="${isSelected}">${favoritesLabel}</kme:tabBarItem>
			</c:when>
			<c:when test="${tabName == 'nearbystops'}">
	<kme:tabBarItem url="${pageContext.request.contextPath}/bus${urlMap[tabName]}" selected="${isSelected}">${nearbystopsLabel}</kme:tabBarItem>
			</c:when>
		</c:choose>
	</c:forEach>
</kme:tabBar>
