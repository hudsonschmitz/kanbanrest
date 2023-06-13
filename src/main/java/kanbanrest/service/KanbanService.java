package kanbanrest.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import kanbanrest.exception.CampoInvalidoException;
import kanbanrest.model.ColunaModel;
import kanbanrest.model.KanbanModel;
import kanbanrest.request.KanbanRequest;
import kanbanrest.response.ColunaResponse;
import kanbanrest.response.KanbanResponse;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@ApplicationScoped
public class KanbanService {
  private static final Logger LOG = Logger.getLogger(KanbanService.class);

  private final ItemService itemService;

  public KanbanService(ItemService itemService) {
    this.itemService = itemService;
  }

  public KanbanResponse criarKanban(KanbanRequest kanbanRequest) {
    KanbanModel kanban = new KanbanModel();
    validarKanbanRequest(kanbanRequest);
    kanban.setNome(kanbanRequest.getNome());
    kanban.setAtivo(true);
    LOG.info("criarKanban: Criando novo kanban: ");
    kanban.persist();
    return modelToResponse(kanban);
  }

  private void validarKanbanRequest(KanbanRequest kanbanRequest) {
    if(StringUtils.isBlank(kanbanRequest.getNome())) {
      throw new CampoInvalidoException("Campo nome do kanban é obrigatório.");
    }
  }

  private KanbanResponse modelToResponse(KanbanModel model) {
    return KanbanResponse.builder()
            .idKanban(model.getId())
            .nome(model.getNome())
            .ativo(model.getAtivo())
            .build();
  }

  public KanbanResponse buscarKanbanPorId(Long id) {
    KanbanResponse response = KanbanResponse.builder().build();
    List<ColunaResponse> colunaResponse;
    List<ColunaModel> colunasModel;

    KanbanModel kanban = KanbanModel.buscarPorId(id);

    TypedQuery<ColunaModel> query = ColunaModel.getEntityManager().createNamedQuery("Coluna.findByKanbanId", ColunaModel.class);

    query.setParameter("kanban", kanban);
    colunasModel = query.getResultList();
    colunaResponse = colunasModel.stream().map((colunaModel -> new ColunaResponse(colunaModel.getId(),
            colunaModel.getStage(),
            colunaModel.getNome(),
            itemService.buscaItensColunaResponse(colunaModel.getId())))).collect(Collectors.toList());

    response.setColunas(colunaResponse);
    response.setIdKanban(id);
    return response;
  }


}
