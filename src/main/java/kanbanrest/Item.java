package kanbanrest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import kanbanrest.model.ColunaModel;
import kanbanrest.model.ItemModel;
import kanbanrest.request.ItemRequest;
import kanbanrest.service.ItemService;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/kanban/{idKanban}/item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Item {

  private static final Logger LOG = Logger.getLogger(Item.class);

  private final ItemService itemService;

  public Item(ItemService itemService) {
    this.itemService = itemService;
  }

  @GET
  public List<ItemModel> buscaItens(@QueryParam(value = "idColuna") long idColuna) {
    LOG.info("buscaItens:     Buscando itens da coluna: " + idColuna);
    return ItemModel.buscaItensColuna(ColunaModel.findById(idColuna));
  }

  @GET
  @Path("/{id}")
  public ItemModel buscaItemPorId(@PathParam(value = "id") long id) {
    LOG.info("buscaItemPorId: Buscando item: " + id);
    return ItemModel.buscaItemPorId(id);
  }

  @POST
  public Response insereItem(@PathParam(value = "idKanban") long idKanban, ItemRequest itemRequest) {
    itemService.insereItem(idKanban, itemRequest);
    return Response.status(201).build();
  }

  @PUT
  @Path("/{id}")
  public Response moveItem(@PathParam(value = "id") long id, @QueryParam(value = "idColunaDestino") long idColunaDestino) {
    itemService.moveItem(id, idColunaDestino);
    return Response.status(201).build();
  }
}
