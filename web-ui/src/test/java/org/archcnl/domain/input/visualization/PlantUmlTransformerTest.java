package org.archcnl.domain.input.visualization;

import java.util.Collections;
import java.util.Set;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.FamixConcept;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;
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
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager);
        String plantUmlCode = transformer.transformToPlantUml(mapping);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title weirdRelationMapping\n"
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
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager);
        String plantUmlCode = transformer.transformToPlantUml(mapping);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title paramRelationMapping\n"
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

    @Test
    void givenRelationMappingWithCustomConcept_whenTransform_thenCorrectPlantUml()
            throws NoMappingException, MappingToUmlTranslationFailedException,
                    ConceptAlreadyExistsException, UnrelatedMappingException {
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

        String mappingString =
                "usedByControllerMapping: (?class rdf:type famix:FamixClass)"
                        + " (?controller rdf:type architecture:Controller)"
                        + " (?controller famix:imports ?class)"
                        + " -> (?class architecture:usedByController ?controller)";
        RelationMapping mapping =
                MappingParser.parseMapping(mappingString, relationManager, conceptManager);

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager);
        String plantUmlCode = transformer.transformToPlantUml(mapping);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title usedByControllerMapping\n"
                        + "class \"?class\" as class {\n"
                        + "}\n"
                        + "class \".*Controller\" as controller1 {\n"
                        + "}\n"
                        + "note \"Controller\" as Controller\n"
                        + "Controller .. controller1\n"
                        + "controller1 -[dashed]-> class: <<imports>>\n"
                        + "class -[bold]-> controller1\n"
                        + "note on link: usedByController\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenConceptMappingWithCustomConcept_whenTransform_thenCorrectPlantUml()
            throws NoMappingException, MappingToUmlTranslationFailedException,
                    ConceptAlreadyExistsException, UnrelatedMappingException {
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
                MappingParser.parseMapping(
                        mappingString, importingController, relationManager, conceptManager);

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
                MappingParser.parseMapping(
                        mappingString, importingController, relationManager, conceptManager);

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
                MappingParser.parseMapping(
                        mappingString, importingController, relationManager, conceptManager);
        AndTriplets secondWhen =
                MappingParser.parseWhenPart(secondWhenString, relationManager, conceptManager);
        mapping.addAndTriplets(secondWhen);

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
    void givenUseMapping_whenTransform_thenCorrectPlantUml()
            throws NoMappingException, MappingToUmlTranslationFailedException,
                    ConceptAlreadyExistsException, UnrelatedMappingException, NoTripletException {
        // given
        String mappingString =
                "useMapping: (?class rdf:type famix:FamixClass)"
                        + " (?class2 rdf:type famix:FamixClass)"
                        + " (?class famix:imports ?class2)"
                        + " -> (?class architecture:use ?class2)";
        String secondWhenString =
                "(?class rdf:type famix:FamixClass)"
                        + " (?class2 rdf:type famix:FamixClass)"
                        + " (?class famix:definesAttribute ?attribute)"
                        + " (?attribute famix:hasDeclaredType ?class2)";
        RelationMapping mapping =
                MappingParser.parseMapping(mappingString, relationManager, conceptManager);
        AndTriplets secondWhen =
                MappingParser.parseWhenPart(secondWhenString, relationManager, conceptManager);
        mapping.addAndTriplets(secondWhen);

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager);
        String plantUmlCode = transformer.transformToPlantUml(mapping);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title useMapping\n"
                        + "package useMapping1 <<Cloud>> {\n"
                        + "class \"?class\" as class {\n"
                        + "}\n"
                        + "class \"?class2\" as class2 {\n"
                        + "}\n"
                        + "class -[dashed]-> class2: <<imports>>\n"
                        + "class -[bold]-> class2\n"
                        + "note on link: use\n"
                        + "}\n"
                        + "package useMapping2 <<Cloud>> {\n"
                        + "class \"?class1\" as class1 {\n"
                        + "	{field} ?attribute : ?class21\n"
                        + "}\n"
                        + "class \"?class21\" as class21 {\n"
                        + "}\n"
                        + "class1 -[bold]-> class21\n"
                        + "note on link: use\n"
                        + "}\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenCircularUseMapping_whenTransform_thenCorrectPlantUml()
            throws NoMappingException, MappingToUmlTranslationFailedException,
                    ConceptAlreadyExistsException, UnrelatedMappingException, NoTripletException,
                    RelationAlreadyExistsException {
        // given
        String useMappingString =
                "useMapping: (?class rdf:type famix:FamixClass)"
                        + " (?class2 rdf:type famix:FamixClass)"
                        + " (?class famix:imports ?class2)"
                        + " -> (?class architecture:use ?class2)";
        String secondWhenString =
                "(?class rdf:type famix:FamixClass)"
                        + " (?class2 rdf:type famix:FamixClass)"
                        + " (?class famix:definesAttribute ?attribute)"
                        + " (?attribute famix:hasDeclaredType ?class2)";
        RelationMapping useMapping =
                MappingParser.parseMapping(useMappingString, relationManager, conceptManager);
        AndTriplets secondWhen =
                MappingParser.parseWhenPart(secondWhenString, relationManager, conceptManager);
        useMapping.addAndTriplets(secondWhen);
        Set<ActualObjectType> relatableTypes =
                Collections.singleton(new FamixConcept("FamixClass", ""));
        CustomRelation useRelation = new CustomRelation("use", "", relatableTypes, relatableTypes);
        useRelation.setMapping(useMapping, conceptManager);
        relationManager.addRelation(useRelation);

        String mappingString =
                "circularUseMapping: (?class architecture:use ?class2)"
                        + " (?class2 architecture:use ?class)"
                        + " -> (?class architecture:circularUse ?class2)";
        RelationMapping mapping =
                MappingParser.parseMapping(mappingString, relationManager, conceptManager);

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager);
        String plantUmlCode = transformer.transformToPlantUml(mapping);

        // then
        String expectedCode = "";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }
}
