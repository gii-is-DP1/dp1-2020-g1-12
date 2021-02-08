<%@ page session="true" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<dpc:layout pageName="clientes">

    <h2>Mi perfil</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
              <td>${cliente.nombre}</td>
        </tr>
        <tr>
            <th>Apellido</th>
            <td>${cliente.apellido}</td>
        </tr>
          <tr>
            <th>Dirección</th>
            <td>${cliente.direccion}</td>
        </tr>
        <tr>
            <th>DNI</th>
            <td>${cliente.dni}</td>
        </tr>
        <tr>
            <th>Teléfono</th>
            <td>${cliente.telefono}</td>
        </tr>
        <tr>
            <th>Email</th>
            <td>${cliente.email}</td>
        </tr>
              
    </table>

	<a href="/clientes/editar"><button class="btn btn-default" type="submit">Editar</button></a>
    
    <h2><br>Tarjetas de crédito</h2>
    
    <c:if test="${cliente.tarjetas.size() == 0}">
    	<p>No tienes ninguna tarjeta asociada a esta cuenta</p>
    </c:if>
    
    <c:forEach items="${cliente.tarjetas}" var="tarjeta">
    	<table class="table table-bordered">
        <tr>
        	<c:choose>
        		<c:when test = "${tarjeta.titular== 'Tarjeta eliminada' && cliente.tarjetas.size() == 0}">
        			<p>No tienes ninguna tarjeta asociada a esta cuenta</p>
        		</c:when>
        		<c:when test = "${tarjeta.titular== 'Tarjeta eliminada'}"/>
        		<c:otherwise>
					<spring:url value="/tarjetas/{tarjetaId}/delete" var="tarjetaUrl">
	      	    	  <spring:param name="tarjetaId" value="${tarjeta.id}"/>
	     	  		</spring:url> 
	      	 		 <a href="${fn:escapeXml(tarjetaUrl)}"><button class="btn btn-default" type="submit">Eliminar</button></a>       	
          	 	 		<th>Titular</th>
           		 		<td>${tarjeta.titular}</td>
        </tr>
        		<tr>
        	 	   <th>Numero de tarjeta</th>
         	 	   <td>✱✱✱✱✱✱✱✱✱✱✱✱ ${tarjeta.numero.substring(12)}</td>
      	  		</tr>
      	  		<tr>
        	 	   <th>Fecha de caducidad</th>
        	 	   <td>${tarjeta.fechaCaducidad}</td>
       	   		</tr>
       	    	</c:otherwise>
       	    </c:choose>
    	</table>
    </c:forEach>
            
	
	<a href="/tarjetas/new"><button class="btn btn-default" type="submit">Añadir</button></a>
	
</dpc:layout>
