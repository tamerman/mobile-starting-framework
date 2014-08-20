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

function deleteArticle(id){
	displayConfirm(
			// Message
			msgCat_ConfirmDeleteArticle,
			// Clicked yes
			function(){
				submitDeleteArticle(id);
			}
	);
}

/**
 * Submits a request to the server to mark an article as deleted
 * @param id
 */
function submitDeleteArticle(id){
	var submitURL = serverInstanceURL+"/deleteArticle";
	//start the ajax
	$.ajax({
		//this is the php file that processes the data and send mail
		url : submitURL,
		//POST method is used
		type : 'GET',
		//pass the data        
		data : {"articleId" : id},
		//Do not cache the page
		cache : false,
		//success
		success : function(data, textStatus, jqXHR) {
			deleteSuccess();
		},
		error: function(jqXHR, textStatus, errorThrown){
			deleteError(jqXHR.status);
		}
	});
}

/**
 * Action to take when the article deletion was a success
 */
function deleteSuccess(){
	displayInformation (
		msgCat_ArticleDeleted, 
		function(){
			window.location=serverInstanceURL;
		});
}

/**
 * Action to take when there was an error deleting the article.
 * @param httpStatus
 */
function deleteError(httpStatus){
	if (httpStatus == 404){
		displayInformation (msgCat_ArticleNotFound);
	}
	else if (httpStatus == 403){
		displayInformation (msgCat_ArticleDeleteFailedNoRight);
	}
	else {
		displayInformation (msgCat_ArticleDeleteFailed);
	}
}

$(document).ready(function()
	{
		var isNative 	= ("yes" == $.cookie('native'));
		if (isNative){
			
			$("#linkUrl")
            .click(function(){
				window.plugins.KMEDevice.openInExternal(msgCat_LinkUrl);
			});
		}
	}
);
