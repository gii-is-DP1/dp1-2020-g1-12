<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>

<dpc:layout pageName="clientes">

    <h2>Mi perfil</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
              <td>${cliente.nombre}</td>
        </tr>
        <tr>
            <th>Apellido</th>
            <td>${cliente.apellido}</td>
        </tr>
          <tr>
            <th>Dirección</th>
            <td>${cliente.direccion}</td>
        </tr>
        <tr>
            <th>DNI</th>
            <td>${cliente.dni}</td>
        </tr>
        <tr>
            <th>Teléfono</th>
            <td>${cliente.telefono}</td>
        </tr>
        <tr>
            <th>Email</th>
            <td>${cliente.email}</td>
        </tr>
              
    </table>

	<a href="/clientes/editar"><button class="btn btn-default" type="submit">Editar</button></a>

    

</dpc:layout>
