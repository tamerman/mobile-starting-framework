(function(){

	/* Get local ref to global PhoneGap/Cordova/cordova object for exec function.
		- This increases the compatibility of the plugin. */
	var cordovaRef = window.PhoneGap || window.Cordova || window.cordova; // old to new fallbacks


	function AudioStreamer() { }
 
//	AudioStreamer.prototype.setStreamURL = function(url, autoplay, type, success, fail) {
//        console.log("AudioStreamer.prototype.setStreamURL()");
//		var args = {};
//        args.url = url;
//        args.autoplay = autoplay;
//        args.type = type;
////        console.log("AudioStreamer.prototype.setStreamURL was called");
//        cordovaRef.exec(success, fail, "CDVAudioStreamer", "setStreamURL", [args]);
//	};

	AudioStreamer.prototype.play = function(myurl, options, success, fail) {
        options = options || {url: myurl,
                        autoplay: true,
                           type: "mp3"}; 
        cordovaRef.exec(success, fail, "CDVAudioStreamer", "playStream", [options]);
	};


//    AudioStreamer.prototype.toggle = function(success, fail) {
//        var args = {};
////        console.log("AudioStreamer.prototype.playStream was called");
//        cordovaRef.exec(success, fail, "CDVAudioStreamer", "toggleStream", [args]);
//    };
// 
    AudioStreamer.prototype.pause = function(success, fail) {
        cordovaRef.exec(success, fail, "CDVAudioStreamer", "pauseStream", []);
    };

    AudioStreamer.prototype.stop = function(success, fail) {
        cordovaRef.exec(success, fail, "CDVAudioStreamer", "stopStream", []);
    };
 
	cordovaRef.addConstructor(function() {
		if(!window.plugins) {
			window.plugins = {};
		}
		if(!window.plugins.audioStreamer) {
    		window.plugins.audioStreamer = new AudioStreamer();
		}
	});
	
})(); /* End of Temporary Scope. */