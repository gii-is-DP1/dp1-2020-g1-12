<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags"%>

<dpc:layout pageName="login">

	<form:form modelAttribute="usuario" action="/loginForm" class="form-horizontal">
	<div class="login-wrap">
		<div class="login-html">
			<input id="tab-1" type="radio" name="tab" class="sign-in" checked><label for="tab-1" class="tab">Iniciar Sesión</label>
			<div class="login-form">
				<div class="sign-in-htm">
					<div class="group">
						<label for="user" class="label">Usuario</label>
						<input id="username" type="text" class="input" name="username">
					</div>
					<div class="group">
						<label for="pass" class="label">Contraseña</label>
						<input id="pass" type="password" class="input" name="password">
					</div>
					<div class="group">
						<input type="submit" class="button" value="Iniciar Sesión">
					</div>
					<p style="color:#aaa">¿Aún no tienes una cuenta? <a href="/registro">Regístrate</a></p>
					<p style="color: red"><c:out value="${mensaje}"></c:out></p>
				</div>
			</div>
		</div>
	</div>
	</form:form>

</dpc:layout>