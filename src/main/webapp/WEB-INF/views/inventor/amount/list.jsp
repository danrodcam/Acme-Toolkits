<%--
- list.jsp
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

<acme:list>
	<acme:list-column code="inventor.amount.list.label.units" path="units" width="10%"/>
	<acme:list-column code="inventor.amount.list.label.name" path="item.name" width="60%"/>
	<acme:list-column code="inventor.amount.list.label.code" path="item.code" width="10%"/>
	<acme:list-column code="inventor.amount.list.label.technology" path="item.technology" width="10%"/>
	<acme:list-column code="inventor.amount.list.label.price" path="item.retailPrice" width="10%"/>


</acme:list>

	<jstl:if test="${type == 'component'}">
	<acme:button test="${showCreate}" code="inventor.amount.form.button.add.component" action="/inventor/amount/create-component?masterId=${masterId}"/>
	</jstl:if>
	<jstl:if test="${type == 'tool'}">
	<acme:button test="${showCreate}" code="inventor.amount.form.button.add.tool" action="/inventor/amount/create-tool?masterId=${masterId}"/>
	</jstl:if>
			