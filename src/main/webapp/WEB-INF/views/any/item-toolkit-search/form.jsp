<%--
- form.jsp
-
- Copyright (C) 2012-2022 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox code="any.item-toolkit-search.form.label.search" path="search"/>
	<acme:submit code="any.item-toolkit-search.form.button.perform" action="/any/item-toolkit-search/perform"/>
	<br>
	<h3>
	<acme:message code="any.item-toolkit-search.form.toolkits"/>
	</h3>
	<br>
	<jstl:forEach var="toolkit" items="${toolkits}">
			<acme:button code="${toolkit.code}:${toolkit.title}" action="/any/toolkit/show?id=${toolkit.id}"/>
			<br>
	</jstl:forEach>
		
	
</acme:form>
