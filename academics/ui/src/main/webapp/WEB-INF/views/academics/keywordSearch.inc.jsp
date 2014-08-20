<form:form action="${pageContext.request.contextPath}/academics/classSearch" method="post" commandName="search" data-ajax="false">
    <input id="searchCriteria" name="searchText" type="search" class="text ui-widget-content ui-corner-all" placeholder="${watermark}" value=""/>
    <input id="termId" name="termId" type="hidden" value="${currentTerm.id}"/>
    <input id="termDescription" name="termDescription" type="hidden" value="${currentTerm.description}"/>
</form:form>