package examples;

public enum Enumeration {
    A,
    B,
    C;

    public static int getIndex(Enumeration e) {
        switch (e) {
            case A:
                return 1;
            case B:
                return 2;
            case C:
                return 3;
        }
        return -1;
    }
}
