<%--
- menu.jsp
-
- Copyright (C) 2012-2022 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java" import="acme.framework.helpers.PrincipalHelper,acme.roles.Patron,acme.roles.Inventor"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
		
		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.systemConfiguration" action="/authenticated/system-configuration/show"/>
			<acme:menu-suboption code="master.menu.announcement.list" action="/authenticated/announcement/list"/>
			<acme:menu-suboption code="master.menu.authenticated.money-exchage" action="/authenticated/money-exchange/perform"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.system-configuration" action="/administrator/system-configuration/show"/>
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-suboption code="master.menu.administrator.dashboard" action="/administrator/dashboard/show"/>
			<acme:menu-suboption code="master.menu.administrator.create-announcement" action="/administrator/announcement/create"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.populate-initial" action="/administrator/populate-initial"/>
			<acme:menu-suboption code="master.menu.administrator.populate-sample" action="/administrator/populate-sample"/>			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shut-down" action="/administrator/shut-down"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.inventor" access="hasRole('Inventor')">
			<acme:menu-suboption code="master.menu.inventor.component.list.own" action="/inventor/item/list-own-components"/>
			<acme:menu-suboption code="master.menu.inventor.tool.list.own" action="/inventor/item/list-own-tools"/>
			<acme:menu-suboption code="master.menu.inventor.toolkit.list.own" action="/inventor/toolkit/list-own"/>
			<acme:menu-suboption code="master.menu.inventor.patronage.list.own" action="/inventor/patronage/list-own-patronage"/>		
      		<acme:menu-suboption code="master.menu.inventor.patronage-report.list.own" action="/inventor/patronage-report/list-own-patronage-report"/>

		</acme:menu-option>
		
		<acme:menu-option code="master.menu.patron" access="hasRole('Patron')">
			<acme:menu-suboption code="master.menu.patron.patronage.list.own" action="/patron/patronage/list-own-patron-patronage"/>
			<acme:menu-suboption code="master.menu.patron.dashboard" action="/patron/dashboard/show"/>	
			<acme:menu-suboption code="master.menu.patron.patronage-report.list.own" action="/patron/patronage-report/list-own-patronage-report"/>				
		</acme:menu-option>
		


		
		
		<acme:menu-option code="master.menu.any">
			<acme:menu-suboption code="master.menu.any.userAccount" action="/any/user-account/list"/>
      		<acme:menu-suboption code="master.menu.any.component.list.published" action="/any/item/list-published-components"/>
      		<acme:menu-suboption code="master.menu.any.tool.list.published" action="/any/item/list-published-tools"/>
      		<acme:menu-suboption code="master.menu.any.toolkit.list.published" action="/any/toolkit/list-published-toolkits"/>
      		<acme:menu-suboption code="master.menu.any.chirp.list" action="/any/chirp/list-recent"/>
      		<acme:menu-suboption code="master.menu.any.toolkit.list.published.item-toolkit-search" action="/any/item-toolkit-search/perform"/>
      		
		</acme:menu-option>
		


	</acme:menu-left>

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/master/sign-in" access="isAnonymous()"/>
		
		<acme:menu-option code="master.menu.user-account" access="isAuthenticated">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-patron" action="/authenticated/patron/create" access="!hasRole('Patron')"/>
			<acme:menu-suboption code="master.menu.user-account.patron" action="/authenticated/patron/update" access="hasRole('Patron')"/>
			<acme:menu-suboption code="master.menu.user-account.become-inventor" action="/authenticated/inventor/create" access="!hasRole('Inventor')"/>
			<acme:menu-suboption code="master.menu.user-account.inventor" action="/authenticated/inventor/update" access="hasRole('Inventor')"/>
		</acme:menu-option>

		
		
		<acme:menu-option code="master.menu.sign-out" action="/master/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>

