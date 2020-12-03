<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">
    <h2>
        <c:if test="${owner['new']}">New </c:if> Owner
    </h2>
    <form:form modelAttribute="owner" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="nombre"/>
            <petclinic:inputField label="Apellido" name="apellido"/>
            <petclinic:inputField label="Dirección" name="direccion"/>
            <petclinic:inputField label="Dni" name="dni"/>
            <petclinic:inputField label="Teléfono" name="telefono"/>
            <petclinic:inputField label="Email" name="email"/>
            <petclinic:inputField label="Usuario" name="user.username"/>
            <petclinic:inputField label="Contraseña" name="user.password"/>
            <petclinic:inputField label="Repetir contraseña" name="user.password"/>
            <label for="tipo">Tipo de usuario</label>
            <select name="tipo">
    			<option value="Comprador">Comprador</option>
    			<option value="Vendedor">Vendedor</option>
  			</select>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${owner['new']}">
                        <button class="btn btn-default" type="submit">Registrarse</button>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
