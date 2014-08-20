<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="writer" uri="http://kuali.org/mobility/tags/writer" %>

<spring:message code="writer.confirmPublish" var="msgCat_ConfirmPublish"/>
<spring:message code="writer.confirmDiscard" var="msgCat_ConfirmDiscard"/>
<spring:message code="writer.confirmReject" 	var="msgCat_ConfirmReject"/>
<spring:message code="writer.confirmSubmit" 	var="msgCat_ConfirmSubmit"/>
<spring:message code="writer.yes" 			var="msgCat_Yes"/>
<spring:message code="writer.no" 			var="msgCat_No"/>

<spring:message code="writer.isMandatory" 	var="msgCat_IsMandatory"/>
<spring:message code="writer.maximum" 		var="msgCat_Maximum"/>
<spring:message code="writer.minimum" 		var="msgCat_Minimum"/>
<spring:message code="writer.characters" 	var="msgCat_Characters"/>
<spring:message code="writer.validationFailed" var="msgCat_ValidationFailed"/>
<spring:message code="writer.invalidFiletype" var="msgCat_InvalidFiletype"/>
<spring:message code="writer.functionNotAvailable" var="msgCat_FunctionNotAvailable"/>
<spring:message code="writer.submitting" 	var="msgCat_Submitting"/>
<spring:message code="writer.uploadingMedia" var="msgCat_UploadingMedia"/>
<spring:message code="writer.articleMediaError" var="msgCat_ArticleMediaError"/>
<spring:message code="writer.invalid.url"	var="msgCat_InvalidUrl" />


<spring:message code="writer.title" var="msgCat_ToolTitle"/>

<kme:page
		title="${msgCat_ToolTitle}"
	id="${toolInstance}-webapp" 
	backButton="true"
	backButtonURL="${pageContext.request.contextPath}/writer/${toolInstance}/admin"
	homeButton="true" 
	cssFilename="writer,jquery.confirm"
    theme="${theme}">

	<kme:content>

	<form action="maintainArticle" method="post" id="editArticleForm"  enctype="multipart/form-data" autocomplete="off">
		<input type="hidden" id="articleId" name="articleId" value="${article.id == '' ? '0' : article.id}" />
		<input type="hidden" id="pageAction" name="pageAction" value="0" />
		<input type="hidden" id="removeImage" name="removeImage" value="false" />
		<input type="hidden" id="removeVideo" name="removeVideo" value="false" />
		
		<%-- Heading --%>
		<div>
			<label for="heading"><spring:message code="writer.heading" /></label>
			<input type="text" name="heading" id="heading" value="${article.heading}"/>
		</div>
		
		<%-- Journalist --%>
		<div>
			<label class="itemLabel"><spring:message code="writer.journalist" /></label>
			<c:choose>
					<c:when test="${fn:startsWith(article.journalist, 'writer.') == true}">
						<spring:message code="${article.journalist}" var="varDisplayName" />
				 	</c:when>
					<c:otherwise>
						<c:set var="varDisplayName" value="${article.journalist}"/>
					</c:otherwise>
				</c:choose>
				<label class="itemValue"><c:out value="${varDisplayName}" /></label>
		</div>
			
		<%-- Category --%>
		<div>
			<label for="category"><spring:message code="writer.category" /></label>
			<select name="category" id="category" data-native-menu="false">
				<c:forEach var="category" items="${categories}">
					<c:set var="selected" value="${category.id == article.category ? 'selected=\"selected\"' : ''}" />
					<spring:message code="${category.label}" var="label"/>
					<option value="${category.id}" ${selected}>${label}</option>
				</c:forEach>
			</select>
		</div> 
		
		<%-- Topic --%>
		<div>
			<label for="topicId"><spring:message code="writer.topic" /></label>
			<select name="topicId" id="topicId" data-native-menu="false" >
			<c:forEach var="topic" items="${topics}">
				<c:set var="selected" value="${topic.id == article.topic.id ? 'selected=\"selected\"' : ''}" />
				<spring:message code="${topic.label}" var="label"/>
				<option value="${topic.id}" ${selected}>${label}</option>
			</c:forEach>
			</select> 
		</div>
		
		
		<%-- Photo --%>
		<div>
			<label><spring:message code="writer.photo" /></label>
			<div>
				<table style="width: 100%; border: 0px">
				<tr>
					<td style="text-align: center">
						<writer:articleImage article="${article}" id="imagePreview" instance="${toolInstance}" style="max-width: 80px;max-height: 80px; border:1px solid black"/>
					</td>
				</tr>
				<tr>
					<td style="text-align: center">
						<%-- When using web interface we can use a normal file upload --%>
						<c:if test="${sessionScope['session_native'] == false}">
							<input type="file" id="uploadImage" name="uploadImage" accept="image/*" />
						</c:if>
						<%-- When using the native app we need to use PhoneGap Tricks ;)--%>
						<c:if test="${sessionScope['session_native'] == true}">
							<c:if test="${!nativePlatform.equalsIgnoreCase('blackberry') && isBlackberry == false}" >
								<div>
									<a id="captureNativeImage" href="javascript:;" data-role="button" style="display: inline-block;" data-mini="true"><spring:message code="writer.shootImage" /></a>
									<a id="selectNativeImage" href="javascript:;" data-role="button" style="display: inline-block;" data-mini="true"><spring:message code="writer.selectImage" /></a>
								</div>
							</c:if>
						</c:if>
					</td>
				</tr>
				<c:set var="imageRemoveButtonsStyle" value="display:none" />
				<c:if test="${article.image.id >= 0 }">
					<c:set var="imageRemoveButtonsStyle" value="" />
				</c:if>
				<tr id="imageRemoveButtons" style="${imageRemoveButtonsStyle}">
					<td style="text-align: center">
						<%-- The view button will only be available if the image had an image --%>
						<c:if test="${article.image.id >= 0 }">
							<a href="javascript:previewImage('${article.image.id}');" id="opendialog" data-role="button" style="display: inline-block;" data-mini="true">
								<spring:message code="writer.view"/>
							</a>
						</c:if>
						<a id="btnRemoveImage" href="javascript:;" data-role="button" style="display: inline-block;" data-mini="true"><spring:message code="writer.remove" /></a>
					</td>
				</tr>
				</table>
			</div>
		</div>
		
		
		<div>
			<label for="topicId"><spring:message code="writer.video" /></label>
			<div>
			<table style="width: 100%; border: 0px">
			<%--
				<tr>
					 
					<td style="text-align: center">
						<img style="width: 120px; border:1px solid black" id="videoPreview" src="${pageContext.request.contextPath}/images/wapad-default.png" />
					</td>
				</tr>
			--%>
				<tr>
					<td style="text-align: center">
						<%-- When using web interface we can use a normal file upload --%>
						<c:if test="${sessionScope['session_native'] == false}">
							<input type="file" id="uploadVideo" name="uploadVideo" accept="video/*" disabled="disabled"/>
						</c:if>
						<%-- When using the native app we need to use PhoneGap Tricks ;) --%>
						<c:if test="${sessionScope['session_native'] == true}">
							<a id="captureNativeVideo" href="javascript:;" data-role="button" style="display: inline-block;" data-mini="true"><spring:message code="writer.shootImage" /></a>
							<a id="selectNativeVideo" href="javascript:;" data-role="button" style="display: inline-block;" data-mini="true"><spring:message code="writer.selectImage" /></a>
						</c:if>
					</td>
				</tr>
				<tr>
					<td style="text-align: center">
						<c:set var="videoRemoveButtonsStyle" value="display:none" />
						<c:if test="${article.video.id gt 0 }">
							<c:set var="videoRemoveButtonsStyle" value="" />
						</c:if>
						<div data-role="controlgroup" data-type="horizontal" data-mini="true" id="videoRemoveButtons" style="${videoRemoveButtonsStyle}">
							<%-- The view button will only be available if the article had a video --%>
							<c:if test="${article.video.id gt 0 }">
								<!-- a id="btnViewVideo" href="${pageContext.request.contextPath}/wapad/media/2-${article.video.id}" data-role="button">${msgCat_View}</a-->
								<a id="btnViewVideo" href="javascript:displayFunctionNotAvailable();" data-role="button"><spring:message code="writer.view"/></a>
							</c:if>
							<a id="btnRemoveVideo" href="javascript:;" data-role="button" ><spring:message code="writer.remove" /></a>
						</div>
					</td>
				</tr>
				</table>
			</div>
		</div>
		
		<%-- Article contents --%>
		<div>
			<label for="text"><spring:message code="writer.article" /></label>
			<textarea id="text" name="text" rows="10" cols="30" >${article.text}</textarea>
		</div>
		
		<%-- Synopsis--%>
		<div>
			<label for="synopsis"><spring:message code="writer.synopsis" /></label>
			<textarea id="synopsis" name="synopsis" rows="10" cols="30" >${article.synopsis}</textarea>
		</div>
		
		<%-- Link URL --%>
		<div>
			<label for="linkUrl"><spring:message code="writer.linkUrl" /></label>
			<input type="text" id="linkUrl" name="linkUrl" value="${article.linkUrl}" />
		</div>
		</form>
		<!-- Begin action buttons -->
		<ul data-role="listview" data-inset="true" data-theme="a" id="actionButtons">
			<li><a href="javascript:;" id="btnSave"><spring:message code="writer.save" /></a></li>
			<c:if test="${canSubmit == true}"><li data-icon="check"><a href="javascript:;" id="btnSubmit"><spring:message code="writer.submit" /></a></li></c:if>
			<c:if test="${canPublish == true}"><li data-icon="check"><a href="javascript:;" id="btnPublish"><spring:message code="writer.publish" /></a></li></c:if>
			<c:if test="${article.id != 0}"><li data-icon="delete"><a href="javascript:;" id="btnDiscard"><spring:message code="writer.discard" /></a></li></c:if>
			<c:if test="${canReject == true}"><li data-icon="alert"><a href="javascript:;" id="btnReject"><spring:message code="writer.reject" /></a></li></c:if>
			<li data-icon="back"><a href="${pageContext.request.contextPath}/writer/${toolInstance}/admin"><spring:message code="writer.cancel" /></a></li>
		</ul>
		<!-- End action buttons -->
	</kme:content>

	<!-- Scripts -->
	<script type="text/javascript" >
		//whether this is a native app or not
		var isNative = ${sessionScope['session_native'] == true};
		//Set the messages that will be used int he javascript files
		var validation_messages  = {
			// Error messages for invalid
			// heading
			heading : {
				required : "${msgCat_IsMandatory}.",
				minlength : "${msgCat_Minimum} 3 ${msgCat_Characters}.",
				maxlength : "${msgCat_Maximum} 60 ${msgCat_Characters}."
			},
			// Error messages for invalid
			// article content
			text : {
				required : "${msgCat_IsMandatory}",
				minlength : "${msgCat_Minimum} 10 ${msgCat_Characters}.",
				maxlength : "${msgCat_Maximum} 4000 ${msgCat_Characters}."
			},
			// Error messages for invalid
			// article synopsis
			synopsis : {
				required : "${msgCat_IsMandatory}.",
				minlength : "${msgCat_Minimum} 10 ${msgCat_Characters}.",
				maxlength : "${msgCat_Maximum} 250 ${msgCat_Characters}."
			},
			// Error messages for invalid
			// article image
			uploadImage : {
				fileSize : "${msgCat_Maximum} 5MB.",
				accept : "${msgCat_InvalidFiletype} (jpeg, jpg, png)."
			},
			// Error messages for invalid
			// article video
			uploadVideo : {
				fileSize : "${msgCat_Maximum} 15MB.",
				accept : "${msgCat_InvalidFiletype} (3gp, mp4, m4v)."
			},
			linkUrl : {
				url : "${msgCat_InvalidUrl}"
			}
		};
		var dialog_submit			= "${msgCat_ConfirmSubmit}";
		var dialog_reject 			= "${msgCat_ConfirmReject}";
		var dialog_publish			= "${msgCat_ConfirmPublish}";
		var dialog_discard			= "${msgCat_ConfirmDiscard}";
		var dialog_disabledFunction = "${msgCat_FunctionNotAvailable}";
		var dialog_submitting		= "${msgCat_Submitting}";
		var uploadingMedia			= "${msgCat_UploadingMedia}";
		var articleMediaError		= "${msgCat_ArticleMediaError}";
		var msgCat_Yes 				= "${msgCat_Yes}";
		var msgCat_No 				= "${msgCat_No}";

		var server = window.kme.serverDetails;
		var defaultImage = server.getContextPath() + "/getIcon/${defaultIcon}";
		var serverInstanceURL  = server.getServerPath() + "/writer/${toolInstance}";
		var imageBaseURL = serverInstanceURL+"/media/";
		var imagePreviewBaseURL = serverInstanceURL+ "/media/view/";
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-disabler.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.confirm.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/writer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.additional-methods.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/writer.article.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/writer.article.validate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/writer.article.media.js"></script>
	<c:if test="${sessionScope['session_native'] == true}">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/writer.article.native.js"></script>
	</c:if>

</kme:page>