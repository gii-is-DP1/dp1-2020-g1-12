<%@ page session="true" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="articulos">


 	<spring:url value="/resources/images/Logo.png" htmlEscape="true" var="logo"/>
	<!--  <img style="height: 200px; width: 220px" src="${logo}"/>-->
	
    
    <h2>Ofertas destacadas</h2>
    <c:if test="${ofertas.size() == 0}">
    	<p>No existen ofertas actualmente.</p>
    </c:if>
    
    <fieldset>
		 <c:forEach items="${ofertas}" var="oferta">
            <div style="display: inline-table;width:17.5%;margin-left: 2%;overflow: hidden;">
	            <spring:url value="/articulos/{articuloId}" var="articuloUrl">
	              		<spring:param name="articuloId" value="${oferta.id}"/>
	            </spring:url>
	            
	            <a href="${fn:escapeXml(articuloUrl)}"><img style='width: 100%; height: 10%' alt='' 
	            	onerror="this.src=''" src='${oferta.urlImagen}'/><br><br>
	            	
	            <c:out value="${oferta.marca} ${oferta.modelo}"/></a><br>
	            
				<span style="color: red; font-size: large"><fmt:formatNumber type="number" maxFractionDigits="2" 
					value="${oferta.precio * (1 - oferta.oferta.porcentaje/100)}"/> € </span>
				<span style="font-size: small; padding: 0px 6px 0px 6px"><strike>${oferta.precio} € </strike></span>
				<span style="color: white; background-color: #f35a5a; border-radius: 3px">${oferta.oferta.porcentaje}%</span>
				
				<br><br>

            </div>
        </c:forEach> 
	</fieldset>
	
</dpc:layout>