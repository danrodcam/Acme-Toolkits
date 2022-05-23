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
	<acme:input-textbox code="patron.patronage.form.label.status" path="status"/>
	<acme:input-textbox code="patron.patronage.form.label.legalStuff" path="legalStuff"/>
	<acme:input-textbox code="patron.patronage.form.label.code" path="code"/>
	<acme:input-double code="patron.patronage.form.label.budget" path="budget"/>
	<acme:input-money code="patron.patronage.form.label.exchange" path="exchange"/>
	<acme:input-moment code="patron.patronage.form.label.creationMoment" path="creationMoment"/>
	<acme:input-url code="patron.patronage.form.label.optionalLink" path="optionalLink"/>
	<acme:input-moment code="patron.patronage.form.label.initialDate" path="initialDate"/>
	<acme:input-moment code="patron.patronage.form.label.finalDate" path="finalDate"/>
	<acme:input-textbox code="patron.patronage.patron.form.label.name" path="inventor.userAccount.identity.name"/>
	<acme:input-textbox code="patron.patronage.patron.form.label.surname" path="inventor.userAccount.identity.surname"/>
	<acme:input-textbox code="patron.patronage.patron.form.label.username" path="inventor.userAccount.username"/>
	<acme:input-textbox code="patron.patronage.patron.form.label.company" path="inventor.company"/>
	<acme:input-textbox code="patron.patronage.patron.form.label.statement" path="inventor.statement"/>
	<acme:input-url code="patron.patronage.patron.form.label.optionalLink" path="inventor.optionalLink"/>
</acme:form>
