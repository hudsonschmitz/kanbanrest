package kanbanrest.exception;

public class CampoObrigatorioException extends RuntimeException {

  private static final long serialVersionUID = -270011251070016288L;

  private String message;

  public CampoObrigatorioException() {

  }
  public CampoObrigatorioException(String message) {
    super(message);
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
