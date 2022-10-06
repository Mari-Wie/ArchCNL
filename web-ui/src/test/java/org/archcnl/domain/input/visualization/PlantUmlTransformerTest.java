package org.archcnl.domain.input.visualization;

import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.common.io.importhelper.MappingParser;
import org.archcnl.domain.common.io.importhelper.exceptions.NoMappingException;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlantUmlTransformerTest {

    private ConceptManager conceptManager;
    private RelationManager relationManager;

    @BeforeEach
    private void setup() throws ConceptDoesNotExistException {
        conceptManager = new ConceptManager();
        relationManager = new RelationManager(conceptManager);
    }

    @Test
    void givenSuperSimpleMapping_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException {
        // given
        String mappingString =
                "isThenConcept: (?class rdf:type famix:FamixClass) -> (?class rdf:type architecture:ThenConcept)";
        CustomConcept thenConcept = new CustomConcept("ThenConcept", "");
        ConceptMapping mapping =
                MappingParser.parseMapping(
                        mappingString, thenConcept, relationManager, conceptManager);

        // when
        AndTriplets whenTriplets = mapping.getWhenTriplets().get(0);
        Triplet thenTriplet = mapping.getThenTriplet();
        PlantUmlTransformer transformer =
                new PlantUmlTransformer(conceptManager, whenTriplets, thenTriplet);
        String plantUmlCode = transformer.transformToPlantUml();

        // then
        String expectedCode =
                "@startuml\n"
                        + "class \"?class\" as class {\n"
                        + "}\n"
                        + "note \"ThenConcept\" as ThenConcept\n"
                        + "ThenConcept .. class\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenSimpleMapping_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException {
        // given
        String mappingString =
                "isThenConcept: (?class rdf:type famix:FamixClass) "
                        + "(?class famix:isInterface 'true'^^xsd:boolean)"
                        + " -> (?class rdf:type architecture:ThenConcept)";
        CustomConcept thenConcept = new CustomConcept("ThenConcept", "");
        ConceptMapping mapping =
                MappingParser.parseMapping(
                        mappingString, thenConcept, relationManager, conceptManager);

        // when
        AndTriplets whenTriplets = mapping.getWhenTriplets().get(0);
        Triplet thenTriplet = mapping.getThenTriplet();
        PlantUmlTransformer transformer =
                new PlantUmlTransformer(conceptManager, whenTriplets, thenTriplet);
        String plantUmlCode = transformer.transformToPlantUml();

        // then
        String expectedCode =
                "@startuml\n"
                        + "interface \"?class\" as class {\n"
                        + "}\n"
                        + "note \"ThenConcept\" as ThenConcept\n"
                        + "ThenConcept .. class\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenRelationMapping_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException {
        // given
        String mappingString =
                "weirdRelationMapping: (?namespace famix:namespaceContains ?class)"
                        + " (?namespace famix:namespaceContains ?interface)"
                        + " (?class rdf:type famix:FamixClass)"
                        + " (?class famix:hasName 'ClassName')"
                        + " (?class famix:definesAttribute ?attribute)"
                        + " (?attribute famix:hasModifier 'public')"
                        + " (?attribute famix:hasModifier 'static')"
                        + " (?class famix:definesMethod ?method)"
                        + " (?method famix:hasName ?methodName)"
                        + " regex(?methodName, '.*main')"
                        + " (?method famix:hasModifier 'abstract')"
                        + " (?method famix:hasModifier 'private')"
                        + " (?interface famix:isInterface 'true'^^xsd:boolean)"
                        + " (?interface famix:hasModifier 'public') "
                        + " -> (?class architecture:weirdRelation ?interface)";
        RelationMapping mapping =
                MappingParser.parseMapping(mappingString, relationManager, conceptManager);

        // when
        AndTriplets whenTriplets = mapping.getWhenTriplets().get(0);
        Triplet thenTriplet = mapping.getThenTriplet();
        PlantUmlTransformer transformer =
                new PlantUmlTransformer(conceptManager, whenTriplets, thenTriplet);
        String plantUmlCode = transformer.transformToPlantUml();

        // then
        String expectedCode =
                "@startuml\n"
                        + "folder \"?namespace\" as namespace {\n"
                        + "	class \"ClassName\" as class {\n"
                        + "		{field} {static} +?attribute\n"
                        + "		{method} {abstract} -.*main()\n"
                        + "	}\n"
                        + "	+interface \"?interface\" as interface {\n"
                        + "	}\n"
                        + "}\n"
                        + "class -[bold]-> interface\n"
                        + "note on link: weirdRelation\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenSimpleMappingWithConnections_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException {
        // given
        String mappingString =
                "isThenConcept: (?file rdf:type main:SoftwareArtifactFile)"
                        + " (?file main:containsArtifact ?class)"
                        + " (?class famix:imports ?class2)"
                        + " (?file main:containsArtifact ?interface)"
                        + " (?inheritance famix:hasSubClass ?class)"
                        + " (?inheritance famix:hasSuperClass ?interface)"
                        + " (?interface famix:isInterface 'true'^^xsd:boolean)"
                        + " -> (?class rdf:type architecture:ThenConcept)";
        CustomConcept thenConcept = new CustomConcept("ThenConcept", "");
        ConceptMapping mapping =
                MappingParser.parseMapping(
                        mappingString, thenConcept, relationManager, conceptManager);

        // when
        AndTriplets whenTriplets = mapping.getWhenTriplets().get(0);
        Triplet thenTriplet = mapping.getThenTriplet();
        PlantUmlTransformer transformer =
                new PlantUmlTransformer(conceptManager, whenTriplets, thenTriplet);
        String plantUmlCode = transformer.transformToPlantUml();

        // then
        String expectedCode =
                "@startuml\n"
                        + "class \"?class\" as class {\n"
                        + "}\n"
                        + "note as file\n"
                        + "===File\n"
                        + "end note\n"
                        + "class \"?class2\" as class2 {\n"
                        + "}\n"
                        + "interface \"?interface\" as interface {\n"
                        + "}\n"
                        + "file +-- class\n"
                        + "class -[dashed]-> class2: <<imports>>\n"
                        + "file +-- interface\n"
                        + "note \"ThenConcept\" as ThenConcept\n"
                        + "ThenConcept .. class\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenMappingWithConnectionWithinClass_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException {
        // given
        String mappingString =
                "weirdRelationMapping: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:definesMethod ?method)"
                        + " (?method famix:definesParameter ?parameter)"
                        + " (?method famix:definesVariable ?localVariable)"
                        + " -> (?class architecture:paramRelation ?parameter)";
        RelationMapping mapping =
                MappingParser.parseMapping(mappingString, relationManager, conceptManager);

        // when
        AndTriplets whenTriplets = mapping.getWhenTriplets().get(0);
        Triplet thenTriplet = mapping.getThenTriplet();
        PlantUmlTransformer transformer =
                new PlantUmlTransformer(conceptManager, whenTriplets, thenTriplet);
        String plantUmlCode = transformer.transformToPlantUml();

        // then
        String expectedCode =
                "@startuml\n"
                        + "class \"?class\" as class {\n"
                        + "	{method} ?method(?parameter)\n"
                        + "}\n"
                        + "note as localVariable\n"
                        + "===LocalVariable\n"
                        + "?localVariable\n"
                        + "end note\n"
                        + "class::method *-- localVariable: definesVariable\n"
                        + "class -[bold]-> class::parameter\n"
                        + "note on link: paramRelation\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }
}
