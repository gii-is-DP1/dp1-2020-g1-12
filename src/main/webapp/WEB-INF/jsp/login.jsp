<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags"%>

<dpc:layout pageName="login">
	<div class="container">
		<div class="row justify-content-center align-items-center"
			style="height: 100vh">
			<div class="col-4">
				<div class="card">
					<div class="card-body">
						<h2>Iniciar Sesión</h2>

						<form:form modelAttribute="usuario" action="/loginForm" class="form-horizontal">
                            <div class="form-group">
								<p style="color: red"><c:out value="${mensaje}"></c:out></p>
								<br>
								<label for="username">Usuario</label>
								<input placeholder="Usuario" name="username" />
								<br>
								<label for="paswword">Contraseña:</label> 
								<input type="password" placeholder="Contraseña" name="password" />
							</div>
                            <div class="form-group">
								<button class="btn btn-default" type="submit">Iniciar Sesión</button>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</dpc:layout>