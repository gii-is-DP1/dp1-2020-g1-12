<%@ page session="true" trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<dpc:layout pageName="articulos">
    <jsp:attribute name="customScript">
    	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/css/bootstrap-select.css" />
		<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/js/bootstrap-select.min.js"></script>
        <script>
	        function alerta() {
	        	var opcion = confirm('¿Seguro que desea eliminar el comentario del artículo?');
	        	return opcion;
	        }
	        
	        function editar() {
	        	var editar = confirm('¿Seguro que desea editar el comentario del artículo?');
	        	return editar;
	        }
        </script>
        <script>
			function vermas(id){
				if(id=="mas"){
					document.getElementById("desplegar").style.display="block";   
					document.getElementById("mas").style.display="none"; 
					document.getElementById("noDesplegar").style.display="none"; 					
				}
				else{
					document.getElementById("desplegar").style.display="none";
					document.getElementById("mas").style.display="inline";
					document.getElementById("noDesplegar").style.display="block";   
				}
			}
        </script>
    </jsp:attribute>
    
    <jsp:body>
    <form:form modelAttribute="query" action="/busqueda" class="form-horizontal" >
        <div class="form-group has-feedback">
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

    </form:form>
    <h1>${articulo.marca} ${' '} ${articulo.modelo}</h1>
    
    <div style="display:flex;">
	    <div style="width:50%; height:100%">
		    <img style='width: 70%; height: 100%' alt='' onerror="this.src=''" src='${articulo.urlImagen}'/>
		</div>
		<div style="width:50%;">
			<p id="noDesplegar" align="justify">${articulo.descripcion.substring(0, articulo.descripcion.length()/2)}...
				<br><br>
			</p>
			
			<a onclick="vermas('mas');" id="mas">Leer más</a>
			<p id="desplegar" style="display: none;" align="justify">${articulo.descripcion}
				<br><br>
				<a onclick="vermas('menos');" id="menos">Leer menos</a>
			</p>
		</div>
	</div>
	<sec:authorize access="hasAuthority('cliente')">
		<c:if test="${articulo.stock > 0 && puedeComprar}" >	
			<spring:url value="/cesta/anyadirArticulo/{articuloId}" var="añadirArticuloUrl">
		   		<spring:param name="articuloId" value="${articulo.id}"/>
			</spring:url>
			<br>
			<a href="${fn:escapeXml(añadirArticuloUrl)}">
				<button style="width:22%;float:right" class="btn btn-primary btn-lg btn-block" type="submit">Añadir al carrito</button>
			</a>
		</c:if>
	</sec:authorize>
	<br>
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
            <td>${articulo.stock} unidades</td>
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
            <th>Valoración media</th>
            <td>
            	<c:if test="${valoracion == 0}">
            		Sin valoraciones
            	</c:if>
            	<c:if test="${valoracion != 0}">
            	<fmt:formatNumber  type="number" maxFractionDigits="1" 
					value="${valoracion}"/> ★
            	</c:if>
            </td>
        </tr>
		 <tr>
            <th>Géneros</th>
            <td><c:forEach items="${articulo.generos}" var="genero">
				<span class="badge badge-pill badge-success">${genero.nombre}</span></c:forEach></td>
        </tr>
		
        </table>
		
		<c:if test="${puedeComentar}">
			<spring:url value="/comentario/articulo/{articuloId}" var="comentarioUrl">
		   		<spring:param name="articuloId" value="${articulo.id}"/>
			</spring:url>
	
			<a href="${fn:escapeXml(comentarioUrl)}">
				<button class="btn btn-default" type="submit">Añadir un comentario</button>
			</a>
			<br><br>
		</c:if>
		<h2>Comentarios:</h2>	
		<c:if test="${articulo.comentarios.size() == 0}">
			<p>Sé el primero en comentar.</p>
		</c:if>
		<c:forEach items="${comentarios}" var="comentario">
			<fieldset>
				<c:if test="${comentario.cliente != null}">
					<legend><span class="badge badge-pill badge-success">Cliente</span>
						<c:out value=" ${comentario.cliente.nombre} ${comentario.cliente.apellido}		"></c:out>
						<sec:authorize access="hasAuthority('moderador')">
							<spring:url value="/comentario/eliminar/{comentarioId}/articulo/{articuloId}" 
							var="eliminarComentarioUrl">
								<spring:param name="comentarioId" value="${comentario.id}"/>
								<spring:param name="articuloId" value="${articulo.id}"/>
							</spring:url>
							<a onclick="return alerta()" class="glyphicon glyphicon-remove-circle" 
							style="color: #F03232; text-decoration: none" href="${fn:escapeXml(eliminarComentarioUrl)}">
							</a>
						</sec:authorize>
						<c:if test="${puedeEditarCliente==comentario.cliente.id}">
						
							<spring:url value="/comentario/editar/{comentarioId}/articulo/{articuloId}" var="comentarioEditarCliente">
								<spring:param name="comentarioId" value="${comentario.id}"/>
		   						<spring:param name="articuloId" value="${articulo.id}"/>
							</spring:url>
							<a onclick="return editar()" class="glyphicon glyphicon-pencil" 
							style="color: #000000; text-decoration: none" href="${fn:escapeXml(comentarioEditarCliente)}">
							</a>
						</c:if>
					</legend>	
					<p><c:out value="Valoración: "></c:out>
						<c:forEach begin="1" end="${comentario.valoracion}">
							<c:out value="★"></c:out>
						</c:forEach>
					</p>
				<p><c:out value="Opinión: ${comentario.descripcion}"></c:out></p>
				</c:if>
				<c:if test="${comentario.moderador != null}">
					<legend><span class="badge badge-pill badge-danger">Administrador</span>		
						<c:out value=" ${comentario.moderador.nombre} ${comentario.moderador.apellido}		"></c:out>
						<sec:authorize access="hasAuthority('moderador')">
							<spring:url value="/comentario/eliminar/{comentarioId}/articulo/{articuloId}" 
							var="eliminarComentarioUrl">
								<spring:param name="comentarioId" value="${comentario.id}"/>
								<spring:param name="articuloId" value="${articulo.id}"/>
							</spring:url>
							<a onclick="return alerta()" class="glyphicon glyphicon-remove-circle" 
							style="color: #F03232; text-decoration: none" href="${fn:escapeXml(eliminarComentarioUrl)}">
							</a>
						</sec:authorize>
					</legend>	
				<p><c:out value="Comentario: ${comentario.descripcion}"></c:out></p>
				</c:if>
				<c:if test="${comentario.vendedor != null}">
					<legend><span class="badge badge-pill badge-info">Vendedor</span>
						<c:out value=" ${comentario.vendedor.nombre} ${comentario.vendedor.apellido}		"></c:out>
						<sec:authorize access="hasAuthority('moderador')">
							<spring:url value="/comentario/eliminar/{comentarioId}/articulo/{articuloId}" 
							var="eliminarComentarioUrl">
								<spring:param name="comentarioId" value="${comentario.id}"/>
								<spring:param name="articuloId" value="${articulo.id}"/>
							</spring:url>
							<a onclick="return alerta()" class="glyphicon glyphicon-remove-circle" 
							style="color: #F03232; text-decoration: none" href="${fn:escapeXml(eliminarComentarioUrl)}">
							</a>
						</sec:authorize>
						<c:if test="${puedeEditarVendedor==comentario.vendedor.id}">
						
							<spring:url value="/comentario/editar/{comentarioId}/articulo/{articuloId}" var="comentarioEditarVendedor">
								<spring:param name="comentarioId" value="${comentario.id}"/>
		   						<spring:param name="articuloId" value="${articulo.id}"/>
							</spring:url>
							<a onclick="return editar()" class="glyphicon glyphicon-pencil" 
							style="color: #000000; text-decoration: none" href="${fn:escapeXml(comentarioEditarVendedor)}">
							</a>
						</c:if>
					</legend>	
					<p><c:out value="Respuesta: ${comentario.descripcion}"></c:out></p>
				</c:if>
			</fieldset>
			<br>
		</c:forEach>
		<br>
		<c:if test="${relacionados.size() != 0 }">
        <hr width=900></hr>
        <br>

        <h2>Productos relacionados:</h2>
        <fieldset>
	        <c:forEach items="${relacionados}" var="relacionado">
				<div style="display: inline-table;width:17.5%;margin-left: 2%;overflow: hidden;">
					<spring:url value="/articulos/{articuloId}" var="articuloUrl">
				   		<spring:param name="articuloId" value="${relacionado.id}"/>
					</spring:url>
					<a href="${fn:escapeXml(articuloUrl)}"><img style='width: 100%; height: 10%' alt='' 
			        	onerror="this.src=''" src='${relacionado.urlImagen}'/><br><br>
			            	
			            <c:out value="${relacionado.marca} ${relacionado.modelo}"/></a><br>
			            <c:if test="${relacionado.oferta.disponibilidad}" >
		                <span style="color: red; font-size: large"><fmt:formatNumber type="number" maxFractionDigits="2" 
		                    value="${relacionado.precio * (1 - relacionado.oferta.porcentaje/100)}"/> € </span>
		                <span style="font-size: small; padding: 0px 6px 0px 6px"><strike>${relacionado.precio} € </strike></span>
		                <span style="color: white; background-color: #f35a5a; border-radius: 3px">${relacionado.oferta.porcentaje}%</span>
		
		                </c:if>
		                <c:if test="${!relacionado.oferta.disponibilidad}" >
		                    <c:out value="${relacionado.precio} €"/>
		               </c:if>
		               <br>
	               </div>
			</c:forEach>
		</fieldset>
		</c:if>
	</jsp:body>
</dpc:layout>