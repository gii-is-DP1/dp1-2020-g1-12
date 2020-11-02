<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="clientes">

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
   
           <spring:url value="{clienteId}/editar" var="clienteUrl">
               <spring:param name="clienteId" value="${cliente.id}"/>
           </spring:url>
        
         
            
    </table>
            <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				<a href="${fn:escapeXml(clienteUrl)}"><button class="btn btn-default" type="submit">Editar</button></a>
            </div>
        </div>

    

</petclinic:layout>
