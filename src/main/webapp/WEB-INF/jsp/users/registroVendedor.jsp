<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="registroVendedor">
    <h2>
        Nuevo Vendedor
    </h2>
    <form:form modelAttribute="vendedor" class="form-horizontal" id="add-vendedor-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="nombre"/>
            <petclinic:inputField label="Apellido" name="apellido"/>
            <petclinic:inputField label="Direcci�n" name="direccion"/>
            <petclinic:inputField label="Dni" name="dni"/>
            <petclinic:inputField label="Tel�fono" name="telefono"/>
            <petclinic:inputField label="Email" name="email"/>
            <petclinic:inputField label="Usuario" name="user.username"/>
             <label class="col-sm-2 control-label" for="user.password">Contrase�a</label>
			<div class="col-sm-10">
	            <input type="password" name="user.password"/>
	  			<br>
	  		</div>
        </div>
        <div>
        	<c:if test="${errores != null}">
				    <p style="color:red;"><c:out value="${errores}"></c:out></p>
				   	<br>
			</c:if>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            	<button class="btn btn-default" type="submit">Registrarse</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>