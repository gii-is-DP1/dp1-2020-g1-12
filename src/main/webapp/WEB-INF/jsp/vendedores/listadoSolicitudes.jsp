<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="solicitudes">
    
    <h2>Lista de solicitudes</h2>

    <table id="solicitudesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Artículo</th>
            <th style="width: 30px;">Stock</th>
            <th style="width: 30px;">Precio</th>
            <th style="width: 50px">Situación</th>
        </tr>
        </thead>
        <tbody>
      <c:forEach items="${solicitudes}" var="solicitudes">
            <tr>
                <td>
					<spring:url value="/vendedores/solicitud/{solicitudId}" var="solicitudUrl">
						<spring:param name="solicitudId" value="${solicitudes.id}"/>
					</spring:url>
					<a href="${fn:escapeXml(solicitudUrl)}"><c:out value="${solicitudes.marca} ${solicitudes.modelo}"></c:out></a>
                </td>
                <td>
                    <c:out value="${solicitudes.stock}"/>
                </td>
                <td>
                    <c:out value="${solicitudes.precio}"/>
                </td>
                <td>
                	<c:out value="${solicitudes.situacion}"></c:out>
                </td>
            </tr>
        </c:forEach> 
        </tbody>
    </table>
</dpc:layout>
