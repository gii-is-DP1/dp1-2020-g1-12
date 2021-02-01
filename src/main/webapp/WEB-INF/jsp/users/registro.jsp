<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="registro">
    <div style="margin-top:5%;text-align:center">
		<h2 style="padding: 50px">Nuevo Usuario</h2>
    	<a href="/registro/cliente"><button class="btn btn-default">Registrarse como cliente</button></a>
    	<a href="/registro/vendedor"><button class="btn btn-default">Registrarse como vendedor</button></a>
   	</div> 
</petclinic:layout>

