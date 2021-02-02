<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="solicitudes">
    
	<form action="/vendedores/listadoSolicitudes?" method="get">
		<div style="float:right">
			<input type="hidden" name="page" value="${solicitudes.getNumber()}"/>
			<input type="hidden" name="size" value="${solicitudes.getSize()}"/>			
	 		<select onchange="this.form.submit();" name="orderBy">
	       		<option value="" disabled selected>Ordenar por:</option>
	 			<option value="-id">Más recientes</option>
	 			<option value="id">Más antiguos</option>
	 			<option value="marca">Nombre A-Z</option>
	 			<option value="-marca">Nombre Z-A</option>
	 			<option value="precio">Precio de más bajo a más alto</option>
	 			<option value="-precio">Precio de más alto a más bajo</option>
	 		</select>
		</div>
	</form>
    <h2>Lista de solicitudes</h2>
	<c:if test="${solicitudes.getNumberOfElements() == 0}">
    <p>Aún tienes ninguna solicitud de venta.</p>
    </c:if>
    <c:if test="${solicitudes.getNumberOfElements() != 0}">
    <table id="solicitudesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Artículo</th>
            <th style="width: 30px;">Stock</th>
            <th style="width: 30px;">Precio</th>
            <th style="width: 50px">Situación</th>
        </tr>
        </thead>
        <tbody>
      <c:forEach items="${solicitudes.getContent()}" var="solicitudes">
            <tr>
                <td>
					<spring:url value="/vendedores/solicitud/{solicitudId}" var="solicitudUrl">
						<spring:param name="solicitudId" value="${solicitudes.id}"/>
					</spring:url>
					<a href="${fn:escapeXml(solicitudUrl)}"><c:out value="${solicitudes.marca} ${solicitudes.modelo}"></c:out></a>
                </td>
                <td>
                    <c:out value="${solicitudes.stock} unidades"/>
                </td>
                <td>
                   <fmt:formatNumber type="number" maxFractionDigits="2" 
	                    value="${solicitudes.precio}"/> €
                </td>
                <td>
                	<c:out value="${solicitudes.situacion}"></c:out>
                </td>
            </tr>
        </c:forEach> 
        </tbody>
    </table>
	<div class="container">
		<div class="row text-center">
			<form action="/vendedores/listadoSolicitudes?" method="get">
				<input type="hidden" name="page" value="${solicitudes.getNumber()}"/>
				<input type="hidden" name="orderBy" value="${ordenacion}"/>
				<label for="size">Elementos por página: </label>
				<input onchange="this.form.submit();" style="border-radius: 5px; width:10%; text-align: center"
				 type="number" min="2" name="size" value="${solicitudes.getSize()}">
			 </form>
			<br><br>
			<div class="col-12 text-center">
				<c:forEach begin="0" end="${solicitudes.getTotalPages()-1}" varStatus="page">
					<c:if test="${page.index != solicitudes.getNumber()}">
						<spring:url value="/vendedores/listadoSolicitudes?" var="siguienteUrl">
							<spring:param name="page" value="${page.index}"/>
							<spring:param name="size" value="${solicitudes.getSize()}"/>
							<spring:param name="orderBy" value="${ordenacion}"/>									
						</spring:url>
						
						<a href="${fn:escapeXml(siguienteUrl)}">
							<button class="btn btn-default" type="submit">${page.index+1}</button>
						</a>
					</c:if> 
					<c:if test="${page.index == solicitudes.getNumber()}">
						<button class="btn btn-default" disabled>${page.index+1}</button>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</div>
	</c:if>
</dpc:layout>
