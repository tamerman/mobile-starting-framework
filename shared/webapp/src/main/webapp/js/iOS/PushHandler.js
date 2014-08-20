/*
  The MIT License (MIT)
  
  Copyright (C) 2014 by Kuali Foundation

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
*/

// Set a deviceready listener, this will execute onIncomingPush once the native app and the webview 
// is completely loaded. 

$(function(){	
	document.addEventListener("deviceready",onIncomingPush,false);	
	console.log("onIncomingPush Listener enabled");
});


// Deal with an inbound push when app is not running. Basically we have to use the native portion
// of the app to inject the JSON into the webview, then use this function to use it. 
function onIncomingPush(){
	console.log("onIncomingPush");	
	// Get the hasPush variable from the native app. Which is set on start up if there is a push. 
	window.plugins.applicationPreferences.get('hasPush', function(result) {

			// If yes, grap the JSON formatted pushMessage, and get the pertenant parts out of it. 
			if(result == "YES"){
				

				
				var pushObj = jQuery.parseJSON(pushMessageJSON);
				var msg		= pushObj.aps.alert;
				var badge	= pushObj.aps.badge;
				var msgID	= pushObj.i;
				var emer	= pushObj.e;

				// Deal with it like any other incoming push notification. 
				handleIncomingPush(msg, msgID, emer, badge);

				// Reset the hasPush variable to NO, indicating that it has been dealt with. 
				window.plugins.applicationPreferences.set('hasPush','NO', function() {
						console.log("It is saved");
					}, function(error) {
						console.log("Failed to retrieve a setting: " + error);
					}
				);
			}
		}, function(error) {
			console.log("Failed to retrieve a setting: " + error);
		}
	);
}

function test(){
	alert("suck");
}



function handleIncomingPush(msg, mid, emergency, badge){
	console.log("handleIncomingPush");
    var url = window.kme.serverDetails.getServerPath() + "/push/get/" + mid;

    $.ajax({
        type: "GET",
        url: url,
        dataType: 'jsonp',
        jsonp: 'pushJSON',
        jsonpCallback: 'jsonpCallback',
         
        //if received a response from the server
        success: function( data, textStatus, jqXHR) {
            console.log(data);
        },
         
        //If there was no resonse from the server
        error: function(jqXHR, textStatus, errorThrown){
             console.log("Something really bad happened " + textStatus);
        },
    });  	
	
//	$.getJSON("http://mtwagner.dyndns.org:9999/mdot/push/get/" + mid + "?callback=pushJSON", null, function(json){
//		console.log(json);
//		// If the push is an Emergency, make a noise and vibrate.  
//		if(emergency == "YES"){
//			navigator.notification.beep(3);
//			navigator.notification.vibrate(3000); 
//		}else{
//			console.log("not an emergency");
//		}
//		
//		// If there is no URL associated with the Push, just display message. 
//		if(json.url == ""){
//			console.log("no url");
//			navigator.notification.alert(
//				json.message, 
//				function(){
//				
//				}, 
//				json.title, 
//				'OK');
//		}else{	// If there is a URL included in the Push, give an option to follow it. 
//			console.log("has url");
//			navigator.notification.confirm(
//				json.message, 
//				function(button){
//					if(button == 1){
//						window.location = json.url;
//					}
//				}, 
//				json.title, 
//				'Open Link, Close');
//		}
//	});
	return;
}


function pushJSON(data){
    data = JSON.parse(data);
    
	// If the push is an Emergency, make a noise and vibrate.  
	if(data.emergency == "YES"){
		navigator.notification.beep(3);
		navigator.notification.vibrate(3000); 
	}
	
	// If there is no URL associated with the Push, just display message. 
	if(data.url == ""){
		navigator.notification.alert(
			data.message, 
			function(){
			
			}, 
			data.title, 
			'OK');
	}else{	// If there is a URL included in the Push, give an option to follow it. 
		navigator.notification.confirm(
			data.message, 
			function(button){
				if(button == 1){
					window.location = data.url;
				}
			}, 
			data.title, 
			'Open Link, Close');
	}
}

function jsonpCallback(data) {
    console.log("callback: ",data);
}


