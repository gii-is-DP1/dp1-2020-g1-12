<%@ page session="true" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<dpc:layout pageName="tramitar">
	<jsp:attribute name="customScript">
        <script>
			function alertaCompra() {
				var opcion = confirm('¿Seguro que desea llevar a cabo la compra de estos artículos?');
				return opcion;
			}
		</script>
    </jsp:attribute>
	<jsp:body>
    <form:form modelAttribute="tarjetaSel" action="confirmarCompra" class="form-horizontal">
	<div class="modal-body row">
		<div class="col-md-5">
			<p>Seleccione una tarjeta para realizar el pago:</p>
			<br>
			<select name="id" title="Seleccione una tarjeta">
	        	<c:forEach items="${tarjetas}" var="tarjeta">
	        	<c:choose>
	        		<c:when test = "${tarjeta.titular== 'Tarjeta eliminada'}"/>
	    			<c:otherwise> 
	    				<option value="${tarjeta.id}">✱✱✱✱ ✱✱✱✱ ✱✱✱✱  ${tarjeta.numero.substring(12)}</option>
	    			</c:otherwise>
	    		</c:choose>
	    		</c:forEach>
	  		</select>
	  		<br><br>
	  		<p>Si necesita añadir otra tarjeta pulse <a href="/tarjetas/new">aquí</a></p>
	  	</div>
		<div class="col-md-7">
		    <h2>Resumen del pedido</h2>
			    <table style="text-align: center" id="articulosTable"
				class="table table-striped">
			        <thead>
			        <tr>
			            <th style="width: 115px; text-align: center">Producto</th>
			            <th style="width: 10px; text-align: center">Cantidad</th>
			            <th style="width: 10px; text-align: center">Precio unidad</th>
			            <th style="width: 10px; text-align: center">Envío</th>
			        </tr>
			        </thead>
			        <tbody>
			      <c:forEach items="${cesta.lineas}" var="linea"
						varStatus="status">
			            <tr>
			                <td>
								<spring:url value="/articulos/{articuloId}" var="articuloUrl">
									<spring:param name="articuloId" value="${linea.articulo.id}" />
								</spring:url>
								<a href="${fn:escapeXml(articuloUrl)}"><c:out
										value="${linea.articulo.marca} ${linea.articulo.modelo}"></c:out></a>
			                </td>
			                <td>
			                    <c:out value="${linea.cantidad}"></c:out>
			                </td>
							<td>
								<c:if test="${linea.articulo.oferta.disponibilidad}">
					                <span style="color: red;"><fmt:formatNumber
											type="number" maxFractionDigits="2"
											value="${linea.articulo.precio * (1 - linea.articulo.oferta.porcentaje/100)}" /> € </span>
					                <span
										style="color: white; background-color: #f35a5a; border-radius: 3px">${linea.articulo.oferta.porcentaje}%</span>
				                </c:if>
				                <c:if test="${!linea.articulo.oferta.disponibilidad}">
					                <c:out value="${linea.articulo.precio} €" />
				               </c:if>
							</td>
							<td>
								<c:out value="${linea.articulo.gastoEnvio} €"></c:out>
							</td>
			            </tr>
			        </c:forEach>
			        <tr>
			        	<td>Importe final:</td>
			        	<td>
			        		<fmt:formatNumber type="number" maxFractionDigits="2" value="${cesta.precioFinal}" /> €
			        	</td>
			        	<td></td>
			        	<td></td>
			        </tr> 
			        </tbody>
			    </table>
			    <div class="col-sm-offset-1">
			    	<c:out value="Fecha estimada de entrega: ${fechaEstimada}"></c:out>
			    </div>
		    </div>
	        <div class="col-sm-offset-1">
			    <button onclick="return alertaCompra()" title="Confirmar Compra" class="btn btn-default"
					style="color: #DAD6D6; text-decoration: Tramitar pedido; FONT-SIZE: 12pt;">Confirmar compra</button> 
	        </div>
        </div>
        </form:form>
    </jsp:body>
</dpc:layout>
