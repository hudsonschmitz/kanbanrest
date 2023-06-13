package kanbanrest.repository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.transaction.Transactional;
import kanbanrest.Item;
import kanbanrest.exception.NenhumRegistroException;
import kanbanrest.model.ColunaModel;
import kanbanrest.model.ItemModel;
import kanbanrest.model.KanbanModel;
import kanbanrest.response.ColunaResponse;
import kanbanrest.response.ItemResponse;
import kanbanrest.response.KanbanResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Priority(1)
@Alternative
@ApplicationScoped
public class  TestRepository {

  public static KanbanModel kanban;
  public static List<ColunaModel> colunas = new ArrayList<>();
  public static List<ItemModel> itens = new ArrayList<>();

  @PostConstruct
  @Transactional
  public static void init() {
    criaKanban();
    criaColunas();
    criaItens();
  }

  private static void criaKanban() {
    kanban = new KanbanModel();
    kanban.setAtivo(true);
    kanban.setNome("Kanban");
    kanban.persist();
  }

  private static void criaColunas() {
    for(int i = 1; i <= 5; i++) {
      ColunaModel coluna = new ColunaModel(i, "Coluna teste " + i, kanban);
      coluna.persist();
      colunas.add(coluna);
    }
  }

  private static void criaItens() {
    for(ColunaModel coluna : colunas) {
      for(int i = 0; i < 4; i++) {
        ItemModel item = new ItemModel("Teste descrição item " + i, List.of("#Teste" + i), coluna);
        item.persist();
        itens.add(item);
      }
    }
  }

  @Transactional
  public static void destroy() {
    deletaItens();
    deletaColunas();
    deletaKanban();
  }

  private static void deletaItens() {
    for(ItemModel item : itens) {
      item.delete();
    }
    itens = new ArrayList<>();
  }
  private static void deletaColunas() {
    for (ColunaModel coluna: colunas) {
      coluna.delete();
    }
    colunas = new ArrayList<>();
  }
  private static void deletaKanban() {
    kanban = KanbanModel.buscarPorId(kanban.getId());
    kanban.delete();
    kanban = null;
  }

  public static KanbanResponse kanbanToResponse() {
    KanbanResponse response = KanbanResponse.builder().idKanban(kanban.getId()).build();
    response.setColunas(colunasToResponse());
    return response;
  }
  public static List<ColunaResponse> colunasToResponse() {
    List<ColunaResponse> colunasKanban = new ArrayList<>();
    colunasKanban = colunas.stream()
            .map((coluna) -> new ColunaResponse(coluna.getId(), coluna.getStage(), coluna.getNome(), itensToResponse(coluna.getId()))).collect(Collectors.toList());
    return colunasKanban;
  }

  public static List<ItemResponse> itensToResponse(long idColuna) {
    List<ItemModel> itensColuna = itens.stream().filter((item) -> item.getColuna().getId() == idColuna).collect(Collectors.toList());
    List<ItemResponse> itensResponse;

    itensResponse = itensColuna.stream()
            .map((itemModel) -> new ItemResponse(itemModel.getId(), itemModel.getDescricao(), itemModel.getHashTags(), itemModel.getColuna().getId())).collect(Collectors.toList());

    return itensResponse;
  }
}
