package kanbanrest;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import kanbanrest.exception.NenhumRegistroException;
import kanbanrest.model.ColunaModel;
import kanbanrest.model.ItemModel;
import kanbanrest.request.ItemRequest;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/kanban/{idKanban}/coluna/{idColuna}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Item {

  private static final Logger LOG = Logger.getLogger(Item.class);

  @GET
  @Path("/item")
  public List<ItemModel> buscaItens(@PathParam(value = "idKanban") long idKanban, @PathParam(value = "idColuna") long idColuna) {
    LOG.info("buscaItens:     Buscando itens do kanban: " + idKanban + " coluna: " + idColuna);
    return ItemModel.findAll(idKanban, idColuna);
  }

  @GET
  @Path("/item/{id}")
  public ItemModel buscaItemPorId(@PathParam(value = "idKanban") long idKanban, @PathParam(value = "idColuna") long idColuna, @PathParam(value = "id") long id) {
    LOG.info("buscaItemPorId: Buscando itens do kanban: " + idKanban + " coluna: " + idColuna);
    LOG.info("buscaItemPorId: Buscando item: " + id);
    return ItemModel.buscaItemPorId(idKanban, idColuna, id);
  }

  @POST
  @Path("/item/")
  @Transactional
  public Response insereItem(@PathParam(value = "idKanban") long idKanban, @PathParam(value = "idColuna") long idColuna, ItemRequest itemRequest) {
    LOG.info("insereItem:     Convertendo objeto request em model.");
    ItemModel itemModel = itemRequest.toModel();
    LOG.info("insereItem:     validando model gerado.");
    itemModel.validate();

    LOG.info("insereItem:     Buscando coluna do kanban: " + idKanban + " coluna: " + idColuna);
    ColunaModel coluna = ColunaModel.findById(idKanban, idColuna);

    LOG.info("insereItem:     Inserindo referencia de coluna no item.");
    itemModel.setColuna(coluna);

    LOG.info("insereItem:     Inserindo item na coluna.");
    coluna.getItens().add(itemModel);

    return Response.status(201).build();
  }

  @PUT
  @Path("/item/{id}")
  @Transactional
  public Response alteraItem(@PathParam(value = "idKanban") long idKanban, @PathParam(value = "idColuna") long idColuna, @PathParam(value = "id") long id, @QueryParam(value = "idColunaDestino") long idColunaDestino) {
    LOG.info("alteraItem:     Buscando coluna atual do item. kanban: " + idKanban + " coluna: " + idColuna);
    ColunaModel colunaAtual = ColunaModel.findById(idKanban, idColuna);
    LOG.info("alteraItem:     Buscando coluna de destino do item. kanban: " + idKanban + " coluna: " + idColunaDestino);
    ColunaModel colunaDestino = ColunaModel.findById(idKanban, idColunaDestino);

    LOG.info("alteraItem:     Buscando referencia do item");
    ItemModel itemAtual = colunaAtual.getItens().stream().filter((item) -> item.getId() == id).findFirst().orElseThrow(() -> new NenhumRegistroException("Nenhum item encontrado para esse id."));
    LOG.info("alteraItem:     Removendo item da coluna atual");
    colunaAtual.getItens().remove(itemAtual);

    LOG.info("alteraItem:     Adicionando o item a coluna de destino");
    colunaDestino.getItens().add(itemAtual);
    return Response.status(201).build();
  }
}
