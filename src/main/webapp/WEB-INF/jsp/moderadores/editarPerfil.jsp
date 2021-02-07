<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>

<dpc:layout pageName="moderadores">
    <h2>
        Editando perfil...
    </h2>
    <form:form modelAttribute="moderador" class="form-horizontal" id="add-moderador-form">
        <div class="form-group has-feedback">
            <dpc:inputField label="Nombre" name="nombre"/>
            <dpc:inputField label="Apellido" name="apellido"/>
            <dpc:inputField label="Direcci�n" name="direccion"/>
            <dpc:inputField label="Dni" name="dni"/>
            <dpc:inputField label="Tel�fono" name="telefono"/>
            <label class="col-sm-2 control-label" for="user.password">Contrase�a</label>
 			<div class="col-sm-2">
	            <input type="password" name="user.password"/>
	  		</div>
	  		<label class="col-sm-2 control-label" for="user.newPassword">Nueva contrase�a</label>
			<div class="col-sm-2">
	            <input type="password" name="user.newPassword"/>
	  		</div> 
        </div>
       	<input type="hidden" name="id" value="${moderador.id}"/> 
        <input type="hidden" name="version" value="${moderador.version}"/> 
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Actualizar datos</button>
            </div>
        </div>
    </form:form>
     <a href="/moderadores/perfil"><button class="btn btn-default" type="submit">Volver</button></a>
</dpc:layout>
