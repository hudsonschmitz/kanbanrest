package kanbanrest.model;

import java.util.ArrayList;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kanbanrest.exception.CampoObrigatorioException;
import kanbanrest.exception.NenhumRegistroException;
import org.apache.commons.lang3.StringUtils;

@Entity(name = "Item")
public class ItemModel extends PanacheEntityBase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  
  private String descricao;
  private List<String> hashTags;
  
  @ManyToOne
  @JoinColumn(name = "coluna_id")
  private ColunaModel coluna;

  public static List<ItemModel> findAll(long idKanban, long idColuna) {
    ColunaModel colunaModel = ColunaModel.findById(idKanban, idColuna);

    return colunaModel.getItens();
  }

  public static ItemModel buscaItemPorId(long idKanban, long idColuna, long id) {
    List<ItemModel> listaItens = ItemModel.findAll(idKanban, idColuna);
    return listaItens.stream().filter((item) -> item.id == id)
                              .findFirst()
                              .orElseThrow(
                                      () -> new NenhumRegistroException("Item não encontrado.")
                              );
  }

  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public String getDescricao() {
    return this.descricao;
  }
  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }
  public List<String> getHashTags() {
    return this.hashTags;
  }
  public void setHashTags(List<String> hashTags) {
    this.hashTags = hashTags;
  }

  public void validate() {
    if(StringUtils.isBlank(this.descricao)) {
      throw new CampoObrigatorioException("Campo descrição do item é obrigatório.");
    }
  }
  public void setColuna(ColunaModel coluna) {
    this.coluna = coluna;
  }

  public ColunaModel getColuna() {
    return coluna;
  }
}
