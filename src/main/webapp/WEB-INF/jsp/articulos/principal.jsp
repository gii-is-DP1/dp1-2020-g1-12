<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="articulos">
 	<spring:url value="/resources/images/Logo.png" htmlEscape="true" var="logo"/>
	<img style="height: 200px; width: 220px" src="${logo}"/>
    
    <fieldset>
		 <c:forEach items="${articulos}" var="articulos">
            <div>
	            <spring:url value="/articulos/{articuloId}" var="articuloUrl">
	              		<spring:param name="articuloId" value="${articulo.id}"/>
	            </spring:url>
	            <a href="${fn:escapeXml(articuloUrl)}"><img style='width: 20%; height: 10%' alt='' 
	            	onerror="this.src=''" src='${articulos.urlImagen}'/></br></br>
	            <c:out value="${articulos.marca} ${articulos.modelo}"/></a></br>
				<c:out value="${articulos.precio} €"/></br></br>
            </div>
        </c:forEach> 
	</fieldset>
</dpc:layout>