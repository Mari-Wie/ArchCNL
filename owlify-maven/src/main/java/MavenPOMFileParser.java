import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
//import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.jena.ontology.Individual;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import core.AbstractOwlifyComponent;

public class MavenPOMFileParser extends AbstractOwlifyComponent {

	private String pathToOntology;
	private MavenOntClasses ontClasses;
	private int pomFileId;
	private static int moduleID = 0;
	private static int dependencyID = 0;
	private static int projectID = 0;
	private static int artifactID = 0;

	public MavenPOMFileParser() {
		super("./maven_output.owl");
		this.pathToOntology = "./ontology/maven.owl";
		this.ontClasses = new MavenOntClasses();

	}

	public void transform() {
		ontClasses.getOntoModel().read(pathToOntology);
		ontClasses.getOntoModel().read("./ontology/main.owl");

		File f = new File(super.getSourcePaths().get(0));
		String[] ext = { "xml" };
		Collection<File> matchingFiles = FileUtils.listFiles(f, ext, true);

		for (File file : matchingFiles) {
			if (file.getName().startsWith("pom")) {
				Reader reader;
				try {
					reader = new FileReader(file);
					MavenXpp3Reader xpp3Reader = new MavenXpp3Reader();
					Model model = xpp3Reader.read(reader);

					// Pom File Individual
					Individual pomFile = ontClasses.createIndividual(
							ontClasses.getOntologyNamespace() + "POMFile" + pomFileId++, ontClasses.pomFileClass());
					pomFile.addLiteral(ontClasses.pomFilePathProperty(), file.getAbsolutePath());

					Individual mavenProject = ontClasses.createIndividual(
							ontClasses.getOntologyNamespace() + "MavenProject_" + projectID,
							ontClasses.mavenProjectClass());
					projectID++;
					// has name
					if (model.getName() != null) {
						mavenProject.addLiteral(ontClasses.hasNameProperty(), model.getName());
					}
					// contains POM File
					mavenProject.addProperty(ontClasses.projectContainsPOMFileProperty(), pomFile);

					if (model.getProjectDirectory() != null) {
						System.out.println(model.getProjectDirectory());
					}

					model.getDistributionManagement();

					// Maven Artifact
					Individual artifact = ontClasses.createIndividual(
							ontClasses.getOntologyNamespace() + "MavenArtifact_" + artifactID,
							ontClasses.mavenArtifactClass());
					artifactID++;
					mavenProject.addProperty(ontClasses.projectCreatesArtifact(), artifact);

					if (model.getParent() != null) {
						// parent data
						Individual resolvedArtifact = resolveParentArtifact(model.getParent());
						artifact.addProperty(ontClasses.hasParentProperty(), resolvedArtifact);
						artifact.addLiteral(ontClasses.hasVersionProperty(), model.getParent().getVersion());
						artifact.addLiteral(ontClasses.hasGroupIDProperty(), model.getParent().getGroupId());
					} else {
						artifact.addLiteral(ontClasses.hasVersionProperty(), model.getVersion());
						artifact.addLiteral(ontClasses.hasGroupIDProperty(), model.getGroupId());
						artifact.addLiteral(ontClasses.hasArtifactIDProperty(), model.getArtifactId());
						artifact.addLiteral(ontClasses.hasPackagingProperty(), model.getPackaging());
						artifact.addLiteral(ontClasses.hasIDProperty(), model.getId());
//						artifact.addLiteral(ontClasses.hasClassifierProperty(), model.get)
					}

					addDependencies(artifact, model.getDependencies());
					addModules(artifact, model.getModules());

					DependencyManagement dependencyManagement = model.getDependencyManagement();
					if (dependencyManagement != null) {
						addDependencies(artifact, dependencyManagement.getDependencies());
					}

					ontClasses.getOntoModel().write(new FileOutputStream(new File(super.getResultPath())));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	private void addDependencies(Individual artifact, List<Dependency> dependencies) {
		for (final Dependency dependency : dependencies) {
			Individual a = ontClasses.createIndividual(
					ontClasses.getOntologyNamespace() + "MavenArtifact_" + artifactID, ontClasses.mavenArtifactClass());
			;
			artifactID++;
			a.addLiteral(ontClasses.hasArtifactIDProperty(), dependency.getArtifactId());
			a.addLiteral(ontClasses.hasGroupIDProperty(), dependency.getGroupId());
			a.addLiteral(ontClasses.hasVersionProperty(), dependency.getVersion());

			Individual d = ontClasses.createIndividual(
					ontClasses.getOntologyNamespace() + "MavenDependency_" + dependencyID,
					ontClasses.mavenDependencyClass());
			dependencyID++;
			d.addProperty(ontClasses.refersToArtifactProperty(), a);
			d.addProperty(ontClasses.hasDependentProperty(), artifact);
			if (dependency.getClassifier() != null) {
				d.addLiteral(ontClasses.hasClassifierProperty(), dependency.getClassifier());
			}
			if (dependency.getScope() != null) {
				d.addLiteral(ontClasses.hasScopeProperty(), dependency.getScope());
			}
			d.addLiteral(ontClasses.hasTypeProperty(), dependency.getType());

			artifact.addProperty(ontClasses.hasDependencyProperty(), d);

		}
	}

	private void addModules(Individual artifact, List<String> modules) {
		for (String module : modules) {
			Individual m = ontClasses.createIndividual(ontClasses.getOntologyNamespace() + "MavenModule_" + moduleID,
					ontClasses.mavenModuleClass());
			moduleID++;
			m.addLiteral(ontClasses.hasNameProperty(), module);
			artifact.addProperty(ontClasses.definesModuleProperty(), m);
		}
	}

	private Individual resolveParentArtifact(Parent parent) {

		Individual i = ontClasses.createIndividual(ontClasses.getOntologyNamespace() + "Artifact_" + artifactID,
				ontClasses.mavenArtifactClass());
		i.addProperty(ontClasses.hasArtifactIDProperty(), parent.getArtifactId());
		i.addProperty(ontClasses.hasGroupIDProperty(), parent.getGroupId());
		i.addProperty(ontClasses.hasVersionProperty(), parent.getVersion());
		i.addProperty(ontClasses.pomFilePathProperty(), parent.getRelativePath());
		i.addProperty(ontClasses.hasIDProperty(), parent.getId());
		artifactID++;
		return i;
	}

	public static void main(String[] args) {

		File f = new File("C:\\Users\\sandr\\Documents\\repositories\\cnl-github\\owlify\\owlify-main");
		String[] ext = { "xml" };
		Collection<File> matchingFiles = FileUtils.listFiles(f, ext, true);

		for (File file : matchingFiles) {
			if (file.getName().startsWith("pom")) {
				System.out.println(file.getName());
			}
		}
	}

	@Override
	public Map<String, String> getProvidedNamespaces() {
		Map<String, String> res = new HashMap<>();
		res.put("maven", ontClasses.getOntologyNamespace());
		res.put("main", ontClasses.getOntologyMainNamespace());
		return res;
	}

}
