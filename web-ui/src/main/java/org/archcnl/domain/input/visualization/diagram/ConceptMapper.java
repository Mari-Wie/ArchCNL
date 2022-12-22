package org.archcnl.domain.input.visualization.diagram;

import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Function;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.diagram.elements.AnnotationInstance;
import org.archcnl.domain.input.visualization.diagram.elements.AnnotationInstanceAttribute;
import org.archcnl.domain.input.visualization.diagram.elements.AnnotationType;
import org.archcnl.domain.input.visualization.diagram.elements.AnnotationTypeAttribute;
import org.archcnl.domain.input.visualization.diagram.elements.FamixClass;
import org.archcnl.domain.input.visualization.diagram.elements.FamixEnum;
import org.archcnl.domain.input.visualization.diagram.elements.Field;
import org.archcnl.domain.input.visualization.diagram.elements.Method;
import org.archcnl.domain.input.visualization.diagram.elements.Namespace;
import org.archcnl.domain.input.visualization.diagram.elements.Parameter;
import org.archcnl.domain.input.visualization.diagram.elements.PlantUmlElement;
import org.archcnl.domain.input.visualization.diagram.elements.PrimitiveType;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public class ConceptMapper {

    private static final Map<String, Function<Variable, PlantUmlElement>> conceptMap =
            Map.ofEntries(
                    // SoftwareArtifactFile and LocalVariable can't be mapped
                    // As they don't fit in a UML class diagram
                    entry("Namespace", Namespace::new),
                    entry("FamixClass", FamixClass::new),
                    entry("Enum", FamixEnum::new),
                    entry("Attribute", Field::new),
                    entry("Method", Method::new),
                    entry("Parameter", Parameter::new),
                    entry("PrimitiveType", PrimitiveType::new),
                    entry("AnnotationType", AnnotationType::new),
                    entry("AnnotationTypeAttribute", AnnotationTypeAttribute::new),
                    entry("AnnotationInstance", AnnotationInstance::new),
                    entry("AnnotationInstanceAttribute", AnnotationInstanceAttribute::new));

    private ConceptMapper() {}

    public static PlantUmlElement createElement(Concept concept, Variable variable)
            throws MappingToUmlTranslationFailedException {
        String key = concept.getName();
        if (conceptMap.containsKey(key)) {
            return conceptMap.get(concept.getName()).apply(variable);
        }
        throw new MappingToUmlTranslationFailedException(key + " couldn't be translated.");
    }

    private static Map.Entry<String, Function<Variable, PlantUmlElement>> entry(
            String name, Function<Variable, PlantUmlElement> factoryMethod) {
        return new AbstractMap.SimpleEntry<>(name, factoryMethod);
    }
}
