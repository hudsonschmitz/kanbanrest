package kanbanrest.request;

import java.io.Serializable;
import java.util.logging.Logger;

import kanbanrest.model.ColunaModel;

public class ColunaRequest implements Serializable {

  private static final long serialVersionUID = -4542116155732222416L;
  private int coluna;
  private String nome;
  
  public ColunaRequest(){
    
  }
  public ColunaRequest(int coluna, String nome) {
    super();
    this.coluna = coluna;
    this.nome = nome;
  }
  
  public int getColuna() {
    return coluna;
  }
  public void setColuna(int coluna) {
    this.coluna = coluna;
  }
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }
  
  public ColunaModel toModel() {
    ColunaModel colunaModel = new ColunaModel();
    Logger.getGlobal().warning("testesssss" + this.coluna);
    colunaModel.setStage(this.coluna);
    colunaModel.setNome(this.nome);
    return colunaModel;
  }
  
}
