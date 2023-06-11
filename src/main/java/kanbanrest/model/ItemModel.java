package kanbanrest.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import kanbanrest.exception.CampoInvalidoException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Entity(name = "Item")
@Data
@NoArgsConstructor
@NamedQuery(name = "Item.findByColunaId", query = "SELECT i FROM Item i WHERE i.coluna = :coluna")
@EqualsAndHashCode(callSuper = true)
public class ItemModel extends PanacheEntityBase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  
  private String descricao;
  private List<String> hashTags;

  @ManyToOne
  @JoinColumn(name = "coluna_id")
  private ColunaModel coluna;

  public ItemModel(String descricao, List<String> hashTags, ColunaModel coluna) {
    this.descricao = descricao;
    this.hashTags = hashTags;
    this.coluna = coluna;
  }

  public static List<ItemModel> buscaItensColuna(ColunaModel colunaModel) {
    TypedQuery<ItemModel> query = ItemModel.getEntityManager().createNamedQuery("Item.findByColunaId", ItemModel.class);
    query.setParameter("coluna", colunaModel);
    return query.getResultList();
  }
  public static ItemModel buscaItemPorId(long id) {
    return ItemModel.findById(id);
  }
  public void validate() {
    if(StringUtils.isBlank(this.descricao)) throw new CampoInvalidoException("Campo descrição do item é obrigatório.");
  }
}
