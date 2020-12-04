<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="solicitudes">

    <h2>Solicitud de <c:out value="${solicitud.marca} ${solicitud.modelo}"></c:out></h2>

    <table class="table table-striped">
         <tr>
			<img style='width: 20%; height: 10%' alt='' onerror="this.src=''" src='${solicitud.urlImagen}'/>        </tr>
        <tr>
        	<th>Descripción</th>
            <td>${solicitud.descripcion}</td>
        </tr>
		<tr>
            <th>Precio</th>
            <td><c:out value="${solicitud.precio} €"/></td>
        </tr>
          <tr>
            <th>Stock</th>
            <td>${solicitud.stock}</td>
        </tr>
        <tr>
            <th>Tipo</th>
            <td>${solicitud.tipo}</td>
        </tr>
        <tr>
            <th>Gastos de envío</th>
            <td>${solicitud.gastoEnvio}</td>
        </tr>
        <tr>
            <th>Tiempo de Entrega</th>
            <td>${solicitud.tiempoEntrega}</td>
        </tr>
        <tr>
            <th>Situación</th>
            <td>${solicitud.situacion}</td>
		</tr>
        <c:if test="${solicitud.situacion == 'Denegada'}" >
	        <tr>
	            <th>Respuesta</th>
	            <td>${solicitud.respuesta}</td>
			</tr>
		</c:if>	
	</table>
		<a href="/vendedores/listadoSolicitudes"><button class="btn btn-default" type="submit">Volver</button></a>
</dpc:layout>
