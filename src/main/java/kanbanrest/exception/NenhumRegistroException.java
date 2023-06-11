package kanbanrest.exception;

public class NenhumRegistroException extends RuntimeException  {

  private static final long serialVersionUID = 4056308970507774104L;
  private String message;
  
  private NenhumRegistroException() {
    
  }
  
  public NenhumRegistroException(String message) {
    super(message);
    this.message = message;
  }
  
  public String getMessage() {
    return message;
  }

}
