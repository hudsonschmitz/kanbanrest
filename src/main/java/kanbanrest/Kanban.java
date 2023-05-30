package kanbanrest;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import kanbanrest.model.KanbanModel;

@Path("/kanban")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Kanban {

  @GET
  @Path("/{id}")
  public PanacheEntityBase buscarPorId(@PathParam(value = "id") long id) {
    return KanbanModel.findById(id);
  }
  
  @POST
  @Transactional
  public Response criarKanban() {
    KanbanModel kanban = new KanbanModel();
    kanban.persist();
    return Response.status(201).build();
  }
  
}
