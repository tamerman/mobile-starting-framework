/*	Dependancies 
*		ApplicationPreferences plugin and variable called "LocalNotification".
*		LocalNotification plugin (of course)
*		jQuery
*/		


/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
*	Added the onIncoming as a event listener for deviceready. This is needed to deal with a
*	LocalNotification that occurs when the app is not running at. all. 
*	
*
*/	
$(function(){	
	document.addEventListener("deviceready", Notifications.onIncoming, false);	
});


/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
*	Notification Handler. Once this file/object is included, it can be used directly via the following call. 
*
*
*	var id = '1234';
*	Notifications.add({...});
*	Notifications.cancel(id);
*	etc...
*/	
var Notifications = {
	onIncoming:function(){
		window.plugins.applicationPreferences.get('LocalNotification', function(LocalNotification){
				if(typeof(LocalNotification) != undefined && LocalNotification != ''){
					var noteOBJ = jQuery.parseJSON(LocalNotification);
					var msg = noteOBJ.alertBody;
					var url = noteOBJ.url;
					var id	= noteOBJ.notificationId;
					
					Notifications.handle(msg, url, id);
					
					window.plugins.applicationPreferences.set('LocalNotification','', function() {
							console.log("LocalNotification: nullified");
						}, function(error) {
							console.log("Failed to nullify LocalNotification: " + error);
						}
					);											
				}
			}, function(error){
				console.log("Failed to get setting: " + error);
			}
		);	
	},
	handle:function(msg, url, id){
		if(url == ""){
			navigator.notification.alert(	msg, 
											function(){
												window.plugins.localNotification.cancel(id);
											}, 
											mdotName, 
											'Ok');	
		}else{
			navigator.notification.confirm(	msg,
											function(button){
												window.plugins.localNotification.cancel(id);
												if(button == 1){
													window.location = url;
												}
											},
											mdotName,
											'Open Link, Close');
		
		}	
	},
	cancel:function(id){
		window.plugins.localNotification.cancel(id);
		console.info("Cancelled Notification ID: " + id);	
	},
	cancelAll:function(){
		window.plugins.localNotification.cancelAll();	
		console.info("Cancelled All Notification");	
	},
	add:function(notObj){
		window.plugins.localNotification.add(notObj);
		console.log("Added new Note: " + d);
	}
};

