package org.architecture.cnl;

import java.util.List;

//import org.architecture.cnl.ArchcnlStandaloneSetup;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.GeneratorDelegate;
import org.eclipse.xtext.generator.InMemoryFileSystemAccess;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Injector;

import datatypes.RuleType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CNL2OWLGenerator {

private static final Logger LOG = LogManager.getLogger(CNL2OWLGenerator.class);

	private static int id = 0;

	/**
	 * Translates the given file from ArchCNL's CNL to OWL. The resulting OWL ontology
	 * is written to the file "architecture{id}.owl", where ID is the a counter starting at 0 which is incremented after
	 * each call. Thus, the output file of the first call will be "architecture0.owl", the second one
	 * "architecture1.owl", and so on. Each input file must contain exactly one rule in the CNL!
	 * @param path The path of the file to transform.
	 * @return The RuleType of the parsed rule or null when something went wrong.
	 */
	public RuleType transformCNLFile(String path) {

		LOG.trace("transforming CNL file with path: "+path);

		ArchcnlStandaloneSetup setup = new ArchcnlStandaloneSetup();

		Injector injector = setup.createInjectorAndDoEMFRegistration();

		XtextResourceSet set = injector.getInstance(XtextResourceSet.class);
		
		// load a resource by URI, in this case from the file system
		URI uri = URI.createFileURI(path);
		LOG.trace("uri: "+uri);
		
		LOG.debug("Reading CNL file: " + path);
		Resource resource = set.getResource(uri, true);
		
		IResourceValidator validator = ((XtextResource) resource).getResourceServiceProvider().getResourceValidator();
		List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
		for (Issue issue : issues) {
			LOG.error(issue.getMessage());
		}
		GeneratorDelegate generator = injector.getInstance(GeneratorDelegate.class);
		
		//fsa wird nur als leer Hülle  für den Aufruf erstellt, aber nicht weiter verwendet.
		InMemoryFileSystemAccess fsa = new InMemoryFileSystemAccess();

		LOG.trace("resource: "+resource);
		generator.doGenerate(resource, fsa);
		
		RuleType ruleType = RuleTypeStorageSingleton.getInstance().retrieveTypeOfRule(id);
		
		id++;
		return ruleType;
	}
}