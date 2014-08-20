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

$(document).ready(function(){
	// Display the no comments message if required on document ready
	checkEmptyComments();
});
/**
 * Method to delete a comment
 * @param commentId Id of the comment to delete
 */
function deleteComment(commentId){

	var dataObject = {"commentId" : commentId};
	$.ajax({
		url: "deleteComment?time="+new Date().getTime(),
		type: "POST",
		data : dataObject,
		success: function(data, textStatus, jqXHR){
			$("#comment_"+commentId).slideUp("slow", function(){
				$(this).remove(); // and remove from DOM
				checkEmptyComments();
			});
	  }
	});
}

/**
 * Method to check if there are no comments.
 * This method will be called after a comment is 
 * deleted, and will display a message
 * if there are no more comments for the article
 */
function checkEmptyComments(){
	if ($("ul[id^=comment_]").length == "0" ){
		$("#noComments").fadeIn();
	}
}

/**
 * Allows the user to confirm deleting a comment
 * @param commentId
 */
function confirmDelete(commentId){
	
	// List of buttons to display
	var buttonsList = {};

	// Configuration for selecting yes
	buttonsList[msgCat_Yes] = {
		'default'	: 'yes',
		'action'	: function(){deleteComment(commentId)}
	};

	// Configuration for selecting no
	buttonsList[msgCat_No] = {
		'default'	: 'no',
		'action'	:  function(){}
	};

	// Display the confirmation dialog
	$.confirm({
		'message'	: msgCat_ConfirmDeleteComment,
		'buttons'	: buttonsList
	});
}

