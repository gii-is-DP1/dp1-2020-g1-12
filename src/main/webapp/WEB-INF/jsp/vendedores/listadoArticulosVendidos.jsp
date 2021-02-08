<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="articulosVendidos">
	<jsp:attribute name="customScript">
        <script>
            function ventana(URL) {
            	window.open(URL, "Chat", "width:1000, height:1000,scrollbars=0,resizable:1");
            }
        </script>
    </jsp:attribute>

    <jsp:body>
	<form action="/vendedores/articulosVendidos?" method="get">
		<div style="float:right">
			<input type="hidden" name="page" value="${lineaPedido.getNumber()}"/>
			<input type="hidden" name="size" value="${lineaPedido.getSize()}"/>			
	 		<select onchange="this.form.submit();" name="orderBy">
	       		<option value="" disabled selected>Ordenar por:</option>
	 			<option value="-id">Más recientes</option>
	 			<option value="id">Más antiguos</option>
	 		</select>
		</div>
	</form>
    <h2>Lista de artículos vendidos</h2>
    <c:if test="${lineaPedido.getContent().size() == 0}">Aún no ha vendido ningún artículo.</c:if>
    <c:if test="${lineaPedido.getContent().size() != 0}">
    <table id="articulosVendidosTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 50px;">Fecha</th>
            <th style="width: 180px;">Artículo</th>
            <th style="width: 100px;">Comprador</th>
            <th style="width: 30px">Cantidad</th>
            <th style="width: 30px">Estado</th>
            <th style="width: 90px">Acciones</th>
        </tr>
        </thead>
        <tbody>
      <c:forEach items="${lineaPedido.getContent()}" var="lp" varStatus="status">
            <tr>
            	<td>
                    <c:out value="${lp.pedido.fecha}"/>
                </td>
                <td>
					<spring:url value="/vendedores/articulo/{articuloId}" var="articuloUrl">
						<spring:param name="articuloId" value="${lp.articulo.id}"/>
					</spring:url>
					<a href="${fn:escapeXml(articuloUrl)}"><c:out value="${lp.articulo.marca} ${lp.articulo.modelo}"></c:out></a>
                </td>
                 <td>
                 
                 	<spring:url value="/vendedores/perfilCliente/{clienteId}" var="compradorUrl">
						<spring:param name="clienteId" value="${lp.pedido.cliente.id}"/>
					</spring:url>
                    <a href="${fn:escapeXml(compradorUrl)}">
                    	<c:out value="${lp.pedido.cliente.nombre} ${lp.pedido.cliente.apellido}"/>
                    </a>
                </td>
                <td>
                    <c:out value="${lp.cantidad} unidades"/>
                </td>
                <td>
                	<c:if test="${lp.estado == 'EnReparto'}">
                    	<c:out value="En Reparto"/>
                    </c:if>
                    <c:if test="${lp.estado != 'EnReparto'}">
                    	<c:out value="${lp.estado}"/>
                    </c:if>
                </td>
                <td>
	                <spring:url value="/pedidos/modificar/{lineaPedidoId}" var="modificarUrl">
			   			<spring:param name="lineaPedidoId" value="${lp.id}"/>
					</spring:url>
		
					<a href="${fn:escapeXml(modificarUrl)}">
						<button class="btn btn-default" type="submit">Modificar</button>
					</a>
					<spring:url value="/chat/{rol}/{id}" var="articuloUrl">
						<spring:param name="id" value="${lp.pedido.cliente.id}"/>
						<spring:param name="rol" value="vendedor"/>
					</spring:url>
					<a href="javascript:ventana('${fn:escapeXml(articuloUrl)}')"><button title="Si tienes algún problema puedes inicar un chat con el vendedor" 
						class="btn btn-default" >Chat</button></a>
					<span class="badge badge-pill badge-success">
                    	<strong>${contadores[status.index]} mensajes nuevos</strong></span>
                </td>
            </tr>
        </c:forEach> 
        </tbody>
    </table>
    	<div class="container">
		<div class="row text-center">
			<form action="/vendedores/articulosVendidos?" method="get">
				<input type="hidden" name="page" value="${lineaPedido.getNumber()}"/>
				<input type="hidden" name="orderBy" value="${ordenacion}"/>
				<label for="size">Elementos por página: </label>
				<input onchange="this.form.submit();" style="border-radius: 5px; width:10%; text-align: center"
				 type="number" min="2" name="size" value="${lineaPedido.getSize()}">
			 </form>
			<br><br>
			<div class="col-12 text-center">
				<c:forEach begin="0" end="${lineaPedido.getTotalPages()-1}" varStatus="page">
					<c:if test="${page.index != lineaPedido.getNumber()}">
						<spring:url value="/vendedores/articulosVendidos?" var="siguienteUrl">
							<spring:param name="page" value="${page.index}"/>
							<spring:param name="size" value="${lineaPedido.getSize()}"/>
							<spring:param name="orderBy" value="${ordenacion}"/>									
						</spring:url>
						
						<a href="${fn:escapeXml(siguienteUrl)}">
							<button class="btn btn-default" type="submit">${page.index+1}</button>
						</a>
					</c:if> 
					<c:if test="${page.index == lineaPedido.getNumber()}">
						<button class="btn btn-default" disabled>${page.index+1}</button>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</div>
	</c:if>
	</jsp:body>
</dpc:layout>
