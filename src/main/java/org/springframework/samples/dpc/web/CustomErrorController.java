package org.springframework.samples.dpc.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/error")
@Controller
public class CustomErrorController implements ErrorController {

	private static final String MENSAJE = "mensaje";
	private static final String GIF = "gif";
	
	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	@GetMapping()
	public String handleError(HttpServletRequest request, Exception ex) {
		log.warn("Se ha entrado en el controlador de Excepciones.");

		// get error status
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
			int statusCode = Integer.parseInt(status.toString());

			// display specific error page
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				log.info("La excepción es página no encontrada, ERROR 404.");

				request.setAttribute(MENSAJE, "Lo sentimos. La dirección web que has especificado no es "
						+ "una página activa de nuestra web.");
				request.setAttribute(GIF, "/resources/images/404.gif");
				return "exception";
			}
			else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				log.info("La excepción es error del servidor, ERROR 500.");

				request.setAttribute(MENSAJE, "¡Vaya! Parece que estamos teniendo problemas 😔 . Vuelve "
						+ "a intentarlo más tarde.");
				request.setAttribute(GIF, "/resources/images/500.gif");
				return "exception";
			}
			else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				log.info("La excepción es prohibido el acceso por permisos, ERROR 403.");

				request.setAttribute(MENSAJE, "Lo sentimos. Parece ser que no tienes permisos "
						+ "para acceder a esta página.");
				request.setAttribute(GIF, "/resources/images/403.gif");
				return "exception";
			}
			else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
				log.info("La excepción es parámetro de búsqueda no váldio, ERROR 400.");

				request.setAttribute(MENSAJE, "El párametro de búsqueda introducido no es válido.");
				request.setAttribute(GIF, "/resources/images/error.gif");
				return "exception";				
			}
		}
		log.warn("La excepción es indefinida.");

		request.setAttribute(MENSAJE, "¡Vaya! Ha ocurrido un error...");
		request.setAttribute(GIF, "/resources/images/error.gif");
		return "exception";
	}
}
