<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="moderadores">
    <h2>
        Editando perfil...
    </h2>
    <form:form modelAttribute="moderador" class="form-horizontal" id="add-moderador-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="nombre"/>
            <petclinic:inputField label="Apellido" name="apellido"/>
            <petclinic:inputField label="Direcci�n" name="direccion"/>
            <petclinic:inputField label="Dni" name="dni"/>
            <petclinic:inputField label="Tel�fono" name="telefono"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Actualizar datos</button>
            </div>
        </div>
    </form:form>
     <a href="/moderadores/perfil"><button class="btn btn-default" type="submit">Volver</button></a>
</petclinic:layout>
