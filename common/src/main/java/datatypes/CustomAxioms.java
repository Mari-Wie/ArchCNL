package datatypes;

import java.util.HashMap;
import java.util.Map;

import datatypes.axioms.CustomAxiom;

public class CustomAxioms {
	
	private static CustomAxioms instance;
	private Map<CustomAxiom, Integer> axioms;
	private static int id;
	
	public static CustomAxioms get() {
		if(instance == null) {
			instance = new CustomAxioms();
		}
		return instance;
	}
	
	public CustomAxioms() {

		axioms = new HashMap<>();
	
	}
	
	public void addCustomAxiom(CustomAxiom axiom) {
		axioms.put(axiom, id);
		id++;
	}
	
	public void addCustomAxioms(CustomAxiom axiom, CustomAxiom axiom2) {
		axioms.put(axiom, id);
		axioms.put(axiom2, id);
		
		id++;
	}
	
	public Map<CustomAxiom, Integer> getAxioms() {
		return axioms;
	}

}
