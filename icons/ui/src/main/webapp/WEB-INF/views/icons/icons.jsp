<%@page contentType="text/css; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="icons"   uri="http://kuali.org/mobility/icons" %>

/* Small screen*/
img[data-kme-icon]{
 	background-size : 48px 48px;
 	background-clip: content-box;
 	background-repeat: no-repeat;
 	width: 48px;
 	height: 48px;
 }
 
 <c:forEach var="icon" items="${icons}">
 	<icons:iconCSS icon="${icon}" size="48" />
 </c:forEach>
 
 /* Small screens with atleast 1.5 pixel density*/
 @media only screen and (-webkit-min-device-pixel-ratio : 1.5),
		only screen and (min-device-pixel-ratio : 1.5) {
		
		img[data-kme-icon]{
			width: 48px;
			height: 48px;
		}
		
	<c:forEach var="icon" items="${icons}">
 		<icons:iconCSS icon="${icon}" size="48" multiplier="2"/>
 	</c:forEach>
}

/* Large screen with normal pixel density*/
 @media only screen and (min-width: 768px) {

	 img[data-kme-icon]{
	 	background-size : 114px 114px;
	 	width: 114px;
	 	height: 114px;
 	}
 	
 	 <c:forEach var="icon" items="${icons}">
 		<icons:iconCSS icon="${icon}" size="114" />
 	</c:forEach>
 }
 
 
/* Large screens with atleast 1.5 pixel density*/
@media only screen and (-webkit-min-device-pixel-ratio : 1.5) and (min-width: 768px),
	   only screen and (min-device-pixel-ratio : 1.5) and (min-width: 768px){ 
	   
	 img[data-kme-icon]{
		background-size : 114px 114px;
	 	width: 114px;
	 	height: 114px;
 	}
 	
 	<c:forEach var="icon" items="${icons}">
 		<icons:iconCSS icon="${icon}" size="114"  multiplier="2"/>
 	</c:forEach>
}
