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


var validator;
$(document).ready(function () {
    // Validator for file size
    $.validator.addMethod("fileSize", function (value, element, param) {
        var files = element.files;
        var fileSize = 0;
        if (!files) {
            // for IE
            try {
                var fs = new ActiveXObject('Scripting.FileSystemObject');
                var file = fs.getFile(element.value);
                fileSize = file.size;
            } catch (ex) {
                fileSize = -1;
            }
        } else if (files.length > 0) {
            // for rest of the world
            fileSize = files[0].size;
        }
        return this.optional(element) || fileSize <= param;
    }, "Check file size");

    validator = $("#editArticleForm").validate(
        {
            errorElement: 'div',
            errorPlacement: function (error, element) {
                if (element.prop('tagName').toLowerCase() === 'input') {
                    error.insertAfter(element.parent(".ui-input-text"));
                }
                else {
                    error.insertAfter(element);
                }
            },
            rules: {
                // Validation rules on the heading
                heading: {
                    required: true,
                    minlength: 3,
                    maxlength: 60
                },
                // Validation of the article content
                text: {
                    required: true,
                    minlength: 10,
                    maxlength: 4000
                },
                // Validation of the article
                // synopsis
                synopsis: {
                    required: true,
                    minlength: 10,
                    maxlength: 250
                },
                uploadImage: {
                    required: false,
                    fileSize: 5242880, // 5 MB
                    accept: "jpeg|jpg|png"
                },
                uploadVideo: {
                    required: false,
                    fileSize: 15728640, // 15 MB
                    accept: "3gp|mp4|m4v"
                },
                linkUrl: {
                    required: false,
                    url: true
                }
            },
            messages: validation_messages
        });
});
