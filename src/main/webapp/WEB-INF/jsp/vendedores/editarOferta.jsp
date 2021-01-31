<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>

<dpc:layout pageName="ofertas">
    
    ${mensaje}
    <h2>Editar oferta</h2>
    
    <form:form modelAttribute="oferta" class="form-horizontal" id="add-oferta-form">
        <div class="form-group has-feedback">
            <dpc:inputField label="Porcentaje" name="porcentaje"/>
        </div>
        <input type="hidden" name="version" value="${oferta.version}"/> 
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Crear Oferta</button>
            </div>
        </div>
    </form:form>
    <a href="/vendedores/articulosEnVenta"><button class="btn btn-default" type="submit">Volver</button></a>
</dpc:layout>
