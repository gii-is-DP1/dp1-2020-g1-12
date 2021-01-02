package org.springframework.samples.dpc.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/error")
@Controller
public class CustomErrorController implements ErrorController {

	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	@GetMapping()
	public String handleError(HttpServletRequest request, Exception ex) {
		// get error status
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
			int statusCode = Integer.parseInt(status.toString());

			// display specific error page
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				request.setAttribute("mensaje", "Lo sentimos. La dirección web que has especificado no es "
						+ "una página activa de nuestra web.");
				request.setAttribute("gif", "/resources/images/404.gif");
				return "exception";
			}
			else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				request.setAttribute("mensaje", "¡Vaya! Parece que estamos teniendo problemas 😔 . Vuelve "
						+ "a intentarlo más tarde.");
				request.setAttribute("gif", "/resources/images/500.gif");
				return "exception";
			}
			else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				request.setAttribute("mensaje", "Lo sentimos. Parece ser que no tienes permisos "
						+ "para acceder a esta página.");
				request.setAttribute("gif", "/resources/images/403.gif");
				return "exception";
			}
			else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
				request.setAttribute("mensaje", "El párametro de búsqueda introducido no es válido.");
				request.setAttribute("gif", "/resources/images/error.gif");
				return "exception";				
			}
		}
		request.setAttribute("mensaje", "¡Vaya! Ha ocurrido un error...");
		request.setAttribute("gif", "/resources/images/error.gif");
		return "exception";
	}
}
