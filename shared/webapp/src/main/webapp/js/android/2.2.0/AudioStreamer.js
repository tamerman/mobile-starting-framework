

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
