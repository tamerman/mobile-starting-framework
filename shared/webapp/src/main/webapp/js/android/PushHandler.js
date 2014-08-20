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

$(function(){	
	document.addEventListener("deviceready",onIncomingPush,false);	
});

function onIncomingPush(){
	console.log("ONINCOMINGPUSH: " + window.Push.getUrl());
	if(window.Push.hasPush()){
		handle(window.Push.getMessage(), window.Push.getUrl(), window.Push.getTitle());
//		if(window.Push.getUrl() != ""){
//		    navigator.notification.confirm(
//		            window.Push.getMessage(),  // message
//		            function(buttonIndex){
//		            	window.Push.markRead();
//		            	window.plugins.statusBarNotification.clear();
//		            	if(buttonIndex == 1){
//		            		window.location = window.Push.getUrl();
//		            	}
//		            },              // callback to invoke with index of button pressed
//		            'Kuali Mobile',            // title
//		            'Go to Link,Ok'          // buttonLabels
//		    );
//		}else{
//			navigator.notification.alert(	window.Push.getMessage(), 
//											function(){
//												window.Push.markRead();
//								            	window.plugins.statusBarNotification.clear();
//											}, 
//											'Kuali Mobile', 
//											'OK'
//			);
//		}
	}
}

function handle(msg, url, title){
	console.log("HANDLE");
	if(url != ""){
	    navigator.notification.confirm(
	            msg,  // message
	            function(buttonIndex){
	            	window.Push.markRead();
	            	window.plugins.statusBarNotification.clear();
	            	if(buttonIndex == 1){
	            		window.location = url;
	            	}
	            },              // callback to invoke with index of button pressed
	            title,            // title
	            'Go to Link,Ok'          // buttonLabels
	    );
	}else{
		navigator.notification.alert(	msg,
										function(){
											window.Push.markRead();
							            	window.plugins.statusBarNotification.clear();
										}, 
										title, 
										'OK'
		);
	}	
	
	console.log($("head").html());
}
