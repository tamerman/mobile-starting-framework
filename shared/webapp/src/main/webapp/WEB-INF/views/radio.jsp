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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="radio.srCompatibility" var="msgCat_Compatibility" />
<spring:message code="radio.srOldAndroid" var="msgCat_OldAndroid" />
<spring:message code="radio.notSupported" var="msgCat_NotSupported" />
<spring:message code="radio.dataPlanWarning" var="msgCat_DataPlanWarning" />
<spring:message code="radio.lowBitrate" var="msgCat_LowBitrate" />
<spring:message code="radio.highBitrate" var="msgCat_HighBitrate" />

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${fn:contains(header['User-Agent'],'iPhone') || fn:contains(header['User-Agent'],'iPad') || fn:contains(header['User-Agent'],'iPod') || fn:contains(header['User-Agent'],'Macintosh')}">
	<c:set var="platform" value="iOS"/>
</c:if>
<c:if test="${fn:contains(header['User-Agent'],'Android')}">
	<c:set var="platform" value="Android"/>
</c:if>

<c:set var="phonegap" value="${cookie['phonegap'].value}"/>


<kme:page title="Radio" id="radio" backButton="true" homeButton="true" cssFilename="alerts" backButtonURL="${pageContext.request.contextPath}/home" platform="${platform}" phonegap="${phonegap}">
<script type="text/javascript" charset="utf-8">
$(function(){		
	//
	// If Audio can't be played on the current device, inform the user with a dismissable ListItem and Alert. 
	//
	if(!(isChrome || isSafari || isExplorer || isNativeApp)){
		if(!($.cookie('srCompatibility') == "hide")){
			$("#list").html(
					"<li data-theme=\"c\" data-icon=\"delete\" id=\"srCompatibility\">" +
						"<a href=\"#\" onClick=\"dismiss('srCompatibility')\" style=\"white-space:normal\">"+
							"${msgCat_Compatibility}"+
						"</a>"+
					"</li>" + 
					$("#list").html()
				).listview("refresh");
		}
		alert("${msgCat_NotSupported}");
	}
	
	//
	// If the user is on an older Android device, inform them of the slow buffering issue.
	//
	if(isAndroid && !($.cookie('srOldAndroid') == "hide") ){
		$("#list").html(
			"<li data-theme=\"c\" data-icon=\"delete\" style=\"white-space:normal\" id=\"srOldAndroid\">" +
				"<a href=\"#\" onClick=\"dismiss('srOldAndroid')\" style=\"white-space:normal\">" +
					"${msgCat_OldAndroid}" +
				"</a>" +
			"</li>" +
			$("#list").html()
		).listview("refresh");
	}
		
	document.addEventListener("deviceready",onDeviceReady,false);
});


//
// If we're in a native app, update the play/pause icon based on whether or not we are returning 
// with audio streaming in the background. 
//
function onDeviceReady(){		
	//	window.localStorage.clear();
	var id = window.localStorage.getItem("id");
	var isPlaying = window.localStorage.getItem("isPlaying");
	var url = window.localStorage.getItem("url");
	
	console.log("ID "+ id);
	console.log(isPlaying?"Is playing":" Is NOT playing");
	console.log("URL " + url);

	if(isiOS || isAndroid){
		if(isPlaying){
			$("#"+id+"").attr("src","images/pause.png");
			player.isPlaying = isPlaying;
		}
	}else{

	}
}

// 
// Save info for player. 
// Read player from app prefs? 
//
var player = {};

//
// Audio Tag object. 
// Undisplayed/Unrendered Tag. 
//
var audioElement = document.createElement('audio');

//
// Play the stream at the given URL 
//
function playStream(url){	
	//
	// Add Native apps as the become compatible. 
	//
	if(isiOS || isAndroid){		
    	var networkType = navigator.connection.type;
    	var states = {};
        states[Connection.UNKNOWN]  = 'Unknown connection';
        states[Connection.ETHERNET] = 'Ethernet connection';
        states[Connection.WIFI]     = 'WiFi connection';
        states[Connection.CELL_2G]  = 'Cell 2G connection';
        states[Connection.CELL_3G]  = 'Cell 3G connection';
        states[Connection.CELL_4G]  = 'Cell 4G connection';
        states[Connection.NONE]     = 'No network connection';
        console.log(states[networkType]);
		
		//
		// If data is via Cellular notify user of possible data usage cost. 
		//
	    if(!((networkType == Connection.WIFI) || (networkType == Connection.ETHERNET))){
			navigator.notification.confirm(
 				'${msgCat_DataPlanWarning}', 
				function(buttonIndex){
					if(buttonIndex == 2){
			            player.isPlaying = true;
			    	    window.plugins.audioStreamer.play(
			    	    		url,
			                    null,
			                    function(){
			                      console.log("Loaded URL!");
			                    },
			                    function(result){
			                      console.log(result);
			                    });
			            
			            $("#"+player.id+"").attr("src","images/pause.png");
					}else{
			            player.isPlaying = false;
			            $("#"+player.id+"").attr("src","images/play.png");
					}
				},
				'Audio Streaming',
				'Close,Agree');
    	}else{
        	player.isPlaying = true;
    	    window.plugins.audioStreamer.play(
    	    		url,
                    null,
                    function(){
                      console.log("Loaded URL!");
                    },
                    function(result){
                      console.log(result);
                    });	
            $("#"+player.id+"").attr("src","images/pause.png");
    	}  
	    
	//
	// Play from audio tag in a desktop browser. 
	// If playing an MP3 stream there will need to be a fallback for firefox, which doesn't support mp3 in <audio>
	//
	}else{
		audioElement.setAttribute('src', url);
		audioElement.play();
	}
}


//
// Stop the playing stream. 
//
function stopStream(){
	if(isiOS || isAndroid){
		window.plugins.audioStreamer.stop(function(){
    	                                    console.log("Stopped Audio!");
        	                              },
            	                          function(){
                	                        console.log(result);
                    	                  });
	}else{
		audioElement.pause();		
	}
}


//
// Handle the Pause/Play toggling of the audio as well as the UI elements. 
//
function playpause(url, id){
    player.url = url;
    player.id = id; 
	$(".playimage").attr("src", "${pageContext.request.contextPath}/images/play.png");
	if(player.isPlaying){
    	player.isPlaying = false;
        stopStream();
        $("#"+id+"").attr("src","${pageContext.request.contextPath}/images/play.png");
    }else{
       	player.isPlaying = true;
       	playStream(url);
        $("#"+id+"").attr("src","${pageContext.request.contextPath}/images/pause.png");
    }   
    window.localStorage.setItem("id", player.id);
    window.localStorage.setItem("url", player.url);
    window.localStorage.setItem("isPlaying", player.isPlaying);
}



//
// Dismiss a notification ListItem, and hide it via a cookie. 
//
function dismiss(id){
	$.cookie(id, 'hide', {expires: 365, path: '/'});
	$("#" + id + "").animate({
		height:"toggle"	
	},{duration:"fast"});
}


</script>
	
	<kme:content>
	    <kme:listView id="list" filter="false">
			<li data-theme="c" data-role="list-divider" id="list-divider">${msgCat_LowBitrate}</li>	
           	<li data-icon="false" data-theme="c">                
                <a href="#" onClick="playpause('http://hannibal.ucs.indiana.edu:8080/wiuxlow', 'playLow0');" style="padding-left: 70px;">
                    <img src="${pageContext.request.contextPath}/images/play.png" style="left:5px; top:1.3em; height:40px; padding-left:5px" class="playimage" id="playLow0"/>
	            	<h3>WIUX 99.1 Mhz (64kbps)</h3>
                	<p style="white-space:normal">Pure Student Radio at Indiana University</p>
 				</a>
           	</li>	
			<li data-theme="c" data-role="list-divider" id="list-divider">${msgCat_HighBitrate}</li>		
           	<li  data-icon="false" data-theme="c">                
                <a href="#" onClick="playpause('http://hannibal.ucs.indiana.edu:8080/wiuxultra', 'playHigh0');" style="padding-left: 70px;">
                    <img src="${pageContext.request.contextPath}/images/play.png" style="left:5px; top:1.3em; height:40px; padding-left:5px" class="playimage" id="playHigh0"/>
                	<h3>WIUX 99.1 Mhz (320kbps)</h3>
                	<p style="white-space:normal">Pure Student Radio at Indiana University</p>
				</a>
           	</li>
	    </kme:listView>
	</kme:content>

</kme:page>
