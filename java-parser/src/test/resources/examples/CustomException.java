package examples;

public class CustomException extends RuntimeException {
    
  public CustomException() {
    super();
  }
  
  public CustomException(String s) {
      super(s);
  }
}
