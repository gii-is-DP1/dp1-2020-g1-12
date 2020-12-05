<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="articulos">
 	<spring:url value="/resources/images/Logo.png" htmlEscape="true" var="logo"/>
	<h2>Artículos por el género buscado</h2>
    
    <fieldset>
		 <c:forEach items="${articulos}" var="articulos">
            <div>
	            <spring:url value="/articulos/{articuloId}" var="articuloUrl">
	              		<spring:param name="articuloId" value="${articulos.id}"/>
	            </spring:url>
	            
	            <a href="${fn:escapeXml(articuloUrl)}"><img style='width: 20%; height: 10%' alt='' 
	            	onerror="this.src=''" src='${articulos.urlImagen}'/><br><br>
	            	
	            <c:out value="${articulos.marca} ${articulos.modelo}"/></a><br>
	            
				<c:if test="${articulos.oferta.disponibilidad}" >
				<span style="color: red; font-size: large"><fmt:formatNumber type="number" maxFractionDigits="2" 
					value="${articulos.precio * (1 - articulos.oferta.porcentaje/100)}"/> € </span>
				<span style="font-size: small; padding: 0px 6px 0px 6px"><strike>${articulos.precio} € </strike></span>
				<span style="color: white; background-color: #f35a5a; border-radius: 3px">${articulos.oferta.porcentaje}%</span>
				
				<br><br>
				</c:if>
				<c:if test="${!articulos.oferta.disponibilidad}" >
				<c:out value="${articulos.precio} €"/><br><br>
				</c:if>
            </div>
        </c:forEach> 
	</fieldset>
</dpc:layout>