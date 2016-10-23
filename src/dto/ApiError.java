package dto;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ApiError {
	public String error;

	public ApiError(String message) {
		this.error = message;
	}

	public WebApplicationException toWAE(int status) {
		return new WebApplicationException(Response.status(status)
		                                           .entity(this)
		                                           .build());
	}
}
