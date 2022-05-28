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
	<acme:input-textbox code="inventor.chimpum.form.label.title" path="title"/>
	<acme:input-moment readonly= "true" code="inventor.chimpum.form.label.creationMoment" path="creationMoment"/>
	<acme:input-textbox readonly= "true" code="inventor.chimpum.form.label.code" path="code"/>
	<acme:input-textarea code="inventor.chimpum.form.label.description" path="description"/>
	<acme:input-moment code="inventor.chimpum.form.label.initialDate" path="initialDate"/>
	<acme:input-moment code="inventor.chimpum.form.label.finalDate" path="finalDate"/>
	<acme:input-money code="inventor.chimpum.form.label.budget" path="budget"/>
	<acme:input-url code="inventor.chimpum.form.label.link" path="link"/>
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(command, 'show, update, delete')}">
			<acme:submit code="inventor.chimpum.form.button.update" action="/inventor/chimpum/update"/>
			<acme:submit code="inventor.chimpum.form.button.delete" action="/inventor/chimpum/delete"/>	
		</jstl:when>

		<jstl:when test="${command == 'create-chimpum'}">
			<acme:submit code="inventor.chimpum.form.button.create-chimpum" action="/inventor/chimpum/create-chimpum?masterId=${item.id}"/>
		</jstl:when>
				
	</jstl:choose>
	
	
	
</acme:form>