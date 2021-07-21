package org.archcnl.owlify.core;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.jena.rdf.model.Model;
import org.junit.Test;

public class AbstractOwlifyComponentTest extends AbstractOwlifyComponent {
	
	// AbstractOwlifyComponent is the abstract base class for Input Parsers
	@Test
	public void givenPath_whenAddedToAbstractOwlifyComponent_thenPathIsProperlyAdded() {
		// given
		String testPath = "c:/dir1/dir2/dir3";
		String testPath2 = "c:/dir1/dir2/dir3/dir4";
		Path path = Paths.get(testPath);
		Path path2 = Paths.get(testPath2);

		// when
		addSourcePath(path);
		addSourcePath(path2);

		// then
		assertEquals(path, getSourcePaths().get(0));
		assertEquals(2, getSourcePaths().size());
	}

	@Test
	public void givenStubMethods_whenTestingDueToDesired100percentTestCoverage_thenGetExpectedResults() {
		// given, when, then
		assertEquals(null, getProvidedNamespaces());
		assertEquals(null, transform());
	}

	// Necessary stub implementations of inherited methods
	@Override
	public Map<String, String> getProvidedNamespaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model transform() {
		// TODO Auto-generated method stub
		return null;
	}
}
