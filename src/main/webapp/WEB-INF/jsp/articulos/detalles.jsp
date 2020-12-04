<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="articulos">

    <h1>${articulo.marca} ${' '} ${articulo.modelo}</h1>
    <img style='width: 40%; height: 20%' alt='' 
	            	onerror="this.src=''" src='${articulo.urlImagen}'/>

<table class="table table-borderless">
        <tr>
            <th style="width: 600px;">Vendedor</th>
            <td>${vendedor.nombre} ${' '} ${vendedor.apellido}</td>
        </tr>
        <tr>
            <th>Precio</th>
            <td>
            <c:if test="${articulo.oferta.disponibilidad}" >
                <span style="color: red; font-size: large"><fmt:formatNumber type="number" maxFractionDigits="2" 
                    value="${articulo.precio * (1 - articulo.oferta.porcentaje/100)}"/> € </span>
                <span style="font-size: small; padding: 0px 6px 0px 6px"><strike>${articulo.precio} € </strike></span>
                <span style="color: white; background-color: #f35a5a; border-radius: 3px">${articulo.oferta.porcentaje}%</span>

                </c:if>
                <c:if test="${!articulo.oferta.disponibilidad}" >
                    <c:out value="${articulo.precio} €"/>
               </c:if>
              </td>
        </tr>
        <tr>
            <th>Stock</th>
            <td>${articulo.stock}</td>
        </tr>
        <tr>
            <th>Estado</th>
            <td>${articulo.tipo}</td>
        </tr>
        <tr>
            <th>Gastos de envío</th>
            <td>${articulo.gastoEnvio} €</td>
        </tr>
        <tr>
            <th>Tiempo de Entrega</th>
            <td>${articulo.tiempoEntrega} días</td>
        </tr>
        </table>
</dpc:layout>