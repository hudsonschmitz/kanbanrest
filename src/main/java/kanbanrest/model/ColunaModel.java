package kanbanrest.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import kanbanrest.exception.CampoInvalidoException;
import kanbanrest.exception.NenhumRegistroException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "Coluna")
@Data
@NoArgsConstructor
@NamedQueries(
  {
    @NamedQuery(name = "Coluna.findByKanbanId", query = "SELECT c FROM Coluna c WHERE c.kanban = :kanban"),
    @NamedQuery(name = "Coluna.findByIdAndKanbanId", query = "SELECT c FROM Coluna c WHERE c.id = :idColuna AND c.kanban = :kanban")
  }
)

public class ColunaModel extends PanacheEntityBase {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  
  private int stage;
  
  private String nome;
  
  @ManyToOne
  @JoinColumn(name = "kanban_id")
  private KanbanModel kanban;

  public ColunaModel(int stage, String nome, KanbanModel kanbanModel) {
    this.stage = stage;
    this.nome = nome;
    this.kanban = kanbanModel;
  }

  public static ColunaModel buscarPorId(Long id) {
    if(id == null) {
      throw new CampoInvalidoException("ID da coluna não pode ser nulo.");
    }
    return (ColunaModel) ColunaModel.findByIdOptional(id).orElseThrow(() -> new NenhumRegistroException("Nenhuma coluna encontrada com id: " + id));
  }

}
