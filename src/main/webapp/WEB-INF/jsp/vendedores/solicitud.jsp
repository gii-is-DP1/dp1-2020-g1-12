<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="solicitudes">
    <jsp:attribute name="customScript">
        <script>
	        function alerta() {
	        	var opcion = confirm('¿Seguro que desea eliminar la solicitud?');
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
	    <h2>Solicitud de <c:out value="${solicitud.marca} ${solicitud.modelo}"></c:out></h2>
	
		<div style="display:flex;">
		    <div style="width:50%; height:100%">
			    <img style='width: 70%; height: 100%' alt='' onerror="this.src=''" src='${solicitud.urlImagen}'/>
			</div>
			<div style="width:50%;">
				<p id="noDesplegar"align="justify">${solicitud.descripcion.substring(0, solicitud.descripcion.length()/2)}...
					<br><br>
				</p>
				
				<a onclick="vermas('mas');" id="mas">Leer más</a>
				<p id="desplegar" style="display: none;" align="justify">${solicitud.descripcion}
					<br><br>
					<a onclick="vermas('menos');" id="menos">Leer menos</a>
				</p>
			</div>
		</div>
		<br>
	    <table class="table table-striped">
			<tr>
	            <th>Precio</th>
	            <td><fmt:formatNumber type="number" maxFractionDigits="2" 
	                    value="${solicitud.precio}"/> €</td>
	        </tr>
	          <tr>
	            <th>Stock</th>
	            <td><c:out value="${solicitud.stock} unidades"></c:out></td>
	        </tr>
	        <tr>
	            <th>Tipo</th>
	            <td><c:out value="${solicitud.tipo}"></c:out></td>
	        </tr>
	        <tr>
	            <th>Gastos de envío</th>
	            <td><fmt:formatNumber type="number" maxFractionDigits="2" 
	                    value="${solicitud.gastoEnvio}"/> €</td>
	        </tr>
	        <tr>
	            <th>Tiempo de Entrega</th>
	            <td><c:out value="${solicitud.tiempoEntrega} días"></c:out></td>
	        </tr>
	        <tr>
	            <th>Situación</th>
	            <td><c:out value="${solicitud.situacion}"></c:out></td>
			</tr>
	        <c:if test="${solicitud.situacion == 'Denegada'}" >
		        <tr>
		            <th>Respuesta</th>
		            <td><c:out value="${solicitud.respuesta}"></c:out></td>
				</tr>
			</c:if>	
		</table>
		<c:if test="${solicitud.situacion == 'Pendiente'}" >
			<spring:url value="/vendedores/eliminarSolicitud/{solicitudId}" var="eliminarSolicitudUrl">
				<spring:param name="solicitudId" value="${solicitud.id}"/>
			</spring:url>
				
			<a href="${fn:escapeXml(eliminarSolicitudUrl)}">
				<button onclick="return alerta()" class="btn btn-default" type="submit">Descartar solicitud</button>
			</a>
		</c:if>
		<a href="/vendedores/listadoSolicitudes"><button class="btn btn-default" type="submit">Volver</button></a>
	</jsp:body>
</dpc:layout>
