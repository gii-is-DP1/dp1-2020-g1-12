<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="clientes">
    <h2>Listado de clientes</h2>

    <table id="clientesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 200px;">Apellidos</th>
            <th>Dni</th>
            <th style="width: 120px">Teléfono</th>
            <th>Dirección</th>
            <th style="width: 120px">Email</th>
        </tr>
        </thead>
        <tbody>
      <c:forEach items="${clientes}" var="clientes">
            <tr>
  
                <td>
                    <c:out value="${clientes.nombre}"/>
                </td>
                <td>
                    <c:out value="${clientes.apellido}"/>
                </td>
                <td>
                    <c:out value="${clientes.dni}"/>
                </td>
                <td>
                    <c:out value="${clientes.telefono}"/>
                </td>
                <td>
                    <c:out value="${clientes.direccion}"/>
                </td>
                <td>
                    <c:out value="${clientes.email}"/>
                </td>                                                
                 
      
<!--
                <td> 
                    <c:out value="${owner.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${owner.user.password}"/> 
                </td> -->

                
            </tr>
        </c:forEach> 
           
        </tbody>
    </table>
</petclinic:layout>
