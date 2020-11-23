package core;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractOwlifyComponent implements OwlifyComponent {
	
	private String resultPath;
	
	private List<String> sourcePaths;
	
	public AbstractOwlifyComponent(String resultPath) {
		this.resultPath = resultPath;
		this.sourcePaths = new ArrayList<String>();
	}

	public void addSourcePath(String path) {
		sourcePaths.add(path);
	}
	
	public List<String> getSourcePaths(){
		
		return sourcePaths;
		
	}

	public abstract void transform();

	public String getResultPath() {
		
		return this.resultPath;
	}
	
}
