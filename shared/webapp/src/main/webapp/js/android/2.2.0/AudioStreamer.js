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



/**
 * Constructor
 */
function AudioStreamer() {
};

/**
 * Play a stream at a given URL, with options. 
 */
AudioStreamer.prototype.play = function(url, options, success, fail) {
    options = options || {
        autoplay: true,
        type: "mp3"
    };
    cordova.exec(success,fail, "AudioStreamer", "play", [url, options]);
};

/**
 * Pause the current playing stream. Effectively stops and destroys the service. 
 */
AudioStreamer.prototype.pause = function(success, fail) {
	cordova.exec(success, fail, "AudioStreamer", "pause", []);
};

AudioStreamer.prototype.stop = function(success, fail) {
	cordova.exec(success, fail, "AudioStreamer", "pause", []);
};


/**
 * Maintain API consistency with iOS
 */
AudioStreamer.prototype.install = function(){
};

/**
 * Load ChildBrowser
 */

if(!window.plugins) {
    window.plugins = {};
}
if (!window.plugins.audioStreamer) {
    window.plugins.audioStreamer = new AudioStreamer();
}

console.log("Loaded AudioStreamer.js");
