<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="crearSolicitud">
    <h2>
        <c:if test="${solicitud['new']}">Nueva </c:if> Solicitud
    </h2>
    <form:form modelAttribute="solicitud" action="/solicitudes/save" class="form-horizontal" id="add-solicitud-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Descripción" name="descripcion"/>
            <petclinic:inputField label="Modelo" name="modelo"/>
            <petclinic:inputField label="Marca" name="marca"/>
            <label for="tipo">Tipo </label>
            <select name="tipo">
    			<option value="Nuevo">Nuevo</option>
    			<option value="Reacondicionado">Reacondicionado</option>
    			<option value="SemiNuevo">Seminuevo</option>
  			</select>
            <petclinic:inputField label="Url de la imagen" name="urlImagen"/>
            <petclinic:inputField label="Precio" name="precio"/>
            <petclinic:inputField label="Stock" name="stock"/>
            <petclinic:inputField label="Tiempo de la entrega" name="tiempoEntrega"/>
            <petclinic:inputField label="Gastos de envío" name="gastoEnvio"/>
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
     
</petclinic:layout>
