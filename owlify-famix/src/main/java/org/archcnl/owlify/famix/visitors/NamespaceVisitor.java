package org.archcnl.owlify.famix.visitors;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.codemodel.Namespace;
import org.archcnl.owlify.famix.ontology.FamixOntology;

public class NamespaceVisitor extends VoidVisitorAdapter<Void> {

    private FamixOntology ontology;
    private Individual currentUnitIndividual;

    private Namespace namespace;

    public NamespaceVisitor(FamixOntology ontology, Individual currentUnitIndividual) {
        this.ontology = ontology;
        this.currentUnitIndividual = currentUnitIndividual;

        Individual metaNamespace = ontology.createNamespaceIndividual("");

        ontology.setHasNamePropertyForNamedEntity("", metaNamespace);

        namespace = Namespace.TOP;
    }

    public NamespaceVisitor() {
        namespace = Namespace.TOP;
    }

    @Override
    public void visit(PackageDeclaration n, Void arg) {
        createPackageBasedOnQualifiedName(n.getName().asString());
        //        Individual namespaceIndividual =
        // ontology.getNamespaceIndividual(n.getName().asString());
        //        ontology.setNamespaceContainsProperty(namespaceIndividual, currentUnitIndividual);
    }

    private void createPackageBasedOnQualifiedName(String packageName) {
        String[] split = packageName.split("\\.");
        String name = "";

        createOrUpdate("", split[0]);

        String parent = split[0];

        for (int n = 1; n < split.length; n++) {
            name = parent + "." + split[n];
            createOrUpdate(parent, name);
            parent = name;
        }
    }

    private void createOrUpdate(String parent, String name) {
        //        Individual parentNamespace = ontology.getNamespaceIndividual(parent);
        //        Individual namespaceIndividual = ontology.createNamespaceIndividual(name);

        //        ontology.setNamespaceContainsProperty(parentNamespace, namespaceIndividual);

        namespace = new Namespace(name, namespace);
    }

    public Namespace getNamespace() {
        return namespace;
    }
}
