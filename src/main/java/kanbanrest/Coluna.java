package kanbanrest;

import java.util.logging.Logger;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import kanbanrest.exception.NenhumRegistroException;
import kanbanrest.model.ColunaModel;
import kanbanrest.request.ColunaRequest;
import kanbanrest.service.ColunaService;

@Path("/kanban/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Coluna {


  private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(Coluna.class);
  @Inject
  ColunaService colunaService;

  @GET
  @Path("{idKanban}/coluna/{id}")
  public ColunaModel buscaColunaPorId(@PathParam(value = "idKanban") long idKanban, @PathParam(value = "id") long id) {
    LOG.info("buscaColunaPorId: Buscando coluna. kanban: " + idKanban + " coluna: " + id);
    return ColunaModel.findById(idKanban, id);
  }

  @POST
  @Path("{idKanban}/coluna/")
  public Response insereColuna(@PathParam(value = "idKanban") long idKanban, ColunaRequest colunaRequest) {
    LOG.info("insereColuna:     invocando service");
    colunaService.insereColuna(idKanban, colunaRequest);
    return Response.status(201).build();
  }

  @PATCH
  @Path("{idKanban}/coluna/{id}")
  public Response alteraColuna(@PathParam(value = "idKanban") long idKanban, @PathParam(value = "id") long id, ColunaRequest colunaRequest) {
    LOG.info("alteraColuna:     invocando service");
    colunaService.alteraColuna(idKanban, id, colunaRequest);
    return Response.status(201).build();
  }

}
