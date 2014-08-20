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
