<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>

<dpc:layout pageName="solicitante">
	 <h2>Perfil de <c:out value="${vendedor.nombre} ${vendedor.apellido}"></c:out> </h2>

    <table class="table table-striped">
    
    	<tr>
            <th>DNI</th>
            <td>${vendedor.dni}</td>
        </tr>
        <tr>
            <th>Dirección</th>
            <td>${vendedor.direccion}</td>
        </tr>
        
        <tr>
            <th>Teléfono</th>
            <td>${vendedor.telefono}</td>
        </tr>
        <tr>
            <th>Email</th>
            <td>${vendedor.email}</td>
        </tr>
    
    </table>
    
    <spring:url value="/solicitudes/{solicitudId}" var="solicitudUrl">
		<spring:param name="solicitudId" value="${solicitudId}"/>
	</spring:url>
	           
	<a href="${fn:escapeXml(solicitudUrl)}"><button class="btn btn-default" type="submit">Volver</button></a>
    
</dpc:layout>