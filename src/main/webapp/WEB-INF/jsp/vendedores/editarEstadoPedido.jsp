<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>

<dpc:layout pageName="EditarEstado">
    
    <h2>Editando estado del pedido con ID <c:out value="${lineaPedido.id}" /></h2>
    
    <form:form modelAttribute="lineaPedido" action="/pedidos/modificar/${lineaPedido.id}/save" class="form-horizontal">
    	<select name="estado">
    		<option value="Pendiente">Pendiente</option>
    		<option value="Enviado">Enviado</option>
    		<option value="EnReparto">En Reparto</option>
    		<option value="Entregado">Entregado</option>
  		</select>
  		<button class="btn btn-default" type="submit">Guardar</button>
  		
    </form:form>
    
    <a href="/vendedores/articulosVendidos"><button class="btn btn-default" type="submit">Volver</button></a>
    
</dpc:layout>