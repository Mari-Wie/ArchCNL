package ids;

public class GlobalJavaClassId {
	
	private static int id;
	
	public static int get() {
		final int tmp = id;
		increase();
		return tmp;
	}
	
	private static void increase() {
		id++;
	}

}
