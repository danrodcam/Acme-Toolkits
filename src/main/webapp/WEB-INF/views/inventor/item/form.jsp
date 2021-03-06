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
	<acme:input-textbox code="inventor.item.form.label.name" path="name"/>
	<jstl:if test="${command != 'create'}">
	<acme:input-textbox readonly= "true" code="inventor.item.form.label.code" path="code"/>
	</jstl:if>
	
	<acme:input-textbox code="inventor.item.form.label.technology" path="technology"/>
	<acme:input-textarea code="inventor.item.form.label.description" path="description"/>
	<acme:input-url code="inventor.item.form.label.link" path="link"/>
	<acme:input-money code="inventor.item.form.label.price" path="retailPrice"/>
	<jstl:if test="${command != 'create'}">
	<acme:input-money readonly = "true" code="inventor.item.form.label.exchange" path="exchange"/>
	</jstl:if>
	
	
	
<jstl:choose>	 
		<jstl:when test="${acme:anyOf(command, 'show, update, delete, publish') && published == false }">
			<acme:submit code="inventor.item.form.button.delete" action="/inventor/item/delete"/>	
			<acme:submit code="inventor.item.form.button.update" action="/inventor/item/update"/>
			<acme:submit code="inventor.item.form.button.publish" action="/inventor/item/publish"/>	
		</jstl:when>

		<jstl:when test="${command == 'create-component'}">
			<acme:submit code="inventor.item.form.button.create" action="/inventor/item/create-component"/>
		</jstl:when>
		
		<jstl:when test="${command == 'create-tool'}">
			<acme:submit code="inventor.item.form.button.create" action="/inventor/item/create-tool"/>
		</jstl:when>		
	</jstl:choose>
	
</acme:form>