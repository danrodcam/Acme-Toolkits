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
	<acme:input-textbox code="inventor.patronage.form.label.status" path="status"/>
	<acme:input-textbox code="inventor.patronage.form.label.legalStuff" path="legalStuff"/>
	<acme:input-textbox code="inventor.patronage.form.label.code" path="code"/>
	<acme:input-double code="inventor.patronage.form.label.budget" path="budget"/>
	<acme:input-money code="inventor.patronage.form.label.exchange" path="exchange"/>
	<acme:input-moment code="inventor.patronage.form.label.creationMoment" path="creationMoment"/>
	<acme:input-url code="inventor.patronage.form.label.optionalLink" path="optionalLink"/>
	<acme:input-moment code="inventor.patronage.form.label.initialDate" path="initialDate"/>
	<acme:input-moment code="inventor.patronage.form.label.finalDate" path="finalDate"/>
	<acme:input-textbox code="inventor.patronage.patron.form.label.name" path="patron.userAccount.identity.name"/>
	<acme:input-textbox code="inventor.patronage.patron.form.label.surname" path="patron.userAccount.identity.surname"/>
	<acme:input-textbox code="inventor.patronage.patron.form.label.username" path="patron.userAccount.username"/>
	<acme:input-textbox code="inventor.patronage.patron.form.label.company" path="patron.company"/>
	<acme:input-textbox code="inventor.patronage.patron.form.label.statement" path="patron.statement"/>
	<acme:input-url code="inventor.patronage.patron.form.label.optionalLink" path="patron.optionalLink"/>
  
	<jstl:if test="${status != 'PROPOSED'}">
		<acme:input-textbox code="inventor.patronage.form.label.status" path="status" readonly="true"/>
	</jstl:if>
	
	<jstl:if test="${status == 'PROPOSED'}">
		<acme:input-select path="status" code="inventor.patronage.form.label.new-status">
			<acme:input-option code="PROPOSED" value="PROPOSED" selected="true"/>
			<acme:input-option code="ACCEPTED" value="ACCEPTED"/>
			<acme:input-option code="DENIED" value="DENIED"/>
		</acme:input-select>		
	</jstl:if>
	
			
	
	
	<acme:input-textbox code="inventor.patronage.form.label.legalStuff" path="legalStuff" readonly="true"/>
	<acme:input-textbox code="inventor.patronage.form.label.code" path="code" readonly="true"/>
	<acme:input-double code="inventor.patronage.form.label.budget" path="budget" readonly="true"/>
	<acme:input-moment code="inventor.patronage.form.label.creationMoment" path="creationMoment" readonly="true"/>
	<acme:input-url code="inventor.patronage.form.label.optionalLink" path="optionalLink" readonly="true"/>
	<acme:input-moment code="inventor.patronage.form.label.initialDate" path="initialDate" readonly="true"/>
	<acme:input-moment code="inventor.patronage.form.label.finalDate" path="finalDate" readonly="true"/>
	<acme:input-textbox code="inventor.patronage.patron.form.label.name" path="patron.userAccount.identity.name" readonly="true"/>
	<acme:input-textbox code="inventor.patronage.patron.form.label.surname" path="patron.userAccount.identity.surname" readonly="true"/>
	<acme:input-textbox code="inventor.patronage.patron.form.label.username" path="patron.userAccount.username" readonly="true"/>
	<acme:input-textbox code="inventor.patronage.patron.form.label.company" path="patron.company" readonly="true"/>
	<acme:input-textbox code="inventor.patronage.patron.form.label.statement" path="patron.statement" readonly="true"/>
	<acme:input-url code="inventor.patronage.patron.form.label.optionalLink" path="patron.optionalLink" readonly="true"/>
	
	<acme:button code="inventor.patronage.report.form.button.create" action="/inventor/patronage-report/create?masterId=${id}"/>
	
	<acme:submit test="${acme:anyOf(command, 'show, update') && status == 'PROPOSED'}" code="inventor.patronage.form.button.update" action="/inventor/patronage/update"/>
</acme:form>
