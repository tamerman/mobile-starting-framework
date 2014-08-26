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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.js" type="text/javascript"></script>
		<script src="https://maps-api-ssl.google.com/maps/api/js?libraries=geometry&v=3&sensor=false" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/arcgislink.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/polylineEdit.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/jquery-ui-1.8.16.custom.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.contextmenu.r2.packed.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/math.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/mapArrows.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/kml.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/foursquare.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/media.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/permissions.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/phoneNumbers.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/emailAddresses.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/arcgis.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/js/toursBase.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/tourMaps.js" type="text/javascript"></script>
        <link type="text/css" href="${pageContext.request.contextPath}/css/smoothness/jquery-ui-1.8.16.custom.css" rel="Stylesheet" />	
        <link type="text/css" href="${pageContext.request.contextPath}/css/tours.css" rel="Stylesheet" />	

		<script> <%-- JQuery plugin to limit textarea size --%>
			jQuery.fn.limitMaxlength = function(options){
			
			  var settings = jQuery.extend({
			    attribute: "maxlength",
			    onLimit: function(){},
			    onEdit: function(){}
			  }, options);
			  
			  // Event handler to limit the textarea
			  var onEdit = function(){
			    var textarea = jQuery(this);
			    var maxlength = parseInt(textarea.attr(settings.attribute));
			
			    if(textarea.val().length > maxlength){
			      textarea.val(textarea.val().substr(0, maxlength));
			      
			      // Call the onlimit handler within the scope of the textarea
			      jQuery.proxy(settings.onLimit, this)();
			    }
			    
			    // Call the onEdit handler within the scope of the textarea
			    jQuery.proxy(settings.onEdit, this)(maxlength - textarea.val().length);
			  }
			
			  this.each(onEdit);
			
			  return this.keyup(onEdit)
			        .keydown(onEdit)
			        .focus(onEdit);
			}
		</script>
		<script>
			jQuery(window).load(function() {
				initializeMap('${pageContext.request.contextPath}');
				$("#selector").tabs({
					show: changeSelectorTabs
				});
				$("#wizard").tabs({
					show: changeWizardTabs
				});
				
				var onEditCallback = function(remaining) {
					$(this).siblings('.charsRemaining').text(remaining);
	
					if (remaining > 0) {
						$(this).css('background-color', 'white');
					}
					if (remaining <= 10) {
						$(this).siblings('.charsRemaining').css('color', 'red');
					} else {
						$(this).siblings('.charsRemaining').css('color', 'black');
					}
				}
	
				var onLimitCallback = function() {
					$(this).css('background-color', 'red');
				}
	
				$('textarea[maxlength]').limitMaxlength({
					onEdit : onEditCallback,
					onLimit : onLimitCallback,
				});
				
				$('input[name="tweetText1Enabled"]').click(function () {
					if ($('input[name="tweetText1Enabled"]').attr('checked')) {
						$('#tweetText1').removeAttr('disabled');
					} else {
						$('#tweetText1').attr('disabled', 'disabled');
					}
				});
				$('input[name="tweetText2Enabled"]').click(function () {
					if ($('input[name="tweetText2Enabled"]').attr('checked')) {
						$('#tweetText2').removeAttr('disabled');
					} else {
						$('#tweetText2').attr('disabled', 'disabled');
					}
				});
				$('input[name="fbText1Enabled"]').click(function () {
					if ($('input[name="fbText1Enabled"]').attr('checked')) {
						$('#fbText1').removeAttr('disabled');
					} else {
						$('#fbText1').attr('disabled', 'disabled');
					}
				});
				$('input[name="fbText2Enabled"]').click(function () {
					if ($('input[name="fbText2Enabled"]').attr('checked')) {
						$('#fbText2').removeAttr('disabled');
					} else {
						$('#fbText2').attr('disabled', 'disabled');
					}
				});
			});
		</script>
        <style>
			p {
				margin: 0;
				padding: 0;
			}
		
			.ui-widget {
				font-family: inherit;
				font-size: inherit;
			}
			
			.ui-widget input, .ui-widget select, .ui-widget textarea, .ui-widget button {
				font-family: inherit;
				font-size: inherit;
			}
			
			#searchResults, #definedPOIs {
				border: 1px solid #CCCCCC;
				overflow:auto;
				height:175px;
				font-size:0.8em;
				/*width: 600px;*/
			}
			
			#definedPOIs {
				height:223px;
			}
	
			#selectedPOIs {
				border: 1px solid #CCCCCC;
				font-size:0.8em;
				/*width: 600px;*/
			}
			
			.limitedTextarea {
				position: relative;
			}
			
			.charsRemaining {
				position: absolute;
				right: 5px;
				bottom: 8px;
				font-size: 14px;
				background-color: #eee;
			}
			
			ul.permissionsList, ul#phoneNumbers, ul#emailAddresses {
				border: 1px solid #CCC;
				width: 350px;
				height: 150px;
				margin: 0;
				padding: 0;
				overflow:auto;
			}
			
			li button.removePermissionLink, li button.removePhoneNumberLink, li button.removeEmailLink {
				margin-right: 5px;
				margin-left: 5px;
				padding: 0;
			}
			
			li button.removePermissionLink a, li button.removePhoneNumberLink a, li button.removeEmailLink a {
				color: red;
				text-decoration: none;
				margin: 0;
				padding: 0;
			}
			
			.venues {
				border: 1px solid #CCC;
				overflow:auto;
				height:203px;
				font-size:0.8em;
				/*width: 600px;*/
			}
			
			.venue {
				border-bottom: 1px solid #CCC;
				border-top: 1px solid #EEE;
				padding: 5px 10px 5px 10px;
				cursor: pointer;
				overflow: hidden;
			}
			
			.venue .info .name {
				font-weight: bold;
			}
			
			#venues .odd:hover { background-color: #ccffff; }
			#venues .even:hover { background-color: #ccffff; }
			#venues .odd { background-color: #ffffff; }
			#venues .even { background-color: #f4f4f4; }
			#venues .last { border-bottom: none; }
			
			.venue .image {
				float: left;
				height: 38px;
				width: 50px;
			}
			
			.venue .image img {
				background-color: #FFFFFF;
				padding: 3px;
			}
			
			ul#images {
				padding:0;
			}
			
			ul#images li {
				display: inline;
				margin:0;
				padding:0;
				position: relative;
			}
			
			ul#images li img {
				border: 1px solid #AAA;
				margin: 0 2px 2px 0;
				padding: 2px;
				height: 48px;
			}
			
			div#selectedMedia, div.border {
				border: 1px solid #AAAAAA;
				border-radius: 4px;
				padding: 4px;
			}

		</style>
	</head>
	<body>
		<div class="contextMenu" id="myMenu">
			<ul>			
				<li id="move_left">Move Up</li>				
				<li id="move_right">Move Down</li>				
				<li id="remove">Remove</li>						
			</ul>		
		</div>
		<table border="0" style="width:100%px;">
			<tr>
				<td>
					<input type="hidden" name="tourId" id ="tourId" value="" />
	            	<input type="hidden" name="tourVersion" id ="tourVersion" value="" />
	            	<c:set var="json"><c:out value="${tourJson}" escapeXml="true" /></c:set>
	            	<input type="hidden" name="tourJson" id="tourJson" value="${json}" />
	            	<c:set var="json"><c:out value="${pois}" escapeXml="true" /></c:set>
	            	<input type="hidden" name="definedPoisJson" id="definedPoisJson" value="${json}" />
	                <table>
	                    <tr align="left" valign="top">
	                        <td>Name: </td>
	                        <td><input type="text" name="tourName" id="tourName" style="width:250px"/></td>
	                    </tr>
	                    <tr align="left" valign="top">
	                        <td>Description: </td>
	                        <td><textarea rows="4" name="tourDescription" id="tourDescription" style="width:500px"></textarea></td>
	                    </tr>
	                    <tr align="left" valign="top">
	                        <td>Thumbnail URL:<br />(80&times;80 pixels)</td>
	                        <td><input type="text" name="tourThumbnailUrl" id="tourThumbnailUrl" style="width:500px"/></td>
	                    </tr>
	                </table>
                </td>
			</tr>
			<tr><td style="width:650px" valign="top"><div id="map_canvas" style="width:650px; height:400px"></div></td></tr>
            <tr>
                <td valign="top" align="left">
                	<div id="wizard" style="width:650px">
                        <ul>
                            <li><a href="#poi">1. Points of Interest</a></li>
                            <li><a href="#route">2. Route</a></li>
                            <li><a href="#social">3. Social Media</a></li>
                            <li><a href="#permissions">4. Permissions</a></li>
                            <li><a href="#save">5. Save</a></li>
                        </ul>
                        <div id="poi">
                        	<p id="editingPoi" style="color: red; display: none;"></p>
                            <table style="width:100%;">
                                <tr>
                                    <th align="left">1. Select a location</th>
                                </tr>
                                <tr>
                                    <td>
                                        <div id="selector" style="height:300px;">
                                            <ul>
                                            	<li><a href="#defined">Pre-defined</a></li>
                                                <li><a href="#building">IU Building</a></li>
                                                <li><a href="#point">Custom Point</a></li>
                                                <li><a href="#foursquare">Foursquare Venue</a></li>
                                            </ul>
                                            <div id="defined">
                                                <div id="definedPOIs"></div>
                                            </div>
                                            <div id="building">
                                            	<p>Find a building.</p>
                                                <input type="text" name="buildingName" id="buildingName" /><button type="button" onclick="searchBuildings();" id="findButton" disabled>Find</button><span id='busy' style="color:red;display:none;"> Searching...</span>
                                                <div id="searchResults"></div>
                                            </div>
                                            <div id="point">
                                                <p>Click the map to select a location or search for an address.</p>
                                                <div id="addressSearch">
													<input id="address" type="text" value="" size="40" style="width:auto;" >
													<button id="addressSearchButton" onclick="searchAddress();">Search</button>
												</div>
                                            </div>
                                            <div id="foursquare">
                                                <p>Click the map to find nearby venues.</p>
                                                <div id="venues" class="venues"></div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th>2. Set Details</th>
                                </tr>
                                <tr>
                                    <td>
                                    	<div class="border">
                                    		<table>
		                                		<tr align="left" valign="top">
		                                			<td align="right">Location Name: </td>
		                                			<td><input type="text" name="poiName" id="poiName" size="40" /></td>
		                                		</tr>
		                                		<tr align="left" valign="top">
													<td align="right">Location Official Name:</td>
													<td><input type="text" name="poiOfficialName" id="poiOfficialName" size="60" /></td>
												</tr>
		                                		<%--<tr align="left" valign="top">
		                                			<td>IU Building Code: </td>
		                                			<td><span name="iuBuildingCode" id="iuBuildingCode" ></span><button type="button" id="iuBuildingCodeClearButton" onclick="$('#iuBuildingCode').html('');$('#iuBuildingCodeClearButton').hide();">Clear</button></td>
		                                		</tr>
		                                		<tr align="left" valign="top">
		                                			<td>Foursquare Venue Id: </td>
		                                			<td><span name="venueId" id="venueId" ></span><button type="button" id="venueIdClearButton" onclick="$('#venueId').html('');$('#venueIdClearButton').hide();">Clear</button></td>
		                                		</tr> --%>
		                                		<tr align="left" valign="top">
		                                			<td align="right">Coordinates: </td>
		                                			<td><input type="text" name="latitude" id="latitude" />, <input type="text" name="longitude" id="longitude" /></td>
		                                		</tr>
		                                		<tr align="left" valign="top">
		                                			<td align="right">URL: </td>
		                                			<td><input type="text" name="url" id="url" size="40" /></td>
		                                		</tr>
		                                		<tr align="left" valign="top">
		                                			<td align="right">Thumbnail Image URL:<br />(80&times;80 pixels) </td>
		                                			<td><input type="text" name="thumbnailUrl" id="thumbnailUrl" size="40" /></td>
		                                		</tr> 		
		                                		<tr align="left" valign="top">
		                                			<td align="right">Short Description: </td>
		                                			<td><textarea rows="5" cols="50" id="shortDescription"></textarea></td>
		                                		</tr>
		                                		<tr align="left" valign="top">
		                                			<td align="right">Full Description: </td>
		                                			<td><textarea rows="5" cols="50" id="description"></textarea></td>
		                                		</tr>
		                                		<tr align="left" valign="top">
		                                			<td align="right">Phone Number(s):</td>
		                                			<td>
		                                				Name: <input type="text" name="phoneNumberLabel" id="phoneNumberLabel" size="20" /><br />
		                                				Number: (<input type="text" name="newPhoneNumber1" id="newPhoneNumber1" size="3" />) <input type="text" name="newPhoneNumber2" id="newPhoneNumber2" size="3"/>-<input type="text" name="newPhoneNumber3" id="newPhoneNumber3" size="4"/> <button id="addPhoneNumberButton">Add</button>
		                                				<ul id="phoneNumbers"></ul>
		                                			</td>
		                                		</tr>
		                                		<tr align="left" valign="top">
		                                			<td align="right">Email Address(es):</td>
		                                			<td>
		                                				Name: <input type="text" name="emailName" id="emailName" size="20" /><br />
		                                				Address: <input type="text" name="newEmailAddress" id="newEmailAddress" size="20" /> <button id="addEmailButton">Add</button>
		                                				<ul id="emailAddresses"></ul>
		                                			</td>
		                                		</tr>
		                                	</table>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th>3. Setup Social Media</th>
                                </tr>
                            	<tr>
                                    <td>
                                    	<div class="border">
                                    		<table>
		                                		<tr align="left" valign="top">
				                            		<td align="right">Show Facebook Like Button:</td>
				                            		<td><input type="checkbox" name="fbLikeButtonEnabled" id="fbLikeButtonEnabled" value="true" /></td>
				                            	</tr>
				                            	<tr align="left" valign="top">
				                            		<td align="right">URL to &quot;Like&quot;: </td>
				                            		<td><input type="text" name="fbLikeUrl" id="fbLikeUrl" size="40" /></td>
				                            	</tr>
		                                	</table>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th>4. Add Multimedia <span id="editingMedia" style="color: red; display: none;"></span></th>
                                </tr>
                                <tr align="left" valign="top">
                                	<td>
                                		<div id="mediaTabs">
                                            <ul>
                                                <li><a href="#imageTab">Image</a></li>
                                                <li><a href="#videoTab">Video</a></li>
                                                <li><a href="#audioTab">Audio</a></li>
                                            </ul>
                                            <div id="imageTab">
                                            	<table>
			                                		<tr align="left" valign="top">
			                                			<td>URL: </td>
			                                			<td><input type="text" name="imageUrl" id="imageUrl" size="50" /></td>
			                                		</tr>
			                                		<tr align="left" valign="top">
			                                			<td>Title: </td>
			                                			<td><input type="text" name="imageTitle" id="imageTitle" size="50" /></td>
			                                		</tr>
			                                		<tr align="left" valign="top">
			                                			<td>Caption: </td>
			                                			<td><textarea rows="5" cols="50" id="imageCaption"></textarea></td>
			                                		</tr>
			                                	</table>
			                                	<button type="button" onclick="addImageToList();">Save Image</button><button type="button" onclick="stopEditingMedia();" class="mediaCancel">Cancel Edit</button>
                                            </div>
                                            <div id="videoTab">
                                            	<table>
			                                		<tr align="left" valign="top">
			                                			<td>Source: </td>
				                                		<td>
					                                		<div id="videoTabs">
					                                            <ul>
					                                                <li><a href="#videoFile">File</a></li>
					                                                <li><a href="#videoYouTube">YouTube</a></li>
					                                            </ul>
					                                            <div id="videoFile">
					                                            	<table>
					                                            		<tr>
					                                            			<th>Format</th>
					                         								<th>URL</th>
					                         							</tr>
					                         							<tr>
					                         								<td>Ogg</td>
					                         								<td><input type="text" name="oggUrl" id="oggUrl" size="50" /></td>
					                         							</tr>
					                         							<tr>
					                         								<td>MPEG 4</td>
					                         								<td><input type="text" name="mp4Url" id="mp4Url" size="50" /></td>
					                         							</tr>
					                         							<tr>
					                         								<td>WebM</td>
					                         								<td><input type="text" name="webMUrl" id="webMUrl" size="50" /></td>
					                         							</tr>
					                                            	</table>
					                                            </div>
					                                            <div id="videoYouTube">
																	Code: <input type="text" name="youTubeUrl" id="youTubeUrl" size="50" />
																	<p>Copy and paste iframe embed code from YouTube.  This can be found by clicking the share button below the video.</p><br />
																	<p>Example: &lt;iframe width="420" height="315" src="http://www.youtube.com/embed/w0oBLuMT5ok" frameborder="0" allowfullscreen&gt;&lt;/iframe&gt;</p>
					                                            </div>
				                                            </div>
			                                            </td>
			                                		</tr>
			                                		<tr align="left" valign="top">
			                                			<td>Title: </td>
			                                			<td><input type="text" name="videoTitle" id="videoTitle" size="50" /></td>
			                                		</tr>
			                                		<tr align="left" valign="top">
			                                			<td>Caption: </td>
			                                			<td><textarea rows="5" cols="50" id="videoCaption"></textarea></td>
			                                		</tr>
			                                	</table>
	                                            <button type="button" onclick="addVideoToList();">Save Video</button><button type="button" onclick="stopEditingMedia();" class="mediaCancel">Cancel Edit</button>
                                            </div>
                                            <div id="audioTab">
                                            	<table>
			                                		<tr align="left" valign="top">
			                                			<td>Source: </td>
				                                		<td>
			                                            	<table>
			                                            		<tr>
			                                            			<th>Format</th>
			                         								<th>URL</th>
			                         							</tr>
			                         							<tr>
			                         								<td>Ogg Vorbis</td>
			                         								<td><input type="text" name="oggVorbisUrl" id="oggVorbisUrl" size="50" /></td>
			                         							</tr>
			                         							<tr>
			                         								<td>MP3</td>
			                         								<td><input type="text" name="mp3Url" id="mp3Url" size="50" /></td>
			                         							</tr>
			                         							<tr>
			                         								<td>Wav</td>
			                         								<td><input type="text" name="wavUrl" id="wavUrl" size="50" /></td>
			                         							</tr>
			                                            	</table>
			                                            </td>
			                                		</tr>
			                                		<tr align="left" valign="top">
			                                			<td>Title: </td>
			                                			<td><input type="text" name="audioTitle" id="audioTitle" size="50" /></td>
			                                		</tr>
			                                		<tr align="left" valign="top">
			                                			<td>Caption: </td>
			                                			<td><textarea rows="5" cols="50" id="audioCaption"></textarea></td>
			                                		</tr>
			                                	</table>
	                                            <button type="button" onclick="addAudioToList();">Save Audio</button><button type="button" onclick="stopEditingMedia();" class="mediaCancel">Cancel Edit</button>
                                            </div>
                                        </div>
                                	</td>
                                </tr>
                                
                                <tr>
                                    <td>
                                    	<p>Selected Media</p>
										<div id="selectedMedia">
											<ul id="images">
											
											</ul>
										</div>
                                    </td>
                                </tr>
                                <tr>
                                    <th align="left">4. Add Point of Interest to Tour</th>
                                </tr>
                                <tr>
                                    <td>
                                        <button type="button" onclick="addToRoute();">Save Point</button><button type="button" onclick="stopEditingPoi();" class="poiCancel">Cancel Edit</button>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div id="route">
                        	<table>
                                <tr align="left" valign="top">
                                	<td>Create A Rough Route: </td>
                                    <td><button type="button" onclick="startEditingRoute();">Edit Route</button></td><td><button type="button" onclick="stopEditingRoute();">Stop Editing</button></td>
                                </tr>
                                <tr align="left" valign="top">
                                    <td>Fine-Tune The Route: </td>
                            		<td><button type="button" onclick="fineTuneRoute();">Fine-Tune Route</button></td><td><button type="button" onclick="stopFineTuning();">Stop Fine-Tuning</button></td>
                                </tr>
                                <tr>
                                	<td><span>Status: </span><span id="editStatus">Not Editing</span></td>
                                </tr>
                                <tr>
                                	<td><span>Route Distance: </span><span id="routeDistanceMi">0.0 Miles</span> / <span id="routeDistanceKm">0.0 Kilometers</span></td>
                                </tr>
                            </table>
                        </div>
                        <div id="social">
                        	<table style="background-color:#eee;width:100%;">
                        		<tr align="left" valign="top" style="background-color:#ccc">
                        			<th>Above the POI list<br />(start of the tour)</th>
                        			<th>Show Button</th>
                        			<th>Default Text</th>
                        		</tr>
                        		<tr align="left" valign="top">
                        			<td></td>
                        			<th><input type="checkbox" name="tweetText1Enabled" value="true" />Twitter</th>
                        			<td>
                                    	<div class="limitedTextarea" >
	                                    	<textarea maxlength="140" rows="5" cols="50" id="tweetText1"></textarea>
	                                    	<span class="charsRemaining"></span>
                                    	</div>
                                    </td>
                        		</tr>
                                <tr align="left" valign="top">
                                	<td></td>
                                	<th><input type="checkbox" name="fbText1Enabled" value="true" />Facebook</th>
                                    <td>
                                    	<div class="limitedTextarea" style="display:none;">
	                                    	<textarea maxlength="420" rows="5" cols="50" id="fbText1" disabled="disabled"></textarea>
	                                    	<span class="charsRemaining"></span>
                                    	</div>
                                    </td>
                                </tr>
                                <tr align="left" valign="top" style="background-color:#ccc">
                        			<th>Below the POI list<br />(end of the tour)</th>
                        			<td></td>
                        			<td></td>
                        		</tr>
                                <tr align="left" valign="top">
                                    <td></td>
                                    <th><input type="checkbox" name="tweetText2Enabled" value="true" />Twitter</th>
                            		<td>
                            			<div class="limitedTextarea" >
	                            			<textarea maxlength="140" rows="5" cols="50" id="tweetText2"></textarea>
	                            			<span class="charsRemaining"></span>
                            			</div>
                            		</td>
                                </tr>
                                <tr align="left" valign="top">
                                    <td></td>
                                    <th><input type="checkbox" name="fbText2Enabled" value="true" />Facebook</th>
                            		<td>
                            			<div class="limitedTextarea" style="display:none;">
	                            			<textarea maxlength="420" rows="5" cols="50" id="fbText2" disabled="disabled"></textarea>
	                            			<span class="charsRemaining"></span>
                            			</div>
                            		</td>
                                </tr>
                            </table>
                        </div>
                        <div id="permissions">
                        	Give viewing/editing permission to ADS groups.
                        	<table style="background-color:#eee;width:100%;">
                        		<tr align="left" valign="top" style="background-color:#ccc">
                        			<th colspan="2">View Permissions</th>
                        		</tr>
                        		<tr align="left" valign="top">
                        			<td colspan="2"></td>
                        		</tr>
                        		<tr align="left" valign="top">
                        			<td>
	                                    <input type="text" name="newViewPermission" id="newViewPermission"/><button id="addViewPermissionButton">Add</button><br />
	                                    <span id="publicTour">This tour will be publicly viewable.  To restrict access, add a group.</span>
                                    </td>
                        			<td>
	                                    <ul class="permissionsList" id="viewPermissionsList"></ul>
                                    </td>
                        		</tr>
                        		<tr align="left" valign="top" style="background-color:#ccc">
                        			<th colspan="2">Edit Permissions</th>
                        		</tr>
                                <tr align="left" valign="top">
                                	<td>
	                                    <input type="text" name="newEditPermission" id="newEditPermission"/><button id="addEditPermissionButton">Add</button><p>At least one group is required.</p>
                                    </td>
                                    <td>
                                    	<ul class="permissionsList" id="editPermissionsList"></ul>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div id="save">
                        	<p>Review Selected Points of Interest</p>
                            <div id="selectedPOIs"></div><br />
                        	<button type="button" onclick="saveTour();">Save</button><button type="button" onclick="generateRouteKml();">Generate KML</button>
                        	<form id="postForm" action="${pageContext.request.contextPath}/tours/edit" method="post" >
                        		<input type="hidden" id="data" name="data" value="" />
                        	</form>
                        </div>
                    </div>
                </td>
			</tr>
<!--
            <tr>
				<td style="padding-top:10px;">
					<br />
					Markers:
					<input type="radio" name="group1" value="Show" onclick="showMarkers2();" checked />Show
					<input type="radio" name="group1" value="Hide" onclick="hideMarkers();" />Hide
				</td>				
			</tr>-->
		</table>
	</body>
</html>