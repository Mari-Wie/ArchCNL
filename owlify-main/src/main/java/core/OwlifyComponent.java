package core;

import java.util.List;

public interface OwlifyComponent {
	public void transform();
	public String getResultPath();
	public List<String> getSourcePaths();
	public void addSourcePath(String path);
}
