package kanbanrest.service;

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

  public ColunaModel getColunaPorId(long id) {
    return (ColunaModel) ColunaModel.findByIdOptional(id).orElseThrow(() -> new NenhumRegistroException("Nenhuma coluna encontrada para o id: " + id));
  }
  public ColunaModel getColunaPorId(long idKanban, long id) {
    return ColunaModel.findById(idKanban, id);
  }
  
  @Transactional
  public void insereColuna(long idKanban, ColunaRequest colunaRequest) {
    KanbanModel kanban = KanbanModel.findById(idKanban);
    ColunaModel coluna = colunaRequest.toModel();
    
    validaRequestInsercao(colunaRequest);
    
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
    ColunaModel coluna = getColunaPorId(idKanban, id);
    validaRequestInsercao(colunaRequest);
    coluna.setStage(colunaRequest.getColuna());
    coluna.setNome(colunaRequest.getNome());
    coluna.persist();
  }
  
}
