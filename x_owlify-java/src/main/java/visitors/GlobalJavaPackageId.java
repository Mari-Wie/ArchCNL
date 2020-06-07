package visitors;

public class GlobalJavaPackageId {
	
	private static int id;
	
	public static int get() {
		int tmp = id;
		increase();
		return tmp;
	}

	private static void increase() {
		id++;
	}
	
	

}
