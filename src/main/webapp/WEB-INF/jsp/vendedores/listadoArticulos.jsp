<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="articulosEnVenta">
    
	<form action="/vendedores/articulosEnVenta?" method="get">
		<div style="float:right">
			<input type="hidden" name="page" value="${articulos.getNumber()}"/>
			<input type="hidden" name="size" value="${articulos.getSize()}"/>			
	 		<select onchange="this.form.submit();" name="orderBy">
	       		<option value="" disabled selected>Ordenar por:</option>
	 			<option value="-id">Más recientes</option>
	 			<option value="id">Más antiguos</option>
	 			<option value="marca">Nombre A-Z</option>
	 			<option value="-marca">Nombre Z-A</option>
	 			<option value="precio">Precio base de más bajo a más alto</option>
	 			<option value="-precio">Precio base de más alto a más bajo</option>
	 		</select>
		</div>
	</form>
    <h2>Lista de artículos en venta</h2>

    <c:if test="${articulos.getContent().size() == 0}">Aún tienes ningún artículo en venta.</c:if>
    <c:if test="${articulos.getContent().size() != 0}">
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
      <c:forEach items="${articulos.getContent()}" var="articulos">
            <tr>
                <td>
					<spring:url value="/vendedores/articulo/{articuloId}" var="articuloUrl">
						<spring:param name="articuloId" value="${articulos.id}"/>
					</spring:url>
					<a href="${fn:escapeXml(articuloUrl)}"><c:out value="${articulos.marca} ${articulos.modelo}"></c:out></a>
                </td>
                <td>
                    <c:out value="${articulos.stock} unidades"/>
                </td>
                <td>
                    <c:out value="${articulos.precio} €"/>
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
	<div class="container">
		<div class="row text-center">
			<form action="/vendedores/articulosEnVenta?" method="get">
				<input type="hidden" name="page" value="${articulos.getNumber()}"/>
				<input type="hidden" name="orderBy" value="${ordenacion}"/>
				<label for="size">Elementos por página: </label>
				<input onchange="this.form.submit();" style="border-radius: 5px; width:10%; text-align: center"
				 type="number" min="2" name="size" value="${articulos.getSize()}">
			 </form>
			<br><br>
			<div class="col-12 text-center">
				<c:forEach begin="0" end="${articulos.getTotalPages()-1}" varStatus="page">
					<c:if test="${page.index != articulos.getNumber()}">
						<spring:url value="/vendedores/articulosEnVenta?" var="siguienteUrl">
							<spring:param name="page" value="${page.index}"/>
							<spring:param name="size" value="${articulos.getSize()}"/>
							<spring:param name="orderBy" value="${ordenacion}"/>									
						</spring:url>
						
						<a href="${fn:escapeXml(siguienteUrl)}">
							<button class="btn btn-default" type="submit">${page.index+1}</button>
						</a>
					</c:if> 
					<c:if test="${page.index == articulos.getNumber()}">
						<button class="btn btn-default" disabled>${page.index+1}</button>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</div>
	</c:if>
</dpc:layout>
