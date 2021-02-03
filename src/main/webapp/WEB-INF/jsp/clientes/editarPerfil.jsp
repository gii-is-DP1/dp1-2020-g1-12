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
            <dpc:inputField label="Direcci�n" name="direccion"/>
            <dpc:inputField label="Dni" name="dni"/>
            <dpc:inputField label="Tel�fono" name="telefono"/>
            <dpc:inputField label="Email" name="email"/>
            <dpc:inputField label="Contrase�a" name="user.password"/>
            <dpc:inputField label="Nueva contrase�a" name="user.username"/>
        </div>
        <input type="hidden" name="id" value="${cliente.id}"/> 
        <input type="hidden" name="version" value="${cliente.version}"/> 
        <c:if test="${errores != null}">
				    <p style="color:red;"><c:out value="${errores}"></c:out></p>
				   	<br>
			</c:if>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Guardar</button>
            </div>
        </div>
    </form:form>
    <a href="/clientes/perfil"><button class="btn btn-default" type="submit">Volver</button></a>
</dpc:layout>
