<%@ page session="true" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>

<dpc:layout pageName="clientes">
    <h2>
        Editando perfil...
    </h2>
    <form:form modelAttribute="cliente" class="form-horizontal" id="add-cliente-form">
        <div class="form-group has-feedback">
            <dpc:inputField label="Nombre" name="nombre"/>
            <dpc:inputField label="Apellido" name="apellido"/>
            <dpc:inputField label="Dirección" name="direccion"/>
            <dpc:inputField label="Dni" name="dni"/>
            <dpc:inputField label="Teléfono" name="telefono"/>
            <dpc:inputField label="Email" name="email"/>
            <label class="col-sm-2 control-label" for="user.password">Contraseña</label>
 			<div class="col-sm-2">
	            <input type="password" name="user.password"/>
	  		</div>
	  		<label class="col-sm-2 control-label" for="user.newPassword">Nueva contraseña</label>
			<div class="col-sm-2">
	            <input type="password" name="user.newPassword"/>
	  		</div> 
        </div>
        <input type="hidden" name="id" value="${cliente.id}"/> 
        <input type="hidden" name="version" value="${cliente.version}"/> 
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Guardar</button>
            </div>
        </div>
    </form:form>
    <a href="/clientes/perfil"><button class="btn btn-default" type="submit">Volver</button></a>
</dpc:layout>
