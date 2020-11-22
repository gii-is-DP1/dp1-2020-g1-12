<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="solicitante">
	 <h2>Perfil de ${vendedor.nombre} ${vendedor.apellido}</h2>

    <table class="table table-striped">
    
    	<tr>
            <th>DNI</th>
            <td>${vendedor.dni}</td>
        </tr>
        <tr>
            <th>Dirección</th>
            <td>${vendedor.direccion}</td>
        </tr>
        
        <tr>
            <th>Teléfono</th>
            <td>${vendedor.telefono}</td>
        </tr>
        <tr>
            <th>Email</th>
            <td>${vendedor.email}</td>
        </tr>
    
    </table>
    
</petclinic:layout>