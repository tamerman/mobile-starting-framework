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


//Register media events after page has loaded
$(document).ready(function () {
    // Invoked when the user adds an upload image
    // Web Only!
    $("#uploadImage").change(function () {
        //https://developer.mozilla.org/en/DOM/Using_the_Camera_API
        if ($(this).val()) { // Fix for old browsers that fire a change event even though nothing was selected
            var image = this.files[0];
            updateMediaObject(ArticleConstants.mediaType.IMAGE, ArticleConstants.mediaAction.ADD, "", image.type, image.name, image.size);
            newImageSelected();
        }
    });

    // Remove buttons when removing image
    $("#btnRemoveImage").click(function () {
        $("#imagePreview").attr("src", defaultImage);
        $("#imageRemoveButtons").fadeOut(function () {
            $("#btnViewImage").hide();
        });
        $("#removeImage").val("true");
        updateMediaObject(ArticleConstants.mediaType.IMAGE, ArticleConstants.mediaAction.DELETE);
    });

    // Invoked when the user adds an upload image
    // Web Only!
    $("#uploadVideo").change(function () {
        updateMediaObject(ArticleConstants.mediaType.VIDEO, ArticleConstants.mediaAction.ADD);
        newVideoSelected();
    });

    // Remove buttons when removing video
    $("#btnRemoveVideo").click(function () {
        $("#videoPreview").attr("src", defaultImage);
        $("#videoRemoveButtons").fadeOut(function () {
            $("#btnViewVideo").hide();
        });
        $("#removeVideo").val("true");
        updateMediaObject(ArticleConstants.mediaType.VIDEO, ArticleConstants.mediaAction.DELETE);
    });
});

/**
 * Function called when a new image is selected
 */
function newImageSelected() {
    $("#imagePreview").attr("src", defaultImage);
    $("#removeImage").val("false");
    $("#btnViewImage").remove();
    $("tr#imageRemoveButtons").fadeIn();
}

/**
 * Function called when a new video is selected
 */
function newVideoSelected() {
    $("#videoPreview").attr("src", defaultImage);
    $("#removeVideo").val("false");
    $("#btnViewVideo").remove();
    $("tr#videoRemoveButtons").fadeIn();
}

function updateMediaObject(mediaType, action, uri, mime, name, size) {
    var media;
    if (mediaType == ArticleConstants.mediaType.VIDEO) {
        media = mediaObj.video;
    } else {
        media = mediaObj.image;
    }

    if (action) {
        media.action = action;
    }

    // Set URI if specified
    if (uri) {
        media.uri = uri;
    }

    // Set name if specified
    if (name) {
        media.name = name;
    }

    // Set mime if specified
    if (mime) {
        media.mimeType = mime;
    }

    // Set size if specified
    if (size) {
        media.size = size;
    }
}
