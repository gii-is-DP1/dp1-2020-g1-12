<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="moderadores">

    <h2>Mi perfil</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
              <td>${moderador.nombre}</td>
        </tr>
        <tr>
            <th>Apellido</th>
            <td>${moderador.apellido}</td>
        </tr>
          <tr>
            <th>Direcci�n</th>
            <td>${moderador.direccion}</td>
        </tr>
        <tr>
            <th>DNI</th>
            <td>${moderador.dni}</td>
        </tr>
        <tr>
            <th>Tel�fono</th>
            <td>${moderador.telefono}</td>
        </tr>
   
           <spring:url value="{moderadorId}/editar" var="moderadorUrl">
               <spring:param name="moderadorId" value="${moderador.id}"/>
           </spring:url>
    </table>
            <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				<a href="${fn:escapeXml(moderadorUrl)}"><button class="btn btn-default" type="submit">Editar</button></a>
            </div>
        </div>

    

</petclinic:layout>
