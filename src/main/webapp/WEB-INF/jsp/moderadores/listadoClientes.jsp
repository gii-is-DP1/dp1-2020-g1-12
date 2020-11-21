<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="clientes">
    <h2>Listado de clientes</h2>

    <table id="clientesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 200px;">Apellidos</th>
            <th>Dni</th>
            <th style="width: 120px">Teléfono</th>
            <th>Dirección</th>
            <th style="width: 120px">Email</th>
            <th style="width: 120px">Bloquear Actividad</th>
        </tr>
        </thead>
        <tbody>
      <c:forEach items="${clientes}" var="clientes">
            <tr>
  
                <td>
                    <c:out value="${clientes.nombre}"/>
                </td>
                <td>
                    <c:out value="${clientes.apellido}"/>
                </td>
                <td>
                    <c:out value="${clientes.dni}"/>
                </td>
                <td>
                    <c:out value="${clientes.telefono}"/>
                </td>
                <td>
                    <c:out value="${clientes.direccion}"/>
                </td>
                <td>
                    <c:out value="${clientes.email}"/>
                </td>                              
                <c:choose>
                    <c:when test="${clientes.bloqueo.bloqueado == false}">
				 		<spring:url value="/bloqueos/{bloqueoId}" var="bloqueoClienteUrl">
				              <spring:param name="bloqueoId" value="${clientes.bloqueo.id}"/></spring:url>
		
				        <td>
							<a href="${fn:escapeXml(bloqueoClienteUrl)}">
								<button class="btn btn-default" type="submit">Bloquear</button>
							</a>
						</td>
		            </c:when>
					<c:otherwise>
				 		<spring:url value="/bloqueos/desbloquear/{bloqueoId}" var="bloqueoClienteUrl">
				              <spring:param name="bloqueoId" value="${clientes.bloqueo.id}"/></spring:url>
		
				        <td>
							<a href="${fn:escapeXml(bloqueoClienteUrl)}">
								<button class="btn btn-default" type="submit">Desbloquear</button>
							</a>
						</td>
					</c:otherwise>
                </c:choose>
            </tr>
        </c:forEach> 
           
        </tbody>
    </table>
    
    <h2>Listado de vendedores</h2>

    <table id="vendedoresTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 200px;">Apellidos</th>
            <th>Dni</th>
            <th style="width: 120px">Teléfono</th>
            <th>Dirección</th>
            <th style="width: 120px">Email</th>
            <th style="width: 120px">Bloquear Actividad</th>
        </tr>
        </thead>
        <tbody>
      <c:forEach items="${vendedores}" var="vendedores">
            <tr>
  
                <td>
                    <c:out value="${vendedores.nombre}"/>
                </td>
                <td>
                    <c:out value="${vendedores.apellido}"/>
                </td>
                <td>
                    <c:out value="${vendedores.dni}"/>
                </td>
                <td>
                    <c:out value="${vendedores.telefono}"/>
                </td>
                <td>
                    <c:out value="${vendedores.direccion}"/>
                </td>
                <td>
                    <c:out value="${vendedores.email}"/>
                </td>                                                
                 
                <c:choose>
                    <c:when test="${vendedores.bloqueo.bloqueado == false}">
		 				<spring:url value="/bloqueos/{bloqueoId}" var="bloqueoVendedorUrl">
		              		<spring:param name="bloqueoId" value="${vendedores.bloqueo.id}"/>
		           		</spring:url>
		        		
		        		<td>
						<a href="${fn:escapeXml(bloqueoVendedorUrl)}"><button class="btn btn-default" type="submit">Bloquear</button></a>
		            	</td>
		            </c:when>
                    <c:otherwise>
		 				<spring:url value="/bloqueos/desbloquear/{bloqueoId}" var="bloqueoVendedorUrl">
		              		<spring:param name="bloqueoId" value="${vendedores.bloqueo.id}"/>
		           		</spring:url>
		        		
		        		<td>
						<a href="${fn:escapeXml(bloqueoVendedorUrl)}"><button class="btn btn-default" type="submit">Desbloquear</button></a>
		            	</td>
		            </c:otherwise>
                </c:choose>
                                 

            </tr>
        </c:forEach> 
           
        </tbody>
    </table>
</petclinic:layout>
