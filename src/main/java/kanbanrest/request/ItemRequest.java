package kanbanrest.request;

import kanbanrest.model.ItemModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest  implements Serializable  {

  private String descricao;
  private List<String> hashTags;

  private Long idColuna;

  public ItemModel toModel() {
    ItemModel item = new ItemModel();
    item.setDescricao(this.getDescricao());
    item.setHashTags(this.getHashTags());
    return item;
  }
}
