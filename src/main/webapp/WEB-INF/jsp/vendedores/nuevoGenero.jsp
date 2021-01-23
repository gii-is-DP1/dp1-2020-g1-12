<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>

<dpc:layout pageName="generos">
    
    <h2>Añadir género</h2>
    
    <form:form modelAttribute="genero" action="${articuloId}/save" class="form-horizontal">
    	<select name="genero">
    		<c:forEach items="${generosDisponibles}" var="generoDisponible">
    			<option value="${generoDisponible.id}">${generoDisponible.nombre}</option>
        	</c:forEach>
  		</select>
  		
  		<button class="btn btn-default" type="submit">Añadir género</button>
  		
    </form:form>
    
    <a href="/vendedores/articulo/${articuloId}"><button class="btn btn-default" type="submit">Volver</button></a>
    
</dpc:layout>