<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="solicitudes">

    <h2>Solicitud ${solicitud.id}</h2>

			<spring:url value="/solicitudes/solicitante/{solicitanteId}" var="solicitanteUrl">
               <spring:param name="solicitanteId" value="${solicitud.vendedor.id}"/>
           </spring:url>

    <table class="table table-striped">
        <tr>
            <th>Marca</th>
              <td>${solicitud.marca}</td>
        </tr>
         <tr>
            <th>Vendedor</th>
              <td><a href="${fn:escapeXml(solicitanteUrl)}"> ${solicitud.vendedor.nombre}</a></td>
        </tr>
        <tr>
            <th>Modelo</th>
            <td>${solicitud.modelo}</td>
        </tr>
          <tr>
            <th>Descripci�n</th>
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
            <th>Gasto de Env�o</th>
            <td>${solicitud.gastoEnvio}</td>
        </tr>
        <tr>
        <th>
		   <spring:url value="/solicitudes/{solicitudId}/denegar" var="denegarUrl">
               <spring:param name="solicitudId" value="${solicitud.id}"/>
           </spring:url>
           
 		<form:form modelAttribute="solicitud" action="/solicitudes/${solicitudId}/denegar" class="form-horizontal" id="add-owner-form">
			<petclinic:inputField label="Respuesta" name="respuesta"/>
			</th>
			<td>
			<button class="btn btn-default" type="submit">Denegar</button>
			</td>	
   		</form:form>    	
   		
   		</table>
   		
           <spring:url value="/solicitudes/{solicitudId}/aceptar" var="aceptarUrl">
               <spring:param name="solicitudId" value="${solicitud.id}"/>
           </spring:url>      
           
 
       
		<a href="${fn:escapeXml(aceptarUrl)}"><button class="btn btn-default" type="submit">Aceptar</button></a>

</petclinic:layout>
