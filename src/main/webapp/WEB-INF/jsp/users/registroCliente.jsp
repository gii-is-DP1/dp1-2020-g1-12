<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>

<dpc:layout pageName="registroCliente">
    <h2>
        Nuevo Cliente
    </h2>
    <form:form modelAttribute="cliente" class="form-horizontal" id="add-cliente-form">
        <div class="form-group has-feedback">
            <dpc:inputField label="Nombre" name="nombre"/>
            <dpc:inputField label="Apellido" name="apellido"/>
            <dpc:inputField label="Dirección" name="direccion"/>
            <dpc:inputField label="Dni" name="dni"/>
            <dpc:inputField label="Teléfono" name="telefono"/>
            <dpc:inputField label="Email" name="email"/>
            <dpc:inputField label="Usuario" name="user.username"/>
	        <label class="col-sm-2 control-label" for="user.password">Contraseña</label>
 			<div class="col-sm-2">
	            <input type="password" name="user.password"/>
	            <br>
	  		</div>
	  		<label class="col-sm-2 control-label" for="user.newPassword">Confirmar contraseña</label>
			<div class="col-sm-2">
	            <input type="password" name="user.newPassword"/>
	            <br>
	  		</div> 
        </div> 
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            	<button class="btn btn-default" type="submit">Registrarse</button>
            </div>
        </div>
    </form:form>
</dpc:layout>
