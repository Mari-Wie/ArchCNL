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
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

public class GitOntClassesAndProperties {

	private OntModel ontoModel;
	private String ontologyNamespace;
	private String ontologyMainNamespace;
	private String ontologyHistoryNamespace;

	public GitOntClassesAndProperties() {
		ontologyNamespace = "http://www.arch-ont.org/ontologies/git.owl#";
		ontologyMainNamespace = "http://arch-ont.org/ontologies/main.owl#";
		ontologyHistoryNamespace = "http://arch-ont.org/ontologies/history.owl#";
		ontoModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		ontoModel.read("./ontology/main.owl");
		ontoModel.read("./ontology/history.owl");
		ontoModel.read("./ontology/git.owl");
	}

	public String getOntologyNamespace() {
		return ontologyNamespace;
	}

	public Individual createIndividual(String uri, Resource resource) {
		return this.ontoModel.createIndividual(uri, resource);
	}

	public OntModel getOntoModel() {
		return ontoModel;
	}

	public OntClass gitRepositoryClass() {
		return this.ontoModel.getOntClass(ontologyNamespace + "GitRepository");
	}

	public OntClass gitCommitClass() {
		return this.ontoModel.getOntClass(ontologyNamespace + "GitCommit");
	}

	public OntClass gitBranchClass() {
		return this.ontoModel.getOntClass(ontologyNamespace + "GitBranch");
	}

	public OntClass gitTagClass() {
		return this.ontoModel.getOntClass(ontologyNamespace + "GitTag");
	}

	public OntClass gitChangeClass() {
		return this.ontoModel.getOntClass(ontologyNamespace + "GitChange");
	}

	public OntClass gitAuthor() {
		return this.ontoModel.getOntClass(ontologyNamespace + "GitAuthor");
	}

	public OntClass gitCommitter() {
		return this.ontoModel.getOntClass(ontologyHistoryNamespace + "Committer");
	}
	
	public Resource fileClass() {
		return this.ontoModel.getOntClass(ontologyMainNamespace + "File");
	}

	// Data Properties
	public DatatypeProperty hasNameProperty() {
		return ontoModel.getDatatypeProperty(ontologyMainNamespace + "hasName");
	}

	public DatatypeProperty hasSHAProperty() {
		return ontoModel.getDatatypeProperty(ontologyNamespace + "hasSHAIdentifier");
	}

	public DatatypeProperty hasMessageProperty() {
		return ontoModel.getDatatypeProperty(ontologyNamespace + "hasMessage");
	}

	public DatatypeProperty hasShortMessageProperty() {
		// TODO Auto-generated method stub
		return ontoModel.getDatatypeProperty(ontologyNamespace + "hasShortMessage");
	}
	
	public DatatypeProperty hasCommitDateProperty() {
		return ontoModel.getDatatypeProperty(ontologyHistoryNamespace + "committedOn");
	}
	
	public DatatypeProperty hasRelativePathProperty() {
		return ontoModel.getDatatypeProperty(ontologyMainNamespace + "hasRelativePath");
	}
	
	public DatatypeProperty isCreatedOnProperty() {
		return ontoModel.getDatatypeProperty(ontologyHistoryNamespace + "isCreatedOn");
	}
	
	public DatatypeProperty copiedOnProperty() {
		return ontoModel.getDatatypeProperty(ontologyHistoryNamespace + "isCopiedOn");
	}
	
	public DatatypeProperty isDeletedOnProperty() {
		return ontoModel.getDatatypeProperty(ontologyHistoryNamespace + "isDeletedOn");
	}
	
	public DatatypeProperty renamedOnProperty() {
		return ontoModel.getDatatypeProperty(ontologyHistoryNamespace + "isRenamedOn");
	}
	
	public DatatypeProperty lastModificationOnProperty() {
		return ontoModel.getDatatypeProperty(ontologyHistoryNamespace + "lastModifiedOn");
	}
	
	public DatatypeProperty hasLabelProperty() {
		return ontoModel.getDatatypeProperty(ontologyNamespace + "hasLabel");
	}
	
	public DatatypeProperty hasEmailAdressProperty() {
		return ontoModel.getDatatypeProperty(ontologyMainNamespace + "hasEmailAdress");
	}

	// Object Properties
	public ObjectProperty hasHeadProperty() {
		return ontoModel.getObjectProperty(ontologyNamespace + "hasHead");
	}

	public ObjectProperty hasBranchProperty() {
		return ontoModel.getObjectProperty(ontologyNamespace + "hasBranch");
	}

	public ObjectProperty hasCommitProperty() {
		return ontoModel.getObjectProperty(ontologyNamespace + "hasCommit");
	}

	public ObjectProperty hasAuthorProperty() {

		return ontoModel.getObjectProperty(ontologyMainNamespace + "hasAuthor");
	}
	
	public ObjectProperty performsCommitProperty() {
		return ontoModel.getObjectProperty(ontologyHistoryNamespace + "performsCommit");
	}


	public ObjectProperty hasCommitterProperty() {
		return ontoModel.getObjectProperty(ontologyHistoryNamespace + "isCommittedBy");
	}
	
	public ObjectProperty modifiesFileProperty() {
		return ontoModel.getObjectProperty(ontologyNamespace + "modifiesFile");
	}
	
	public ObjectProperty hasModificationKindProperty() {
		return ontoModel.getObjectProperty(ontologyNamespace + "hasModificationKind");
	}
	
	public ObjectProperty createsFileProperty() {
		return ontoModel.getObjectProperty(ontologyHistoryNamespace + "createsFile");
	}
	
	//Individuals
	
	public Individual getModificationKindIndividual(String modificationKind) {
		if(modificationKind.equals("A")) {
			return ontoModel.getIndividual(ontologyNamespace + "Add");
		}
		else if(modificationKind.equals("D")) {
			return ontoModel.getIndividual(ontologyNamespace + "Delete");
		}
		else if(modificationKind.equals("C")) {
			return ontoModel.getIndividual(ontologyNamespace + "Copy");
		}
		else if(modificationKind.equals("R")) {
			return ontoModel.getIndividual(ontologyNamespace + "Rename");
		}
		else {
			return ontoModel.getIndividual(ontologyNamespace + "Update");
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
		return ontoModel.getObjectProperty(ontologyHistoryNamespace + "isNewCopyOf");
	}

	public ObjectProperty copiesFileProperty() {
		return ontoModel.getObjectProperty(ontologyHistoryNamespace + "copiesFile");
	}

	public ObjectProperty deletesFileProperty() {
		return ontoModel.getObjectProperty(ontologyHistoryNamespace + "deletesFile");
	}

	public ObjectProperty isRenamedToProperty() {
		return ontoModel.getObjectProperty(ontologyHistoryNamespace + "isRenamedTo");
	}

	public ObjectProperty renamesFileProperty() {
		return ontoModel.getObjectProperty(ontologyHistoryNamespace + "renamesFile");
	}

	public ObjectProperty updatesFileProperty() {
		return ontoModel.getObjectProperty(ontologyHistoryNamespace + "updatesFiles");
	}

	public ObjectProperty hasParentProperty() {
		return ontoModel.getObjectProperty(ontologyMainNamespace + "hasParent");
	}

	public ObjectProperty atCommitProperty() {
		return ontoModel.getObjectProperty(ontologyNamespace + "tagAtCommit");
	}

	public ObjectProperty hasTagProperty() {
		return ontoModel.getObjectProperty(ontologyNamespace + "hasTag");
	}

	

	

	

	
	
	

	

	

	

	

	

	

	

	

	
	

}
