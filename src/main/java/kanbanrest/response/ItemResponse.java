package kanbanrest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse implements Serializable {

  private long id;

  private String descricao;
  private List<String> hashTags;
  private Long idColuna;

}
