<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div ng-init="init()" ng-show="!submissionsFailed" class="list-group">
	<div ng-click="submissionClick(submission.id)" class="list-group-item" ng-repeat="submission in submissions">
		<h3>{{submission.id}}</h3>
		<p>{{submission.type}} - <fmt:formatDate value="${submission.postDate}" type="both" /></p>
	</div>
</div>
<div ng-show="submissionsFailed" class="list-group">
	<div class="list-group-item">
		<h3>failed to collect submissions</h3>
	</div>
</div>