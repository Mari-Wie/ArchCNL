package visitors;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import parser.IndividualCache;

public class PackageVisitor extends VoidVisitorAdapter<Void> {

	private Individual javaClassIndividual;
	private OntModel ontoModel;
	private String ontologyNamespace;

	private OntClass ontoPackageClass;

	public PackageVisitor(String ontologyNamespace, OntModel ontoModel, Individual javaClassIndividual) {

		this.ontoModel = ontoModel;
		this.ontologyNamespace = ontologyNamespace;
		ontoPackageClass = ontoModel.getOntClass(ontologyNamespace + "JavaPackage");
		this.javaClassIndividual = javaClassIndividual;

		Individual metaPackage = IndividualCache.getInstance().getPackageIndividual("");
		if (metaPackage == null) {
			metaPackage = ontoModel.createIndividual(ontologyNamespace + "JavaPackage" + GlobalJavaPackageId.get(), ontoPackageClass);
			metaPackage.addLiteral(ontoModel.getProperty(ontologyNamespace + "hasName"), "");
			IndividualCache.getInstance().updatePackage("", metaPackage);
		}

	}

	@Override
	public void visit(PackageDeclaration n, Void arg) {

		String packageName = n.getName().asString();

		createPackageBasedOnQualifiedName(packageName);

		Individual packageIndividual = IndividualCache.getInstance().getPackageIndividual(packageName);
		if (packageIndividual == null) {
			packageIndividual = ontoModel
					.createIndividual(ontologyNamespace + "JavaPackage" + GlobalJavaPackageId.get(), ontoPackageClass);
			packageIndividual.addLiteral(ontoModel.getProperty(ontologyNamespace + "hasName"), packageName);
			IndividualCache.getInstance().updatePackage(packageName, packageIndividual);
		}
		packageIndividual.addProperty(ontoModel.getProperty(ontologyNamespace + "packageContains"),
				this.javaClassIndividual);
	}

	private void createPackageBasedOnQualifiedName(String packageName) {
		String[] split = packageName.split("\\.");
		String name = "";

		createOrUpdate("", split[0]);

		String parent = split[0];
		;
		for (int n = 1; n < split.length; n++) {
			name = parent + "." + split[n];
			createOrUpdate(parent, name);
			parent = name;
		}
	}

	private void createOrUpdate(String parent, String name) {
//		System.out.println("Parent: " + parent);
//		System.out.println("Package: " + name);
		Individual pckg = IndividualCache.getInstance().getPackageIndividual(name);
		Individual parentPckg = IndividualCache.getInstance().getPackageIndividual(parent);
		if (pckg == null) {
			pckg = ontoModel.createIndividual(ontologyNamespace + "JavaPackage" + GlobalJavaPackageId.get(),
					ontoPackageClass);
			pckg.addLiteral(ontoModel.getProperty(ontologyNamespace + "hasName"), name);
			parentPckg.addProperty(ontoModel.getProperty(ontologyNamespace + "packageContains"), pckg);
			IndividualCache.getInstance().updatePackage(name, pckg);
		}

	}

}
