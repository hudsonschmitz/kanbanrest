package kanbanrest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public class KanbanResponse implements Serializable {

  private long idKanban;
  private List<ColunaResponse> colunas;
  private String nome;
  private Boolean ativo;

}
