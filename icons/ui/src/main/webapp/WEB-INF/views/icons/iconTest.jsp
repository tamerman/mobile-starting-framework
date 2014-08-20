<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="icons"   uri="http://kuali.org/mobility/icons" %>
<html>
<head>
<style type="text/css">
div.floater {
	float: left;
	margin: 0.9em;
}
</style>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/icons.css">
<meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width">
</head>

<body>

	<c:forEach var="icon" items="${icons}">
		<div class="floater">
			<icons:icon icon="${icon.name}" theme="${icon.theme}"/>
		</div>
	</c:forEach>
</body>

</html>