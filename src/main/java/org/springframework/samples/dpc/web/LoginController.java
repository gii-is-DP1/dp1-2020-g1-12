package org.springframework.samples.dpc.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.User;
import org.springframework.samples.dpc.service.BloqueoService;
import org.springframework.samples.dpc.service.CestaService;
import org.springframework.samples.dpc.service.UserService;
import org.springframework.samples.dpc.service.exceptions.UsuarioBloqueadoException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/")
public class LoginController {

	private final BloqueoService bloqueoService;
	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final CestaService cestaService;	
	
	private static final String USUARIO = "usuario";
	private static final String LOGIN = "/login";

	@Autowired
	public LoginController(BloqueoService bloqueoService, AuthenticationManager authenticationManager, 
			UserService userService, CestaService cestaService) {
		this.bloqueoService = bloqueoService;
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.cestaService = cestaService;
	}

	@GetMapping("/login")
	public String login(ModelMap modelMap) {
		log.info("Entrando en la función Iniciar Formulario de Login del controlador de Login.");

		modelMap.addAttribute(USUARIO, new User());

		return LOGIN;
	}

	@PostMapping(value = "/loginForm")
	public String iniciarSesion(HttpServletRequest request, @Valid User user, BindingResult result, 
			ModelMap modelMap) throws Exception {
		log.info("Entrando en la función Procesar Formulario de Login del controlador de Login.");

		UsernamePasswordAuthenticationToken authRequest = 
				new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
		try {
			Authentication authentication = authenticationManager.authenticate(authRequest);
			bloqueoService.usuarioBloqueado(user.getUsername());

			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(authentication);
			
			if(userService.getAuthority().equals("cliente")) {
				request.getSession().setAttribute("contador", cestaService.lineasCesta());
			}
		} catch (BadCredentialsException e) {
			log.info("La función Proceso Formulario de Login ha lanzado la excepción BadCredentials");

			modelMap.addAttribute(USUARIO, user);
			modelMap.addAttribute("mensaje", "El nombre de usuario y la contraseña que ingresaste no coinciden "
					+ "con nuestros registros. Por favor, revisa e inténtalo de nuevo.");
			return LOGIN;
		} catch (UsuarioBloqueadoException e) {
			log.info("La función Proceso Formulario de Login ha lanzado la excepción UsuarioBloqueao");
			
			modelMap.addAttribute(USUARIO, user);
			modelMap.addAttribute("mensaje", "Su usuario ha sido bloqueado. Razón: " + 
					bloqueoService.usuarioBloqueadoMotivo(user.getUsername()));
			return LOGIN;
		}
		return "redirect:/";
	}
	
    @RequestMapping("/logout")
    public void exit(HttpServletRequest request, HttpServletResponse response) {
		log.info("Entrando en la función Cerrar Sesión del controlador de Login.");

        new SecurityContextLogoutHandler().logout(request, null, null);
        try {
            response.sendRedirect("/");
        } catch (IOException e) {
    		log.error("La función Cerrar Sesión ha lanzado una excepción.");
        }
    }
}
