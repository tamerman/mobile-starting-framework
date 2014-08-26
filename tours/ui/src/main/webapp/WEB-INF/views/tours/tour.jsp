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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<kme:page title="${tour.name}" id="tours" backButton="true" homeButton="true" cssFilename="tours"> <%--ogDescription="${tour.description}" ogUrl="${pageUrl}" fbAdmins="100002388485159"--%>
    <kme:content>
    	<div id="fb-root"></div>
		<script>(function(d, s, id) {
		  var js, fjs = d.getElementsByTagName(s)[0];
		  if (d.getElementById(id)) return;
		  js = d.createElement(s); js.id = id;
		  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";
		  fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));</script>
		<script type="text/javascript">
			var localStorageName = 'distances-${tour.tourId}';
		
			$(function() {
				$('li.distanceCheckbox').click(function() {
					var checked = $('li.distanceCheckbox').hasClass('checked');
					if (!checked) {
						$('li.distanceCheckbox').removeClass('unchecked');
						$('li.distanceCheckbox').addClass('checked');
						$('span#showHideText').text('Show');
						$('.poiDistance').slideUp();
						if (typeof (localStorage) != 'undefined') {
							try {
								localStorage.setItem(localStorageName, true);
							} catch (e) {}
						}
					} else {
						$('li.distanceCheckbox').addClass('unchecked');
						$('li.distanceCheckbox').removeClass('checked');
						$('span#showHideText').text('Hide');
						$('.poiDistance').slideDown();
						if (typeof (localStorage) != 'undefined') {
							try {
								localStorage.removeItem(localStorageName);
							} catch (e) {}
						}
					}
					return false;
				});

				if (typeof (localStorage) != 'undefined') {
					try {
						if (localStorage.getItem(localStorageName)) {
							$('.poiDistance').hide();
							$('li.distanceCheckbox').removeClass('unchecked');
							$('li.distanceCheckbox').addClass('checked');
							$('span#showHideText').text('Show');
						}
					} catch (e) {}
				}
			});
		</script>
        <kme:listView filter="false" id="tourList">
        	<kme:listItem cssClass="tightPadding">
             	<p class="wrap descriptionText">${tour.description}</p>
			</kme:listItem>
        	<kme:listItem cssClass="link-gps">
               	<a href="${pageContext.request.contextPath}/tours/map/${tour.tourId}">
               		View Map<br />
               		<span class="wrap" style="font-weight:normal; font-size:.8em">Open the tour in an interactive map and show your GPS location.</span>
               	</a>
            </kme:listItem>
			<c:if test="${(tour.tweetText1Enabled eq 'T' or tour.fbText1Enabled eq 'T') and not empty pageUrl}">
				<kme:listItem dataRole="list-divider" dataTheme="b" dataIcon="listview">
					Share this Tour
				</kme:listItem>
			</c:if>
			<c:if test="${tour.tweetText1Enabled eq 'T'}">
				<kme:listItem cssClass="tightPadding">
	             	<div><a href="https://twitter.com/share" class="twitter-share-button" data-text="${tour.tweetText1}">Tweet</a></div>
					<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
				</kme:listItem>
			</c:if>
			<c:if test="${tour.fbText1Enabled eq 'T'}">
				<kme:listItem cssClass="tightPadding">
	             	<div class="fb-like" data-href="${pageUrl}" data-send="false" data-width="300" data-show-faces="false" data-action="recommend" data-font="arial"></div>
				</kme:listItem>
			</c:if>
            <kme:listItem dataRole="list-divider" dataTheme="b" dataIcon="listview">
				Start the Tour
			</kme:listItem>
            <c:forEach items="${tour.pointsOfInterest}" var="poi" varStatus="status">
                <kme:listItem>
                	<a href="${pageContext.request.contextPath}/tours/details/${poi.poiId}">
                		<h3 class="wrap">${status.count}.
                			<c:choose>
	                			<c:when test="${not empty poi.name}">
	                				${poi.name}
	                			</c:when>
	                			<c:otherwise>
	                				${poi.officialName}
	                			</c:otherwise>
                			</c:choose>
                		</h3>
                		<c:if test="${not empty poi.thumbnailUrl}">
                			<img src="${poi.thumbnailUrl}"/>
                		</c:if>
                		<c:if test="${not empty poi.shortDescription}">
                			<p class="wrap">${poi.shortDescription}</p>
                		</c:if>
               	 	</a>
                </kme:listItem>
                <c:if test="${not empty poi.distanceToNextPoi}">
	                <kme:listItem cssClass="poiDistance extraTightPadding">
	                	<p>&uarr;&darr;&nbsp;
	                		<c:set var="kilometers" value="${poi.distanceToNextPoi / 1000.0}" />
	                		<c:set var="miles" value="${kilometers * 0.621371192}" />
	                		<c:choose>
		                		<c:when test="${miles < 0.1}">
		                			<fmt:formatNumber var="feet" value="${miles * 5280.0}" maxFractionDigits="0" />
		                			${feet} Feet
		                		</c:when>
		                		<c:otherwise>
		                			<fmt:formatNumber var="miles" value="${miles}" maxFractionDigits="2" />
	                				${miles} Miles
		                		</c:otherwise>
	                		</c:choose>
	                		/
	                		<c:choose>
		                		<c:when test="${kilometers < 0.1}">
		                			<fmt:formatNumber var="meters" value="${kilometers * 1000.0}" maxFractionDigits="0" />
		                			${meters} Meters
		                		</c:when>
		                		<c:otherwise>
		                			<fmt:formatNumber var="kilometers" value="${kilometers}" maxFractionDigits="2" />
	                				${kilometers} Kilometers
		                		</c:otherwise>
	                		</c:choose>
	                		between stops
	                	</p>
	                </kme:listItem>
                </c:if>
            </c:forEach>
			<kme:listItem cssClass="extraTightPadding distanceCheckbox unchecked wrap" hideDataIcon="true" >
				<a href='#' style="font-weight:normal;white-space: normal;" class="wrap"><span id="showHideText">Hide</span> distances between points of interest.</a>
			</kme:listItem>
			<c:if test="${(tour.tweetText2Enabled eq 'T' or tour.fbText2Enabled eq 'T') and not empty pageUrl}">
				<kme:listItem dataRole="list-divider" dataTheme="b" dataIcon="listview">
					Share this Tour
				</kme:listItem>
			</c:if>
            <c:if test="${tour.tweetText2Enabled eq 'T'}">
				<kme:listItem cssClass="tightPadding">
	             	<div><a href="https://twitter.com/share" class="twitter-share-button" data-text="${tour.tweetText2}">Tweet</a></div>
					<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
				</kme:listItem>
			</c:if>
            <c:if test="${tour.fbText2Enabled eq 'T'}">
				<kme:listItem cssClass="tightPadding">
	             	<div class="fb-like" data-href="${pageUrl}" data-send="false" data-width="300" data-show-faces="false" data-action="recommend" data-font="arial"></div>
				</kme:listItem>
			</c:if>
        </kme:listView>
    </kme:content>
</kme:page>
