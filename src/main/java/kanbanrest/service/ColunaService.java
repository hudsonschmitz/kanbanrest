package kanbanrest.service;

import jakarta.persistence.TypedQuery;
import kanbanrest.exception.NenhumRegistroException;
import kanbanrest.model.ItemModel;
import kanbanrest.response.ColunaResponse;
import org.apache.commons.lang3.StringUtils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import kanbanrest.exception.CampoInvalidoException;
import kanbanrest.model.ColunaModel;
import kanbanrest.model.KanbanModel;
import kanbanrest.request.ColunaRequest;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ColunaService {

  private static final Logger LOG = Logger.getLogger(ColunaService.class);
  private final ItemService itemService;

  public ColunaService(ItemService itemService) {
    this.itemService = itemService;
  }


  @Transactional
  public void insereColuna(long idKanban, ColunaRequest colunaRequest) {
    LOG.info("insereColuna: Buscando referencia do kanban: " + idKanban);
    KanbanModel kanban = KanbanModel.findById(idKanban);
    LOG.info("insereColuna: Validando objeto request");
    validaRequestInsercao(idKanban, colunaRequest);
    LOG.info("insereColuna: Transformando objeto request em model");
    ColunaModel coluna = colunaRequest.toModel();

    LOG.info("insereColuna: Inserindo coluna no kanban");
    coluna.setKanban(kanban);
    kanban.persist();
  }
  
  private void validaRequestInsercao(Long idKanban, ColunaRequest colunaRequest) {
    int stage = colunaRequest.getStage();
    if(stage == 0) {
      throw new CampoInvalidoException("Campo coluna é obrigatório e deve ser maior do que 0.");
    }
    if(StringUtils.isBlank(colunaRequest.getNome())) {
      throw new CampoInvalidoException("Campo nome da coluna é obrigatório.");
    }
    List<ColunaModel> colunas = buscarColunasPorIdKanban(idKanban);
    int maiorStage = colunas.stream().mapToInt(ColunaModel::getStage).max().orElseThrow(() -> new NenhumRegistroException("Nenhuma coluna encontrada para o id:" + idKanban));

    if(stage >= maiorStage + 1) {
      throw new CampoInvalidoException("Campo stage tem que estar entre 1 e " + maiorStage);
    }
  }
  
  @Transactional
  public void alteraColuna(long idKanban, long id, ColunaRequest colunaRequest) {
    LOG.info("alteraColuna: Buscando coluna. kanban: " + idKanban + " coluna: " + id);
    ColunaModel coluna = ColunaModel.findById(id);
    Integer stageAnterior = coluna.getStage();
    LOG.info("alteraColuna: Validando objeto request");
    validaRequestInsercao(idKanban, colunaRequest);
    LOG.info("alteraColuna: Alterando stage da coluna para: " + colunaRequest.getStage());
    coluna.setStage(colunaRequest.getStage());
    LOG.info("alteraColuna: Alterando nome da coluna para: " + colunaRequest.getNome());
    coluna.setNome(colunaRequest.getNome());

    LOG.info("alteraColuna: Salvando coluna no banco");
    coluna.persist();

    ajustarStages(idKanban, stageAnterior, coluna);
  }

  private void ajustarStages(long idKanban, Integer stageAnterior, ColunaModel colunaModel) {
    int novoStage = colunaModel.getStage();
    TypedQuery<ColunaModel> query = ColunaModel.getEntityManager().createNamedQuery("Coluna.findByKanbanId", ColunaModel.class);
    query.setParameter("kanban", KanbanModel.buscarPorId(idKanban));

    List<ColunaModel> listaColunaModel = query.getResultList();
    listaColunaModel.forEach((coluna) -> {
      if(coluna.getStage() >= novoStage && coluna.getStage() < stageAnterior && coluna.getId() != colunaModel.getId()) {
        coluna.setStage(coluna.getStage() + 1);
        coluna.persist();
      }
    });
  }

  public ColunaResponse modelToResponse(ColunaModel colunaModel) {
    return new ColunaResponse(colunaModel.getId(), colunaModel.getStage(), colunaModel.getNome(), itemService.modelToResponse(ItemModel.buscaItensColuna(colunaModel)));
  }

  public List<ColunaModel> buscarColunasPorIdKanban(Long idKanban) {
    TypedQuery<ColunaModel> query = ColunaModel.getEntityManager().createNamedQuery("Coluna.findByKanbanId", ColunaModel.class);
    query.setParameter("kanban", KanbanModel.buscarPorId(idKanban));

    return query.getResultList();
  }

  public ColunaResponse buscaColunaPorId(Long idKanban, Long id) {
    if(Objects.isNull(id)) {
      throw new CampoInvalidoException("Paramêtro id é obrigatório.");
    }

    TypedQuery<ColunaModel> query = ColunaModel.getEntityManager().createNamedQuery("Coluna.findByIdAndKanbanId", ColunaModel.class);

    query.setParameter("idColuna", id);
    query.setParameter("kanban", KanbanModel.buscarPorId(idKanban));
    ColunaModel colunaModel = query.getSingleResult();

    return modelToResponse(colunaModel);
  }
}
