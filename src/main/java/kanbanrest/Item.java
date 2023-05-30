package kanbanrest;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import kanbanrest.exception.NenhumRegistroException;
import kanbanrest.model.ColunaModel;
import kanbanrest.model.ItemModel;
import kanbanrest.request.ItemRequest;

import java.util.List;

@Path("/kanban/{idKanban}/coluna/{idColuna}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Item {

  @GET
  @Path("/item")
  public List<ItemModel> buscaItens(@PathParam(value = "idKanban") long idKanban, @PathParam(value = "idColuna") long idColuna) {
    return ItemModel.findAll(idKanban, idColuna);
  }

  @GET
  @Path("/item/{id}")
  public ItemModel buscaItemPorId(@PathParam(value = "idKanban") long idKanban, @PathParam(value = "idColuna") long idColuna, @PathParam(value = "id") long id) {
    return ItemModel.buscaItemPorId(idKanban, idColuna, id);
  }

  @POST
  @Path("/item/")
  @Transactional
  public Response insereItem(@PathParam(value = "idKanban") long idKanban, @PathParam(value = "idColuna") long idColuna, ItemRequest itemRequest) {
    ItemModel itemModel = itemRequest.toModel();
    itemModel.validate();

    ColunaModel coluna = ColunaModel.findById(idKanban, idColuna);
    itemModel.setColuna(coluna);
    coluna.getItens().add(itemModel);
    return Response.status(201).build();
  }

  @PUT
  @Path("/item/{id}")
  @Transactional
  public Response alteraItem(@PathParam(value = "idKanban") long idKanban, @PathParam(value = "idColuna") long idColuna, @PathParam(value = "id") long id, @QueryParam(value = "idColunaDestino") long idColunaDestino) {
    ColunaModel colunaAtual = ColunaModel.findById(idKanban, idColuna);
    ColunaModel colunaDestino = ColunaModel.findById(idKanban, idColunaDestino);

    ItemModel itemAtual = colunaAtual.getItens().stream().filter((item) -> item.getId() == id).findFirst().orElseThrow(() -> new NenhumRegistroException("Nenhum item encontrado para esse id."));
    colunaAtual.getItens().remove(itemAtual);

    colunaDestino.getItens().add(itemAtual);
    return Response.status(201).build();
  }
}
