<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="articulosVendidos">

    <h2>Lista de artículos vendidos</h2>

    <table id="articulosVendidosTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 180px;">Artículo</th>
            <th style="width: 80px">Cantidad</th>
        </tr>
        </thead>
        <tbody>
      <c:forEach items="${lineaPedido}" var="lp">
            <tr>
                <td>
					<c:out value="${lp.articulo.marca} ${lp.articulo.modelo}"/>
                </td>
                <td>
                    <c:out value="${lp.cantidad}"/>
                </td>
            </tr>
        </c:forEach> 
        </tbody>
    </table>
</dpc:layout>
