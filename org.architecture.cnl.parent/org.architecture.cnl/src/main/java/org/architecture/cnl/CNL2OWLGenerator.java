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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CNL2OWLGenerator {

private static final Logger LOG = LogManager.getLogger(CNL2OWLGenerator.class);


	public void transformCNLFile(String path) {

		 LOG.info("path    : "+path);

		ArchcnlStandaloneSetup setup = new ArchcnlStandaloneSetup();

		Injector injector = setup.createInjectorAndDoEMFRegistration();

		XtextResourceSet set = injector.getInstance(XtextResourceSet.class);
		// load a resource by URI, in this case from the file system
		URI uri = URI.createFileURI(path);
		LOG.info("uri     : "+uri.toString());
		
		Resource resource = set.getResource(uri, true);
		
		IResourceValidator validator = ((XtextResource) resource).getResourceServiceProvider().getResourceValidator();
		List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
		for (Issue issue : issues) {
			System.out.println(issue.getMessage());
		}
		GeneratorDelegate generator = injector.getInstance(GeneratorDelegate.class);
		
		//fsa wird nur als leer Hülle  für den Aufruf erstellt, aber nicht weiter verwendet.
		InMemoryFileSystemAccess fsa = new InMemoryFileSystemAccess();

		LOG.info("resource: "+resource);
		generator.doGenerate(resource, fsa);
	}

}