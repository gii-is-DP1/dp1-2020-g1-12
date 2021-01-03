package org.springframework.samples.dpc.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.User;
import org.springframework.samples.dpc.service.BloqueoService;
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

@Controller
@RequestMapping("/")
public class LoginController {

	private BloqueoService bloqueoService;
	private AuthenticationManager authenticationManager;

	@Autowired
	public LoginController(BloqueoService bloqueoService, AuthenticationManager authenticationManager) {
		this.bloqueoService = bloqueoService;
		this.authenticationManager = authenticationManager;
	}

	@GetMapping("/login")
	public String login(ModelMap modelMap) {
		modelMap.addAttribute("usuario", new User());

		return "/login";
	}

	@PostMapping(value = "/loginForm")
	public String iniciarSesion(@Valid User user, BindingResult result, ModelMap modelMap) throws Exception {
		UsernamePasswordAuthenticationToken authRequest = 
				new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
		try {
			Authentication authentication = authenticationManager.authenticate(authRequest);
			bloqueoService.usuarioBloqueado(user.getUsername());

			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(authentication);
		} catch (BadCredentialsException e) {
			modelMap.addAttribute("usuario", user);
			modelMap.addAttribute("mensaje", "El nombre de usuario y la contraseña que ingresaste no coinciden "
					+ "con nuestros registros. Por favor, revisa e inténtalo de nuevo.");
			return "/login";
		} catch (UsuarioBloqueadoException e) {
			modelMap.addAttribute("usuario", user);
			modelMap.addAttribute("mensaje", "Su usuario ha sido bloqueado. Razón: " + 
					bloqueoService.usuarioBloqueadoMotivo(user.getUsername()));
			return "/login";
		}
		return "redirect:/";
	}
	
    @RequestMapping("/logout")
    public void exit(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, null, null);
        try {
            response.sendRedirect(request.getHeader("referer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
