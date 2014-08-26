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
