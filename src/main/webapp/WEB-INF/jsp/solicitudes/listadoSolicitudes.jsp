<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="solicitud">
    <h2>Listado de solicitudes</h2>
    <table id="solicitudesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Descripción</th>
            <th style="width: 200px;">Modelo</th>
            <th>Marca</th>
  
        </tr>
        </thead>
        <tbody>
      <c:forEach items="${solicitudes}" var="solicitudes">
            <tr>

                <td>
                    <c:out value="${solicitudes.descripcion}"/>
                </td>
                <td>
                    <c:out value="${solicitudes.modelo}"/>
                </td>
                <td>
                    <c:out value="${solicitudes.marca}"/>
                </td>

                
 				<spring:url value="/solicitudes/{solicitudId}" var="solicitudUrl">
              		<spring:param name="solicitudId" value="${solicitudes.id}"/>
           		</spring:url>
        		
        		<td>
				<a href="${fn:escapeXml(solicitudUrl)}"><button class="btn btn-default" type="submit">Acceder</button></a>
            	</td>
            </tr>
        </c:forEach> 
        </tbody>
    </table>
</petclinic:layout>