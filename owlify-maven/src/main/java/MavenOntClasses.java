import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

public class MavenOntClasses {

	private OntModel ontoModel;
	private String ontologyNamespace;
	private String ontologyMainNamespace;

	public MavenOntClasses() {
		ontologyNamespace = "http://www.arch-ont.org/ontologies/maven.owl#";
		ontologyMainNamespace = "http://arch-ont.org/ontologies/main.owl#";
		ontoModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
	}

	public String getOntologyNamespace() {
		return ontologyNamespace;
	}

	public OntModel getOntoModel() {
		return ontoModel;
	}

	public Individual createIndividual(String uri, Resource resource) {
		return this.ontoModel.createIndividual(uri, resource);
	}

	public OntClass mavenProjectClass() {
		return ontoModel.getOntClass(ontologyNamespace + "MavenProject");
	}

	public OntClass pomFileClass() {
		return ontoModel.getOntClass(ontologyNamespace + "MavenPOMFile");
	}

	public OntClass mavenDependencyClass() {
		return ontoModel.getOntClass(ontologyNamespace + "MavenDependency");
	}
	
	public OntClass mavenModuleClass() {
		return ontoModel.getOntClass(ontologyNamespace + "MavenModule");
	}

	public ObjectProperty hasDependentProperty() {
		return ontoModel.getObjectProperty(ontologyNamespace + "hasDependent");
	}

	public ObjectProperty refersToArtifactProperty() {
		return ontoModel.getObjectProperty(ontologyNamespace + "refersToArtifact");
	}

	public DatatypeProperty hasClassifierProperty() {
		return ontoModel.getDatatypeProperty(ontologyNamespace + "dependencyHasClassifier");
	}

	public DatatypeProperty hasScopeProperty() {
		return ontoModel.getDatatypeProperty(ontologyNamespace + "dependencyHasScope");
	}

	public DatatypeProperty hasTypeProperty() {
		return ontoModel.getDatatypeProperty(ontologyNamespace + "dependencyHasType");
	}

	public DatatypeProperty pomFilePathProperty() {
		return ontoModel.getDatatypeProperty(ontologyMainNamespace + "hasPath");
	}

	public ObjectProperty projectContainsPOMFileProperty() {
		return ontoModel.getObjectProperty(ontologyNamespace + "containsPOMFile");
	}

	public DatatypeProperty hasNameProperty() {
		return ontoModel.getDatatypeProperty(ontologyMainNamespace + "hasName");
	}

	public OntClass mavenArtifactClass() {
		return ontoModel.getOntClass(ontologyNamespace + "MavenArtifact");
	}

	public ObjectProperty projectCreatesArtifact() {
		return ontoModel.getObjectProperty(ontologyNamespace + "producesArtifact");
	}

	public DatatypeProperty hasVersionProperty() {
		return ontoModel.getDatatypeProperty(ontologyNamespace + "hasVersion");
	}

	public DatatypeProperty hasGroupIDProperty() {
		return ontoModel.getDatatypeProperty(ontologyNamespace + "hasGroupId");
	}

	public DatatypeProperty hasArtifactIDProperty() {
		return ontoModel.getDatatypeProperty(ontologyNamespace + "hasArtifactId");
	}

	public DatatypeProperty hasPackagingProperty() {
		return ontoModel.getDatatypeProperty(ontologyNamespace + "hasPackaging");
	}

	public ObjectProperty hasParentProperty() {
		return ontoModel.getObjectProperty(ontologyNamespace + "hasParent");
	}

	public DatatypeProperty hasIDProperty() {
		return ontoModel.getDatatypeProperty(ontologyMainNamespace + "hasIdentifier");
	}

	public ObjectProperty hasDependencyProperty() {
		return ontoModel.getObjectProperty(ontologyNamespace + "hasDependency");
	}

	public ObjectProperty definesModuleProperty() {
		return ontoModel.getObjectProperty(ontologyNamespace + "definesModule");
	}

	

}
