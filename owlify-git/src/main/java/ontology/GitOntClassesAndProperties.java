package ontology;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

public class GitOntClassesAndProperties {

	private OntModel ontoModel;
	private final String ONTOLOGY_GIT_NAMESPACE;
	private final String ONTOLOGY_MAIN_NAMESPACE;
	private final String ONTOLOGY_HISTORY_NAMESPACE;

	public GitOntClassesAndProperties() {
		ONTOLOGY_GIT_NAMESPACE = "http://www.arch-ont.org/ontologies/git.owl#";
		ONTOLOGY_MAIN_NAMESPACE = "http://arch-ont.org/ontologies/main.owl#";
		ONTOLOGY_HISTORY_NAMESPACE = "http://arch-ont.org/ontologies/history.owl#";
		ontoModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		ontoModel.read("./ontology/main.owl");
		ontoModel.read("./ontology/history.owl");
		ontoModel.read("./ontology/git.owl");
	}
	
	public String getOntologyNamespace() {
		return ONTOLOGY_GIT_NAMESPACE;
	}
	
	public String getMainNamespace() {
		return ONTOLOGY_MAIN_NAMESPACE;
	}
	
	public String getHistoryNamespace() {
		return ONTOLOGY_HISTORY_NAMESPACE;
	}

	public Individual createIndividual(String uri, Resource resource) {
		return this.ontoModel.createIndividual(uri, resource);
	}

	public OntModel getOntoModel() {
		return ontoModel;
	}

	public OntClass gitRepositoryClass() {
		return this.ontoModel.getOntClass(ONTOLOGY_GIT_NAMESPACE + "GitRepository");
	}

	public OntClass gitCommitClass() {
		return this.ontoModel.getOntClass(ONTOLOGY_GIT_NAMESPACE + "GitCommit");
	}

	public OntClass gitBranchClass() {
		return this.ontoModel.getOntClass(ONTOLOGY_GIT_NAMESPACE + "GitBranch");
	}

	public OntClass gitTagClass() {
		return this.ontoModel.getOntClass(ONTOLOGY_GIT_NAMESPACE + "GitTag");
	}

	public OntClass gitChangeClass() {
		return this.ontoModel.getOntClass(ONTOLOGY_GIT_NAMESPACE + "GitChange");
	}

	public OntClass gitAuthor() {
		return this.ontoModel.getOntClass(ONTOLOGY_GIT_NAMESPACE + "GitAuthor");
	}

	public OntClass gitCommitter() {
		return this.ontoModel.getOntClass(ONTOLOGY_HISTORY_NAMESPACE + "Committer");
	}
	
	public Resource fileClass() {
		return this.ontoModel.getOntClass(ONTOLOGY_MAIN_NAMESPACE + "SoftwareArtifactFile");
	}

	// Data Properties
	public DatatypeProperty hasNameProperty() {
		return ontoModel.getDatatypeProperty(ONTOLOGY_MAIN_NAMESPACE + "hasName");
	}

	public DatatypeProperty hasSHAProperty() {
		return ontoModel.getDatatypeProperty(ONTOLOGY_GIT_NAMESPACE + "hasSHAIdentifier");
	}

	public DatatypeProperty hasMessageProperty() {
		return ontoModel.getDatatypeProperty(ONTOLOGY_GIT_NAMESPACE + "hasMessage");
	}

	public DatatypeProperty hasShortMessageProperty() {
		return ontoModel.getDatatypeProperty(ONTOLOGY_GIT_NAMESPACE + "hasShortMessage");
	}
	
	public DatatypeProperty hasCommitDateProperty() {
		return ontoModel.getDatatypeProperty(ONTOLOGY_HISTORY_NAMESPACE + "committedOn");
	}
	
	public DatatypeProperty hasRelativePathProperty() {
		return ontoModel.getDatatypeProperty(ONTOLOGY_MAIN_NAMESPACE + "hasRelativePath");
	}
	
	public DatatypeProperty isCreatedOnProperty() {
		return ontoModel.getDatatypeProperty(ONTOLOGY_HISTORY_NAMESPACE + "isCreatedOn");
	}
	
	public DatatypeProperty copiedOnProperty() {
		return ontoModel.getDatatypeProperty(ONTOLOGY_HISTORY_NAMESPACE + "isCopiedOn");
	}
	
	public DatatypeProperty isDeletedOnProperty() {
		return ontoModel.getDatatypeProperty(ONTOLOGY_HISTORY_NAMESPACE + "isDeletedOn");
	}
	
	public DatatypeProperty renamedOnProperty() {
		return ontoModel.getDatatypeProperty(ONTOLOGY_HISTORY_NAMESPACE + "isRenamedOn");
	}
	
	public DatatypeProperty lastModificationOnProperty() {
		return ontoModel.getDatatypeProperty(ONTOLOGY_HISTORY_NAMESPACE + "lastModifiedOn");
	}
	
	public DatatypeProperty hasLabelProperty() {
		return ontoModel.getDatatypeProperty(ONTOLOGY_GIT_NAMESPACE + "hasLabel");
	}
	
	public DatatypeProperty hasEmailAdressProperty() {
		return ontoModel.getDatatypeProperty(ONTOLOGY_GIT_NAMESPACE + "hasEmailAddress");
	}

	// Object Properties
	public ObjectProperty hasHeadProperty() {
		return ontoModel.getObjectProperty(ONTOLOGY_GIT_NAMESPACE + "hasHead");
	}

	public ObjectProperty hasBranchProperty() {
		return ontoModel.getObjectProperty(ONTOLOGY_GIT_NAMESPACE + "hasBranch");
	}

	public ObjectProperty hasCommitProperty() {
		return ontoModel.getObjectProperty(ONTOLOGY_GIT_NAMESPACE + "hasCommit");
	}

	public ObjectProperty hasAuthorProperty() {

		return ontoModel.getObjectProperty(ONTOLOGY_GIT_NAMESPACE + "hasAuthor");
	}
	
	public ObjectProperty performsCommitProperty() {
		return ontoModel.getObjectProperty(ONTOLOGY_HISTORY_NAMESPACE + "performsCommit");
	}


	public ObjectProperty hasCommitterProperty() {
		return ontoModel.getObjectProperty(ONTOLOGY_HISTORY_NAMESPACE + "isCommittedBy");
	}
	
	public ObjectProperty modifiesFileProperty() {
		return ontoModel.getObjectProperty(ONTOLOGY_GIT_NAMESPACE + "modifiesFile");
	}
	
	public ObjectProperty hasModificationKindProperty() {
		return ontoModel.getObjectProperty(ONTOLOGY_GIT_NAMESPACE + "hasModificationKind");
	}
	
	public ObjectProperty createsFileProperty() {
		return ontoModel.getObjectProperty(ONTOLOGY_HISTORY_NAMESPACE + "createsFile");
	}
	
	//Individuals
	
	public Individual getModificationKindIndividual(String modificationKind) {
		if(modificationKind.equals("A")) {
			return ontoModel.getIndividual(ONTOLOGY_GIT_NAMESPACE + "Add");
		}
		else if(modificationKind.equals("D")) {
			return ontoModel.getIndividual(ONTOLOGY_GIT_NAMESPACE + "Delete");
		}
		else if(modificationKind.equals("C")) {
			return ontoModel.getIndividual(ONTOLOGY_GIT_NAMESPACE + "Copy");
		}
		else if(modificationKind.equals("R")) {
			return ontoModel.getIndividual(ONTOLOGY_GIT_NAMESPACE + "Rename");
		}
		else {
			return ontoModel.getIndividual(ONTOLOGY_GIT_NAMESPACE + "Update");
		}
	}
	
	

	// WRITE
	public void writeOntology(String outputPath) {
		try {
			ontoModel.write(new FileOutputStream(new File(outputPath)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ObjectProperty isNewCopyOfProperty() {
		return ontoModel.getObjectProperty(ONTOLOGY_HISTORY_NAMESPACE + "isNewCopyOf");
	}

	public ObjectProperty copiesFileProperty() {
		return ontoModel.getObjectProperty(ONTOLOGY_HISTORY_NAMESPACE + "copiesFile");
	}

	public ObjectProperty deletesFileProperty() {
		return ontoModel.getObjectProperty(ONTOLOGY_HISTORY_NAMESPACE + "deletesFile");
	}

	public ObjectProperty isRenamedToProperty() {
		return ontoModel.getObjectProperty(ONTOLOGY_HISTORY_NAMESPACE + "isRenamedTo");
	}

	public ObjectProperty renamesFileProperty() {
		return ontoModel.getObjectProperty(ONTOLOGY_HISTORY_NAMESPACE + "renamesFile");
	}

	public ObjectProperty updatesFileProperty() {
		return ontoModel.getObjectProperty(ONTOLOGY_HISTORY_NAMESPACE + "updatesFiles");
	}

	public ObjectProperty hasParentProperty() {
		return ontoModel.getObjectProperty(ONTOLOGY_MAIN_NAMESPACE + "hasParent");
	}

	public ObjectProperty atCommitProperty() {
		return ontoModel.getObjectProperty(ONTOLOGY_GIT_NAMESPACE + "tagAtCommit");
	}

	public ObjectProperty hasTagProperty() {
		return ontoModel.getObjectProperty(ONTOLOGY_GIT_NAMESPACE + "hasTag");
	}

	

	

	

	
	
	

	

	

	

	

	

	

	

	

	
	

}
