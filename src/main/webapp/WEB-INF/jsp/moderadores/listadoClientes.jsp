<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>

<dpc:layout pageName="listadoClientes">
    <jsp:attribute name="customScript">
        <script>
	        function alertaDesbloqueo() {
	        	var opcion = confirm('¿Seguro que desea desbloquear al usuario?');
	        	return opcion;
	        }
        </script>
    </jsp:attribute>
    <jsp:body>
    <p style="color: red"><c:out value="${mensaje}"></c:out></p>
		<form action="/clientes?" method="get">
			<div style="float:right">
				<input type="hidden" name="clientPage" value="${clientes.getNumber()}"/>
				<input type="hidden" name="clientSize" value="${clientes.getSize()}"/>			
				<input type="hidden" name="sellerPage" value="${vendedores.getNumber()}"/>
				<input type="hidden" name="sellerSize" value="${vendedores.getSize()}"/>
				<input type="hidden" name="orderSellerBy" value="${ordenacionVendedor}"/>			
		 		<select onchange="this.form.submit();" name="orderClientBy">
		       		<option value="" disabled selected>Ordenar clientes por:</option>
		 			<option value="nombre">Nombre A-Z</option>
		 			<option value="-nombre">Nombre Z-A</option>
		 			<option value="apellido">Apellido A-Z</option>
		 			<option value="-apellido">Apellido Z-A</option>
		 			<option value="dni">DNI de más bajo a más alto</option>
		 			<option value="-dni">DNI de más alto a más bajo</option>
		 		</select>
			</div>
		</form>
	    <h2>Listado de clientes</h2>
	
	    <table id="clientesTable" class="table table-striped">
	        <thead>
	        <tr>
	            <th style="width: 150px;">Nombre</th>
	            <th style="width: 200px;">Apellidos</th>
	            <th>Dni</th>
	            <th style="width: 120px">Teléfono</th>
	            <th>Dirección</th>
	            <th style="width: 120px">Email</th>
	            <th style="width: 150px">Bloquear Actividad</th>
	        </tr>
	        </thead>
	        <tbody>
	      <c:forEach items="${clientes.getContent()}" var="clientes">
	            <tr>
	                <td>
	                    <c:out value="${clientes.nombre}"/>
	                </td>
	                <td>
	                    <c:out value="${clientes.apellido}"/>
	                </td>
	                <td>
	                    <c:out value="${clientes.dni}"/>
	                </td>
	                <td>
	                    <c:out value="${clientes.telefono}"/>
	                </td>
	                <td>
	                    <c:out value="${clientes.direccion}"/>
	                </td>
	                <td>
	                    <c:out value="${clientes.email}"/>
	                </td>                              
	                <c:choose>
	                    <c:when test="${clientes.bloqueo.bloqueado == false}">
					 		<spring:url value="/bloqueos/{bloqueoId}" var="bloqueoClienteUrl">
					              <spring:param name="bloqueoId" value="${clientes.bloqueo.id}"/>
					        </spring:url>
					        <td>
								<a href="${fn:escapeXml(bloqueoClienteUrl)}">
									<button class="btn btn-default" type="submit">Bloquear</button>
								</a>
							</td>
			            </c:when>
						<c:otherwise>
					 		<spring:url value="/bloqueos/desbloquear/{bloqueoId}" var="bloqueoClienteUrl">
					              <spring:param name="bloqueoId" value="${clientes.bloqueo.id}"/>
							</spring:url>
					        <td>
								<a href="${fn:escapeXml(bloqueoClienteUrl)}">
									<button onclick="return alertaDesbloqueo()" class="btn btn-default" type="submit">Desbloquear</button>
								</a>
							</td>
						</c:otherwise>
	                </c:choose>
	            </tr>
	        </c:forEach> 
	           
	        </tbody>
	    </table>
		<div class="container">
			<div class="row text-center">
				<form action="/clientes?" method="get">
					<input type="hidden" name="clientPage" value="${clientes.getNumber()}"/>
					<input type="hidden" name="orderClientBy" value="${ordenacionCliente}"/>
					<input type="hidden" name="sellerPage" value="${vendedores.getNumber()}"/>
					<input type="hidden" name="sellerSize" value="${vendedores.getSize()}"/>
					<input type="hidden" name="orderSellerBy" value="${ordenacionVendedor}"/>
					<label for="size">Elementos por página: </label>
					<input onchange="this.form.submit();" style="border-radius: 5px; width:10%; text-align: center"
					 type="number" min="2" name="clientSize" value="${clientes.getSize()}">
				 </form>
				<ul class="pagination">
					<c:forEach begin="0" end="${clientes.getTotalPages()-1}" varStatus="page">
						<c:if test="${page.index != clientes.getNumber()}">
							<spring:url value="/clientes?" var="siguienteUrl">
								<spring:param name="clientPage" value="${page.index}"/>
								<spring:param name="clientSize" value="${clientes.getSize()}"/>
								<spring:param name="orderClientBy" value="${ordenacionCliente}"/>	
								<spring:param name="sellerPage" value="${vendedores.getNumber()}"/>
								<spring:param name="sellerSize" value="${vendedores.getSize()}"/>
								<spring:param name="orderSellerBy" value="${ordenacionVendedor}"/>									
							</spring:url>
							<li><a href="${fn:escapeXml(siguienteUrl)}">${page.index+1}</a></li>
						</c:if> 
						<c:if test="${page.index == clientes.getNumber()}">
							<li class="disabled"><a href="#">${page.index+1}</a></li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
		<br>
		<form action="/clientes?" method="get">
			<div style="float:right">
				<input type="hidden" name="sellerPage" value="${vendedores.getNumber()}"/>
				<input type="hidden" name="sellerSize" value="${vendedores.getSize()}"/>	
				<input type="hidden" name="clientPage" value="${clientes.getNumber()}"/>
				<input type="hidden" name="clientSize" value="${clientes.getSize()}"/>		
				<input type="hidden" name="orderClientBy" value="${ordenacionCliente}"/>				
		 		<select onchange="this.form.submit();" name="orderSellerBy">
		       		<option value="" disabled selected>Ordenar vendedores por:</option>
		 			<option value="nombre">Nombre A-Z</option>
		 			<option value="-nombre">Nombre Z-A</option>
		 			<option value="apellido">Apellido A-Z</option>
		 			<option value="-apellido">Apellido Z-A</option>
		 			<option value="dni">DNI de más bajo a más alto</option>
		 			<option value="-dni">DNI de más alto a más bajo</option>
		 		</select>
			</div>
		</form>
	    <h2>Listado de vendedores</h2>
	
	    <table id="vendedoresTable" class="table table-striped">
	        <thead>
	        <tr>
	            <th style="width: 150px;">Nombre</th>
	            <th style="width: 200px;">Apellidos</th>
	            <th>Dni</th>
	            <th style="width: 120px">Teléfono</th>
	            <th>Dirección</th>
	            <th style="width: 120px">Email</th>
	            <th style="width: 150px">Bloquear Actividad</th>
	        </tr>
	        </thead>
	        <tbody>
	      <c:forEach items="${vendedores.getContent()}" var="vendedores">
	            <tr>
	  
	                <td>
	                    <c:out value="${vendedores.nombre}"/>
	                </td>
	                <td>
	                    <c:out value="${vendedores.apellido}"/>
	                </td>
	                <td>
	                    <c:out value="${vendedores.dni}"/>
	                </td>
	                <td>
	                    <c:out value="${vendedores.telefono}"/>
	                </td>
	                <td>
	                    <c:out value="${vendedores.direccion}"/>
	                </td>
	                <td>
	                    <c:out value="${vendedores.email}"/>
	                </td>                                                
	                 
	                <c:choose>
	                    <c:when test="${vendedores.bloqueo.bloqueado == false}">
			 				<spring:url value="/bloqueos/{bloqueoId}" var="bloqueoVendedorUrl">
			              		<spring:param name="bloqueoId" value="${vendedores.bloqueo.id}"/>
			           		</spring:url>
			        		
			        		<td>
							<a href="${fn:escapeXml(bloqueoVendedorUrl)}"><button class="btn btn-default" type="submit">Bloquear</button></a>
			            	</td>
			            </c:when>
	                    <c:otherwise>
			 				<spring:url value="/bloqueos/desbloquear/{bloqueoId}" var="bloqueoVendedorUrl">
			              		<spring:param name="bloqueoId" value="${vendedores.bloqueo.id}"/>
			           		</spring:url>
			        		
			        		<td>
							<a href="${fn:escapeXml(bloqueoVendedorUrl)}">
							<button onclick="return alertaDesbloqueo()" class="btn btn-default" type="submit">Desbloquear</button>
							</a>
			            	</td>
			            </c:otherwise>
	                </c:choose>
	            </tr>
	        </c:forEach> 
	        </tbody>
	    </table>
		<div class="container">
			<div class="row text-center">
				<form action="/clientes?" method="get">
					<input type="hidden" name="sellerPage" value="${vendedores.getNumber()}"/>
					<input type="hidden" name="orderSellerBy" value="${ordenacionVendedor}"/>
					<input type="hidden" name="clientPage" value="${clientes.getNumber()}"/>
					<input type="hidden" name="orderClientBy" value="${ordenacionCliente}"/>
					<input type="hidden" name="clientSize" value="${clientes.getSize()}"/>
					<label for="sellerSize">Elementos por página: </label>
					<input onchange="this.form.submit();" style="border-radius: 5px; width:10%; text-align: center"
					 type="number" min="2" name="sellerSize" value="${vendedores.getSize()}">
				 </form>
				<ul class="pagination">
					<c:forEach begin="0" end="${vendedores.getTotalPages()-1}" varStatus="page">
						<c:if test="${page.index != vendedores.getNumber()}">
							<spring:url value="/clientes?" var="siguienteUrl">
								<spring:param name="sellerPage" value="${page.index}"/>
								<spring:param name="sellerSize" value="${vendedores.getSize()}"/>
								<spring:param name="orderSellerBy" value="${ordenacionVendedor}"/>		
								<spring:param name="clientPage" value="${clientes.getNumber()}"/>
								<spring:param name="clientSize" value="${clientes.getSize()}"/>
								<spring:param name="orderClientBy" value="${ordenacionCliente}"/>									
							</spring:url>
							
							<li><a href="${fn:escapeXml(siguienteUrl)}">${page.index+1}</a></li>
						</c:if> 
						<c:if test="${page.index == vendedores.getNumber()}">
							<li class="disabled"><a href=#>${page.index+1}</a>
						</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
    </jsp:body> 
</dpc:layout>
