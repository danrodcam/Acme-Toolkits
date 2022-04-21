<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="authenticated.system-configuration.form.label.system-currency"/>
		</th>
		<td>
			<acme:print value="${systemCurrency}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="authenticated.system-configuration.form.label.accepted-currency"/>
		</th>
		<td>
			<acme:print value="${acceptedCurrency}"/>
		</td>
	</tr>
</table>
