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
	<acme:input-textbox code="inventor.item.form.label.code" path="code"/>
	<acme:input-select code="inventor.item.form.label.type" path="type"/>
	<acme:input-textbox code="inventor.item.form.label.technology" path="technology"/>
	<acme:input-textbox code="inventor.item.form.label.description" path="description"/>
	<acme:input-url code="inventor.item.form.label.link" path="link"/>
	<acme:input-money code="inventor.item.form.label.price" path="retailPrice"/>
	
	
	
	    <jstl:choose>	 
		<jstl:when test="${command == 'show'}">
			<acme:button code="inventor.item.form.button.components" action="/inventor/item/list-own-components?masterId=${id}"/>
			<acme:submit code="inventor.item.form.button.delete" action="/inventor/item/delete"/>	
			<acme:submit code="inventor.item.form.button.update" action="/inventor/item/update"/>
			<acme:submit code="inventor.item.form.button.publish" action="/inventor/item/publish"/>	
		</jstl:when>

		<jstl:when test="${command == 'create'}">
			<acme:submit code="inventor.item.form.button.create" action="/inventor/item/create"/>
		</jstl:when>		
	</jstl:choose>
	
</acme:form>