import parser.FamixOntologyTransformer;
import scanner.GitOntologyTransformer;

public class CombinedTransformer {
	
	private FamixOntologyTransformer famix;
	private GitOntologyTransformer git;
	private MavenPOMFileParser maven;
	
	public CombinedTransformer(String projectPath) {
		famix = new FamixOntologyTransformer();
		famix.setSource(projectPath);
		git = new GitOntologyTransformer();
		git.setSource(projectPath+"\\.git");
		maven = new MavenPOMFileParser();
		maven.setSource(projectPath);
	}
	

	public void transform() {
		//git.transform();
		famix.transform();
		maven.transform();
	}

	
	public static void main(String[] args) {
		String projectPath = "C:\\Users\\sandr\\Documents\\repositories\\cnl-github\\owlify\\owlify-main";
		
		CombinedTransformer transformer = new CombinedTransformer(projectPath);
		transformer.transform();
	}
}
