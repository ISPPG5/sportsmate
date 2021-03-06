<%--
 * list.jsp
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

<br>
<div class='table-responsive'>
	<display:table name="requestTeams" id="row" requestURI="${requestURI}"
		pagesize="5" class="">
		<display:column>
				<div class="col-xs-12 col-md-9 well well-sm">
					<div class="col-xs-8 col-sm-9">
						<b><spring:message code="requestTeam.user" />: </b>
						<spring:message code="user.email" var="userHeader" />
						<jstl:out value="${row.user.email}"></jstl:out>
					<br />
						<b><spring:message code="requestTeam.team" />: </b>
						<spring:message code="team.name" var="teamHeader" />
						<jstl:out value="${row.team.name}"></jstl:out>
					<br />
						<b><spring:message code="requestTeam.requestDate" />: </b>
						<spring:message code="requestTeam.requestDate" var="requestDateHeader" />
						<jstl:out value="${row.requestDate}"></jstl:out>					
					</div>
					
					<spring:message code="requestTeam.acceptRequest"
						var="acceptRequestHeader" />
						
					<security:authorize access="hasRole('USER')">
						<div class="col-xs-4 col-sm-3">
						<a href="requestTeam/user/acceptRequest.do?requestTeamId=${row.id}">
							<button type="button" class="btn btn-sm btn-default col-xs-12 spm-event-detail-btn">
								<spring:message code="requestTeam.acceptRequest" />
							</button>
						</a>
						</div>
					</security:authorize>
					
				</div>
		</display:column>
	</display:table>
</div>

<acme:cancel code="requestTeam.back" url="welcome/index.do" />