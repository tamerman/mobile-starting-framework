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

<kme:page title="Location Details" id="tours" backButton="true" homeButton="true" cssFilename="tours" usesGoogleMaps="true">
    <script src="${pageContext.request.contextPath}/js/foursquare.js" type="text/javascript"></script>
    <script type="text/javascript">
	    var imageType = 'I';
	    var audioType = 'A';
	    var videoType = 'V';
	    var iuBuildingType = 'I';
	    var venueType = 'V';
	    var customPointType = 'C';
	    var predefinedPointType = 'P';
	    
	    var contextPath = '${pageContext.request.contextPath}';
    
	    jQuery(window).load(function() {
	    	try {
		    	var obj = jQuery.parseJSON('${poi.media}');
		    	if (obj) {
		    		var html = '';
					for (var i=0; i<obj.length; i++){
						var media = obj[i];
						if (media.type == imageType){
							html += '<li class="ui-li ui-li-static ui-body-c tightPadding" data-theme="c">';
							
							if (media.title){
								html += '<h3>' + media.title + '</h3>';
							}
							
							html += '<img src="' + media.url + '" style="width:100%;max-width:500px;" />';
							
							if (media.caption){
								html += '<p>' + media.caption + '</p>';
							}
							html += '</li>';
						} else if (media.type == videoType) {
							if (media.oggUrl || media.mp4Url || media.webMUrl || media.youTubeUrl) {
								
								html += '<li class="ui-li ui-li-static ui-body-c tightPadding" data-theme="c">';
								
								if (media.title){
									html += '<h3>' + media.title + '</h3>';
								}
								
								
								if (media.oggUrl || media.mp4Url || media.webMUrl) {
									html += '<video style="width:100%;max-width:500px;" controls="controls">';
									if (media.mp4Url) { html += '<source src="' + media.mp4Url + '" type="video/mp4"/>'; }
									if (media.oggUrl) { html += '<source src="' + media.oggUrl + '" type="video/ogg"/>'; }
									if (media.webMUrl) { html += '<source src="' + media.webMUrl + '" type="video/webm"/>'; }
									html += 'Your browser does not support HTML5 video.';
									html += '</video>';
								} else {
									try {
										var iframe = $(media.youTubeUrl);
										iframe.addClass('youTube');
										html += iframe.wrap('<div></div>').parent().html();
									} catch (err) {}
								}
								
								if (media.caption){
									html += '<p>' + media.caption + '</p>';
								}
								html += '</li>';
							}
						} else if (media.type == audioType) {
							try {
								if (media.oggVorbisUrl || media.mp3Url || media.wavUrl) {
								
									html += '<li class="ui-li ui-li-static ui-body-c tightPadding" data-theme="c">';
									
									if (media.title){
										html += '<h3>' + media.title + '</h3>';
									}
									
									html += '<audio style="width:100%;max-width:500px;" controls="controls">';
									if (media.mp3Url) { html += '<source src="' + media.mp3Url + '" type="audio/mpeg"/>'; }
									if (media.oggVorbisUrl) { html += '<source src="' + media.oggVorbisUrl + '" type="audio/ogg"/>'; }
									if (media.wavMUrl) { html += '<source src="' + media.wavMUrl + '" type="audio/wav"/>'; }
									html += 'Your browser does not support HTML5 audio.';
									html += '</audio>';
									
									if (media.caption){
										html += '<p>' + media.caption + '</p>';
									}
									html += '</li>';
								}
							} catch (err) {}
						}
					}

					$('#mediaContainer').replaceWith(html);
					resizeYouTubeVideos();
		    	}
	    	} catch (err) {
	    		alert (err);
	    	}
	    	if (false && '${poi.type}' == 'V') { //will change when pois can have venue AND building codes. Disabled by request
	    		try {
	    			findFoursquareVenue('${poi.locationId}', setVenueTips);
	    		} catch (err) {}
	    	}
		});
	    
	    function setVenueTips(venue) {
	    	if (venue.tips.length > 0){
    			var html = "";
    			for (var x=0; x<venue.tips.length; x++){
    					var item = venue.tips[x];
    					html = html + "<li class='tip ui-li ui-li-static ui-body-c tightPadding' data-theme='c'>";
    					if (item.user.photo){
    						html = html + "<img src='" + item.user.photo + "'></img>";
    					}
    					
    					html = html + "<h3>" + item.user.name + "</h3>";
    					html = html + "<p>" + item.text + "</p>";
    					html = html + "<p class='actions'>";

    					html = html + "<span class='doneCount'>";
    					html = html + item.count;
    					html = html + "</span>";
    					
    					html = html + item.date;
    					
    					html = html + "</p>";
    					html = html + "</li>";
    			}
    			$("#fourSquareTips").replaceWith(html);
    		}
	    }
	    
	    function resizeYouTubeVideos() {
	    	$('.youTube').each(function() {
	    		var iframe = $(this);
	    		var height = iframe.height();
	    		var width = iframe.width();
	    		
	    		var parentWidth = iframe.parent().width();
	    		if (parentWidth > 500) { parentWidth = 500;}
	    		
	    		iframe.removeAttr('height');
	    		iframe.removeAttr('width');
	    		
	    		iframe.width(parentWidth);
	    		iframe.height((parentWidth / width) * height + 30); //30px for the controls
	    	});
	    }
	    
    </script>
    <kme:content>
    	<div id="fb-root"></div>
		<script>(function(d, s, id) {
		  var js, fjs = d.getElementsByTagName(s)[0];
		  if (d.getElementById(id)) return;
		  js = d.createElement(s); js.id = id;
		  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";
		  fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));</script>
		<c:if test="${prevPoi != null or nextPoi != null}">
			<div id="prevNext" class="ui-grid-a">
				<div class="ui-block-a">
					<c:if test="${prevPoi != null}">
						<a href="${pageContext.request.contextPath}/tours/details/${prevPoi.poiId}" data-role="button" data-icon="arrow-l" data-mini="true" data-theme="c">
							Previous
						</a>
					</c:if>
				</div>
				<c:if test="${nextPoi != null}">
					<div class="ui-block-b">
						<a href="${pageContext.request.contextPath}/tours/details/${nextPoi.poiId}" data-role="button" data-icon="arrow-r" data-iconpos="right" data-mini="true" data-theme="c">
							Next
						</a>
					</div>
				</c:if>
			</div>
		</c:if>
        <kme:listView filter="false">
            <kme:listItem dataRole="list-divider" dataTheme="b" dataIcon="listview">
				<c:choose>
            		<c:when test="${not empty poi.officialName}">
            			${poi.officialName}
            		</c:when>
            		<c:otherwise>
            			${poi.name}
            		</c:otherwise>
            	</c:choose>
			</kme:listItem>
			<kme:listItem cssClass="link-gps">
               	<a href="${pageContext.request.contextPath}/maps/location?latitude=${poi.latitude}&longitude=${poi.longitude}&gps=true&route=true">
               		<h3>View Map</h3>
               		<p class="wrap">Show the location on a map.</p>
               	</a>
            </kme:listItem>
			<c:if test="${not empty poi.description}">
            	<kme:listItem cssClass="tightPadding">
	            	<p class="wrap descriptionText">${poi.description}</p>
            	</kme:listItem>
            </c:if>
			<c:if test="${not empty poi.url}">
            	<kme:listItem>
            		<a href="${poi.url}">${poi.url}</a>
            	</kme:listItem>
            </c:if>
            <c:if test="${not empty poi.emailAddresses}">
            	<c:forEach items="${poi.emailAddresses}" var="emailAddress" varStatus="status">
	            	<kme:listItem cssClass="link-email">
	            		<a href="mailto:${emailAddress.address}"><c:if test="${not empty emailAddress.name}">${emailAddress.name} </c:if>${emailAddress.address}</a>
	            	</kme:listItem>
            	</c:forEach>
            </c:if>
            <c:if test="${not empty poi.phoneNumbers}">
            	<c:forEach items="${poi.phoneNumbers}" var="phoneNumber" varStatus="status">
	            	<kme:listItem cssClass="link-phone">
	            		<a href="tel:${phoneNumber.number}"><c:if test="${not empty phoneNumber.name}">${phoneNumber.name} </c:if>${phoneNumber.formattedNumber}</a>
	            	</kme:listItem>
            	</c:forEach>
            </c:if> 
            <c:if test="${poi.type eq 'V'}">
            	<kme:listItem>
            		<a href=" http://foursquare.com/venue/${poi.locationId}" target="_blank" class="checkIn"><h3>Check in to Foursquare</h3></a>
            	</kme:listItem>
            </c:if> 
            <c:if test="${poi.fbLikeButtonEnabled eq 'T' and not empty poi.fbLikeUrl}">
          		<kme:listItem cssClass="tightPadding">
            		<div class="fb-like" data-href="${poi.fbLikeUrl}" data-send="false" data-width="300" data-show-faces="false"></div>
            	</kme:listItem>
            </c:if>        
            <div id="mediaContainer"></div>
            <div id="fourSquareTips"></div>
			
			<%-- 
			<c:if test="${prevPoi != null or nextPoi != null}">
				<kme:listItem dataRole="list-divider" dataTheme="b">
					Continue the Tour
				</kme:listItem>
			</c:if>
			<c:if test="${nextPoi != null}">
				<kme:listItem>
					<a href="${pageContext.request.contextPath}/tours/details/${nextPoi.poiId}">
						<h3>Next</h3><p>${nextPoi.name}</p>
					</a>
				</kme:listItem>
			</c:if>
			<c:if test="${prevPoi != null}">
				<kme:listItem dataIcon="arrow-l">
					<a href="${pageContext.request.contextPath}/tours/details/${prevPoi.poiId}">
						<h3>Previous</h3><p>${prevPoi.name}</p>
					</a>
				</kme:listItem>
			</c:if>
			--%>
        </kme:listView>
    </kme:content>
</kme:page>
