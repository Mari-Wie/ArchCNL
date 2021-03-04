package examples;

public class ExampleClassWithExceptions {
    private final boolean throwNewException;
    
    public ExampleClassWithExceptions(boolean throwNewException) {
        this.throwNewException = throwNewException;
    }
    
    public void doThrow() throws IllegalArgumentException {
        if (throwNewException) {
            throw new IllegalArgumentException();
        }
        
        IllegalStateException e = new IllegalStateException();
        throw e;
    }
}
