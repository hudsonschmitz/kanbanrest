package kanbanrest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import kanbanrest.request.KanbanRequest;
import kanbanrest.response.KanbanResponse;
import kanbanrest.service.KanbanService;
import org.jboss.logging.Logger;

@Path("/kanban")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Kanban {

  @Inject
  KanbanService kanbanService;

  private static final Logger LOG = Logger.getLogger(Kanban.class);
  @GET
  @Path("/{id}")
  public KanbanResponse buscarPorId(@PathParam(value = "id") Long id) {
    LOG.info("buscarPorId: Buscando kanban por id: " + id);
    return kanbanService.buscarKanbanPorId(id);
  }
  
  @POST
  public Response criarKanban(KanbanRequest kanbanRequest) {
    KanbanResponse kanbanResponse = kanbanService.criarKanban(kanbanRequest);
    return Response.status(201).entity(kanbanResponse).build();
  }
  
}
