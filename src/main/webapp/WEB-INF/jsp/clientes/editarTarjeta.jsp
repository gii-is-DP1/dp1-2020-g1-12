<%@ page session="true" trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>

<dpc:layout pageName="tarjetas">
    <jsp:attribute name="customScript">
        <script type="text/javascript">
        	$(function() {
        		$("#datepicker").datepicker( {
        		    format: "mm/yy",
        		    startView: "months", 
        		    minViewMode: "months"
        		});
            });
        </script>
		<script src="https://netdna.bootstrapcdn.com/bootstrap/2.3.2/js/bootstrap.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.2.0/js/bootstrap-datepicker.min.js"></script>
	</jsp:attribute>
	<jsp:body>
	    <h2>Añadir nueva tarjeta</h2>
	    <c:if test="${errores != null}">
	    <h5>Compruebe los siguientes errores en el formulario:</h5>
	    <c:forEach items="${errores}" var="error">
	   		<p style="color: red;">${error.getDefaultMessage()}</p>
	   	</c:forEach>
	   	<br>
	   	</c:if>
	    <form:form modelAttribute="tarjeta" action="save" class="form-horizontal" id="add-tarjeta-form">
	        <div class="form-group has-feedback">
	            <label for="Titular">Titular:</label>
				<input style="width:55%" type="text" class="form-control" name="titular" value="${tarjeta.titular}"/>
	            <label for="numero">Numero de Tarjeta:</label>
				<input style="width:50%" type="text" class="form-control" name="numero" value="${tarjeta.numero}" />
	            <label for="cvv">CVV:</label>
				<input style="width:10%" type="text" class="form-control" name="cvv" value="${tarjeta.cvv}" />
	            <label for="fechaCaducidad">Fecha de Caducidad:</label>
				<input style="width:10%" type="text" class="form-control"  id="datepicker" name="fechaCaducidad" value="${tarjeta.fechaCaducidad}" />
	        </div>
	        <div class="form-group">
	            <div class="col-sm-offset-2 col-sm-10">
					<button class="btn btn-default" type="submit">Guardar</button>
	            </div>
	        </div>
	    </form:form>
	    <a href="/clientes/perfil"><button class="btn btn-default" type="submit">Volver</button></a>
    </jsp:body>
</dpc:layout>