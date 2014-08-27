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