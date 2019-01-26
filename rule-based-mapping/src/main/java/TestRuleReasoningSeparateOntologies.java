import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.List;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;

import com.github.andrewoma.dexx.collection.ArrayList;

import api.ReasoningConfiguration;

public class TestRuleReasoningSeparateOntologies {

	public static void main(String[] args) throws FileNotFoundException {
		
		ReasoningConfiguration config = Configurations.logic_only_access_storage_api_without_refinement();
		//for (ReasoningConfiguration config : allConfigs) {

			OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
			System.out.println("reading");
			ontModel.read(config.getPathToData());
			ontModel.read(config.getPathToConcepts());

			System.out.println(ontModel.listStatements().toList().size());

			System.out.println("reasoner");
			Reasoner reasoner = new GenericRuleReasoner(Rule.rulesFromURL(config.getPathToMappingRules()));
			System.out.println("infmodel");
			InfModel infmodel = ModelFactory.createInfModel(reasoner, ontModel);
			System.out.println("writing to: " + config.getResultPath());
			infmodel.write(new FileOutputStream(new File(config.getResultPath())));

			StmtIterator statements = infmodel.listStatements();

			System.out.println(statements.toList().size());
		//}

	}

}
