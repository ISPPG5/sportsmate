<%--
 * edit.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<security:authorize access="hasRole('USER')">

	<jstl:if test="${yaVotado==true}">
		<h2>
			<spring:message code="event.cantVoteThisUser"></spring:message>
		</h2>
		<acme:cancel code="event.cancel" url="event/user/list.do" />
	</jstl:if>

	<jstl:if test="${yaVotado==false and voteCustomer==false}">

		<form:form action="event/user/vote.do" modelAttribute="userVoteForm">

			<form:hidden path="id" />
			<acme:textarea code="event.name" path="name" readonly="true" />
			<acme:textbox code="event.score" path="score" />
			<br />

			<acme:submit name="save" code="event.vote" />
			<acme:cancel code="event.cancel" url="event/user/list.do" />
		</form:form>
	</jstl:if>

	<jstl:if test="${yaVotado==false and voteCustomer==true}">

		<form:form action="event/user/voteCustomer.do"
			modelAttribute="userVoteForm">

			<form:hidden path="id" />
			<acme:textarea code="event.place" path="name" readonly="true" />
			<acme:textbox code="event.score" path="score" />
			<br />

			<acme:submit name="save" code="event.vote" />
			<acme:cancel code="event.cancel" url="event/user/list.do" />
		</form:form>
	</jstl:if>
</security:authorize>


