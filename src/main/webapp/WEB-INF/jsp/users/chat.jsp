<%@ page session="true" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%-- The above 4 meta tags *must* come first in the head; any other head content must come *after* these tags --%>

    <spring:url value="/resources/images/fav.png" var="favicon"/>
    <link rel="shortcut icon" type="image/x-icon" href="${favicon}">

    <title>DPC</title>

    <%-- CSS para datepicker de tarjeta de crédito --%>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.2.0/css/datepicker.min.css" rel="stylesheet">

    <%-- CSS generated from LESS --%>
    <%--<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">--%>
    <spring:url value="/resources/css/petclinic.css" var="dpcCss"/>
    <link href="${dpcCss}" rel="stylesheet"/>    

    <%-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries --%>
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="page-content page-container" id="page-content">
		<div class="padding">
			<div class="row container d-flex justify-content-center">
				<div class="col-md-6">
					<div class="card card-bordered">
						<div class="card-header">
							<h4 class="card-title">
								<strong>Chat con <c:out value="${nombre}"></c:out></strong>
							</h4>
						</div>
						<div class="ps-container ps-theme-default ps-active-y"
							id="chat-content"
							style="overflow-y: scroll !important; height: 400px !important;">
							<c:forEach items="${mensajes}" var="mensaje">
								<c:if test="${mensaje.emisor != dni}">
									<div class="media media-chat">
										<img class="avatar"
											src="https://img.icons8.com/color/36/000000/administrator-male.png"
											alt="...">
											<span><c:out value="${nombre}"></c:out></span>
										<div class="media-body">
											<p><c:out value="${mensaje.texto}"></c:out></p>	
											<p class="meta"><c:out value="${mensaje.fechaEnvio}"></c:out></p>
										</div>
									</div>
								</c:if>
								<c:if test="${mensaje.emisor == dni}">
									<div class="media media-chat media-chat-reverse">
										<div class="media-body">
											<p><c:out value="${mensaje.texto}"></c:out></p>	
											<p class="meta"><c:out value="${mensaje.fechaEnvio}"></c:out></p>
										</div>
									</div>
								</c:if>
							</c:forEach>
							<div class="ps-scrollbar-x-rail" style="left: 0px; bottom: 0px;">
								<div class="ps-scrollbar-x" tabindex="0"
									style="left: 0px; width: 0px;"></div>
							</div>
							<div class="ps-scrollbar-y-rail"
								style="top: 0px; height: 0px; right: 2px;">
								<div class="ps-scrollbar-y" tabindex="0"
									style="top: 0px; height: 2px;"></div>
							</div>
						</div>
						<div class="publisher bt-1 border-light">
							<img class="avatar avatar-xs"
								src="https://img.icons8.com/color/36/000000/administrator-male.png"
								alt="..."> 
								 <form:form modelAttribute="nuevoMensaje" action="/chat/${rol}/${receptorId}/${id}">
									<input name="texto" class="publisher-input" type="text" placeholder="Escribe un mensaje">
									<a class="publisher-btn" href="#" data-abc="true"></a>
									<button class="glyphicon glyphicon-send"></button>
								</form:form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>