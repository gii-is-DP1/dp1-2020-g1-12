<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="articulos">
    
    <h2>Lista de artículos en venta</h2>

    <table id="articulosTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Artículo</th>
            <th style="width: 30px;">Precio</th>
            <th style="width: 30px">Oferta</th>
            <th style="width: 50px">Acción</th>
        </tr>
        </thead>
        <tbody>
      <c:forEach items="${articulos}" var="articulos">
            <tr>
                <td>
                    <c:out value="${articulos.marca} ${articulos.modelo}"/>
                </td>
                <td>
                    <c:out value="${articulos.precio}"/>
                </td>
                <c:choose>
                    <c:when test="${articulos.oferta.disponibilidad == true}">          
	                    <td>
	                    	<c:out value="${articulos.oferta.porcentaje}%"/>
	                	</td>
	                	<td>
					 		<spring:url value="/vendedores/ofertas/desofertar/{ofertaId}" var="ofertaArticuloUrl">
					              <spring:param name="ofertaId" value="${articulos.oferta.id}"/>
					        </spring:url>
							<a href="${fn:escapeXml(ofertaArticuloUrl)}">
								<button class="btn btn-default" type="submit">Eliminar Oferta</button>
							</a>
				        </td>
               		</c:when>
					<c:otherwise>
	                    <td>
	                    	<c:out value="N/A"/>
	                	</td>
	                	<td>
					 		<spring:url value="/vendedores/ofertas/{ofertaId}" var="ofertaArticuloUrl">
					              <spring:param name="ofertaId" value="${articulos.oferta.id}"/>
					        </spring:url>
							<a href="${fn:escapeXml(ofertaArticuloUrl)}">
								<button class="btn btn-default" type="submit">Crear oferta</button>
							</a>
				        </td>
	                </c:otherwise>               		
               	</c:choose>                      
            </tr>
        </c:forEach> 
        </tbody>
    </table>
</petclinic:layout>
