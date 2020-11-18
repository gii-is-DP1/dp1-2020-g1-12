<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="solicitudes">

    <h2>Solicitud ${solicitud.id}</h2>

    <table class="table table-striped">
        <tr>
            <th>Marca</th>
              <td>${solicitud.marca}</td>
        </tr>
        <tr>
            <th>Modelo</th>
            <td>${solicitud.modelo}</td>
        </tr>
          <tr>
            <th>Descripción</th>
            <td>${solicitud.descripcion}</td>
        </tr>
        <tr>
            <th>UrlImagen</th>
            <td>${solicitud.urlImagen}</td>
        </tr>
        <tr>
            <th>Precio</th>
            <td>${solicitud.precio}</td>
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
            <th>Tiempo de Entrega</th>
            <td>${solicitud.tiempoEntrega}</td>
        </tr>
        <tr>
            <th>Gasto de Envío</th>
            <td>${solicitud.gastoEnvio}</td>
        </tr>
        <tr>
            <th>Situación</th>
            <td>${solicitud.situacion}</td>
        </tr>
		<tr>
            <th>Respuesta</th>
            <td>${solicitud.respuesta}</td>
        </tr>
   		</table>
           <spring:url value="/solicitudes/{solicitudId}/aceptar" var="aceptarUrl">
               <spring:param name="solicitudId" value="${solicitud.id}"/>
           </spring:url>      
            
    
            <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				<a href="${fn:escapeXml(aceptarUrl)}"><button class="btn btn-default" type="submit">Aceptar</button></a>
            </div>
        </div>

    

</petclinic:layout>
