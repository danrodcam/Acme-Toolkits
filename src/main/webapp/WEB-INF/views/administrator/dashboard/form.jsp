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


<h2>
	<acme:message code="administrator.dashboard.form.title.general-indicators"/>
</h2>
<br>
<h3>
	<acme:message code="administrator.dashboard.form.title.componentStats"/>
</h3>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.total"/>
		</th>
		<td>
			<acme:print value="${numberOfComponents}"/><br>
		</td>
	</tr>
	<jstl:forEach var="entry" items="${statsComponents}">
	<tr>
	<jstl:forEach var="record" items="${entry.key}">
		<th scope="row">
			<strong><acme:print value="${record.key}"/>
			<jstl:out value=","></jstl:out>
			<acme:print value="${record.value}"/>
			</strong>
		</th>	
	</jstl:forEach>
	<jstl:forEach var="map" items="${entry.value}">
		<th scope="row">
			<acme:print value="${map.key}"/>
		</th>
		<td>
			<acme:print value="${map.value}"/>
		</td>	
	</jstl:forEach>
</jstl:forEach>
</table>
<h3>
	<acme:message code="administrator.dashboard.form.title.toolStats"/>
</h3>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.total"/>
		</th>
		<td>
			<acme:print value="${numberOfTools}"/><br>
		</td>
	</tr>
	<jstl:forEach var="entry" items="${statsTools}">
	<tr>
		<th scope="row">
			<strong><acme:print value="${entry.key}"/></strong>
		</th>	
	<jstl:forEach var="map" items="${entry.value}">
		<th scope="row">
			<acme:print value="${map.key}"/>
		</th>
		<td>
			<acme:print value="${map.value}"/>
		</td>	
	</jstl:forEach>
</jstl:forEach>
</table>
<h3>
	<acme:message code="administrator.dashboard.form.title.patronageStats"/>
</h3>
<table class="table table-sm">
	<jstl:forEach var="entry" items="${statsPatronages}">
	<tr>
		<th scope="row">
			<strong><acme:print value="${entry.key}"/></strong>
		</th>	
	<jstl:forEach var="map" items="${entry.value}">
		<th scope="row">
			<acme:print value="${map.key}"/>
		</th>
		<td>
			<acme:print value="${map.value}"/>
		</td>	
	</jstl:forEach>
</jstl:forEach>
</table>

