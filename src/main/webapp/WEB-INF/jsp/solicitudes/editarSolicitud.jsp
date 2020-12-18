<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>

<dpc:layout pageName="crearSolicitud">
    <h2>
        <c:if test="${solicitud['new']}">Nueva </c:if> Solicitud
    </h2>
    <form:form modelAttribute="solicitud" action="/solicitudes/save" class="form-horizontal" id="add-solicitud-form">
        <div class="form-group has-feedback">
            <dpc:inputField label="Descripci�n" name="descripcion"/>
            <dpc:inputField label="Modelo" name="modelo"/>
            <dpc:inputField label="Marca" name="marca"/>
            <label for="tipo">Tipo </label>
            <select name="tipo">
    			<option value="Nuevo">Nuevo</option>
    			<option value="Reacondicionado">Reacondicionado</option>
    			<option value="SemiNuevo">Seminuevo</option>
  			</select>
            <dpc:inputField label="Url de la imagen" name="urlImagen"/>
            <dpc:inputField label="Precio" name="precio"/>
            <dpc:inputField label="Stock" name="stock"/>
            <dpc:inputField label="Tiempo de la entrega" name="tiempoEntrega"/>
            <dpc:inputField label="Gastos de env�o" name="gastoEnvio"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${solicitud['new']}">
                        <button class="btn btn-default" type="submit">Crear Solicitud</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar Solicitud</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
    
      <a href="/"><button class="btn btn-default" type="submit">Volver</button></a>
     
</dpc:layout>
