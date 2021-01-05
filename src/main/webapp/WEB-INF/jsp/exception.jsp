<%@ page session="true" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="error">

	<h2>${mensaje}</h2>
	<a href="/"><button class="btn btn-default" >Volver al inicio</button></a>
	<br><br>
    <img style="width:700px; height:500px" src="${gif}"/>

</dpc:layout>
