package org.archcnl.domain.input.visualization;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.common.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.common.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.common.exceptions.UnrelatedMappingException;
import org.archcnl.domain.common.io.importhelper.MappingParser;
import org.archcnl.domain.common.io.importhelper.exceptions.NoMappingException;
import org.archcnl.domain.common.io.importhelper.exceptions.NoTripletException;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConceptPlantUmlTransformerTest {

    private ConceptManager conceptManager;
    private RelationManager relationManager;

    @BeforeEach
    private void setup() throws ConceptDoesNotExistException {
        conceptManager = new ConceptManager();
        relationManager = new RelationManager(conceptManager);
    }

    @Test
    void givenSuperSimpleMapping_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException, NoTripletException,
                    UnrelatedMappingException {
        // given
        String mappingString =
                "isThenConcept: (?class rdf:type famix:FamixClass) -> (?class rdf:type architecture:ThenConcept)";
        CustomConcept thenConcept = new CustomConcept("ThenConcept", "");
        ConceptMapping mapping =
                createConceptMapping(mappingString, Collections.emptyList(), thenConcept);

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager);
        String plantUmlCode = transformer.transformToPlantUml(mapping);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title isThenConcept\n"
                        + "class \"?class\" as class {\n"
                        + "}\n"
                        + "note \"ThenConcept\" as ThenConcept\n"
                        + "ThenConcept .. class\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenSimpleMapping_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException, NoTripletException,
                    UnrelatedMappingException {
        // given
        String mappingString =
                "isThenConcept: (?class rdf:type famix:FamixClass) "
                        + "(?class famix:isInterface 'true'^^xsd:boolean)"
                        + " -> (?class rdf:type architecture:ThenConcept)";
        CustomConcept thenConcept = new CustomConcept("ThenConcept", "");
        ConceptMapping mapping =
                createConceptMapping(mappingString, Collections.emptyList(), thenConcept);

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager);
        String plantUmlCode = transformer.transformToPlantUml(mapping);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title isThenConcept\n"
                        + "interface \"?class\" as class {\n"
                        + "}\n"
                        + "note \"ThenConcept\" as ThenConcept\n"
                        + "ThenConcept .. class\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenSimpleMappingWithConnections_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException, NoTripletException,
                    UnrelatedMappingException {
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
                createConceptMapping(mappingString, Collections.emptyList(), thenConcept);

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager);
        String plantUmlCode = transformer.transformToPlantUml(mapping);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title isThenConcept\n"
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
    void givenConceptMappingWithCustomConcept_whenTransform_thenCorrectPlantUml()
            throws NoMappingException, MappingToUmlTranslationFailedException,
                    ConceptAlreadyExistsException, UnrelatedMappingException, NoTripletException {
        // given
        CustomConcept controller = new CustomConcept("Controller", "");
        String controllerMappingString =
                "isController: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:hasName ?name)"
                        + " regex(?name, '.*Controller')"
                        + " -> (?class rdf:type architecture:Controller)";
        ConceptMapping controllerMapping =
                MappingParser.parseMapping(
                        controllerMappingString, controller, relationManager, conceptManager);
        controller.setMapping(controllerMapping);
        conceptManager.addConcept(controller);

        CustomConcept importingController = new CustomConcept("ImportingController", "");
        String mappingString =
                "isImportingController: (?class rdf:type famix:FamixClass)"
                        + " (?controller rdf:type architecture:Controller)"
                        + " (?controller famix:imports ?class)"
                        + " -> (?controller rdf:type architechture:ImportingController)";
        ConceptMapping mapping =
                createConceptMapping(mappingString, Collections.emptyList(), importingController);

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager);
        String plantUmlCode = transformer.transformToPlantUml(mapping);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title isImportingController\n"
                        + "class \"?class\" as class {\n"
                        + "}\n"
                        + "class \".*Controller\" as controller1 {\n"
                        + "}\n"
                        + "note \"Controller\" as Controller\n"
                        + "Controller .. controller1\n"
                        + "controller1 -[dashed]-> class: <<imports>>\n"
                        + "note \"ImportingController\" as ImportingController\n"
                        + "ImportingController .. controller1\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenConceptMappingWithElementPropertyOfCustomConcept_whenTransform_thenCorrectPlantUml()
            throws NoMappingException, MappingToUmlTranslationFailedException,
                    ConceptAlreadyExistsException, UnrelatedMappingException, NoTripletException {
        // given
        CustomConcept controller = new CustomConcept("Controller", "");
        String controllerMappingString =
                "isController: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:hasName ?name)"
                        + " regex(?name, '.*Controller')"
                        + " -> (?class rdf:type architecture:Controller)";
        ConceptMapping controllerMapping =
                MappingParser.parseMapping(
                        controllerMappingString, controller, relationManager, conceptManager);
        controller.setMapping(controllerMapping);
        conceptManager.addConcept(controller);

        CustomConcept importingController = new CustomConcept("ImportingController", "");
        String mappingString =
                "isImportingController: (?class rdf:type famix:FamixClass)"
                        + " (?controller rdf:type architecture:Controller)"
                        + " (?class famix:definesAttribute ?attribute)"
                        + " (?attribute famix:hasDeclaredType ?controller)"
                        + " -> (?controller rdf:type architechture:ImportingController)";

        ConceptMapping mapping =
                createConceptMapping(mappingString, Collections.emptyList(), importingController);

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager);
        String plantUmlCode = transformer.transformToPlantUml(mapping);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title isImportingController\n"
                        + "class \"?class\" as class {\n"
                        + "	{field} ?attribute : .*Controller\n"
                        + "}\n"
                        + "class \".*Controller\" as controller1 {\n"
                        + "}\n"
                        + "note \"Controller\" as Controller\n"
                        + "Controller .. controller1\n"
                        + "note \"ImportingController\" as ImportingController\n"
                        + "ImportingController .. controller1\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenDoubleConceptMappingWithCustomConcept_whenTransform_thenCorrectPlantUml()
            throws NoMappingException, MappingToUmlTranslationFailedException,
                    ConceptAlreadyExistsException, UnrelatedMappingException, NoTripletException {
        // given
        CustomConcept controller = new CustomConcept("Controller", "");
        String controllerMappingString =
                "isController: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:hasName ?name)"
                        + " regex(?name, '.*Controller')"
                        + " -> (?class rdf:type architecture:Controller)";
        ConceptMapping controllerMapping =
                MappingParser.parseMapping(
                        controllerMappingString, controller, relationManager, conceptManager);
        controller.setMapping(controllerMapping);
        conceptManager.addConcept(controller);

        CustomConcept importingController = new CustomConcept("ImportingController", "");
        String mappingString =
                "isImportingController: (?class rdf:type famix:FamixClass)"
                        + " (?controller rdf:type architecture:Controller)"
                        + " (?controller famix:imports ?class)"
                        + " -> (?controller rdf:type architechture:ImportingController)";
        String secondWhenString =
                "(?class rdf:type famix:FamixClass)"
                        + " (?controller rdf:type architecture:Controller)"
                        + " (?class famix:definesNestedType ?controller)";
        ConceptMapping mapping =
                createConceptMapping(
                        mappingString, Arrays.asList(secondWhenString), importingController);

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager);
        String plantUmlCode = transformer.transformToPlantUml(mapping);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title isImportingController\n"
                        + "package isImportingController1 <<Cloud>> {\n"
                        + "class \"?class\" as class {\n"
                        + "}\n"
                        + "class \".*Controller\" as controller1 {\n"
                        + "}\n"
                        + "note \"Controller\" as Controller\n"
                        + "Controller .. controller1\n"
                        + "controller1 -[dashed]-> class: <<imports>>\n"
                        + "}\n"
                        + "package isImportingController2 <<Cloud>> {\n"
                        + "class \"?class1\" as class1 {\n"
                        + "}\n"
                        + "class \".*Controller\" as controller21 {\n"
                        + "}\n"
                        + "note \"Controller\" as Controller1\n"
                        + "Controller1 .. controller21\n"
                        + "class1 +-- controller21\n"
                        + "}\n"
                        + "note \"ImportingController\" as ImportingController\n"
                        + "ImportingController .. controller1\n"
                        + "ImportingController .. controller21\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenDeeplyNestedMapping_whenTransform_thenCorrectPlantUml()
            throws NoMappingException, MappingToUmlTranslationFailedException,
                    ConceptAlreadyExistsException, UnrelatedMappingException, NoTripletException,
                    RelationAlreadyExistsException {
        // given
        String definesContentString =
                "definesContentMapping: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:definesMethod ?method)"
                        + " -> (?class architecture:definesContent ?method)";
        String definesContentSecondWhen =
                "(?class rdf:type famix:FamixClass)" + " (?class famix:definesAttribute ?att)";
        RelationMapping definesContentMapping =
                MappingParser.parseMapping(definesContentString, relationManager, conceptManager);
        AndTriplets secondWhen =
                MappingParser.parseWhenPart(
                        definesContentSecondWhen, relationManager, conceptManager);
        definesContentMapping.addAndTriplets(secondWhen);
        CustomRelation definesContentRelation =
                new CustomRelation("definesContent", "", new HashSet<>(), new HashSet<>());
        definesContentRelation.setMapping(definesContentMapping, conceptManager);
        relationManager.addRelation(definesContentRelation);

        CustomConcept doubleConcept = new CustomConcept("Double", "");
        String doubleMappingString =
                "isDouble: (?class rdf:type famix:FamixClass)"
                        + " (?class architecure:definesContent ?content)"
                        + " -> (?class rdf:type architecture:Double)";
        String doubleSecondWhenString =
                "(?class rdf:type famix:FamixClass)" + " (?class famix:imports ?class2)";
        ConceptMapping doubleMapping =
                createConceptMapping(
                        doubleMappingString, Arrays.asList(doubleSecondWhenString), doubleConcept);
        doubleConcept.setMapping(doubleMapping);
        conceptManager.addConcept(doubleConcept);

        CustomConcept triple = new CustomConcept("Triple", "");
        String tripleMappingString =
                "isTriple: (?class rdf:type famix:FamixClass)"
                        + " -> (?class rdf:type architecture:Triple)";
        String tripleSecondWhenString =
                "(?interface rdf:type famix:FamixClass)"
                        + " (?class famix:isInterface 'true'xsd:boolean)";
        String tripleThirdWhenString =
                "(?abstract rdf:type famix:FamixClass)"
                        + " (?abstract famix:hasModifier 'abstract')";
        ConceptMapping tripletMapping =
                createConceptMapping(
                        tripleMappingString,
                        Arrays.asList(tripleSecondWhenString, tripleThirdWhenString),
                        triple);
        triple.setMapping(tripletMapping);
        conceptManager.addConcept(triple);

        String connectionMappingString =
                "connectionMapping: (?class rdf:type famix:FamixClass)"
                        + " (?triple rdf:type architecture:Triple)"
                        + " (?triple famix:imports ?middleClassx)"
                        + " (?middleClass famix:imports ?class)"
                        + " -> (?class architecture:connection ?triple)";
        RelationMapping connectionMapping =
                createRelationMapping(connectionMappingString, Collections.emptyList());
        CustomRelation connectionRelation =
                new CustomRelation("connection", "", new HashSet<>(), new HashSet<>());
        connectionRelation.setMapping(connectionMapping, conceptManager);
        relationManager.addRelation(connectionRelation);

        String multipleMappingString =
                "isMultiple: (?double rdf:type architecture:Double)"
                        + " (?double architecture:connection ?triple)"
                        + " -> (?double rdf:type architecture:Multiple)";
        CustomConcept multipleConcept = new CustomConcept("Multiple", "");
        ConceptMapping multipleMapping =
                createConceptMapping(
                        multipleMappingString, Collections.emptyList(), multipleConcept);

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager);
        String plantUmlCode = transformer.transformToPlantUml(multipleMapping);

        // then
        String expectedCode = "";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    private RelationMapping createRelationMapping(
            String mappingString, List<String> additionalWhens)
            throws NoTripletException, NoMappingException, UnrelatedMappingException {
        RelationMapping mapping =
                MappingParser.parseMapping(mappingString, relationManager, conceptManager);
        for (String additionalWhenString : additionalWhens) {
            AndTriplets additionalWhen =
                    MappingParser.parseWhenPart(
                            additionalWhenString, relationManager, conceptManager);
            mapping.addAndTriplets(additionalWhen);
        }

        // To enable wrapper trick in PlantUmlTransformer
        CustomRelation relation = (CustomRelation) mapping.getThenTriplet().getPredicate();
        relation.setMapping(mapping, conceptManager);
        return mapping;
    }

    private ConceptMapping createConceptMapping(
            String mappingString, List<String> additionalWhens, CustomConcept thisConcept)
            throws NoTripletException, NoMappingException, UnrelatedMappingException {
        ConceptMapping mapping =
                MappingParser.parseMapping(
                        mappingString, thisConcept, relationManager, conceptManager);
        for (String additionalWhenString : additionalWhens) {
            AndTriplets additionalWhen =
                    MappingParser.parseWhenPart(
                            additionalWhenString, relationManager, conceptManager);
            mapping.addAndTriplets(additionalWhen);
        }

        // To enable wrapper trick in PlantUmlTransformer
        CustomConcept concept = (CustomConcept) mapping.getThenTriplet().getObject();
        concept.setMapping(mapping);
        return mapping;
    }
}
