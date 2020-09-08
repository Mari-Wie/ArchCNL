package stemmer;

public class StemmerTest {

	public static void main(String[] args) {
		String result = "";
		Stemmer stemmer = new Stemmer();
		String text = "Such an analysis can reveal features that are not easily visible".toLowerCase();
		char[] charArray = text.toCharArray();
		for (char c : charArray) {
			stemmer.add(c);
		}
		stemmer.stem();
		char[] resultBuffer = stemmer.getResultBuffer();
		result = new String(resultBuffer);
		System.out.println(result);
	}

}
