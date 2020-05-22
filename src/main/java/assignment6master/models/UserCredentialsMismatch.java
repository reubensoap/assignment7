package assignment6master.models;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserCredentialsMismatch extends BadCredentialsException {
	public UserCredentialsMismatch(String message) {
		super(message);
	}
}
