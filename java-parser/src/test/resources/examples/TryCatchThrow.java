package examples;

public class EmptyClass {
    private Exception transform(MyExternalIndexOutOfBoundsException ex){
        return new Exception(ex.getMessage());
    }

    public void doStuff() throws Exception {
        try {
            int[] myNumbers = {1, 2, 3};
            int num = myNumbers[10];
        } catch (MyExternalIndexOutOfBoundsException e) {
            throw transform(e);
        }
    }

    public void doOtherStuff() throws Exception {
        throw new MyExternalIndexOutOfBoundsException(e);
    }
}
