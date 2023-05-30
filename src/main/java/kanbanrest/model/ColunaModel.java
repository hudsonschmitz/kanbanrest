package kanbanrest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import kanbanrest.exception.NenhumRegistroException;

@Entity(name = "Coluna")
public class ColunaModel extends PanacheEntityBase {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  
  private int stage;
  
  private String nome;
  
  @ManyToOne
  @JoinColumn(name = "kanban_id")
  private KanbanModel kanban;
  
  @OneToMany(mappedBy = "coluna", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<ItemModel> itens = new ArrayList<>();
  
  public static ColunaModel findById(long idKanban, long id) {
    KanbanModel kanban = KanbanModel.findById(idKanban);
    return kanban.getColunas().stream().filter((coluna) -> coluna.getId() == id).findFirst().orElseThrow(() -> new NenhumRegistroException("Nenhuma coluna encontrada para esse id."));
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getStage() {
    return stage;
  }

  public void setStage(int stage) {
    this.stage = stage;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public List<ItemModel> getItens() {
    return itens;
  }

  public void setItens(List<ItemModel> itens) {
    this.itens = itens;
  }

}
