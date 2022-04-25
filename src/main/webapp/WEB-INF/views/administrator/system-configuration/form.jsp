<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="administrator.system-configuration.form.label.system-currency"/>
		</th>
		<td>
			<acme:print value="${systemCurrency}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.system-configuration.form.label.accepted-currency"/>
		</th>
		<td>
			<acme:print value="${acceptedCurrency}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.system-configuration.form.label.strong-spam-terms"/>
		</th>
		<td>
			<acme:print value="${strongSpamTerms}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.system-configuration.form.label.weak-spam-terms"/>
		</th>
		<td>
			<acme:print value="${weakSpamTerms}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.system-configuration.form.label.strong-spam-threshold"/>
		</th>
		<td>
			<acme:print value="${strongSpamThreshold}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.system-configuration.form.label.weak-spam-threshold"/>
		</th>
		<td>
			<acme:print value="${weakSpamThreshold}"/>
		</td>
	</tr>
</table>

