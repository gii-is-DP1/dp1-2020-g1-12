<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>

<dpc:layout pageName="bloqueos">
    
    <h2>Bloqueo de usuario</h2>
    
    <form:form modelAttribute="bloqueo" class="form-horizontal" id="add-bloqueo-form">
        <input type="hidden" name="version" value="${bloqueo.version}"/> 
        <div class="form-group has-feedback">
            <dpc:inputField label="Descripción" name="descripcion"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Bloquear Usuario</button>
            </div>
        </div>
    </form:form>
     <a href="/clientes"><button class="btn btn-default" type="submit">Volver</button></a>
</dpc:layout>
