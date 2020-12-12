<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="articulos">
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
    <h1>${articulo.marca} ${' '} ${articulo.modelo}</h1>
    <img style='width: 40%; height: 20%' alt='' 
	            	onerror="this.src=''" src='${articulo.urlImagen}'/>
	<br><a style="width:22%;float:right" class="btn btn-primary btn-lg btn-block" role="button" href="#">Añadir al carrito</a>
	
<table class="table table-borderless">
        <tr>
            <th style="width: 600px;">Vendedor</th>
            <td>${vendedor.nombre} ${' '} ${vendedor.apellido}</td>
        </tr>
        <tr>
            <th>Precio</th>
            <td>
            <c:if test="${articulo.oferta.disponibilidad}" >
                <span style="color: red; font-size: large"><fmt:formatNumber type="number" maxFractionDigits="2" 
                    value="${articulo.precio * (1 - articulo.oferta.porcentaje/100)}"/> € </span>
                <span style="font-size: small; padding: 0px 6px 0px 6px"><strike>${articulo.precio} € </strike></span>
                <span style="color: white; background-color: #f35a5a; border-radius: 3px">${articulo.oferta.porcentaje}%</span>

                </c:if>
                <c:if test="${!articulo.oferta.disponibilidad}" >
                    <c:out value="${articulo.precio} €"/>
               </c:if>
              </td>
        </tr>
        <tr>
            <th>Stock</th>
            <td>${articulo.stock}</td>
        </tr>
        <tr>
            <th>Estado</th>
            <td>${articulo.tipo}</td>
        </tr>
        <tr>
            <th>Gastos de envío</th>
            <td>${articulo.gastoEnvio} €</td>
        </tr>
        <tr>
            <th>Tiempo de Entrega</th>
            <td>${articulo.tiempoEntrega} días</td>
        </tr>
		 <tr>
            <th>Géneros</th>
            <td><c:forEach items="${articulo.generos}" var="genero">
				<span>${genero.nombre}</span>
		</c:forEach></td>
        </tr>
		
        </table>
		<spring:url value="/comentario/articulo/{articuloId}" var="comentarioUrl">
	   		<spring:param name="articuloId" value="${articulo.id}"/>
		</spring:url>

		<a href="${fn:escapeXml(comentarioUrl)}">
			<button class="btn btn-default" type="submit">Añadir un comentario</button>
		</a>
		
		<h2>Comentarios:</h2>	
		<c:if test="${articulo.comentarios.size() == 0}">
			<p>Sé el primero en comentar.</p>
		</c:if>
		<c:forEach items="${articulo.comentarios}" var="comentario">
			<div class="card">
				<div class="card-body">
					<c:out value="Valoración: ${comentario.valoracion}"></c:out>
					<c:out value="Autor: ${comentario.cliente.nombre} ${comentario.cliente.apellido}"></c:out>
					<c:out value="Opinión: ${comentario.descripcion}"></c:out>
				</div>
			</div>
		</c:forEach>
		<br>
        <h2>Productos relacionados:</h2>
        <c:forEach items="${relacionados}" var="relacionado">
			<spring:url value="/articulos/{articuloId}" var="articuloUrl">
		   		<spring:param name="articuloId" value="${relacionado.id}"/>
			</spring:url>
			<a href="${fn:escapeXml(articuloUrl)}"><img style='width: 20%; height: 10%' alt='' 
	        	onerror="this.src=''" src='${relacionado.urlImagen}'/><br><br>
	            	
	        <c:out value="${relacionado.marca} ${relacionado.modelo}"/></a><br>
		</c:forEach>
</dpc:layout>