package kanbanrest.exception.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import kanbanrest.exception.CampoInvalidoException;
import kanbanrest.response.ResponseError;

@Provider
public class CampoInvalidoExceptionHandler implements ExceptionMapper<CampoInvalidoException> {

  private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(CampoInvalidoExceptionHandler.class);

  @Override
  public Response toResponse(CampoInvalidoException exception) {
    LOG.error("toResponse: Campo nome da coluna é obrigatório.", exception);
    return Response.status(400).entity(new ResponseError(exception.getMessage())).build();
  }

}
