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
	
    <form:form modelAttribute="articulo" action="/busqueda" class="form-horizontal" >
        <div class="form-group has-feedback">
            <dpc:inputField label="Busqueda" name="modelo"/>
            
            <select class="selectpicker" name="generos" multiple>
            	<option value="" disabled selected>Seleccione géneros a buscar</option>
    			<option value="1">Smartphones</option>
    			<option value="2">Ordenadores</option>
    			<option value="3">Electrodomésticos</option>
    			<option value="4">Multimedia</option>
    			<option value="5">Entretenimiento</option>
    			<option value="6">Videojuegos</option>
  			</select>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Buscar</button>
            </div>
        </div>
    </form:form>
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
	</jsp:body>
</dpc:layout>