package org.archcnl.webui.datatypes.mappings;

import java.util.List;

public class SpecialRelation extends Relation {

	public SpecialRelation(String name, List<ObjectType> relatableObjectTypes) {
		super(name, relatableObjectTypes);
	}

	@Override
	public String toStringRepresentation() {
		return getName();
	}

}
