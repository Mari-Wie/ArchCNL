package core;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class of input parsers which can be used as a template for new input parsers.
 * It implements the OwlifiyComponent and includes some general behavior of input parsers.
 */
public abstract class AbstractOwlifyComponent implements OwlifyComponent {
	
	// path of the output file
	private String resultPath;
	
	// list of input paths
	private List<Path> sourcePaths;
	
	/**
	 * Constructor.
	 * @param resultPath the path to the file in which the generated ontology will be stored
	 */
	public AbstractOwlifyComponent(String resultPath) {
		this.resultPath = resultPath;
		this.sourcePaths = new ArrayList<Path>();
	}

	public void addSourcePath(Path path) {
		sourcePaths.add(path);
	}
	
	public List<Path> getSourcePaths(){		
		return sourcePaths;
	}

	// implement this method in your parser
	public abstract void transform();

	public String getResultPath() {
		
		return this.resultPath;
	}
	
}
