package conformancecheck.impl;

import com.google.inject.AbstractModule;

import api.StardogConnectionAPI;
import api.StardogICVAPI;
import impl.StardogConnectionAPIImpl;
import impl.StardogICVAPIImpl;

public class ConformanceCheckModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(StardogICVAPI.class).to(StardogICVAPIImpl.class);
		bind(StardogConnectionAPI.class).to(StardogConnectionAPIImpl.class);
	}

}
