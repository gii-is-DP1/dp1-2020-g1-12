<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="articulos">
    
    <h2>Lista de artículos en venta</h2>

    <table id="articulosTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Artículo</th>
            <th style="width: 200px;">Precio</th>
            <th style="width: 120px">Oferta</th>
        </tr>
        </thead>
        <tbody>
      <c:forEach items="${articulos}" var="articulos">
            <tr>
                <td>
                    <c:out value="${articulos.marca} ${articulos.modelo}"/>
                </td>
                <td>
                    <c:out value="${articulos.precio}"/>
                </td>                                                
            </tr>
        </c:forEach> 
        </tbody>
    </table>
</petclinic:layout>
