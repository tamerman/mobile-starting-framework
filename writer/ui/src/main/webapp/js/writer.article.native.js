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

var pictureSource; // picture source
var destinationType; // sets the format of returned value
var childBrowser;

// Wait for PhoneGap to connect with the device
document.addEventListener("deviceready", onDeviceReady, false);

// PhoneGap is ready to be used!
function onDeviceReady() {
	// Setup variable names shorter than the stock phonegap ones.
	pictureSource = navigator.camera.PictureSourceType;
	destinationType = navigator.camera.DestinationType;
	childBrowser = window.plugins.childBrowser;
}

//Set the events for native iputs
$(document).ready(function() {
	$("#captureNativeImage").click(function() {
		captureNativeImage();
	});
	
	$("#selectNativeImage").click(function() {
		selectNativeMedia(Camera.MediaType.PICTURE);
	});

	$("#captureNativeVideo").click(function() {
		//captureNativeVideo();
		displayFunctionNotAvailable();
	});
	
	$("#selectNativeVideo").click(function() {
		//selectNativeMedia(Camera.MediaType.VIDEO);
		displayFunctionNotAvailable();
	});
});

/**
 * Function to preview an image.
 * This function will open a child browser
 * @param imageId ID of the image
 */
function previewImage(imageId){
	childBrowser.onLocationChange = function(loc){
		console.log(loc);
		if (loc.indexOf("close") > 0){
			childBrowser.close();
		}
	};
	childBrowser.showWebPage(imagePreviewBaseURL + imageId,{showLocationBar: false });
}

/**
 * Capture a image from the camera
 */
function captureNativeImage() {
	navigator.device.capture.captureImage(
	// captureImage Success
	function(mediaFiles) {
		var len = mediaFiles.length; // We are only using one file
		if (len >= 1) {
	        updateMediaObject(ArticleConstants.mediaType.IMAGE, ArticleConstants.mediaAction.ADD,  mediaFiles[0].fullPath, mediaFiles[0].type, null/* filname*/, mediaFiles[0].size)
			newImageSelected();
		}
	},
	// captureImage error
	function(error) {
	},
	// captureImage Options
	{
		limit : 1
	});
}
var x = 0;
/** Function to get image/video from the phones library */
function selectNativeMedia(mediaType) {
	navigator.camera.getPicture(
	// Success function
	function(uri) {
		if (mediaType == Camera.MediaType.PICTURE) {
			updateMediaObject(ArticleConstants.mediaType.IMAGE, ArticleConstants.mediaAction.ADD, uri);
			populateFileDetails(mediaObj.image);
			newImageSelected();
		}
		if (mediaType == Camera.MediaType.VIDEO) {
			updateMediaObject(ArticleConstants.mediaType.VIDEO, ArticleConstants.mediaAction.ADD, uri);
			populateFileDetails(mediaObj.video);
			newVideoSelected();
		}
	},
	// Error function
	function(e) {
		// Do not show warning if the user cancelled
		if (e && e.indexOf("cancel") < 0){
			alert(articleMediaError);
		}
	},
	// Options
	{
		quality : 50,
		destinationType : destinationType.FILE_URI,
		sourceType : pictureSource.PHOTOLIBRARY,
		mediaType : mediaType
	});
}


/**
 * Capture video with the native camera
 */
function captureNativeVideo() {
	navigator.device.capture.captureVideo(
	// Success
	function(mediaFiles) {
		var len = mediaFiles.length; // We are only using one file
		if (len >= 1) {
			newVideoSelected("Defaultimage", mediaFiles[0].fullPath, mediaFiles[0].type, "TODO Filename", mediaFiles[0].size);
		}
	},
	// On error
	function(error) {
	},
	// Options
	{
		limit : 1
	});
}

/** Function to remove temporary media files after uploaded to the server **/
function removeTempFile(uri) {
	if (uri.indexOf("localhost") == -1) { uri = "file://localhost" + uri; }
	window.resolveLocalFileSystemURI(uri,
		function(fileEntry) {
			fileEntry.remove(
				function(entry) {
				},
				function(error) {
				}
			);
		},
		function(evt) {}
	);
}

/** Function to check if there is an image to submit */
function nativeSubmit() {
	var articleId = $("#articleId").val();
	
	/**
	 * If we do not have an article ID yet then we need to create the article
	 */
	if (!articleId || articleId == 0){
		
		// Build the object with minimum requirements to create the article
		var jsonObject = {};
		jsonObject.synopsis = $("#synopsis").val();
		jsonObject.text 	= $("#text").val();
		jsonObject.heading 	= $("#heading").val();

		var d = new Date();
		var n = d.getTime();
		
		var submitURL = serverInstanceURL + "/editArticle/createNew?time="+n;
		//start the ajax
			$.ajax({
				//this is the php file that processes the data and send mail
				url : submitURL,
				//POST method is used
				type : 'POST',
				//pass the data        
				data : jsonObject,
				//Do not cache the page
				cache : false,
				// Return data type
				dataType: 'json',
				//success
				success : function(data, textStatus, jqXHR) {
					$("#articleId").val(data.articleId);
					nativeSubmit(); // Call submit again
				},
				error: function(jqXHR, textStatus, errorThrown){
					// TODO do something
				}
			});
		return;
	}
	
	// Upload the image if required
	if (mediaObj.image.action == ArticleConstants.mediaAction.ADD && !mediaObj.image.uploaded) {
		uploadNativeMedia(mediaObj.image, function(){
			mediaObj.image.uploaded = true;
			nativeSubmit(); // Call this function again
		});
		return;
	}
	
	// Upload the video if required
	if (mediaObj.video.action == ArticleConstants.mediaAction.ADD && !mediaObj.video.uploaded) {
		uploadNativeMedia(mediaObj.video, function(){
			mediaObj.video.uploaded = true;
			nativeSubmit(); // Call this function again
		});
		return;
	}
	
	/*
	 * If we are publishing we need to show a prompt as
	 * it takes some time to publish on the server side
	 */
	if (ArticleConstants.pageAction.PUBLISH  == $("#pageAction").val()){
		setTimeout(function(){
			$.mobile.loadingMessageTextVisible = true;
			$.mobile.loadingMessage = dialog_submitting;
			$.mobile.showPageLoadingMsg();
		},1);
	}
	
	/*
	 * Now that all is there, submit the form which will change the page
	 */
	$("#editArticleForm").submit();
}

/**
 * Upload current image/video The media is an object from the mediaObj
 */
function uploadNativeMedia(media, callbackSuccess) {
	// Get URI of image/video to upload
	var mediaURI = media.uri;
	if (!mediaURI) {
		if (callbackSuccess) {
			callbackSuccess();
		}
		return;
	}
	setTimeout(function(){
		$.mobile.loadingMessageTextVisible = true;
		$.mobile.loadingMessage = uploadingMedia;
		$.mobile.showPageLoadingMsg();
	},1);
	
	// Verify server has been entered
	var save = serverInstanceURL + "/editArticle/mediaUpload";
	// Specify transfer options
	var options = new FileUploadOptions();
	options.fileKey = "file";
	options.fileName = media.name;
	options.mimeType = media.mimeType;
	options.chunkedMode = false;

	// Params to send to Controller
	var params = new Object();
	params.mediaType = media.mediaType; // image
	params.articleId = $("#articleId").val();
	options.params = params;

	// Transfer picture to server
	var ft = new FileTransfer();
	ft.upload(mediaURI, save, function(r) {
		if (callbackSuccess) {
			$.mobile.hidePageLoadingMsg();
			removeTempFile(mediaURI);
			callbackSuccess();
		}
	}, function(error) {
		$.mobile.hidePageLoadingMsg();
		removeTempFile(mediaURI);
		revertUISubmit();
		alert("Error uploading media");
	}, options);
}

/**
 * Updates the media object with more details about the native file
 */
function populateFileDetails(media){
	window.resolveLocalFileSystemURI(media.uri, 
		function(fileEntry){
			fileEntry.file(
				function(file){
					updateMediaObject(media.type, null, null, file.type, file.name, file.size);
				},
				function(error){
					// TODO warn?
				});
		}, 
		function(error){
			// TODO warn?
		}
	);
}
