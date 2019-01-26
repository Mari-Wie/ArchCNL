import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;

public class TestRuleReasoning {

	public static void main(String[] args) throws FileNotFoundException {
		String base = "C:\\Users\\Sandra\\Documents\\Promotion\\repositories\\Promotion\\DL_formalizations\\teammates_formalization\\ecsa2018\\logic_component_rules\\logiccore\\";
		String conceptsBase = base + "\\logic_core_should_not_access_logic_api\\";
		String pathToRules = conceptsBase + "mapping_rules.txt";
		String data = "C:\\Users\\Sandra\\Documents\\Promotion\\repositories\\Promotion\\DL_formalizations\\test\\test_teammates.owl";

		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		System.out.println("reading");
		ontModel.read(data);
		
		System.out.println(ontModel.listStatements().toList().size());
		
		ontModel.createClass("http://www.semanticweb.org/sandra/ontologies/2018/1/javacodeontology#LogicCore");
		ontModel.createClass("http://www.semanticweb.org/sandra/ontologies/2018/1/javacodeontology#LogicAPI");
		ontModel.createObjectProperty("http://www.semanticweb.org/sandra/ontologies/2018/1/javacodeontology#access");
		
		
		System.out.println(ontModel.listStatements().toList().size());
		
		System.out.println("reasoner");
		Reasoner reasoner = new GenericRuleReasoner( Rule.rulesFromURL(pathToRules ) );
		System.out.println("infmodel");
		InfModel infmodel = ModelFactory.createInfModel(reasoner,ontModel);
		System.out.println("writing");
		infmodel.write(new FileOutputStream(new File(base+"result_jena_inferred.owl")));
		
		StmtIterator statements = infmodel.listStatements();
		
		System.out.println(statements.toList().size());
		
	}

}
