
public class TestLayerOne {
	private TestLayerTwo layerBelow;
	
	public TestLayerOne() {
		layerBelow = new TestLayerTwo(this);
	}
	
	public int doStuff(int param) {
		return param + layerBelow.doStuff(param);
	}
}
