package ids;

public class GlobalEnumId {
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
