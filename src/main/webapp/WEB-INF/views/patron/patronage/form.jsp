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

	<acme:input-textbox readonly="true" code="patron.patronage.form.label.status" path="status"/>
	<acme:input-textarea code="patron.patronage.form.label.legalStuff" path="legalStuff"/>
	<acme:input-textbox readonly="true" code="patron.patronage.form.label.code" path="code"/>
	<acme:input-money code="patron.patronage.form.label.budget" path="budget"/>
	<acme:input-url code="patron.patronage.form.label.optionalLink" path="optionalLink"/>
	<acme:input-moment code="patron.patronage.form.label.initialDate" path="initialDate"/>
	<acme:input-moment code="patron.patronage.form.label.finalDate" path="finalDate"/>
	
<jstl:choose>
		<jstl:when test="${command == 'show' && isPublished==true}">
			<acme:input-textbox code="patron.patronage.patron.form.label.name" path="inventor.userAccount.identity.name"/>
			<acme:input-textbox code="patron.patronage.patron.form.label.surname" path="inventor.userAccount.identity.surname"/>
			<acme:input-textbox code="patron.patronage.patron.form.label.username" path="inventor.userAccount.username"/>
			<acme:input-textbox code="patron.patronage.patron.form.label.company" path="inventor.company"/>
			<acme:input-textbox code="patron.patronage.patron.form.label.statement" path="inventor.statement"/>
			<acme:input-textbox code="patron.patronage.patron.form.label.optionalLink" path="inventor.optionalLink"/>
		</jstl:when>
		
		<jstl:when test="${acme:anyOf(command, 'show, update, delete, publish') && isPublished == false }">	
		
		<jstl:if test="${acme:anyOf(command, 'show, update') }">
			<acme:input-select code="patron.patronage.form.label.inventor" path="inventor">
				<jstl:forEach var="i" items="${inventors}">
					<jstl:if test="${i.id == inventor.id }">
					<acme:input-option code="${i.userAccount.username}" value="${i.id}" selected="true"/>
					</jstl:if>
					<jstl:if test="${i.id != inventor.id }">
					<acme:input-option code="${i.userAccount.username}" value="${i.id}"/>
					</jstl:if>
					
				</jstl:forEach>
			</acme:input-select>
		</jstl:if>
			<acme:submit code="patron.patronage.form.button.update" action="/patron/patronage/update"/>
			<acme:submit code="patron.patronage.form.button.delete" action="/patron/patronage/delete"/>
			<acme:submit code="patron.patronage.form.button.publish" action="/patron/patronage/publish"/>
			
			
		</jstl:when>	
			
			<jstl:when test="${command == 'create'}">
				<acme:input-select code="patron.patronage.form.label.inventor" path="inventor">
					<jstl:forEach var="i" items="${inventors}">
						<acme:input-option code="${i.userAccount.username}" value="${i.id}"/>
					</jstl:forEach>
				</acme:input-select>
			
				<acme:submit code="patron.patronage.form.button.create" action="/patron/patronage/create"/>
			</jstl:when>	 
		
		
		
			
			
	
	</jstl:choose>
</acme:form>
