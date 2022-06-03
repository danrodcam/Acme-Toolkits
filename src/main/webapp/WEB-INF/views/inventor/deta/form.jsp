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
	<acme:input-textbox readonly="true" code="inventor.deta.form.label.code" path="code"/>
	<acme:input-textbox readonly="true" code="inventor.deta.form.label.creation-moment" path="creationMoment"/>
	<acme:input-textbox code="inventor.deta.form.label.subject" path="subject"/>
	<acme:input-textarea code="inventor.deta.form.label.summary" path="summary"/>
	<acme:input-textbox code="inventor.deta.form.label.initialDate" path="initialDate"/>
	<acme:input-textbox code="inventor.deta.form.label.finalDate" path="finalDate"/>
	<acme:input-money code="inventor.deta.form.label.allowance" path="allowance"/>
	<acme:input-url code="inventor.deta.form.label.more-info" path="moreInfo"/>
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(command, 'show, update, delete')}">
			<acme:submit code="inventor.deta.form.button.update" action="/inventor/deta/update"/>
			<acme:submit code="inventor.deta.form.button.delete" action="/inventor/deta/delete"/>	
		</jstl:when>

		<jstl:when test="${command == 'create-deta'}">
			<acme:submit code="inventor.deta.form.button.create-deta" action="/inventor/deta/create-deta?masterId=${item.id}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>