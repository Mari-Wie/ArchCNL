package core;

public abstract class AbstractOwlifyComponent implements OwlifyComponent {
	
	private String resultPath;
	private String sourcePath;
	
	public AbstractOwlifyComponent(String resultPath) {
		this.resultPath = resultPath;
	}

	public void setSource(String path) {
		this.sourcePath = path;
	}

	public abstract void transform();

	public String getResultPath() {
		
		return this.resultPath;
	}
	
	public String getSourcePath() {
		return sourcePath;
	}

}
