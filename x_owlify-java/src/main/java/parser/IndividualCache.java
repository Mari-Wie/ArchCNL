package parser;
import java.util.HashMap;
import java.util.Map;

import org.apache.jena.ontology.Individual;

import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.type.Type;

public class IndividualCache {

	private static Map<String,Individual> typeCache = new HashMap<String, Individual>();
	private static Map<String,Individual> packageCache = new HashMap<String,Individual>();
	private static Map<String, Individual> enumCache = new HashMap<String,Individual>();
	
	private static IndividualCache instance;
	
	public static IndividualCache getInstance() {
		if (instance == null) {
			instance = new IndividualCache();
		}
		return instance;
	}


	public void updateTypes(String element, Individual individual) {
		if(!typeCache.containsKey(element)) {
			typeCache.put(element, individual);			
		}
	}
	
	public Individual getTypeIndividual(String element) {
		return typeCache.get(element);
	}

	public void updatePackage(String packageName, Individual packageIndividual) {
		if(!packageCache.containsKey(packageName)) {
			packageCache.put(packageName, packageIndividual);
		}
	}
	
	public Individual getPackageIndividual(String name) {
		return packageCache.get(name);
	}

	public void updateEnums(String name, Individual ontoEnumIndividual) {
		
		if(!enumCache.containsKey(name)) {
			enumCache.put(name, ontoEnumIndividual);
		}
		
	}
	
	public Individual getEnumIndividual(String element) {
		return enumCache.get(element);
	}


	public void print() {
		
		for (String s : packageCache.keySet()) {
			
			System.out.println(s);
		
		}
		
	}


	public Map<String,Individual> getPackageCache() {
		return packageCache;
	}


	public  Map<String,Individual> getTypeIndividuals() {
		return typeCache;
	}

}
