<%@ page session="true" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<dpc:layout pageName="comentarios">
        <c:if test="${comentario['new']}">
        <h2>Crear un comentario</h2>
	    <form:form action="/comentario/articulo/${articulo}" modelAttribute="comentario" class="form-horizontal" id="add-comentario-form">
	        <div class="form-group has-feedback">
				<sec:authorize access="hasAuthority('cliente')">
	            	<dpc:inputField label="Opinión" name="descripcion"/>
		            <label class="valor">Valoración:</label>
		            <p class="clasificacion">
		                <input id="radio1" type="radio" name="valoracion" value="5">
		    			<label class="labelComentario" for="radio1">★</label>
						<input id="radio2" type="radio" name="valoracion" value="4">
						<label class="labelComentario" for="radio2">★</label>
						<input id="radio3" type="radio" name="valoracion" value="3">
						<label class="labelComentario" for="radio3">★</label>
						<input id="radio4" type="radio" name="valoracion" value="2">
						<label class="labelComentario" for="radio4">★</label>
						<input id="radio5" type="radio" name="valoracion" value="1">
						<label class="labelComentario" for="radio5">★</label>
					</p>
				    <c:if test="${errores != null}">
					    <p style="color:red;"><c:out value="${errores}"></c:out></p>
					   	<br>
				   	</c:if>
				</sec:authorize>
				<sec:authorize access="hasAuthority('moderador')">
					<dpc:inputField label="Comentario" name="descripcion"/>
					<input type=hidden name="valoracion" value="0">
				</sec:authorize>
				<sec:authorize access="hasAnyAuthority('vendedor')">
					<dpc:inputField label="Respuesta" name="descripcion"/>
					<input type=hidden name="valoracion" value="0">
				</sec:authorize>
	        </div>
	        <div class="form-group">
	            <div class="col-sm-offset-2 col-sm-10">
					<button class="btn btn-default" type="submit">Crear comentario</button>
	            </div>
	        </div>
	    </form:form>
	</c:if>
	
    <c:if test="${!comentario['new']}">
        <h2>Editar un comentario</h2>
	    <form:form action="/comentario/editar/${comentario.id}/articulo/${articulo}" modelAttribute="comentario" class="form-horizontal" id="add-comentario-form">
	        <div class="form-group has-feedback">
				<sec:authorize access="hasAuthority('cliente')">
	            	<dpc:inputField label="Opinión" name="descripcion"/>
		            <label class="valor">Valoración:</label>
		            <p class="clasificacion">
		                <input id="radio1" type="radio" name="valoracion" value="5">
		    			<label class="labelComentario" for="radio1">★</label>
						<input id="radio2" type="radio" name="valoracion" value="4">
						<label class="labelComentario" for="radio2">★</label>
						<input id="radio3" type="radio" name="valoracion" value="3">
						<label class="labelComentario" for="radio3">★</label>
						<input id="radio4" type="radio" name="valoracion" value="2">
						<label class="labelComentario" for="radio4">★</label>
						<input id="radio5" type="radio" name="valoracion" value="1">
						<label class="labelComentario" for="radio5">★</label>
					</p>
				    <c:if test="${errores != null}">
					    <p style="color:red;"><c:out value="${errores}"></c:out></p>
					   	<br>
				   	</c:if>
				</sec:authorize>
				<sec:authorize access="hasAuthority('moderador')">
					<dpc:inputField label="Comentario" name="descripcion"/>
					<input type=hidden name="valoracion" value="0">
				</sec:authorize>
				<sec:authorize access="hasAnyAuthority('vendedor')">
					<dpc:inputField label="Respuesta" name="descripcion"/>
					<input type=hidden name="valoracion" value="0">
				</sec:authorize>
	        </div>
	        <div class="form-group">
	            <div class="col-sm-offset-2 col-sm-10">
					<button class="btn btn-default" type="submit">Editar comentario</button>
	            </div>
	        </div>
	    </form:form>
	</c:if>
	<a href="/articulos/${articuloId}"><button class="btn btn-default" type="submit">Volver</button></a>
</dpc:layout>
