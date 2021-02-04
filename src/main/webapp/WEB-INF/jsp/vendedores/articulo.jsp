<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="articulosEnVenta">
    <jsp:attribute name="customScript">
        <script>
	        function alerta() {
	        	var opcion = confirm('¿Seguro que desea dar de baja el artículo?');
	        	return opcion;
	        }
        </script>
        <script>
	        function alertaGenero() {
	        	var opcion = confirm('¿Seguro que desea eliminar el género?');
	        	return opcion;
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
	    <table class="table table-striped">
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
	            <td></td>
	        </tr>
	          <tr>
	            <th>Stock</th>
	            <td>${articulo.stock} unidades</td>
	            <td></td>
	        </tr>
	        <tr>
	            <th>Tipo</th>
	            <td>${articulo.tipo}</td>
	            <td></td>
	        </tr>
	        <tr>
	            <th>Gastos de envío</th>
	            <td>${articulo.gastoEnvio} €</td>
	            <td></td>
	        </tr>
	        <tr>
	            <th>Tiempo de Entrega</th>
	            <td>${articulo.tiempoEntrega} días</td>
	            <td></td>
	        </tr>
	        <tr>
	            <th>Oferta</th>
				<c:choose>
	                    <c:when test="${articulo.oferta.disponibilidad == true}">          
		                    <td>
		                    	<c:out value="${articulo.oferta.porcentaje}%"/>
		                	</td>
		                	<td>
		                		<form:form modelAttribute="articulo.oferta" action="/vendedores/ofertas/desofertar/${articulo.oferta.id}/articulo/${articulo.id}" class="form-horizontal" id="add-owner-form">
									<input type="hidden" name="version" value="${articulo.oferta.version}"/> 
									<button class="btn btn-default" type="submit">Eliminar</button>
	   							</form:form>
			<%-- 			 		<spring:url value="/vendedores/ofertas/desofertar/{ofertaId}/articulo/{articuloId}" 
						 			var="ofertaArticuloUrl">
						              <spring:param name="ofertaId" value="${articulo.oferta.id}"/>
						              <spring:param name="articuloId" value="${articulo.id}"/>
						        </spring:url>
								<a href="${fn:escapeXml(ofertaArticuloUrl)}">
									<button class="btn btn-default" type="submit">Eliminar</button>
								</a> --%>
					        </td>
	               		</c:when>
						<c:otherwise>
		                    <td>
		                    	<c:out value="N/A"/>
		                	</td>
		                	<td>
						 		<spring:url value="/vendedores/ofertas/{ofertaId}/articulo/{articuloId}" var="ofertaArticuloUrl">
						              <spring:param name="ofertaId" value="${articulo.oferta.id}"/>
						              <spring:param name="articuloId" value="${articulo.id}"/>
						        </spring:url>
								<a href="${fn:escapeXml(ofertaArticuloUrl)}">
									<button class="btn btn-default" type="submit">Crear</button>
								</a>
					        </td>
		                </c:otherwise>               		
	               	</c:choose>           	                  
	        </tr>
	       
	        <tr>
	            <th>Géneros</th>
	            <td>
		            <c:forEach items="${articulo.generos}" var="genero">
		            <spring:url value="/generos/{articuloId}/{generoId}/remove" var="eliminarGeneroUrl">
						<spring:param name="articuloId" value="${articulo.id}"/>
						<spring:param name="generoId" value="${genero.id}"/>
					</spring:url>
					<span class="badge badge-pill badge-success">${genero.nombre} ${' '}
						<a onclick="return alertaGenero()" class="glyphicon glyphicon-remove-circle" 
							style="color: white; text-decoration: none" href="${fn:escapeXml(eliminarGeneroUrl)}">
						</a>
					</span>
					</c:forEach>
				</td>
				<td>
					<spring:url value="/generos/{articuloId}" var="generoUrl">
						<spring:param name="articuloId" value="${articulo.id}"/>
			        </spring:url>
			        <a href="${fn:escapeXml(generoUrl)}">
						<button class="btn btn-default" type="submit">Añadir</button>
					</a>
				</td>
	        </tr>
		</table>
		<c:if test="${articulo.stock != 0}" >
			<spring:url value="/vendedores/eliminarArticulo/{articuloId}" var="eliminarArticuloUrl">
				<spring:param name="articuloId" value="${articulo.id}"/>
			</spring:url>
			
			<a href="${fn:escapeXml(eliminarArticuloUrl)}">
				<button onclick="return alerta()" class="btn btn-default" type="submit">Dar de baja</button>
			</a>
		</c:if>
		<a href="/vendedores/articulosEnVenta"><button class="btn btn-default" type="submit">Volver</button></a>
    </jsp:body>
</dpc:layout>
