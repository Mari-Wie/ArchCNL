package org.archcnl.domain.common.conceptsandrelations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.VariableManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.common.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.model.mappings.ConceptMapping;

public class CustomConcept extends Concept {

    private static final String CONCEPT_TYPE = "architecture";

    private Optional<ConceptMapping> mapping;

    public CustomConcept(String name, String description) {
        super(name, description);
        this.mapping = Optional.empty();
    }

    public void setMapping(ConceptMapping mapping) throws UnrelatedMappingException {
        if (this.equals(mapping.getThenTriplet().getObject())) {
            this.mapping = Optional.of(mapping);
        } else {
            throw new UnrelatedMappingException(
                    getName(), mapping.getThenTriplet().getObject().getName());
        }
    }

    public Set<ActualObjectType> getBaseTypesFromMapping(ConceptManager conceptManager) {
        Set<ActualObjectType> baseTypes = new HashSet<>();
        // Recursively add super types found in mappings
        Queue<CustomConcept> queue = new LinkedList<>(Arrays.asList(this));
        while (!queue.isEmpty()) {
            Optional<ConceptMapping> curMapping = queue.poll().getMapping();
            if (curMapping.isPresent()) {
                String subjectName = curMapping.get().getThenTriplet().getSubject().getName();
                VariableManager varManager = new VariableManager();
                for (AndTriplets andTriplets : curMapping.get().getWhenTriplets()) {
                    varManager.parseVariableTypes(andTriplets, conceptManager);
                    Optional<Variable> subjectVariable = varManager.getVariableByName(subjectName);
                    if (subjectVariable.isPresent()) {
                        Set<ActualObjectType> types = subjectVariable.get().getDynamicTypes();
                        types.stream()
                                .filter(CustomConcept.class::isInstance)
                                .map(CustomConcept.class::cast)
                                .forEach(queue::add);
                        baseTypes.addAll(types);
                    }
                }
            }
        }
        return baseTypes;
    }

    public Optional<ConceptMapping> getMapping() {
        return mapping;
    }

    @Override
    protected String getConceptType() {
        return CONCEPT_TYPE;
    }

    @Override
    public boolean isEditable() {
        return true;
    }
}
