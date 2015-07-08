package org.adorsys.adcore.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * 
 * @author guymoyo
 *
 */
@Provider
public class ExceptionHandler implements ExceptionMapper<AdException> {
  
    @Override
    public Response toResponse(AdException exception) {
        Response.Status httpStatus = Response.Status.INTERNAL_SERVER_ERROR;
  
        if (exception instanceof AdException)
            httpStatus = Response.Status.BAD_REQUEST;
  
        return Response.status(httpStatus).entity(exception.getMessage()).type("text/plain").build();
    }
}