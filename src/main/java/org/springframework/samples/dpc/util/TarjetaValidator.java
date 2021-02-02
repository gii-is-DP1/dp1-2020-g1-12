package org.springframework.samples.dpc.util;

import java.util.Calendar;

import org.springframework.samples.dpc.model.TarjetaCredito;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TarjetaValidator implements Validator {

	@Override
	public void validate(Object obj, Errors errors) {
		TarjetaCredito tarjeta = (TarjetaCredito) obj;
		String titular = tarjeta.getTitular();
		if(titular == null || titular.length() < 8 || titular.length() > 50 || !titular.matches("^[a-zA-Z áéíóúÁÉÍÓÚäëïöüÄËÏÖÜàèìòùÀÈÌÒÙñÑ]+$")) {
			errors.rejectValue("titular", "titular",
					"El campo titular es obligatorio y debe estar comprendido entre 8 y 50 caracteres no numéricos.");
		}
		String numero = tarjeta.getNumero();
		if(numero == null || numero.length() != 16 || !isNumeric(numero)) {
			errors.rejectValue("numero", "El campo numero es obligatorio y debe ser un número de longitud 16",
					"El campo numero es obligatorio y debe ser un número de longitud 16");
		}else {
			if(!algoritmoDeLuhm(numero)) {
				errors.rejectValue("numero", "El número de la tarjeta de crédito no es válido.","El número de la tarjeta de crédito no es válido.");
			}
		}
		String cvv = tarjeta.getCvv();
		if(cvv == null || cvv.length() != 3 || !isNumeric(cvv)) {
			errors.rejectValue("cvv", "El campo cvv es obligatorio y debe ser un número de longitud 3",
					"El campo cvv es obligatorio y debe ser un número de longitud 3");
		}
		String fecha = tarjeta.getFechaCaducidad();
		if(fecha == null || fecha.equals("") || !fecha.contains("/")) { // se puede poner con versión de java superiores fechas.isBlank()
			errors.rejectValue("fechaCaducidad", "El campo fecha es obligatorio y debe ser una fecha válida",
					"El campo fecha es obligatorio y debe ser una fecha válida");
		} else {
			String[] trozos = tarjeta.getFechaCaducidad().split("/");
			String mes = trozos[0];
			String anyo = trozos[1];
			
			if(mes.length() != 2 || anyo.length() != 2 || !isNumeric(mes) || !isNumeric(anyo)) {
				errors.rejectValue("fechaCaducidad", "El mes y el año deben ser válidos",
						"El mes y el año deben ser válidos");
			} else {
				Integer numMes = Integer.valueOf(mes);
				Integer numAnyo = Integer.valueOf(anyo);
				Calendar cal = Calendar.getInstance();
				Integer anyoActual = cal.get(Calendar.YEAR) - 2000;
				Integer mesActual = cal.get(Calendar.MONTH);
				if(numMes > 12 || numAnyo < anyoActual || (numAnyo.equals(anyoActual) && numMes < mesActual)) {
					errors.rejectValue("fechaCaducidad", "La fecha debe ser posterior a la actual",
							"La fecha debe ser posterior a la actual");
				}
			}
		}
	}
	
	private static boolean isNumeric(String cadena){
		try {
			Long.parseLong(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
	
	private boolean algoritmoDeLuhm(String cadena){
		int suma=0;
		for(Integer i = 0; i <= 15;i++) {
			int x = cadena.charAt(i) - 48; // el método char devuelve el número en código ASCII por eso se le resta 48 
			if(i%2==0) {
				if(x<5)	suma += (x * 2);
				else {
					String mul = String.valueOf(x*2);
					suma += (mul.charAt(0)-48 + mul.charAt(1)-48);
					
				}
			}else {
				suma += cadena.charAt(i)-48;
			}
		}
		return suma%10==0; 
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return TarjetaCredito.class.isAssignableFrom(clazz);
	}
	
}
