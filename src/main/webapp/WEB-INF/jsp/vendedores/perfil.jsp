<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vendedores">

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
            <th>Direcci�n</th>
            <td>${vendedor.direccion}</td>
        </tr>
        <tr>
            <th>DNI</th>
            <td>${vendedor.dni}</td>
        </tr>
        <tr>
            <th>Tel�fono</th>
            <td>${vendedor.telefono}</td>
        </tr>
        <tr>
            <th>Email</th>
            <td>${vendedor.email}</td>
        </tr>
   
           <spring:url value="{vendedorId}/editar" var="vendedorUrl">
               <spring:param name="vendedorId" value="${vendedor.id}"/>
           </spring:url>
        
         
            
    </table>
            <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				<a href="${fn:escapeXml(vendedorUrl)}"><button class="btn btn-default" type="submit">Editar</button></a>
            </div>
        </div>

    

</petclinic:layout>
