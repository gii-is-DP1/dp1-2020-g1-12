<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="solicitud">
    <h2>Listado de solicitudes</h2>

    <table id="solicitudesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Descripción</th>
            <th style="width: 200px;">Modelo</th>
            <th>Marca</th>
            <th style="width: 120px">UrlImagen</th>
            <th>Precio</th>
            <th style="width: 120px">Stock</th>
            <th style="width: 120px">Tipo</th>
            <th style="width: 120px">Tiempo de Entrega</th>
            <th style="width: 120px">Gasto de Envio</th>
            <th style="width: 120px">Situación</th>
            <th style="width: 120px">Respuesta</th>
        </tr>
        </thead>
        <tbody>
      <c:forEach items="${solicitudes}" var="solicitudes">
            <tr>

                <td>
                    <c:out value="${solicitudes.descripcion}"/>
                </td>
                <td>
                    <c:out value="${solicitudes.modelo}"/>
                </td>
                <td>
                    <c:out value="${solicitudes.marca}"/>
                </td>
                <td>
                    <c:out value="${solicitudes.urlImagen}"/>
                </td>
                <td>
                    <c:out value="${solicitudes.precio}"/>
                </td>
                <td>
                    <c:out value="${solicitudes.stock}"/>
                </td>
                <td>
                    <c:out value="${solicitudes.tipo}"/>
                </td>  
                <td>
                    <c:out value="${solicitudes.tiempoEntrega}"/>
                </td>  
                <td>
                    <c:out value="${solicitudes.gastoEnvio}"/>
                </td>
                <td>
                    <c:out value="${solicitudes.situacion}"/>
                </td> 
                <td>
                    <c:out value="${solicitudes.respuesta}"/>
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