package kanbanrest.exception.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import kanbanrest.exception.NenhumRegistroException;

public class NenhumRegistroExceptionHandler implements ExceptionMapper<NenhumRegistroException> {

  private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(NenhumRegistroExceptionHandler.class);

  @Override
  public Response toResponse(NenhumRegistroException exception) {
    LOG.error("toResponse: Campo nome da coluna é obrigatório.", exception);
    return Response.status(404).entity(exception.getMessage()).build();
  }

}
