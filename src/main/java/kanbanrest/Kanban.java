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
import org.jboss.logging.Logger;

@Path("/kanban")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Kanban {

  private static final Logger LOG = Logger.getLogger(Kanban.class);
  @GET
  @Path("/{id}")
  public KanbanModel buscarPorId(@PathParam(value = "id") long id) {
    LOG.info("buscarPorId: Buscando kanban por id: " + id);
    return KanbanModel.findById(id);
  }
  
  @POST
  @Transactional
  public Response criarKanban() {
    KanbanModel kanban = new KanbanModel();
    LOG.info("criarKanban: Criando novo kanban: ");
    kanban.persist();
    return Response.status(201).build();
  }
  
}
