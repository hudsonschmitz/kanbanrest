package kanbanrest.service;

import kanbanrest.Coluna;
import org.apache.commons.lang3.StringUtils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import kanbanrest.exception.CampoObrigatorioException;
import kanbanrest.exception.NenhumRegistroException;
import kanbanrest.model.ColunaModel;
import kanbanrest.model.KanbanModel;
import kanbanrest.request.ColunaRequest;

@ApplicationScoped
public class ColunaService {

  private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(ColunaService.class);
  
  @Transactional
  public void insereColuna(long idKanban, ColunaRequest colunaRequest) {
    LOG.info("insereColuna: Buscando referencia do kanban: " + idKanban);
    KanbanModel kanban = KanbanModel.findById(idKanban);
    LOG.info("insereColuna: Validando objeto request");
    validaRequestInsercao(colunaRequest);
    LOG.info("insereColuna: Transformando objeto request em model");
    ColunaModel coluna = colunaRequest.toModel();

    LOG.info("insereColuna: Inserindo coluna no kanban");
    kanban.getColunas().add(coluna);
    kanban.persist();
  }
  
  private void validaRequestInsercao(ColunaRequest colunaRequest) {
    if(colunaRequest.getColuna() == 0) {
      throw new CampoObrigatorioException("Campo coluna é obrigatório.");
    }
    if(StringUtils.isBlank(colunaRequest.getNome())) {
      throw new CampoObrigatorioException("Campo nome da coluna é obrigatório.");
    }
  }
  
  @Transactional
  public void alteraColuna(long idKanban, long id, ColunaRequest colunaRequest) {
    LOG.info("alteraColuna: Buscando coluna. kanban: " + idKanban + " coluna: " + id);
    ColunaModel coluna = ColunaModel.findById(idKanban, id);
    LOG.info("alteraColuna: Validando objeto request");
    validaRequestInsercao(colunaRequest);
    LOG.info("alteraColuna: Alterando stage da coluna para: " + colunaRequest.getColuna());
    coluna.setStage(colunaRequest.getColuna());
    LOG.info("alteraColuna: Alterando nome da coluna para: " + colunaRequest.getNome());
    coluna.setNome(colunaRequest.getNome());

    LOG.info("alteraColuna: Salvando coluna no banco");
    coluna.persist();
  }
  
}
