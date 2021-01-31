<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>

<dpc:layout pageName="EditarEstado">
    
    <div style="margin-top:3%;text-align:center">
	    <h2 style="padding: 50px">Editando el estado del pedido con ID <c:out value="${lineaPedido.id}" /></h2>
	    <form:form modelAttribute="lineaPedido" action="/pedidos/modificar/${lineaPedido.id}/save" class="form-horizontal">
	    	<select style="margin-right:40px" name="estado">
	    		<option value="Pendiente">Pendiente</option>
	    		<option value="Enviado">Enviado</option>
	    		<option value="EnReparto">En Reparto</option>
	    		<option value="Entregado">Entregado</option>
	  		</select>
	  		<button class="btn btn-default" style="margin-left:20px" type="submit">Guardar</button>
	  		
	    </form:form>
	    
	    <a href="/vendedores/articulosVendidos"><button class="btn btn-default" style="margin-top:30px;margin-right:170px" 
	    type="submit">Volver</button></a>
    </div>
</dpc:layout>