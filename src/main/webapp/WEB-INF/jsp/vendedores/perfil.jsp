<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="vendedores">

    <h2>Mi perfil</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
              <td>${vendedor.nombre}</td>
        </tr>
        <tr>
            <th>Apellido</th>
            <td>${vendedor.apellido}</td>
        </tr>
          <tr>
            <th>Dirección</th>
            <td>${vendedor.direccion}</td>
        </tr>
        <tr>
            <th>DNI</th>
            <td>${vendedor.dni}</td>
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

	<a href="/vendedores/editar"><button class="btn btn-default" type="submit">Editar</button></a>

    

</dpc:layout>
