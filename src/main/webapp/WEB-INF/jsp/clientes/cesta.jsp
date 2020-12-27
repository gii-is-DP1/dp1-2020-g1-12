<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="cesta">

	<jsp:attribute name="customScript">
        <script>
            function alerta() {
                var opcion = confirm('¿Seguro que desea eliminar el artículo de la cesta?');
                return opcion;
            }
        </script>
    </jsp:attribute>

    <jsp:body>
    
	    <h2>Mi cesta</h2>
		<form:form modelAttribute="cesta" action="/cesta/actualizar" class="form-horizontal" >	
		    <table id="articulosTable" class="table table-striped">
		        <thead>
		        <tr>
		            <th style="width: 150px;">Producto</th>
		            <th style="width: 30px;">Cantidad</th>
		            <th style="width: 30px;">Precio unidad</th>
		            <th style="width: 50px">Acción</th>
		        </tr>
		        </thead>
		        <tbody>
		      <c:forEach items="${cesta.lineas}" var="linea" varStatus="status">
		            <tr>
		                <td>
							<spring:url value="/articulos/{articuloId}" var="articuloUrl">
								<spring:param name="articuloId" value="${linea.articulo.id}"/>
							</spring:url>
							<a href="${fn:escapeXml(articuloUrl)}"><c:out value="${linea.articulo.marca} ${linea.articulo.modelo}"></c:out></a>
		                </td>
		                <td>
		                    <form:input path="lineas[${status.index}].cantidad" type="number" min="1" max= "${linea.articulo.stock}" value="${linea.cantidad}"/>
		                    <form:input path="lineas[${status.index}].id" type="hidden" value="${linea.id}"/>
		                    <form:input path="lineas[${status.index}].articulo.id" type="hidden" value="${linea.articulo.id}"/>
		                </td>
						<td>
							<c:if test="${linea.articulo.oferta.disponibilidad}" >
				                <span style="color: red; "><fmt:formatNumber type="number" maxFractionDigits="2" 
				                    value="${linea.articulo.precio * (1 - linea.articulo.oferta.porcentaje/100)}"/> € </span>
				                <span style="color: white; background-color: #f35a5a; border-radius: 3px">${linea.articulo.oferta.porcentaje}%</span>
			                </c:if>
			                <c:if test="${!linea.articulo.oferta.disponibilidad}" >
				                <c:out value="${linea.articulo.precio} €"/>
			               </c:if>
						</td>
						<td>
						<spring:url value="/cesta/eliminar/{lineaId}" var="eliminarLineaCestaUrl">
		                	<spring:param name="lineaId" value="${linea.id}"/>
		                </spring:url>
		                <a onclick="return alerta()" class="glyphicon glyphicon-remove-circle" 
		                	style="color: #F03232; text-decoration: none" href="${fn:escapeXml(eliminarLineaCestaUrl)}">
		                 </a>
						
						</td>
		            </tr>
		        </c:forEach>
	        <tr>
	        	<td></td>
	        	<td>Importe total:</td>
	        	<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${cesta.precioFinal}"/> €</td>
	        	<td></td>
	        </tr> 
	        </tbody>
	    </table>
		<div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Actualizar</button>
            </div>
        </div>
	</form:form>
    </jsp:body>
</dpc:layout>
