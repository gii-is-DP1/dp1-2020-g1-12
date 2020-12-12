<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="comentarios">
    
    <h2>Crear un comentario</h2>
    
    <form:form modelAttribute="comentario" class="form-horizontal" id="add-comentario-form">
        <div class="form-group has-feedback">
            <dpc:inputField label="Descripción" name="descripcion"/>
            <label class="valor">Valoración:</label>
            <p class="clasificacion">
                <input id="radio1" type="radio" name="valoracion" value="5">
    			<label for="radio1">★</label>
				<input id="radio2" type="radio" name="valoracion" value="4">
				<label for="radio2">★</label>
				<input id="radio3" type="radio" name="valoracion" value="3">
				<label for="radio3">★</label>
				<input id="radio4" type="radio" name="valoracion" value="2">
				<label for="radio4">★</label>
				<input id="radio5" type="radio" name="valoracion" value="1">
				<label for="radio5">★</label>
			</p>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Crear comentario</button>
            </div>
        </div>
    </form:form>
     <a href="/articulos/${articuloId}"><button class="btn btn-default" type="submit">Volver</button></a>
</dpc:layout>
