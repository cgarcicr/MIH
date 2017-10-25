package com.sophos.semih.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validador que permite identificar si una cadena puede ser número de documento o no
 * 
 * @author sebastian.duque
 * 
 */
@FacesValidator("numeroDocumentoValidator")
public class NumeroDocumentoValidator implements Validator {
	private static final String regex = "^[-\\w]{1,20}$";
	private Pattern pattern;
	private Matcher matcher;				

	/**
	 * Constructor
	 */
	public NumeroDocumentoValidator() {
		pattern = Pattern.compile(regex);		
	}

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
				
		matcher = pattern.matcher(value.toString());
		if (!matcher.matches()) {
			FacesMessage msg = new FacesMessage(
					"No se pudo validar el número de documento", "El número de documento contiene caracteres inválidos o espacios");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}

}
