package com.twitter.clone.beans.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.twitter.clone.beans.dao.UserDao;

@Component
@Scope("request")
public class UsernameValidator implements Validator {

	@Autowired
	private UserDao userDao;

	@Override
	public void validate(FacesContext ctx, UIComponent component, Object value)
			throws ValidatorException {
		if (userDao.getUserByName((String) value) != null) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error",
					"Entered Username already exists"));
		}

	}

}
