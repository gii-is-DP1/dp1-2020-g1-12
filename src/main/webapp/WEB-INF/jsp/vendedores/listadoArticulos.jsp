<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="articulosEnVenta">
    
    <h2>Lista de artículos en venta</h2>

    <table id="articulosTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Artículo</th>
            <th style="width: 30px;">Stock</th>
            <th style="width: 30px;">Precio Base</th>
            <th style="width: 30px">Precio reducido</th>
            <th style="width: 50px">Porcentaje</th>
        </tr>
        </thead>
        <tbody>
      <c:forEach items="${articulos}" var="articulos">
            <tr>
                <td>
					<spring:url value="/vendedores/articulo/{articuloId}" var="articuloUrl">
						<spring:param name="articuloId" value="${articulos.id}"/>
					</spring:url>
					<a href="${fn:escapeXml(articuloUrl)}"><c:out value="${articulos.marca} ${articulos.modelo}"></c:out></a>
                </td>
                <td>
                    <c:out value="${articulos.stock}"/>
                </td>
                <td>
                    <c:out value="${articulos.precio}"/>
                </td>
				<td>
					<c:if test="${articulos.oferta.disponibilidad}" >
		                <span style="color: red; font-size: large"><fmt:formatNumber type="number" maxFractionDigits="2" 
		                    value="${articulos.precio * (1 - articulos.oferta.porcentaje/100)}"/> € </span>
	                </c:if>
	                <c:if test="${!articulos.oferta.disponibilidad}" >
		                <c:out value="${articulos.precio} €"/>
	               </c:if>
				</td>
				<td>	
					<c:if test="${articulos.oferta.disponibilidad}" >
                		<span style="color: white; background-color: #f35a5a; border-radius: 3px">
                			${articulos.oferta.porcentaje}%</span>
					</c:if>
	                <c:if test="${!articulos.oferta.disponibilidad}" >
		                <p>N/A</p>
	               </c:if>
				</td>
            </tr>
        </c:forEach> 
        </tbody>
    </table>
</dpc:layout>
