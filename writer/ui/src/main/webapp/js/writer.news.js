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


$(document).ready(function () {
    $("#articlesList").pagingWidget({
        "dataHandler": handleMoreNews,
        "dataURL": "getNews",
        "beforeServerCall": beforeGetMoreArticles,
        "moreLabelText": msgCat_LoadMoreNews,
        "widgetDataTheme": "c"
    });

    $("#topicSelect").change(function () {
        topicId = $(this).val();
        $("#articlesList").pagingWidget("reload");
    });
});

/**
 * Handler for more article data available
 * @param data
 */
function handleMoreNews(data) {
    /*
     * Loop through the array of articles and create list items of them
     * Once the item is created add it to the widget.
     */
    if (!data || data.length == 0) {
        var item = createNoArticlesItem();
        $("#articlesList").pagingWidget("addItem", item);
    } else {
        var index = 0;
        var callback = function () {
            var item = createArticleItem(data[index]);
            index = index + 1;
            if (index >= data.length) {
                callback = undefined;
            }
            $("#articlesList").pagingWidget("addItem", item, callback);
        }
        callback();
    }

}

/**
 * Function to be called before a request is made to the server
 * to retrieve more articles.
 */
function beforeGetMoreArticles() {
    var data = $("#articlesList").pagingWidget("option", "dataToServer");
    if (!data) {
        data = new Object();
    }
    data.topicId = topicId;
    if (topicId == 0) {
        $("#articlesList").pagingWidget("option", "fetchSize", 10);
        $("#articlesList").pagingWidget("option", "allowFetchMore", false);
        $("#articlesList").pagingWidget("option", "showPosition", false);

    }
    else {
        $("#articlesList").pagingWidget("option", "fetchSize", 2);
        $("#articlesList").pagingWidget("option", "allowFetchMore", true);
        $("#articlesList").pagingWidget("option", "showPosition", true);
    }
    $("#articlesList").pagingWidget("option", "dataToServer", data);
}

/**
 * Returns a list item that can be added to a list
 * which represent a article.
 * @param articleData
 * @returns
 */
function createArticleItem(articleData) {
    var item = $("<li>", {id: "article-" + articleData.id, "data-icon": "false", "data-theme": "c"});
    var link = $("<a>", {"href": "viewArticle?articleId=" + articleData.id});

    // Left of list item
    var divLeft = $("<div>", {"class": "leftArticle" });
    var image = $("<img>", {"src": articleData.imageURL});
    divLeft.append(image);

    // Right of list item
    var divRight = $("<div>", {"class": "rightArticle"});
    var header = $("<h3>", {"class": "wrap"}).html(articleData.heading);
    var details = $("<p>", {"class": "wrap"})
        .append($("<b>").html(articleData.journalist)).append($("<br>"))
        .append($("<span>").html(articleData.date)).append($("<br>"))
        .append($("<span>").html(articleData.synopsis));
    divRight.append(header);
    divRight.append(details);

    link.append(divLeft);
    link.append(divRight);
    item.append(link);
    return item;
}

/**
 * Creates a list item that indicates that there are no data available
 * @returns
 */
function createNoArticlesItem() {
    var item = $("<li>", {id: "noArticles", "data-icon": "false", "data-theme": "c"});
    item.html(msgCat_NoNews);
    return item;
}
