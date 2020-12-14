<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="articulos">

    <jsp:attribute name="customScript">
    	<script src="https://code.jquery.com/jquery-3.1.1.min.js" ></script>
        <script>
        	$(document).ready(function() {
        	$('.mdb-select').materialSelect();
        	});

        </script>
        
    </jsp:attribute>
    <jsp:body>
 	<spring:url value="/resources/images/Logo.png" htmlEscape="true" var="logo"/>
	<!--  <img style="height: 200px; width: 220px" src="${logo}"/>-->
	
    <form:form modelAttribute="query" action="/busqueda" class="form-horizontal" >
        <div class="form-group has-feedback">
            <dpc:inputField label="Busqueda" name="modelo"/>
            
            <select class="selectpicker" name="generos" multiple>
            	<option value="" disabled selected>Seleccione géneros a buscar</option>
            	<c:forEach items="${generos}" var="genero">
    				<option value="${genero.id}">${genero.nombre}</option>
    			</c:forEach>
  			</select>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Buscar</button>
            </div>
        </div>
    </form:form>
    <c:if test="${mensaje == null}">
    <h2><a href="/ofertas"> Ofertas destacadas</a></h2>
    <c:if test="${ofertas.size() == 0}">
    	<p>No existen ofertas actualmente.</p>
    </c:if>
    <fieldset>
		 <c:forEach items="${ofertas}" var="oferta">
            <div>
	            <spring:url value="/articulos/{articuloId}" var="articuloUrl">
	              		<spring:param name="articuloId" value="${oferta.id}"/>
	            </spring:url>
	            
	            <a href="${fn:escapeXml(articuloUrl)}"><img style='width: 20%; height: 10%' alt='' 
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
	<br>
	<hr width =900>
	<br>
	<h2>Artículos destacados</h2>
	</c:if>
	<c:if test="${mensaje != null}">
		<h2>${mensaje}</h2>
	</c:if>
	<c:if test="${articulos.size() == 0}"><p>No se ha encontrado ninguna coincidencia.</p></c:if>
    <fieldset>
		 <c:forEach items="${articulos}" var="articulos">
            <div>
	            <spring:url value="/articulos/{articuloId}" var="articuloUrl">
	              		<spring:param name="articuloId" value="${articulos.id}"/>
	            </spring:url>
	            
	            <a href="${fn:escapeXml(articuloUrl)}"><img style='width: 20%; height: 10%' alt='' 
	            	onerror="this.src=''" src='${articulos.urlImagen}'/><br><br>
	            	
	            <c:out value="${articulos.marca} ${articulos.modelo}"/></a><br>
				<c:if test="${articulos.stock != 0}" >
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
				</c:if>
				<c:if test="${articulos.stock == 0}" >
					<span style="color: grey">AGOTADO</span>
				</c:if>
            </div>
        </c:forEach> 
	</fieldset>
	</jsp:body>
</dpc:layout>