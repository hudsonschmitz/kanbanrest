package kanbanrest;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import kanbanrest.request.ColunaRequest;
import kanbanrest.response.ColunaResponse;
import kanbanrest.service.ColunaService;
import org.jboss.logging.Logger;

@Path("/coluna/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Coluna {


  private static final Logger LOG = Logger.getLogger(Coluna.class);
  private final ColunaService colunaService;

  public Coluna(ColunaService colunaService) {
    this.colunaService = colunaService;
  }

  @GET
  @Path("{idKanban}/{id}")
  public ColunaResponse buscaColunaPorId(@PathParam(value = "idKanban") Long idKanban, @PathParam(value = "id") Long id) {
    LOG.info("buscaColunaPorId: Buscando coluna: " + id);
    return colunaService.buscaColunaPorId(idKanban, id);
  }

  @POST
  @Path("{idKanban}")
  public Response insereColuna(@PathParam(value = "idKanban") long idKanban, ColunaRequest colunaRequest) {
    LOG.info("insereColuna:     invocando service");
    colunaService.insereColuna(idKanban, colunaRequest);
    return Response.status(201).build();
  }

  @PATCH
  @Path("{idKanban}/{id}")
  public Response alteraColuna(@PathParam(value = "idKanban") long idKanban, @PathParam(value = "id") long id, ColunaRequest colunaRequest) {
    LOG.info("alteraColuna:     invocando service");
    colunaService.alteraColuna(idKanban, id, colunaRequest);
    return Response.status(200).build();
  }

}
