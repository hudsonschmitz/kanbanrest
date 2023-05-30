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

  @Inject
  ColunaService colunaService;

  @GET
  @Path("{idKanban}/coluna/{id}")
  public ColunaModel getColuna(@PathParam(value = "idKanban") long idKanban, @PathParam(value = "id") long id) {
    Logger.getAnonymousLogger().warning("Chegou aqui. :" + idKanban + " " + id);
    return ColunaModel.findById(idKanban, id);
  }

  @POST
  @Path("{idKanban}/coluna/")
  public Response insereColuna(@PathParam(value = "idKanban") long idKanban, ColunaRequest colunaRequest) {
    colunaService.insereColuna(idKanban, colunaRequest);
    return Response.status(201).build();
  }

  @PATCH
  @Path("{idKanban}/coluna/{id}")
  public Response alteraColuna(@PathParam(value = "idKanban") long idKanban, @PathParam(value = "id") long id, ColunaRequest colunaRequest) {
    colunaService.alteraColuna(idKanban, id, colunaRequest);
    return Response.status(201).build();
  }

}
