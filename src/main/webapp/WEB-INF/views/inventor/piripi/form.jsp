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
	<acme:input-textbox code="inventor.Piripi.form.label.title" path="title"/>
	<acme:input-moment readonly= "true" code="inventor.Piripi.form.label.creationMoment" path="creationMoment"/>
	<acme:input-textbox readonly= "true" code="inventor.Piripi.form.label.code" path="code"/>
	<acme:input-textarea code="inventor.Piripi.form.label.description" path="description"/>
	<acme:input-moment code="inventor.Piripi.form.label.initialDate" path="initialDate"/>
	<acme:input-moment code="inventor.Piripi.form.label.finalDate" path="finalDate"/>
	<acme:input-money code="inventor.Piripi.form.label.budget" path="budget"/>
	<acme:input-url code="inventor.Piripi.form.label.link" path="link"/>
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(command, 'show, update, delete') && published == true}">
			<acme:submit code="inventor.Piripi.form.button.update" action="/inventor/piripi/update"/>
			<acme:submit code="inventor.Piripi.form.button.delete" action="/inventor/piripi/delete"/>	
		</jstl:when>
		
		<jstl:when test="${command == 'create-piripi'}">
			<acme:submit code="inventor.Piripi.form.button.create" action="/inventor/piripi/create-piripi?masterId=${item.id}"/>	
		</jstl:when>

				
	</jstl:choose>
	
	
	
</acme:form>