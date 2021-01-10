<%@ page session="true" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="articulos">

    <jsp:attribute name="customScript">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/css/bootstrap-select.css" />
		<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/js/bootstrap-select.min.js"></script>
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
	
    <form:form modelAttribute="query" action="/busqueda?" class="form-horizontal" >
        <div class="container">
	    <span>
            <select class="selectpicker" name="generos" multiple title="Elige uno o varios géneros">
            	<c:forEach items="${generos}" var="genero">
    				<option value="${genero.id}">${genero.nombre}</option>
    			</c:forEach>
  			</select>
  		</span>         
        <span>
            <input placeholder="Introduzca su búsqueda" size="100" name="modelo"/>
		</span>
         <div style="float:right">
			<button class="btn btn-default" type="submit">Buscar</button>
		</div>
        </div>

		<br><br><br>

        <c:if test="${mensaje != null}">
	        <div style="float:right">
				<input type="hidden" name="page" value="${articulos.getNumber()}"/>
		    	<select name="orderBy">
            		<option value="" disabled selected>Ordenar por:</option>
		    		<option value="-id">Más recientes</option>
		    		<option value="id">Más antiguos</option>
		    		<option value="marca">Nombre A-Z</option>
		    		<option value="-marca">Nombre Z-A</option>
		    		<option value="precio">Precio de más bajo a más alto</option>
		    		<option value="-precio">Precio de más alto a más bajo</option>
		    	</select>
	    	</div>
	    </c:if>
	</form:form>
    
    <c:if test="${mensaje == null}">
	    <h2><a href="/ofertas"> Ofertas destacadas</a></h2>
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
		<br>
		<hr width =900>
		<br>
		<form action="/?" method="get">
	        <div style="float:right">
				<input type="hidden" name="page" value="${articulos.getNumber()}"/>
		    	<select onchange="this.form.submit();" name="orderBy">
            		<option value="" disabled selected>Ordenar por:</option>
		    		<option value="-id">Más recientes</option>
		    		<option value="id">Más antiguos</option>
		    		<option value="marca">Nombre A-Z</option>
		    		<option value="-marca">Nombre Z-A</option>
		    		<option value="precio">Precio de más bajo a más alto</option>
		    		<option value="-precio">Precio de más alto a más bajo</option>
		    	</select>
	    	</div>
    	</form>
		<h2>Artículos destacados</h2>
	</c:if>
	<c:if test="${mensaje != null}">
		<h2>${mensaje}</h2>
	</c:if>
	<c:if test="${articulos.getNumberOfElements() == 0}"><p>No se ha encontrado ninguna coincidencia.</p></c:if>
    <c:if test="${articulos.getNumberOfElements() != 0}">
	    <fieldset>
			 <c:forEach items="${articulos.getContent()}" var="articulos">
	            <div style="display: inline-table;width:17.5%;margin-left: 2%;overflow: hidden;">
		            <spring:url value="/articulos/{articuloId}" var="articuloUrl">
		              		<spring:param name="articuloId" value="${articulos.id}"/>
		            </spring:url>
		            
		            <a href="${fn:escapeXml(articuloUrl)}"><img style='width: 100%; height: 10%' alt='' 
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
			<div class="container">
    			<div class="row">
        			<div class="col-12 text-center">
						<c:forEach begin="0" end="${articulos.getTotalPages()-1}" varStatus="page">
							<c:if test="${page.index != articulos.getNumber()}">
								<spring:url value="/?" var="siguienteUrl">
									<spring:param name="page" value="${page.index}"/>
									<spring:param name="orderBy" value="${ordenacion}"/>									
								</spring:url>
								
								<a href="${fn:escapeXml(siguienteUrl)}">
									<button class="btn btn-default" type="submit">${page.index+1}</button>
								</a>
							</c:if> 
							<c:if test="${page.index == articulos.getNumber()}">
								<button class="btn btn-default" disabled>${page.index+1}</button>
							</c:if>
						</c:forEach>
					</div>
				</div>
			</div>
		</c:if>
	</jsp:body>
</dpc:layout>