/*
 * generated by Xtext 2.22.0
 */
package org.architecture.cnl.ide;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.architecture.cnl.ArchcnlRuntimeModule;
import org.architecture.cnl.ArchcnlStandaloneSetup;
import org.eclipse.xtext.util.Modules2;

/**
 * Initialization support for running Xtext languages as language servers.
 */
public class ArchcnlIdeSetup extends ArchcnlStandaloneSetup {

	@Override
	public Injector createInjector() {
		return Guice.createInjector(Modules2.mixin(new ArchcnlRuntimeModule(), new ArchcnlIdeModule()));
	}
	
}
