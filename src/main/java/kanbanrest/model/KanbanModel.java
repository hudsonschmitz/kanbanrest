package kanbanrest.model;

import java.util.ArrayList;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name = "Kanban")
public class KanbanModel extends PanacheEntityBase {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  
  @OneToMany(mappedBy = "kanban", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<ColunaModel> colunas = new ArrayList<>();
  
  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public List<ColunaModel> getColunas() {
    return colunas;
  }
  public void setColunas(List<ColunaModel> colunas) {
    this.colunas = colunas;
  }
  
}
