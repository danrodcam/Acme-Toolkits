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
	<acme:input-textbox readonly= "true" code="inventor.item.form.label.code" path="code"/>
	<acme:input-textbox code="inventor.item.form.label.technology" path="technology"/>
	<acme:input-textbox code="inventor.item.form.label.description" path="description"/>
	<acme:input-url code="inventor.item.form.label.link" path="link"/>
	<acme:input-money code="inventor.item.form.label.price" path="retailPrice"/>
	
	
<jstl:choose>	 
		<jstl:when test="${acme:anyOf(command, 'show-component, update-component, delete-component, publish-component') && published == false }">
			<acme:submit code="inventor.item.form.button.delete" action="/inventor/item/delete-component"/>	
			<acme:submit code="inventor.item.form.button.update" action="/inventor/item/update-component"/>
			<acme:submit code="inventor.item.form.button.publish" action="/inventor/item/publish-component"/>	
		</jstl:when>
		
		<jstl:when test="${acme:anyOf(command, 'show, update-tool, delete-tool, publish-tool') && published == false }">
			<acme:submit code="inventor.item.form.button.delete" action="/inventor/item/delete-tool"/>	
			<acme:submit code="inventor.item.form.button.update" action="/inventor/item/update-tool"/>
			<acme:submit code="inventor.item.form.button.publish" action="/inventor/item/publish-tool"/>	
		</jstl:when>

		<jstl:when test="${command == 'create-component'}">
			<acme:submit code="inventor.item.form.button.create" action="/inventor/item/create-component"/>
		</jstl:when>
		
		<jstl:when test="${command == 'create-tool'}">
			<acme:submit code="inventor.item.form.button.create" action="/inventor/item/create-tool"/>
		</jstl:when>		
	</jstl:choose>
	
</acme:form>