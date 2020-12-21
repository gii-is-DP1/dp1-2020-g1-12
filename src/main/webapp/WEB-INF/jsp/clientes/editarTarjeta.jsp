<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>

<dpc:layout pageName="tarjetas">
    <h2>
        A�adir nueva tarjeta
    </h2>
    <form:form modelAttribute="tarjeta" action="save" class="form-horizontal" id="add-tarjeta-form">
        <div class="form-group has-feedback">
            <dpc:inputField label="Titular" name="titular"/>
            <dpc:inputField label="N�mero de Tarjeta" name="numero"/>
            <dpc:inputField label="CVV" name="cvv"/>
            <dpc:inputField label="Mes de Caducidad" name="mesCaducidad"/>
            <dpc:inputField label="A�o de Caducidad" name="anyoCaducidad"/>
            
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Guardar</button>
            </div>
        </div>
    </form:form>
    <a href="/clientes/perfil"><button class="btn btn-default" type="submit">Volver</button></a>
</dpc:layout>