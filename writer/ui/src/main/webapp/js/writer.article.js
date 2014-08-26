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


var ArticleConstants = {
    pageAction: {
        SAVE: 1,	// Save article to current user
        SUBMIT: 2,	// Submit article to an editor
        REJECT: 3,	// Reject article back to journalist
        PUBLISH: 4,	// Publish article to public
        DISCARD: 5		// Throw away the article
    },
    mediaAction: {
        DEFAULT: 0,	// no action
        ADD: 1,	// add the media
        DELETE: 2		// delete the media
    },
    mediaType: {
        IMAGE: 1,		// Media is an image
        VIDEO: 2		// Media is a video
    }
};

/*
 *  Media object
 *  At the moment we only support one video and one image
 */
var mediaObj = {
    video: {
        action: ArticleConstants.mediaAction.DEFAULT,
        uri: "",
        mimeType: "",
        name: "",
        size: 0,
        uploaded: false,
        mediaType: ArticleConstants.mediaType.VIDEO
        // DO NOT CHANGE
    },
    image: {
        action: ArticleConstants.mediaAction.DEFAULT,
        uri: "",
        mimeType: "",
        name: "",
        size: 0,
        uploaded: false,
        mediaType: ArticleConstants.mediaType.IMAGE
        // DO NOT CHANGE
    }
};

var editArticleForm;
var previewImage;
//Register submit events after page has loaded
$(document).ready(function () {

    $("input, select, textarea").addClass("disabler-show-text-readonly");

    editArticleForm = $("form#editArticleForm").disabler({
        disable: false
    });

    // Save button clicked
    $("#btnSave").click(function () {
        submitForm(ArticleConstants.pageAction.SAVE);
    });

    // Submit button clicked
    $("#btnSubmit").click(function () {
        submitForm(ArticleConstants.pageAction.SUBMIT, dialog_submit);
    });

    // Reject button clicked
    $("#btnReject").click(function () {
        submitForm(ArticleConstants.pageAction.REJECT, dialog_reject);
    });

    // Publish button clicked
    $("#btnPublish").click(function () {
        submitForm(ArticleConstants.pageAction.PUBLISH, dialog_publish);
    });

    // Discard button clicked
    $("#btnDiscard").click(function () {
        displayConfirm(dialog_discard, function () {
            var url = "discard/" + $("#articleId").val();
            $(location).attr('href', url); // Redirect to discard page
        });
    });
});

/**
 * This method will call validation
 * set the page action and submit the form
 */
function submitForm(pageAction, dialogMessage) {
    var submit = false; // Flag if we should continue submitting the form

    // First validate the form
    if ($("#editArticleForm").valid()) {
        if (dialogMessage) {
            displayConfirm(dialogMessage,
                // yes function
                function () {
                    finalSubmit(pageAction);
                },
                // No function
                null);
        } else {
            submit = true;
        }
    }
    else {
        validator.focusInvalid();
    }
    if (submit) {
        finalSubmit(pageAction);
    }
}

function finalSubmit(pageAction) {
    $("#pageAction").val(pageAction);
    updateUISubmit();
    if (isNative) {
        // Submit using the native flow
        nativeSubmit();
    } else {
        /*
         * If we are publishing we need to show a prompt as
         * it takes some time to publish on the server side
         */
        if (ArticleConstants.pageAction.PUBLISH == pageAction) {
            setTimeout(function () {
                $.mobile.loadingMessageTextVisible = true;
                $.mobile.loadingMessage = dialog_submitting;
                $.mobile.showPageLoadingMsg();
            }, 1);
        }
        // Submit the form normally
        $("#editArticleForm").submit();
    }
}

/**
 * Function to update the UI before submitting a page
 */
function updateUISubmit() {
    editArticleForm.disabler("readOnly", "editArticleForm", true);
    $("div.ui-select a").hide();
    $("ul#actionButtons").hide();
}


/**
 * Function to revert the UI if there were any failures to the submittion
 */
function revertUISubmit() {
    editArticleForm.disabler("readOnly", "editArticleForm", false);
    $("div.ui-select a").show();
    $("ul#actionButtons").show();
}

function displayFunctionNotAvailable() {
    // List of buttons to display
    var buttonsList = {};

    // Configuration for selecting ok
    buttonsList['Ok'] = {
        'default': 'yes',
        'action': function () {
        }
    };

    // Display the confirmation dialog
    $.confirm({
        'message': dialog_disabledFunction,
        'buttons': buttonsList
    });
}

if (!isNative) {
    previewImage = function (imageId) {
        var buttonsList = {};
        buttonsList['Ok'] = {
            'default': 'yes',
            'action': function () {
            }
        };
        var imgURL = imageBaseURL + imageId;
        $.confirm({
            'message': '<img width="100%" src="' + imgURL + '">',
            'buttons': buttonsList
        });
    };
}
