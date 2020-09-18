package api.axioms;

import java.util.Collections;
import java.util.List;

import datatypes.RuleType;

public class UniversalAxiom implements CustomAxiom {
	
	private String iriPath;
	private String subject;
	private List<String> objects;
	private String property;
	
	public UniversalAxiom(String iriPath, String subject, List<String> objects, String property) {
		this.iriPath = iriPath;
		this.subject = subject;
		this.objects = objects;
		this.property = property;
	}

	public String getAxiomString() {
		
		Collections.sort(objects);
		
		if(objects.size()==1) {
			return "AxiomConstraint{<"+iriPath+subject+"> rdfs:subClassOf (<"+iriPath+property+"> only <"+iriPath+objects.get(0)+">)}";			
		}
		else {
			String header = "AxiomConstraint{<"+iriPath+subject+"> rdfs:subClassOf (<"+iriPath+property+"> only ";
			String objectUnion = "(";
			for(int i = 0; i < objects.size(); i++) {
				if(i!=objects.size()-1)
				{
					objectUnion+="<"+iriPath+objects.get(i)+"> or ";					
				}
				else
				{
					objectUnion+="<"+iriPath+objects.get(i)+">))}";
				}
			}
			return header+objectUnion;
		}
	}

	public RuleType getRuleType() {
		return RuleType.UNIVERSAL;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
