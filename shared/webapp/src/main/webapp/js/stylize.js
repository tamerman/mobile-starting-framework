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

$(document).ready(function() {
	$('#saveButton').click(applyStyles);
	$('#clearButton').click(clearStyles);
	if($.cookie("css")) {
		var json = $.cookie("css");
		var obj = jQuery.parseJSON(json);
		
		$('input[name="pageBackgroundColor"]').val(obj.pageBackgroundColor);
		$('input[name="headerLogoUrl"]').val(obj.headerLogoUrl);
		$('input[name="headerTextColor"]').val(obj.headerTextColor);
		$('input[name="headerTextShadow"]').val(obj.headerTextShadow);
		$('input[name="headerGradientStartColor"]').val(obj.headerGradientStartColor);
		$('input[name="headerGradientStopColor"]').val(obj.headerGradientStopColor);
		
		$('input[name="buttonATextColor"]').val(obj.buttonATextColor);
		$('input[name="buttonAGradientStartColor"]').val(obj.buttonAGradientStartColor);
		$('input[name="buttonAGradientStopColor"]').val(obj.buttonAGradientStopColor);
		$('input[name="buttonAHoverGradientStartColor"]').val(obj.buttonAHoverGradientStartColor);
		$('input[name="buttonAHoverGradientStopColor"]').val(obj.buttonAHoverGradientStopColor);
		$('input[name="buttonADownGradientStartColor"]').val(obj.buttonADownGradientStartColor);
		$('input[name="buttonADownGradientStopColor"]').val(obj.buttonADownGradientStopColor);
		
		$('input[name="buttonCTextColor"]').val(obj.buttonCTextColor);
		$('input[name="buttonCGradientStartColor"]').val(obj.buttonCGradientStartColor);
		$('input[name="buttonCGradientStopColor"]').val(obj.buttonCGradientStopColor);
		$('input[name="buttonCHoverGradientStartColor"]').val(obj.buttonCHoverGradientStartColor);
		$('input[name="buttonCHoverGradientStopColor"]').val(obj.buttonCHoverGradientStopColor);
		$('input[name="buttonCDownGradientStartColor"]').val(obj.buttonCDownGradientStartColor);
		$('input[name="buttonCDownGradientStopColor"]').val(obj.buttonCDownGradientStopColor);
	}
});

function applyStyles() {
	var obj = new Object();
	
	obj.pageBackgroundColor = $('input[name="pageBackgroundColor"]').val();
	obj.headerLogoUrl = $('input[name="headerLogoUrl"]').val();
	obj.headerTextColor = $('input[name="headerTextColor"]').val();
	obj.headerTextShadow = $('input[name="headerTextShadow"]').val();
	obj.headerGradientStartColor = $('input[name="headerGradientStartColor"]').val();
	obj.headerGradientStopColor = $('input[name="headerGradientStopColor"]').val();
	
	obj.buttonATextColor = $('input[name="buttonATextColor"]').val();
	obj.buttonAGradientStartColor = $('input[name="buttonAGradientStartColor"]').val();
	obj.buttonAGradientStopColor = $('input[name="buttonAGradientStopColor"]').val();
	obj.buttonAHoverGradientStartColor = $('input[name="buttonAHoverGradientStartColor"]').val();
	obj.buttonAHoverGradientStopColor = $('input[name="buttonAHoverGradientStopColor"]').val();
	obj.buttonADownGradientStartColor = $('input[name="buttonADownGradientStartColor"]').val();
	obj.buttonADownGradientStopColor = $('input[name="buttonADownGradientStopColor"]').val();
	
	obj.buttonCTextColor = $('input[name="buttonCTextColor"]').val();
	obj.buttonCGradientStartColor = $('input[name="buttonCGradientStartColor"]').val();
	obj.buttonCGradientStopColor = $('input[name="buttonCGradientStopColor"]').val();
	obj.buttonCHoverGradientStartColor = $('input[name="buttonCHoverGradientStartColor"]').val();
	obj.buttonCHoverGradientStopColor = $('input[name="buttonCHoverGradientStopColor"]').val();
	obj.buttonCDownGradientStartColor = $('input[name="buttonCDownGradientStartColor"]').val();
	obj.buttonCDownGradientStopColor = $('input[name="buttonCDownGradientStopColor"]').val();

	if (!obj.headerGradientStopColor) {obj.headerGradientStopColor = obj.headerGradientStartColor;}
	if (!obj.buttonAGradientStopColor) {obj.buttonAGradientStopColor = obj.buttonAGradientStartColor;}
	if (!obj.buttonAHoverGradientStopColor) {obj.buttonAHoverGradientStopColor = obj.buttonAHoverGradientStartColor;}
	if (!obj.buttonADownGradientStopColor) {obj.buttonADownGradientStopColor = obj.buttonADownGradientStartColor;}
	if (!obj.buttonCGradientStopColor) {obj.buttonCGradientStopColor = obj.buttonCGradientStartColor;}
	if (!obj.buttonCHoverGradientStopColor) {obj.buttonCHoverGradientStopColor = obj.buttonCHoverGradientStartColor;}
	if (!obj.buttonCDownGradientStopColor) {obj.buttonCDownGradientStopColor = obj.buttonCDownGradientStartColor;}
	
	$.cookie("css", JSON.stringify(obj), {expires: 365, path: '/', raw: true});
}
	
function clearStyles() {
	$.cookie("css", '', {expires: 0, path: '/', raw: true});
	window.location.reload();
}
