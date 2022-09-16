package org.archcnl.domain.input.visualization;

import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Function;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.input.visualization.elements.FamixClass;
import org.archcnl.domain.input.visualization.elements.Field;
import org.archcnl.domain.input.visualization.elements.Method;
import org.archcnl.domain.input.visualization.elements.Namespace;
import org.archcnl.domain.input.visualization.elements.Parameter;
import org.archcnl.domain.input.visualization.elements.PlantUmlElement;

public class PlantUmlMapper {

    private static final Map<String, Function<String, PlantUmlElement>> conceptMap =
            Map.ofEntries(
                    entry("SoftwareArtifactFile", null),
                    entry("Namespace", Namespace::new),
                    entry("FamixClass", FamixClass::new),
                    entry("Enum", null),
                    entry("Attribute", Field::new),
                    entry("Method", Method::new),
                    entry("Parameter", Parameter::new),
                    entry("LocalVariable", null),
                    entry("PrimitiveType", null),
                    entry("AnnotationType", null),
                    entry("AnnotationTypeAttribute", null),
                    entry("AnnotationInstance", null),
                    entry("AnnotationInstanceAttribute", null));

    private PlantUmlMapper() {}

    public static PlantUmlElement createElement(Concept concept, String variableName) {
        // TODO add support for BooleanValue and StringValue
        return conceptMap.get(concept.getName()).apply(variableName);
    }

    private static Map.Entry<String, Function<String, PlantUmlElement>> entry(
            String name, Function<String, PlantUmlElement> factoryMethod) {
        return new AbstractMap.SimpleEntry<>(name, factoryMethod);
    }
}
