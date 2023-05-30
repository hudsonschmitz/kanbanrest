package kanbanrest.exception.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import kanbanrest.exception.NenhumRegistroException;

public class NenhumRegistroExceptionHandler implements ExceptionMapper<NenhumRegistroException> {

  @Override
  public Response toResponse(NenhumRegistroException exception) {
    return Response.status(404).entity(exception.getMessage()).build();
  }

}
