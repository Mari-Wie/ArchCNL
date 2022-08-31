package org.archcnl.owlify.git.ontology;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.archcnl.owlify.core.MainOntology;

public class GitOntology {

    private OntModel ontoModel;
    private MainOntology mainModel;
    public static final String GIT_PREFIX = "http://arch-ont.org/ontologies/git.owl#";
    public static final String HISTORY_PREFIX = "http://arch-ont.org/ontologies/history.owl#";
    public static final String MAIN_PREFIX = "http://arch-ont.org/ontologies/main.owl#";

    public GitOntology(
            InputStream gitOntology, InputStream historyOntology, InputStream mainOntology) {
        loadBaseOntologies(gitOntology, historyOntology);
        mainModel = new MainOntology(mainOntology);
    }

    private void loadBaseOntologies(InputStream gitOntology, InputStream historyOntology) {
        ontoModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        ontoModel.read(historyOntology, null);
        ontoModel.read(gitOntology, null);
    }

    public OntModel codeModel() {
        return ontoModel;
    }

    public MainOntology mainModel() {
        return mainModel;
    }
    /**
     * This method finalizes the ontology and returns the result. DO NOT call this method more than
     * once. DO NOT call it before the modelling is completed.
     */
    public Model getFinalModel() {
        ontoModel.add(mainModel.getOntology());
        return ontoModel;
    }

    public String getOntologyNamespace() {
        return GIT_PREFIX;
    }

    public String getHistoryNamespace() {
        return HISTORY_PREFIX;
    }

    public String getMainNamespace() {
        return MAIN_PREFIX;
    }

    public Individual createIndividual(String uri, Resource resource) {
        return this.ontoModel.createIndividual(uri, resource);
    }

    public OntModel getOntoModel() {
        return ontoModel;
    }

    public OntClass gitRepositoryClass() {
        return this.ontoModel.getOntClass(GIT_PREFIX + "GitRepository");
    }

    public OntClass gitCommitClass() {
        return this.ontoModel.getOntClass(GIT_PREFIX + "GitCommit");
    }

    public OntClass gitBranchClass() {
        return this.ontoModel.getOntClass(GIT_PREFIX + "GitBranch");
    }

    public OntClass gitTagClass() {
        return this.ontoModel.getOntClass(GIT_PREFIX + "GitTag");
    }

    public OntClass gitChangeClass() {
        return this.ontoModel.getOntClass(GIT_PREFIX + "GitChange");
    }

    public OntClass gitAuthor() {
        return this.ontoModel.getOntClass(GIT_PREFIX + "GitAuthor");
    }

    public OntClass gitCommitter() {
        return this.ontoModel.getOntClass(HISTORY_PREFIX + "Committer");
    }

    public OntClass historyVersion() {
        return this.ontoModel.getOntClass(HISTORY_PREFIX + "Version");
    }

    public Resource fileClass() {
        return this.mainModel.getOntology().getOntClass(MAIN_PREFIX + "SoftwareArtifactFile");
    }

    // Data Properties
    public DatatypeProperty hasNameProperty() {
        return ontoModel.getDatatypeProperty(GIT_PREFIX + "hasName");
    }

    public DatatypeProperty hasSHAProperty() {
        return ontoModel.getDatatypeProperty(GIT_PREFIX + "hasSHAIdentifier");
    }

    public DatatypeProperty hasMessageProperty() {
        return ontoModel.getDatatypeProperty(GIT_PREFIX + "hasMessage");
    }

    public DatatypeProperty hasShortMessageProperty() {
        return ontoModel.getDatatypeProperty(GIT_PREFIX + "hasShortMessage");
    }

    public DatatypeProperty hasCommitDateProperty() {
        return ontoModel.getDatatypeProperty(HISTORY_PREFIX + "committedOn");
    }

    public DatatypeProperty hasRelativePathProperty() {
        return this.mainModel.getOntology().getDatatypeProperty(MAIN_PREFIX + "hasRelativePath");
    }

    public DatatypeProperty isCreatedOnProperty() {
        return ontoModel.getDatatypeProperty(HISTORY_PREFIX + "isCreatedOn");
    }

    public DatatypeProperty copiedOnProperty() {
        return ontoModel.getDatatypeProperty(HISTORY_PREFIX + "isCopiedOn");
    }

    public DatatypeProperty isDeletedOnProperty() {
        return ontoModel.getDatatypeProperty(HISTORY_PREFIX + "isDeletedOn");
    }

    public DatatypeProperty renamedOnProperty() {
        return ontoModel.getDatatypeProperty(HISTORY_PREFIX + "isRenamedOn");
    }

    public DatatypeProperty lastModificationOnProperty() {
        return ontoModel.getDatatypeProperty(HISTORY_PREFIX + "lastModifiedOn");
    }

    public DatatypeProperty hasVersionNumberProperty() {
        return ontoModel.getDatatypeProperty(HISTORY_PREFIX + "hasVersionNumber");
    }

    public DatatypeProperty hasLabelProperty() {
        return ontoModel.getDatatypeProperty(GIT_PREFIX + "hasLabel");
    }

    public DatatypeProperty hasEmailAdressProperty() {
        return ontoModel.getDatatypeProperty(GIT_PREFIX + "hasEmailAddress");
    }

    // Object Properties
    public ObjectProperty hasHeadProperty() {
        return ontoModel.getObjectProperty(GIT_PREFIX + "hasHead");
    }

    public ObjectProperty hasBranchProperty() {
        return ontoModel.getObjectProperty(GIT_PREFIX + "hasBranch");
    }

    public ObjectProperty hasCommitProperty() {
        return ontoModel.getObjectProperty(GIT_PREFIX + "hasCommit");
    }

    public ObjectProperty hasAuthorProperty() {

        return ontoModel.getObjectProperty(GIT_PREFIX + "hasAuthor");
    }

    public ObjectProperty performsCommitProperty() {
        return ontoModel.getObjectProperty(HISTORY_PREFIX + "performsCommit");
    }

    public ObjectProperty hasCommitterProperty() {
        return ontoModel.getObjectProperty(HISTORY_PREFIX + "isCommittedBy");
    }

    public ObjectProperty modifiesFileProperty() {
        return ontoModel.getObjectProperty(GIT_PREFIX + "modifiesFile");
    }

    public ObjectProperty hasModificationKindProperty() {
        return ontoModel.getObjectProperty(GIT_PREFIX + "hasModificationKind");
    }

    public ObjectProperty createsFileProperty() {
        return ontoModel.getObjectProperty(HISTORY_PREFIX + "createsFile");
    }

    public ObjectProperty hasVersionProperty() {
        return ontoModel.getObjectProperty(HISTORY_PREFIX + "hasVersion");
    }

    // Individuals

    public Individual getModificationKindIndividual(String modificationKind) {
        if (modificationKind.equals("A")) {
            return ontoModel.getIndividual(GIT_PREFIX + "Add");
        } else if (modificationKind.equals("D")) {
            return ontoModel.getIndividual(GIT_PREFIX + "Delete");
        } else if (modificationKind.equals("C")) {
            return ontoModel.getIndividual(GIT_PREFIX + "Copy");
        } else if (modificationKind.equals("R")) {
            return ontoModel.getIndividual(GIT_PREFIX + "Rename");
        } else {
            return ontoModel.getIndividual(GIT_PREFIX + "Update");
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
        return ontoModel.getObjectProperty(HISTORY_PREFIX + "isNewCopyOf");
    }

    public ObjectProperty copiesFileProperty() {
        return ontoModel.getObjectProperty(HISTORY_PREFIX + "copiesFile");
    }

    public ObjectProperty deletesFileProperty() {
        return ontoModel.getObjectProperty(HISTORY_PREFIX + "deletesFile");
    }

    public ObjectProperty isRenamedToProperty() {
        return ontoModel.getObjectProperty(HISTORY_PREFIX + "isRenamedTo");
    }

    public ObjectProperty renamesFileProperty() {
        return ontoModel.getObjectProperty(HISTORY_PREFIX + "renamesFile");
    }

    public ObjectProperty updatesFileProperty() {
        return ontoModel.getObjectProperty(HISTORY_PREFIX + "updatesFiles");
    }

    public ObjectProperty hasParentProperty() {
        return this.mainModel.getOntology().getObjectProperty(MAIN_PREFIX + "hasParent");
    }

    public ObjectProperty atCommitProperty() {
        return ontoModel.getObjectProperty(GIT_PREFIX + "tagAtCommit");
    }

    public ObjectProperty hasTagProperty() {
        return ontoModel.getObjectProperty(GIT_PREFIX + "hasTag");
    }
}
