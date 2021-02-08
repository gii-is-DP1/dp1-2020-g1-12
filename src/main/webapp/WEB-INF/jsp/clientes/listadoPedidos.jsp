<%@ page session="true" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="pedidos">
    
	<form action="/pedidos?" method="get">
		<div style="float:right">
			<input type="hidden" name="page" value="${pedidos.getNumber()}"/>
			<input type="hidden" name="size" value="${pedidos.getSize()}"/>			
	 		<select onchange="this.form.submit();" name="orderBy">
	       		<option value="" disabled selected>Ordenar por:</option>
	 			<option value="-id">Más recientes</option>
	 			<option value="id">Más antiguos</option>
	 		</select>
		</div>
	</form>
    <h2>Mis pedidos</h2>
    
    <c:if test="${pedidos.getContent().size() == 0}">Aún no has realizado ningún pedido.</c:if>
    <c:if test="${pedidos.getContent().size() != 0}">
    <table id="pedidosTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Pedido #Número de referencia</th>
            <th style="width: 70px;">Precio Total</th>
            <th style="width: 70px;">Fecha del Pedido</th>
        </tr>
        </thead>
        <tbody>
			<c:forEach items="${pedidos.getContent()}" var="pedido">
	            <tr>
	                <td>
						<spring:url value="/pedidos/{pedidoId}" var="pedidoUrl">
							<spring:param name="pedidoId" value="${pedido.id}"/>
						</spring:url>
						<a href="${fn:escapeXml(pedidoUrl)}"><c:out value="Pedido # ${pedido.id}"></c:out></a>
					</td>
	                <td>
	                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${pedido.precioTotal}"/> €
	                </td>
					<td>
	                    <c:out value="${pedido.fecha}"/>
					</td>
	            </tr>
			</c:forEach> 
        </tbody>
    </table>
	<div class="container">
		<div class="row text-center">
			<form action="/pedido?" method="get">
				<input type="hidden" name="page" value="${pedidos.getNumber()}"/>
				<input type="hidden" name="orderBy" value="${ordenacion}"/>
				<label for="size">Elementos por página: </label>
				<input onchange="this.form.submit();" style="border-radius: 5px; width:10%; text-align: center"
				 type="number" min="2" name="size" value="${pedidos.getSize()}">
			 </form>
			<br><br>
			<div class="col-12 text-center">
				<c:forEach begin="0" end="${pedidos.getTotalPages()-1}" varStatus="page">
					<c:if test="${page.index != pedidos.getNumber()}">
						<spring:url value="/pedidos?" var="siguienteUrl">
							<spring:param name="page" value="${page.index}"/>
							<spring:param name="size" value="${pedidos.getSize()}"/>
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
