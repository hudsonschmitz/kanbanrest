package kanbanrest.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import kanbanrest.exception.CampoInvalidoException;
import kanbanrest.model.ColunaModel;
import kanbanrest.model.ItemModel;
import kanbanrest.request.ItemRequest;
import kanbanrest.response.ItemResponse;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class ItemService {

  private static final Logger LOG = Logger.getLogger(ItemService.class);

  @Transactional
  public void insereItem(Long idKanban, ItemRequest itemRequest) {
    LOG.info("insereItem:     Convertendo objeto request em model.");
    ItemModel itemModel = itemRequest.toModel();
    LOG.info("insereItem:     validando model gerado.");
    itemModel.validate();

    LOG.info("insereItem:     Buscando coluna: " + itemRequest.getIdColuna());
    ColunaModel coluna = ColunaModel.buscarPorId(itemRequest.getIdColuna());

    LOG.info("insereItem:     Inserindo referencia de coluna no item.");
    itemModel.setColuna(coluna);

    LOG.info("insereItem:     Inserindo item no banco.");
    itemModel.persistAndFlush();
  }

  @Transactional
  public void moveItem(long id, long idColunaDestino) {
    LOG.info("alteraItem:     Buscando item: " + id);
    ItemModel item = ItemModel.buscaItemPorId(id);
    LOG.info("alteraItem:     Buscando coluna: " + idColunaDestino);
    ColunaModel colunaDestino = ColunaModel.findById(idColunaDestino);

    if(colunaDestino.getStage() <= item.getColuna().getStage()) {
      throw new CampoInvalidoException("Item só pode ser movido para uma coluna com um stage maior do que o stage atual!");
    }

    LOG.info("alteraItem:     Inserindo referência de coluna no item.");
    item.setColuna(colunaDestino);

    LOG.info("alteraItem:     Alterando item no banco.");
    item.persistAndFlush();
  }

  public List<ItemResponse> buscaItensColunaResponse(long idColuna) {
    return ItemModel.buscaItensColuna(ColunaModel.findById(idColuna)).stream().map((itemModel -> new ItemResponse(itemModel.getId(), itemModel.getDescricao(), itemModel.getHashTags(), itemModel.getColuna().getId()))).collect(Collectors.toList());
  }

  public List<ItemResponse> modelToResponse(List<ItemModel> itemModels) {
    return itemModels.stream().map((item) -> new ItemResponse(item.getId(), item.getDescricao(), item.getHashTags(), item.getColuna().getId())).collect(Collectors.toList());
  }
}
