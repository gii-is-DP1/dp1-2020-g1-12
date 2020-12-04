<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="articulosEnVenta">

    <h2>Artículo <c:out value="${articulo.marca} ${articulo.modelo}"></c:out></h2>

    <table class="table table-striped">
         <tr>
			<img style='width: 20%; height: 10%' alt='' onerror="this.src=''" src='${articulo.urlImagen}'/>        </tr>
        <tr>
            <th>Precio</th>
            <td>
			<c:if test="${articulo.oferta.disponibilidad}" >
                <span style="color: red; font-size: large"><fmt:formatNumber type="number" maxFractionDigits="2" 
                    value="${articulo.precio * (1 - articulo.oferta.porcentaje/100)}"/> € </span>
                <span style="font-size: small; padding: 0px 6px 0px 6px"><strike>${articulo.precio} € </strike></span>
                <span style="color: white; background-color: #f35a5a; border-radius: 3px">${articulo.oferta.porcentaje}%</span>

                </c:if>
                <c:if test="${!articulo.oferta.disponibilidad}" >
	                <c:out value="${articulo.precio} €"/>
               </c:if>
              </td>
            <td></td>
        </tr>
          <tr>
            <th>Stock</th>
            <td>${articulo.stock}</td>
            <td></td>
        </tr>
        <tr>
            <th>Tipo</th>
            <td>${articulo.tipo}</td>
            <td></td>
        </tr>
        <tr>
            <th>Gastos de envío</th>
            <td>${articulo.gastoEnvio}</td>
            <td></td>
        </tr>
        <tr>
            <th>Tiempo de Entrega</th>
            <td>${articulo.tiempoEntrega}</td>
            <td></td>
        </tr>
        <tr>
            <th>Oferta</th>
			<c:choose>
                    <c:when test="${articulo.oferta.disponibilidad == true}">          
	                    <td>
	                    	<c:out value="${articulo.oferta.porcentaje}%"/>
	                	</td>
	                	<td>
					 		<spring:url value="/vendedores/ofertas/desofertar/{ofertaId}/articulo/{articuloId}" 
					 			var="ofertaArticuloUrl">
					              <spring:param name="ofertaId" value="${articulo.oferta.id}"/>
					              <spring:param name="articuloId" value="${articulo.id}"/>
					        </spring:url>
							<a href="${fn:escapeXml(ofertaArticuloUrl)}">
								<button class="btn btn-default" type="submit">Eliminar</button>
							</a>
				        </td>
               		</c:when>
					<c:otherwise>
	                    <td>
	                    	<c:out value="N/A"/>
	                	</td>
	                	<td>
					 		<spring:url value="/vendedores/ofertas/{ofertaId}/articulo/{articuloId}" var="ofertaArticuloUrl">
					              <spring:param name="ofertaId" value="${articulo.oferta.id}"/>
					              <spring:param name="articuloId" value="${articulo.id}"/>
					        </spring:url>
							<a href="${fn:escapeXml(ofertaArticuloUrl)}">
								<button class="btn btn-default" type="submit">Crear</button>
							</a>
				        </td>
	                </c:otherwise>               		
               	</c:choose>                     
        </tr>
	</table>
		<br><br><a href="/vendedores/articulosEnVenta"><button class="btn btn-default" type="submit">Volver</button></a>
</dpc:layout>
