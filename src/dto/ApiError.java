package dto;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * An API error to display to the client.
 */
public class ApiError {
	/**
	 * The error message
	 */
	public String error;

	/**
	 * Constructs a new ApiError
	 *
	 * @param message the error message to send
	 */
	public ApiError(String message) {
		this.error = message;
	}

	/**
	 * Transforms this object to a WebApplicationException that can be thrown
	 * form inside an API controller method.
	 *
	 * @param status the status code to use
	 * @return a new WebApplicationException instance
	 */
	public WebApplicationException toWAE(int status) {
		return new WebApplicationException(Response.status(status)
		                                           .entity(this)
		                                           .build());
	}
}
