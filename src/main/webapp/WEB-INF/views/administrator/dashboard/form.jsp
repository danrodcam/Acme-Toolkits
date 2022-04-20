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
<acme:message code="administrator.dashboard.form.total"/>
<acme:print value="${numberOfComponents}"/><br>
<jstl:forEach var="entry" items="${statsComponents}">
<jstl:forEach var="record" items="${entry.key}">
<strong><acme:print value="${record.key}"/>
<jstl:out value=","></jstl:out>
<acme:print value="${record.value}"/>
</strong>
</jstl:forEach>
<jstl:forEach var="map" items="${entry.value}">
<acme:print value="${map.key}"/>
<jstl:out value=":"></jstl:out>
<acme:print value="${map.value}"/>
</jstl:forEach>
<br>
</jstl:forEach>
<br>
<h3>
	<acme:message code="administrator.dashboard.form.title.toolStats"/>
</h3>
<acme:message code="administrator.dashboard.form.total"></acme:message>
<acme:print value="${numberOfTools}"/><br>
<jstl:forEach var="entry" items="${statsTools}">
<strong><acme:print value="${entry.key}"/></strong>
<jstl:forEach var="map" items="${entry.value}">
<acme:print value="${map.key}"/>
<jstl:out value=":"></jstl:out>
<acme:print value="${map.value}"/>
</jstl:forEach>
<br>
</jstl:forEach>
<br>
<h3>
	<acme:message code="administrator.dashboard.form.title.patronageStats"/>
</h3>
<jstl:forEach var="entry" items="${statsPatronages}">
<strong><acme:print value="${entry.key}"/></strong>
<jstl:forEach var="map" items="${entry.value}">
<acme:print value="${map.key}"/>
<jstl:out value=":"></jstl:out>
<acme:print value="${map.value}"/>
</jstl:forEach>
<br>
</jstl:forEach>

