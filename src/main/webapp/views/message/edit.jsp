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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="message/actor/edit.do" modelAttribute="messageForm">

	<form:hidden path="id" />	
	
	<acme:textbox code="message.sendMoment" path="sendMoment" readonly="true"/>
	<br />

	<b><spring:message code="message.sender" />: </b>
		<jstl:out value="${sender}" />
	<br />
	<br />
	
	
	<form:label path="recipient"><spring:message code="message.recipient"/></form:label>
		<form:select path="recipient" >
			<form:options items="${actors}"  itemLabel="email"/>
		</form:select>
		<form:errors cssClass="error" path="recipient" />
	<br />
	<br />
	
	<acme:textbox code="message.subject" path="subject"/>
	<br />
	
	<acme:textarea code="message.body" path="body"/>
	<br />
	
	<input type="submit" name="send" value="<spring:message code="message.send" />" />&nbsp; 

</form:form>


	

