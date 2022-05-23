<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

  
<acme:form> 

	<acme:input-textbox code="administrator.system-configuration.form.label.system-currency" path="systemCurrency"/>
	<acme:input-textbox code="administrator.system-configuration.form.label.accepted-currency" path="acceptedCurrency"/>
	<acme:input-textbox code="administrator.system-configuration.form.label.strong-spam-terms" path="strongSpamTerms"/>
	<acme:input-textbox code="administrator.system-configuration.form.label.weak-spam-terms" path="weakSpamTerms"/>
	<acme:input-textbox code="administrator.system-configuration.form.label.strong-spam-threshold" path="strongSpamThreshold"/> 
	<acme:input-textbox code="administrator.system-configuration.form.label.weak-spam-threshold" path="weakSpamThreshold"/>  
	 
	                                 


	<acme:submit test="${acme:anyOf(command, 'show, update')}" code="administrator.system-configuration.form.button.update" action="/administrator/system-configuration/update"/>

</acme:form>
