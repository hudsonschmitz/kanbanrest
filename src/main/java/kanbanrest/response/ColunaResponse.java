package kanbanrest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColunaResponse implements Serializable {

  private long idColuna;
  private int stage;
  private String nome;
  private List<ItemResponse> itens;

}
