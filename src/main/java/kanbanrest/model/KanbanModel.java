package kanbanrest.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kanbanrest.exception.NenhumRegistroException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.logging.Logger;


@EqualsAndHashCode(callSuper = true)
@Entity(name = "Kanban")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KanbanModel extends PanacheEntityBase {

  private static final Logger LOG = Logger.getLogger(KanbanModel.class.getName());
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String nome;
  private Boolean ativo;

  public static KanbanModel buscarPorId(long id) {
    LOG.info("-------------------------------------------------");
    LOG.info(String.valueOf(id));
   return (KanbanModel) KanbanModel.findByIdOptional(id).orElseThrow(() -> new NenhumRegistroException("Nenhum kanban encontrado com o id: " + id));
  }

}
