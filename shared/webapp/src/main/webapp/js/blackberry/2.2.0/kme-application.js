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

/** Constructor */
function KMEApplication(){
	this.init();
};

/**
 * Initialises the KME Application
 */
KMEApplication.prototype.init = function(){
	this.pushConfig = window.kme.pushConfig;
	this.config = {
		// Page to load when a push notification is received
		pushPage : "local:///index.html", 

		// Base url including context path of web application
		baseUrl : "/mdot",

		// Home page url
		homeUrl : "/mdot/home", 

		// Maximum number of push notifications that can be queued
		maxPushQueue : 10,
		
		// Flag if push notification registration should be forced instead of once a day
		forcePushRegister : true,
		
		// The name of the application to display on notifications
		// TODO get this from the server config
		applicationName : "Kuali Mobile",

		// Flag if debug output is enabled
		debug : true
	};

	this.serverInterval = null;
	this.serverOffline = false; // Assume online
	this.deviceOffline = false;	// Assume online
	this.isForeground = true;	// App usually starts in foreground...
	var app = this;
	$(document).ready(function(){app.onDocumentReady();});
	document.addEventListener('deviceready', function(){app.initDeviceReady()}, false);
};

/**
 * Invoked when the document is ready
 */
KMEApplication.prototype.onDocumentReady = function(){
	// Start an activity spinner when the page is changing
	 $(window).unload(
        function(event) {
			window.navigator.notification.activityStart();
        }
    );
};

/**
 * Callback when cordova sees the devices as ready
 */
KMEApplication.prototype.initDeviceReady = function(){
	this.log("initDeviceReady()");
	//this.offlineChildBrowser = window.plugins.childBrowser;

	/**
	 * Always call to register for push to update the callback methods on each page load.
	 * BB will see it is already listening for message (if it is) and only update the call backs
	 */
	this.registerForPush();
	
	var app = this;
	document.addEventListener('deviceready', 	function(){app.handleDeviceReady();}, false); // On Startup
	document.addEventListener('resume', 		function(){app.onResume();}, false); // Going to foreground
	document.addEventListener('pause', 			function(){app.onPause();}); // Going to background
	document.addEventListener('offline', 		function(){app.onDeviceOffline();}, false);
	document.addEventListener('online', 		function(){app.onDeviceOnline();}, false);

};

/**
 * Check if the server is online.
 */
KMEApplication.prototype.isServerOnline = function(){
	this.log("isServerOnline()");
	var online = false;
	$.ajax({
		url: this.config.baseUrl + "/device/ping",
		type:"GET",
		async: false,
		cache: false,
		success: function() {
			online = true;
		},
		error: function() {
			online = false;
		}
	});
	
	return online;
};

/**
 * Callback when the server goes offline
 */
KMEApplication.prototype.onServerOffline = function(){
	this.log("onServerOffline()");
	if (!this.serverOffline) {
		this.serverOffline = true;
		//TODO offlineChildBrowser.showWebPage("local:///serverOffline.html");
		var app = this;
		this.serverInterval = window.setInterval(
			function() {
				if (app.isServerOnline()) {
					app.onServerOnline(); 
				}
			},
		5000);
	}
};

/**
 * Callback when the server comes back online
 */
KMEApplication.prototype.onServerOnline = function(){
	this.log("onServerOnline()");
	window.clearInterval(this.serverInterval);
	//TODO this.offlineChildBrowser.close();
	this.serverOffline = false;
};


/**
 * Callback when the device goes offline
 */
KMEApplication.prototype.onDeviceOffline = function(){
	this.log("onDeviceOffline()");
	this.deviceOffline = true;
	this.serverOffline = false; // We don't know, since we are offline
	// TODO offlineChildBrowser.showWebPage("local:///deviceOffline.html");
};

/**
 * Callback when the devices comes back online
 */
KMEApplication.prototype.onDeviceOnline = function(){
	this.log("onDeviceOffline()");
	this.deviceOffline = false;
	if (!this.isServerOnline()) {
		this.onServerOffline();
	} else {
		// TODO this.offlineChildBrowser.close();
	}
};


/**
 * Yet another device ready handler
 */
KMEApplication.prototype.handleDeviceReady = function(){
	this.log("handleDeviceReady()");
	window.navigator.notification.activityStop();
};


/**
 * Returns true if the device is already registered for push notifications
 */
KMEApplication.prototype.isRegisteredForPush = function(){
	return !(this.config.forcePushRegister || ($.cookie("rid") != null));
};

/**
 * Register for push notifications on the vendor.
 */
KMEApplication.prototype.registerForPush = function(){
	this.log("registerForPush()");
	try {
		var app = this;
		var options = {
				"port" 			: this.pushConfig.getPort(),
				"appId" 		: this.pushConfig.getApplicationId(),
				"serverUrl" 	: this.pushConfig.getRegistrationUrl(),
				"wakeUpPage" 	: this.config.pushPage,
				"maxQueueCap"	: this.config.maxPushQueue};
		
		blackberry.push.openBISPushListener(
				options, 
				function(data)	{app.onPushMessage(data);}, 
				function(status){app.onPushRegistered(status);}, 
				function()		{app.onSimChange();}
		);
	}
	catch (err) {
		// Most probably we are already registered
		this.log("Error listening for push: " + err);
	}
};

/**
 * Deregister to no longer listen for push notifications
 */
KMEApplication.prototype.deRegisterForPush = function(){
	this.log("deRegisterForPush()");
	blackberry.push.closePushListener();
	// Remove registered cookie
	$.cookie("rid", null);
};


/**
 * Callback when a push notification is received
 */
KMEApplication.prototype.onPushMessage = function(data){
	this.log("onPushMessage()");

	var notification = blackberry.utils.blobToString(data.payload);

	if(this.isDebugging()){
		this.log("Notification Received: " + notification);
	}

	notification = JSON.parse(notification); // Create object from json string
	
	if (notification.id == null) {
		this.log("Notification ID Null, not handling notification.");
		return; 
	}
	
	// Init variables
	var emergency = (notification.emer != null && notification.emer == "YES");
	var message = notification.message;
	var isForeground = this.isForeground;
	var url = notification.url;
	var app = this;
	
	
	if (this.isDebugging()){
		this.log("Notification ID           : " + notification.id);
		this.log("Emergency                 : " + emergency);
		this.log("Application in foreground : " + isForeground);
	}
	
	// Notify KME Server
	this.notifyPushReceived(notification.id);

	var viewNotification = function() {
		if (url && url != "") {
			app.changePage(app.config.baseUrl + url);
		}
		// If the notification did not have a URL, take the user home
		else {
			app.changePage(app.config.homeUrl);
		}
	};
	
	/* If the app is in the foreground, and the message is an emergency,
	 * we will display a popup, and direct the user to the content if the
	 * user selected to view it.
	 */
	if (emergency) {
		
		if (isForeground){
			// Callback function for when the user chooses to view the message
			var onOkay = function (buttonIndex) {
				if (buttonIndex == 1) { // Ok Button
					viewNotification();
				}
			}
		
			navigator.notification.confirm(
				message,
				onOkay,
				this.config.applicationName,
				'Ok,Cancel');

		}else { // Background emergency message
			viewNotification();
		}
	}
};


/**
 * Callback when the user changed sim card
 */
KMEApplication.prototype.onSimChange = function(){
	this.log("onSimChange()");
	// When the sim card changed the registration id will change as well as the device id
	this.registerForPush();
	// TODO delete old device
};

/**
 * Registeres the device on the KME server for push notifications
 */
KMEApplication.prototype.registerDevice = function(deviceId, registrationId){
	this.log("registerDevice()");

	// Data to send to KME server
	var dataObject = { 
			"name" 		: device.name,
			"deviceId"	: deviceId,
			"regId"		: registrationId,
			"type"		: "Blackberry"
	};
	
	// Send registration details to server
	$.ajax({
		url			: this.config.baseUrl + "services/device/register",
		type		: "GET",
		data		: "data=" + JSON.stringify(dataObject),
		contentType	: "application/json; charset=utf-8",
		dataType	: "json",
		success		: function() {}
	});
	
	// Set the cookie with registration id
	$.cookie("rid", registrationId, {
		expires : 1, // 1day
		path : this.config.baseUrl
	} );
};

/**
 * Notifies the KME Server that a push notification has been received
 */
KMEApplication.prototype.notifyPushReceived = function(pushId){
	this.log("notifyPushReceived()");

	var app = this;
	var dataObject = {
		"deviceId" 		: app.getDeviceId(),
		"notificationId": pushId
	};
	// Let KME server know we received the notification
	$.ajax({
		url: this.config.baseUrl + "/push/received",
		type:"POST",
		data:JSON.stringify(dataObject),
		contentType:"application/json; charset=utf-8",
		dataType:"json",
		success: function() {
			app.log("Success notifying server of push received");
		},
		error : function(jqXHR, textStatus, errorThrown){
			app.log("Error trying to notifify server : " + textStatus + " - " + errorThrown);
		}
	});
}

/**
 * Callback when the device is registered to listen for push notifications
 */
KMEApplication.prototype.onPushRegistered = function(status){
	this.log("onPushRegistered()");
	if (status == 0) {
		this.log("success");
		if (!this.isRegisteredForPush()){
			this.registerDevice(this.getDeviceId(), this.getDeviceId());
		}else{
			this.log("Device already registered for push notifications");
		}
		
	}
	else if (status == 1) {
		this.log("network error");
	}
	else if (status == 2) {
		this.log("rejected by server");
	}
	else if (status == 3) {
		this.log("invalid parameters");
	}
	else if (status == -1) {
		this.log("general error");
	}
	else {
		this.log("unknown status");
	}
};

/**
 * Gets the device id
 */
KMEApplication.prototype.getDeviceId = function(){
	this.log("getDeviceId()");
	return Number(device.uuid).toString(16);
};


/**
 * Function to change the current page
 */
KMEApplication.prototype.changePage = function(url){
	this.log("changePage()");
	window.location=url;
};

/**
 * Callback when the application resumes from the background
 */
KMEApplication.prototype.onResume = function(){
	this.log("onResume()");
	this.isForeground = true;
	/*
	  if (!deviceOffline && !isServerOnline()) {
			onServerOffline();
		}
	 */
};

/**
 * Callback when the application resumes from the background
 */
KMEApplication.prototype.onPause = function(){
	this.log("onPause()");
	this.isForeground = false;
};

/**
 * Returns true if debugging is enabled
 */
KMEApplication.prototype.isDebugging = function(){
	return this.config.debug == true;
};

/**
 * Logs a message to the console if loggin is enabled
 */
KMEApplication.prototype.log = function(str){
	if (this.isDebugging()){
		console.log(str);
	}
};

// Attach the application to window.kme.app
window.kme = window.kme || {};
window.kme.app = window.kme.app || new KMEApplication();
