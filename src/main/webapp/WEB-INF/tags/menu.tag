<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<petclinic:menuItem active="${name eq 'inicio'}" url="/"
					title="home page">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Inicio</span>
				</petclinic:menuItem>


				<sec:authorize access="hasAuthority('moderador')">
					<petclinic:menuItem active="${name eq 'moderador'}" url="/solicitudes" title="Solicitudes">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>Solicitudes</span>
					</petclinic:menuItem>
					
					<petclinic:menuItem active="${name eq 'clientes'}" url="/clientes"
						title="Listado de clientes">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>Listado de clientes</span>
					</petclinic:menuItem>
				
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('vendedor')">
					<petclinic:menuItem active="${name eq 'vendedores'}" url="/vendedores/articulosEnVenta" title="Artículos en venta">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>Artículos en venta</span>
					</petclinic:menuItem>
					<petclinic:menuItem active="${name eq 'vendedores'}" url="/solicitudes/new" title="Creación solicitudes">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>Creación solicitudes</span>
					</petclinic:menuItem>					
				</sec:authorize>
				
				<petclinic:menuItem active="${name eq 'error'}" url="/oups"
					title="trigger a RuntimeException to see how it is handled">
					<span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span>
					<span>Error</span>
				</petclinic:menuItem>

			</ul>




			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Login</a></li>
					<li><a href="<c:url value="/users/new" />">Register</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span> 
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
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
													class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
<!-- 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-primary btn-block">My Profile</a>
												<a href="#" class="btn btn-danger btn-block">Change
													Password</a>
											</p>
										</div>
									</div>
								</div>
							</li>
-->
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
