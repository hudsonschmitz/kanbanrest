package kanbanrest.exception;

public class CampoInvalidoException extends RuntimeException {

  private static final long serialVersionUID = -270011251070016288L;

  private String message;

  private CampoInvalidoException() {

  }
  public CampoInvalidoException(String message) {
    super(message);
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

}
