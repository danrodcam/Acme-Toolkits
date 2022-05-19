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
	
	<acme:input-integer code="inventor.amount.form.label.units" path="units"/>
	<acme:input-select code="inventor.amount.form.label.${type}" path="item">
		<jstl:forEach var="i" items="${publishedItems}">
			<acme:input-option code="${i.code}" value="${i.id}"/>
		</jstl:forEach>
	</acme:input-select>
	
	
    
    
	<acme:submit code="inventor.amount.form.button.create" action="/inventor/amount/create-${type}?masterId=${masterId}"/>
</acme:form>

