package kanbanrest.request;

import kanbanrest.model.ColunaModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class ColunaRequest implements Serializable {

  private static final long serialVersionUID = -4542116155732222416L;
  private int stage;
  private String nome;
  
  public ColunaRequest(){}

  public ColunaRequest(int stage, String nome) {
    super();
    this.stage = stage;
    this.nome = nome;
  }
  
  public ColunaModel toModel() {
    ColunaModel colunaModel = new ColunaModel();
    colunaModel.setStage(this.stage);
    colunaModel.setNome(this.nome);
    return colunaModel;
  }
  
}
