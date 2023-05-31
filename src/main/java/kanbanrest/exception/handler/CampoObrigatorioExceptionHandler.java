package kanbanrest.exception.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import kanbanrest.exception.CampoObrigatorioException;
import kanbanrest.service.ColunaService;

public class CampoObrigatorioExceptionHandler implements ExceptionMapper<CampoObrigatorioException> {

  private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(CampoObrigatorioExceptionHandler.class);

  @Override
  public Response toResponse(CampoObrigatorioException exception) {
    LOG.error("toResponse: Campo nome da coluna é obrigatório.", exception);
    return Response.status(404).entity(exception.getMessage()).build();
  }

}
