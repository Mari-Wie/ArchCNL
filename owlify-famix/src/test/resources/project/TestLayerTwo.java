
public class TestLayerTwo {
	private TestLayerOne layerAbove;
	
	public TestLayerTwo(TestLayerOne badIdea) {
		layerAbove = badIdea;
	}
	
	public int doStuff(int param) {
		return 2 * param;
	}
}
