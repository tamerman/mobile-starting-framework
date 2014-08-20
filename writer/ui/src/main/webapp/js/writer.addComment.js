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

var validator;
// Register events after page has loaded
$(document).ready(function() { 
	
	validator = $("#commentForm").validate({
		wrapper: 'div',
       errorElement : 'div',
        errorPlacement: function(error, element) {
            if(element.prop('tagName').toLowerCase() === 'input'){
                error.insertAfter(element.parent(".ui-input-text"));
            }
            else{
                error.insertAfter(element);
            }
        },
		rules: {
			// Validation rules on the title
			commentTitle: {
				required: true,
				minlength: 2,
				maxlength: 60
			},
			// Validation rules on the comment text
			commentText: {
				required: true,
				minlength: 2,
				maxlength: 250
			}
		},
		messages: validationMessages
	});
	
	$("#btnPlaceComment").click(function(){
		$("#commentForm").submit();
	});
	
	$("#commentForm").submit(function() {
		if ($("#commentForm").valid()){ // First validate form
			return true;
		}
		else {
			validator.focusInvalid();
			return false;
		}
	});
});
