import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Enumeration;
import java.util.List;

import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Resource;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

public class MavenPOMFileParser {

	private String outputPath;
	private String pathToOntology;
	private MavenOntClasses ontClasses;
	private String pathToPOM;
	private static int moduleID = 0;
	private static int dependencyID = 0;
	private static int projectID = 0;
	private static int artifactID = 0;

	public MavenPOMFileParser(String pathToPOM, String outputPath, String pathToOntology) {

		this.pathToPOM = pathToPOM;
		this.outputPath = outputPath;
		this.pathToOntology = pathToOntology;
		this.ontClasses = new MavenOntClasses();

	}

	public void parse() throws IOException, XmlPullParserException {
		ontClasses.getOntoModel().read(pathToOntology);
		ontClasses.getOntoModel()
				.read("C:\\Users\\sandr\\Documents\\repositories\\ArchitectureCNL\\ontologies\\main.owl");

		Reader reader = new FileReader(new File(pathToPOM));

		MavenXpp3Reader xpp3Reader = new MavenXpp3Reader();
		Model model = xpp3Reader.read(reader);

		// Pom File Individual
		Individual pomFile = ontClasses.createIndividual(ontClasses.getOntologyNamespace() + "POMFile",
				ontClasses.pomFileClass());
		pomFile.addLiteral(ontClasses.pomFilePathProperty(), pathToPOM);

		Individual mavenProject = ontClasses.createIndividual(
				ontClasses.getOntologyNamespace() + "MavenProject_" + projectID, ontClasses.mavenProjectClass());
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
				ontClasses.getOntologyNamespace() + "MavenArtifact_" + artifactID, ontClasses.mavenArtifactClass());
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
//			artifact.addLiteral(ontClasses.hasClassifierProperty(), model.get)
		}

		addDependencies(artifact, model.getDependencies());
		addModules(artifact, model.getModules());

		DependencyManagement dependencyManagement = model.getDependencyManagement();
		if (dependencyManagement != null) {
			addDependencies(artifact,dependencyManagement.getDependencies());
		}
		
		ontClasses.getOntoModel().write(new FileOutputStream(new File(outputPath)));

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
		MavenPOMFileParser parser = new MavenPOMFileParser(
				"C:\\Users\\sandr\\Documents\\repositories\\ArchitectureCNL\\org.architecture.cnl.parent\\org.architecture.cnl\\pom.xml",
				"./maven_test.owl",
				"C:\\Users\\sandr\\Documents\\repositories\\ArchitectureCNL\\ontologies\\maven.owl");
		try {
			parser.parse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
