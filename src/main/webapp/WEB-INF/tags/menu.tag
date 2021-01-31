<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">

	<div class="container">
		<div class="navbar-header">
			<a
				href="<spring:url value="/" htmlEscape="true" />"><span>
				<img style="width:125px;height:60px;margin-top:7px;margin-right:10px" 
					onmouseout="this.src='/resources/images/nav-logo.png';"
					onmouseover="this.src='/resources/images/nav-logo2.png';" 
					src='/resources/images/nav-logo.png' alt="Logo.png"/></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<dpc:menuItem active="${name eq 'articulos'}" url="/"
					title="home page">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Inicio</span>
				</dpc:menuItem>			

				<sec:authorize access="hasAuthority('cliente')">
					<dpc:menuItem active="${name eq 'pedidos'}" url="/pedidos"
						title="Mis pedidos">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>Mis pedidos</span>
					</dpc:menuItem>
				</sec:authorize>

				<sec:authorize access="hasAuthority('moderador')">
					<dpc:menuItem active="${name eq 'solicitudes'}" url="/solicitudes" title="Solicitudes">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>Solicitudes</span>
					</dpc:menuItem>
					
					<dpc:menuItem active="${name eq 'listadoClientes'}" url="/clientes"
						title="Listado de clientes">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>Listado de clientes</span>
					</dpc:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('vendedor')">
					<!--<dpc:menuItem active="${name eq 'articulosEnVenta'}" url="/vendedores/articulosEnVenta" title="Artículos en venta">
						<span>Artículos en venta</span>
					</dpc:menuItem> -->
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-tag"></span> Artículos
						<span class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-md-10 col-sm-offset-1">
												<p class="text-left">
													<a href="<c:url value="/vendedores/articulosEnVenta" />"
														class="btn btn-primary btn-block btn-sm">En Venta</a>
												</p>
											<p class="text-left">
													<a href="<c:url value="/vendedores/articulosVendidos" />"
														class="btn btn-primary btn-block btn-sm">Vendidos</a>
											</p>
										</div>
									</div>
								</div>
							</li>
						</ul></li>
					<dpc:menuItem active="${name eq 'crearSolicitud'}" url="/solicitudes/new" title="Crear solicitud">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>Crear solicitud</span>
					</dpc:menuItem>
					<dpc:menuItem active="${name eq 'solicitudes'}" url="/vendedores/listadoSolicitudes" title="Mis solicitudes">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>Mis solicitudes</span>
					</dpc:menuItem>
				</sec:authorize>
				
				<dpc:menuItem active="${name eq 'error'}" url="/error"
					title="trigger a RuntimeException to see how it is handled">
					<span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span>
					<span>Error</span>
				</dpc:menuItem>

			</ul>




			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Iniciar Sesión</a></li>
					<li><a href="<c:url value="/registro" />">Registrarse</a></li>

				</sec:authorize>
				<sec:authorize access="isAuthenticated()">	
					<sec:authorize access="hasAuthority('cliente')">
						<li>
							<a style="color:white; text-decoration: none" href="/cesta">
                            <span class="glyphicon glyphicon-shopping-cart">
                            <strong> Cesta </strong></span>
                            <span class="badge badge-pill badge-success">
                            <strong>${sessionScope.contador}</strong></span></a>
						</li>
					</sec:authorize>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-md-10 col-sm-offset-1">
											<sec:authorize access="hasAuthority('cliente')">
												<p class="text-left">
													<a href="<c:url value="/clientes/perfil" />"
														class="btn btn-primary btn-block btn-sm">Perfil</a>
												</p>
											</sec:authorize>
											<sec:authorize access="hasAuthority('vendedor')">
												<p class="text-left">
													<a href="<c:url value="/vendedores/perfil" />"
														class="btn btn-primary btn-block btn-sm">Perfil</a>
												</p>
											</sec:authorize>
											<sec:authorize access="hasAuthority('moderador')">
												<p class="text-left">
													<a href="<c:url value="/moderadores/perfil" />"
														class="btn btn-primary btn-block btn-sm">Perfil</a>
												</p>
											</sec:authorize>																						
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Cerrar Sesión</a>
											</p>
										</div>
									</div>
								</div>
							</li>
						</ul></li>
				</sec:authorize>
			</ul>
		</div>
	</div>
</nav>
