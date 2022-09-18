package org.archcnl.domain.input.visualization;

import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Function;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.elements.AnnotationInstance;
import org.archcnl.domain.input.visualization.elements.AnnotationInstanceAttribute;
import org.archcnl.domain.input.visualization.elements.AnnotationType;
import org.archcnl.domain.input.visualization.elements.AnnotationTypeAttribute;
import org.archcnl.domain.input.visualization.elements.FamixClass;
import org.archcnl.domain.input.visualization.elements.FamixEnum;
import org.archcnl.domain.input.visualization.elements.Field;
import org.archcnl.domain.input.visualization.elements.Method;
import org.archcnl.domain.input.visualization.elements.Namespace;
import org.archcnl.domain.input.visualization.elements.Parameter;
import org.archcnl.domain.input.visualization.elements.PlantUmlElement;
import org.archcnl.domain.input.visualization.elements.PrimitiveType;
import org.archcnl.domain.input.visualization.elements.SoftwareArtifaceFile;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public class PlantUmlMapper {

    private static final Map<String, Function<Variable, PlantUmlElement>> conceptMap =
            Map.ofEntries(
                    entry("SoftwareArtifactFile", SoftwareArtifaceFile::new),
                    entry("Namespace", Namespace::new),
                    entry("FamixClass", FamixClass::new),
                    entry("Enum", FamixEnum::new),
                    entry("Attribute", Field::new),
                    entry("Method", Method::new),
                    entry("Parameter", Parameter::new),
                    // LocalVariable is hard to model in class diagram
                    // entry("LocalVariable", LocalVariable::new),
                    entry("PrimitiveType", PrimitiveType::new),
                    entry("AnnotationType", AnnotationType::new),
                    entry("AnnotationTypeAttribute", AnnotationTypeAttribute::new),
                    entry("AnnotationInstance", AnnotationInstance::new),
                    entry("AnnotationInstanceAttribute", AnnotationInstanceAttribute::new));

    private PlantUmlMapper() {}

    public static PlantUmlElement createElement(Concept concept, Variable variable)
            throws MappingToUmlTranslationFailedException {
        // TODO add support for BooleanValue and StringValue
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
