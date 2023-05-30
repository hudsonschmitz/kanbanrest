package kanbanrest.exception.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import kanbanrest.exception.CampoObrigatorioException;

public class CampoObrigatorioExceptionHandler implements ExceptionMapper<CampoObrigatorioException> {
  
  private CampoObrigatorioExceptionHandler() {
    
  }

  @Override
  public Response toResponse(CampoObrigatorioException exception) {
    return Response.status(404).entity(exception.getMessage()).build();
  }

}
