//console.log( localStorage.site );
gApp = new Array();

gApp.deviceready = false;
gApp.gcmregid = '';

window.onbeforeunload  =  function(e) {
    if ( gApp.gcmregid.length > 0 ){
    	// The same routines are called for success/fail on the unregister. You can make them unique if you like
    	//window.plugins.GCM.unregister( GCM_Success, GCM_Fail );      // close the GCM
    }
};


document.addEventListener('deviceready', function() {
	window.Registration.setDeviceName(device.name);
	window.Registration.setDeviceId(device.uuid);

});

document.addEventListener('deviceready', function() {
	console.log('calling GCMRegistrar.register, register our Sender ID with Google' );
	gApp.DeviceReady = true;

	var username = $.cookie('currentNetworkId');
	console.log("++++ Username: " + username);


	if(username != null){
		window.Registration.setUsername(username);
	}

	// CHANGE: your_app_id
	// TO: what ever your GCM authorized senderId is
	//
	var pushConfig = window.kme.pushConfig;
	window.plugins.GCM.register(pushConfig.getApplicationId(), "GCM_Event", GCM_Success, GCM_Fail );
	console.log('Did Call Register' );

    updateRegistration();

}, false );


function GCM_Event(e){
	console.log('EVENT -> RECEIVED:' + e.event);

	switch( e.event ){
	case 'registered':
	    // the definition of the e variable is json return defined in GCMReceiver.java
	    // In my case on registered I have EVENT and REGID defined
	    gApp.gcmregid = e.regid;
	    if ( gApp.gcmregid.length > 0 ){
			console.log('REGISTERED -> REGID:' + e.regid);
			console.log('DEVICE -> UUID:' + device.uuid);
			
			var username = $.cookie('currentNetworkId');
			if(username != null){
				window.Registration.setUsername(username);
			}else{
				username = '';
			}				
			window.Registration.setDeviceName(device.name);
			window.Registration.setRegId(e.regid);
			window.Registration.setDeviceId(device.uuid);

			var mdotServer = window.kme.serverDetails.getServerPath();
            var jsonTemplate = "{name:\"" + device.name + "\",regId:\"" + e.regid + "\",deviceId:\"" + device.uuid + "\",type:\"Android\",username:\"" + username + "\"}";
            var registerHost = mdotServer + '/services/device/register?data=' + encodeURIComponent(jsonTemplate);

            console.log(registerHost);
								
			$.get(registerHost, function(data){
				console.log("LOCAL REGID -> Sent to AppServer: " + data);			
			});
	    }
	    break
	case 'message':
	    handle(e.msg, e.url, e.title);
	    console.log('MESSAGE -> MSG:' + e.msg);
	    break;
	case 'error':
		console.log('ERROR -> MSG:' + e.msg);
		break;
	default:
		console.log('EVENT -> Unknown, an event was received and we do not know what it is');
    	break;
	}
}

function updateRegistration(){	
	console.log('UPDATE REGISTRATION')
	var username 	= window.Registration.getUsername();
	var regId 		= window.Registration.getRegId();
	var deviceId	= window.Registration.getDeviceId();
	var deviceName 	= window.Registration.getDeviceName();
	
	if(	username.length > 0 &&
		regId.length > 0 &&
		deviceId.length > 0 &&
		deviceName.length > 0){
		var mdotServer = window.kme.serverDetails.getServerPath();
		var jsonTemplate = "{name:\"" + deviceName + "\",regId:\"" + regId + "\",deviceId:\"" + deviceId + "\",type:\"Android\",username:\"" + username + "\"}";
		var registerHost = mdotServer + '/services/device/register?data=' + encodeURIComponent(jsonTemplate);
		console.log("##### UPDATE REGISTRATION: " + registerHost);
		$.get(registerHost, function(data){
			console.log("LOCAL REGID -> Sent to AppServer: " + data);
		});	

	}
}

function GCM_Success(e){
	console.log('GCM_Success -> We have successfully registered and called the GCM plugin, waiting for GCM_Event:reistered -> REGID back from Google');
}

function GCM_Fail(e){
	console.log('GCM_Fail -> GCM plugin failed to register');
	console.log('GCM_Fail -> ' + e.msg);
}

console.log("Loaded CORDOVA_GCM.js");