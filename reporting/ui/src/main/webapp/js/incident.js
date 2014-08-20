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

// Called when a photo is successfully retrieved
function onPhotoURISuccess(imageURI) {
	alert("something somthing " +  imageURI);
	// View the Photo's URI in the  console. Can use this to grap the photo with a form for 
	console.log(imageURI);

	// Get image handle
	var incidentImage = document.getElementById('incidentImage');

	// Unhide image elements
	incidentImage.style.display = 'block';

	// Show the captured photo
	// The inline CSS rules are used to resize the image
	incidentImage.src = imageURI;
}



// A button will call this function
function getPhoto(source) {
	alert("Cool!");
	// Retrieve image file location from specified source
	// The quality, targetWidth, targetHeight taken together probably affect file size. 
	navigator.camera.getPicture(onPhotoURISuccess, onFail, {quality: 50, 
															destinationType: navigator.camera.DestinationType.FILE_URI,
															sourceType: source, 
															targetWidth: 320,
															targetHeight: 320});
}

// Called if something bad happens or fail silently...
function onFail(message) {
	alert('Failed because: ' + message);
}

