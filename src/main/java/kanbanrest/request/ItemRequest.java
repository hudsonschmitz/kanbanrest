package kanbanrest.request;

import kanbanrest.model.ItemModel;

import java.io.Serializable;
import java.util.List;

public class ItemRequest  implements Serializable  {

  private String descricao;
  private List<String> hashTags;

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public void setHashTags(List<String> hashTags) {
    this.hashTags = hashTags;
  }

  public List<String> getHashTags() {
    return hashTags;
  }

  public String getDescricao() {
    return descricao;
  }
  public ItemModel toModel() {
    ItemModel item = new ItemModel();
    item.setDescricao(this.getDescricao());
    item.setHashTags(this.getHashTags());
    return item;
  }
}
