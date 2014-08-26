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


// expanded form
var expanded = 0;

var legendVisible = 0;

var menuHeight = 0;

$(document).ready(function () {
    $('li.contentItem').hide();
    $('ul#dropdownHeader span.ui-icon').hide();


    /* hide menu and display "dining" page results if more than 1 name exists*/
    if ($('div[data-url="dining"]').length == 1) {
        var numdropdownMenuItems = 0;
        $('ul[id="dropdownMenu"] li').each(function (index) {
            numdropdownMenuItems = numdropdownMenuItems + 1;
        });
        if (numdropdownMenuItems == 1) {
            numdropdownMenuItems = 0;
            $('a[id="toggleDropdown"]').hide();
            $('ul#results li').show()
        }
    }
    var thisCount = 0;
    $('li.contentItem').each(function (index) {
        thisCount = 0;
        $($(this).children('div.nutritionIcons')).each(function (index) {
            $($(this).children('span.nutrition')).each(function (index) {
                thisCount = thisCount + 1;
            });
        });
        $(this).css('padding-right', (thisCount * 40) + "px");
    });
});

if (!$.support.transition)
    $.fn.transition = $.fn.animate;

$(function () {
    $('a#toggleDropdown').click(
        function () {
            if (expanded == 0) {
                $('ul#results').transition({
                    y: $('ul#dropdownMenu').height() + 'px',
                    easing: 'in-out',
                    duration: '300ms'
                });
                $('li.dropdownHeader span.ui-icon').removeClass('ui-icon-arrow-d');
                $('li.dropdownHeader span.ui-icon').addClass('ui-icon-arrow-u');
                expanded = 1;
            } else {
                $('ul#results').transition({
                    y: '0px',
                    easing: 'in-out',
                    duration: '300ms'
                });
                $('li.dropdownHeader span.ui-icon').removeClass('ui-icon-arrow-u');
                $('li.dropdownHeader span.ui-icon').addClass('ui-icon-arrow-d');
                expanded = 0;
            }
            return false;
        }
    );
});

function loadContents(passedItem, itemName) {
    $('.dropdownHeader a').text(itemName);
    $('li.contentItem').hide();
    $('li.category-' + passedItem).show();
}

$(function () {
    $('li.dropdownItem').click(
        function () {
            $('ul#dropdownHeader span.ui-icon').show();
            viewportHeight = $(window).height();
            var str = $(this).text();
            selectedItem = $(this).attr("value");
            $('.dropdownHeader a').text('Loading.....');
            $('ul#results').transition({ y: viewportHeight, easing: 'in-out', duration: '500ms'}, function () {
                loadContents(selectedItem, str);
            });
            // flip dropdown menu header arrow
            $('li.dropdownHeader span.ui-icon').removeClass('ui-icon-arrow-u');
            $('li.dropdownHeader span.ui-icon').addClass('ui-icon-arrow-d');
            $('li.dropdownHeader span.ui-icon').prop('data-icon', 'arrow-d');
            $('ul#results').transition({ y: '0px', easing: 'in-out', duration: '500ms'});
            expanded = 0;
            return false;
        }
    );
});

$(function () {
    $('span.nutrition').click(
        function () {
            viewportHeight = $(window).height();
            selectedItem = $(this).text();
            if (legendVisible == 1) {
                $('div#legend').transition({ y: '-500px', easing: 'in-out', duration: '500ms'});
                legendVisible = 0;
            } else {
                $('div#legend').transition({ y: Math.floor($(this).offset().top + 400) + 'px', easing: 'in-out', duration: '500ms'}, function () {
                        legendVisible = 1;
                    }
                );
            }
            return false;
        }
    );
});

$(function () {
    $('span.closeLegend').click(
        function () {
            if (legendVisible == 1) {
                $('div#legend').transition({ y: '-500px', easing: 'in-out', duration: '500ms'});
                legendVisible = 0;
                return false;
            }
        }
    );
});


$(document).bind("scrollstart", function () {
    if (legendVisible == 1) {
        $('div#legend').transition({ y: '-500px', easing: 'in-out', duration: '500ms'});
        legendVisible = 0;
    }
});
