/*
 * The MIT License
 * Copyright (c) 2011 Kuali Mobility Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


var appStatus = $("#applicationStatus");
var appEvents = $("#applicationEvents");
var manualUpdate = $("#manualUpdate");
var cacheProgress = $("#cacheProgress");
var cacheProgressText = $("#cacheProgressText");

// Short-hand for our appcache object.
var appCache = window.applicationCache;

// Create a cache properties object to help us keep track of the progress of the caching.
var cacheProperties = {
    filesDownloaded: 0,
    totalFiles: 0
};

// Get the total number of files in the cache manifest - manually parse the manifest file.
function getTotalFiles() {
    // Init.
	console.info("Entering getTotalFiles()");
    cacheProperties.filesDownloaded = 0;
    cacheProperties.totalFiles = 0;

    // Get the cache manifest file, hard-coded link.
    $.ajax({
        type: "get",
        url: "./kme.appcache",
        dataType: "text",
        cache: false,
        success: function (content) {
            // Strip out the non-cache sections.
            content = content.replace(new RegExp("(NETWORK|FALLBACK):" + "((?!(NETWORK|FALLBACK|CACHE):)[\\w\\W]*)", "gi"), "");

            // Strip out all comments.
            content = content.replace(new RegExp("#[^\\r\\n]*(\\r\\n?|\\n)", "g"), "");

            // Strip out the cache manifest header and
            // trailing slashes.
            content = content.replace(new RegExp("CACHE MANIFEST\\s*|\\s*$", "g"), "");

            // Strip out extra line breaks and replace with
            // a hash sign that we can break on.
            content = content.replace(new RegExp("[\\r\\n]+", "g"), "#");

            // Get the total number of files.
            var totalFiles = content.split("#").length;

            // Store the total number of files. Here, we are
            // adding one for *THIS* file, which is cached
            // implicitly as it points to the manifest.
            //cacheProperties.totalFiles = (totalFiles + 1);

            // For some reason we are getting one extra file in the count. Removing the +1 from above.
            cacheProperties.totalFiles = (totalFiles + 0);
        }
    });
}

// Display the download progress.
function displayProgress() {
    // Increment the running total.
    cacheProperties.filesDownloaded++;

    // Check to see if we have a total number of files.
    if (cacheProperties.totalFiles) {

        // We have the total number of files, so output the running total as a fraction of the known total.
        //cacheProgressText.text(cacheProperties.filesDownloaded + " of " + cacheProperties.totalFiles + " files downloaded.");
        var percentDownloaded = 0;

        percentDownloaded = (cacheProperties.filesDownloaded / cacheProperties.totalFiles) * 100;
        if (percentDownloaded > 99) { percentDownloaded = 99; }

        var progressBar = '<div id="appcacheProgressBar"><div id="appcacheProgressAmount" style="width:' + percentDownloaded.toFixed() + '%">&nbsp;' + percentDownloaded.toFixed() + '%</div></div>';
        cacheProgress.html(progressBar);

        if (percentDownloaded > 1) { cacheProgressText.text("Downloading KME App Foundation...");
	        if (percentDownloaded > 20) { cacheProgressText.text("Updating Session Schedules...");
		        if (percentDownloaded > 30) { cacheProgressText.text("Downloading Monday Schedule...");
		        	if (percentDownloaded > 35) { cacheProgressText.text("Downloading Tuesday Schedule...");
			        	if (percentDownloaded > 41) { cacheProgressText.text("Downloading Wednesday Schedule...");
				        	if (percentDownloaded > 58) { cacheProgressText.text("Processing Sessions Schedule...");
					        	if (percentDownloaded > 66) { cacheProgressText.text("Updating Caches...");
						        	if (percentDownloaded > 75) { cacheProgressText.text("Downloading Map Data...");
							        	if (percentDownloaded > 90) { cacheProgressText.text("Processing HTML5 AppCache...");
							            }
						            }
					            }
				            }
			            }
		        	}
		        }
	        }
	    }

        /*if (percentDownloaded > 95) {
        	$('div#cacheProgressModal').fadeOut();
            $('div#cacheProgressMessage').fadeOut();
        }*/
    } else {
        // If we don't know the total number of files, just output the running total.
        //cacheProgress.text(cacheProperties.filesDownloaded + " files downloaded.");
    }
}

// Bind the manual update link.
manualUpdate.click(function (event) {
	// Prevent the default event.
	event.preventDefault();
	// Manually ask the cache to update.
	appCache.update();
});

// Bind to online/offline events.
$(window).bind("online offline", function (event) {
    // Update the online status.
    appStatus.text(navigator.onLine ? "Online" : "Offline");
});

// Set the initial status of the application.
appStatus.text(navigator.onLine ? "Online" : "Offline");

// List for checking events. This gets fired when the browser
// is checking for an udpated manifest file or is attempting
// to download it for the first time.
$(appCache).bind("checking", function (event) {
	//alert('checking for new manifest');
    // Do nothing.
});

// There is no update to the manifest file that has just been checked.
$(appCache).bind("noupdate", function (event) {
	// Do nothing.
	//alert('Cache NoUpdate');
});

// The browser is downloading the files defined in the cache manifest.
$(appCache).bind("downloading", function (event) {
    $('div#cacheProgressModal').fadeIn(
//	function() {
//		$.data(this, 'timer', setTimeout(function() {
//  		  // timeout
//  		  $('div#cacheProgressModal').fadeOut();
//	      $('div#cacheProgressMessage').fadeOut();
//	  }, 100000)); // 100 second timeout
//	}
	);

    $('div#cacheProgressMessage').css('top', '60px');
    // Get the total number of files in our manifest.
    getTotalFiles();
});

// Run for every file that is downloaded by the cache update.
$(appCache).bind("progress", function (event) {
    // Show the download progress.
    displayProgress();
    //console.log(event);
});

// All cached files have been downloaded and are available to the application cache.
$(appCache).bind("cached", function (event) {
    $('div#cacheProgressModal').fadeOut();
    $('div#cacheProgressMessage').fadeOut(1000, function () {
    	window.location.reload();
    });
    //alert('Cache Cached');
});

// New cache files have been downloaded and are ready to replace the existing cache. The old cache will need to be swapped out.
$(appCache).bind("updateready", function (event) {
    $('div#cacheProgressModal').fadeOut();
    $('div#cacheProgressMessage').fadeOut(1000, function () {
    	window.location.reload();
    });
    //alert('Cache UpdateReady');
    appCache.swapCache();
});

// The cache manifest cannot be found.
$(appCache).bind("obsolete", function (event) {
	$('div#cacheProgressModal').fadeOut();
    $('div#cacheProgressMessage').fadeOut();
    //alert('Cache Obsolete');
});

// An error occurred.
$(appCache).bind("error", function (event) {
	$('div#cacheProgressModal').fadeOut();
    $('div#cacheProgressMessage').fadeOut();
    //alert('Cache Error');
});
