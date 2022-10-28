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
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
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
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
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
                "isThenConcept: (?class famix:imports ?class2)"
                        + " (?inheritance famix:hasSubClass ?class)"
                        + " (?inheritance famix:hasSuperClass ?interface)"
                        + " (?interface famix:isInterface 'true'^^xsd:boolean)"
                        + " -> (?class rdf:type architecture:ThenConcept)";
        CustomConcept thenConcept = new CustomConcept("ThenConcept", "");
        ConceptMapping mapping =
                createConceptMapping(mappingString, Collections.emptyList(), thenConcept);

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(mapping);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title isThenConcept\n"
                        + "class \"?class\" as class implements interface {\n"
                        + "}\n"
                        + "class \"?class2\" as class2 {\n"
                        + "}\n"
                        + "interface \"?interface\" as interface {\n"
                        + "}\n"
                        + "class -[dashed]-> class2: <<imports>>\n"
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
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(mapping);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title isImportingController\n"
                        + "class \"?class\" as class {\n"
                        + "}\n"
                        + "class \".*Controller\" as controller1 {\n"
                        + "}\n"
                        + "controller1 -[dashed]-> class: <<imports>>\n"
                        + "note \"Controller\" as Controller\n"
                        + "Controller .. controller1\n"
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
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(mapping);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title isImportingController\n"
                        + "class \"?class\" as class {\n"
                        + "{field} ?attribute : .*Controller\n"
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
    void givenNestedMappingWithMissingParent_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException, NoTripletException,
                    UnrelatedMappingException, RelationAlreadyExistsException {
        // given
        String noParentString =
                "noParentMapping: (?method rdf:type famix:Method)"
                        + " (?method famix:definesParameter ?parameter)"
                        + " (?parameter famix:hasName 'flag')"
                        + " -> (?method architecture:noParent ?parameter)";
        RelationMapping noParentMapping =
                MappingParser.parseMapping(noParentString, relationManager, conceptManager);
        CustomRelation noParentRelation =
                new CustomRelation("noParent", "", new HashSet<>(), new HashSet<>());
        noParentRelation.setMapping(noParentMapping, conceptManager);
        relationManager.addRelation(noParentRelation);

        CustomConcept thisConcept = new CustomConcept("MainWithoutParent", "");
        String mappingString =
                "isMainWithoutParent: (?method architecture:noParent ?parameter)"
                        + " (?method famix:hasName 'main')"
                        + " (?parameter famix:hasDeclaredType ?type)"
                        + " -> (?method rdf:type architecture:MainWithoutParent)";
        ConceptMapping mapping =
                createConceptMapping(mappingString, Collections.emptyList(), thisConcept);

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(mapping);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title isMainWithoutParent\n"
                        + "class \"?GENERATED1\" as GENERATED1 {\n"
                        + "{method} main(flag:?type)\n"
                        + "}\n"
                        + "class \"?type\" as type {\n"
                        + "}\n"
                        + "GENERATED1::main -[bold]-> GENERATED1::main\n"
                        + "note on link: noParent\n"
                        + "note \"MainWithoutParent\" as MainWithoutParent\n"
                        + "MainWithoutParent .. GENERATED1::main\n"
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
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
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
                        + "controller1 -[dashed]-> class: <<imports>>\n"
                        + "note \"Controller\" as Controller\n"
                        + "Controller .. controller1\n"
                        + "}\n"
                        + "package isImportingController2 <<Cloud>> {\n"
                        + "class \"?class1\" as class1 {\n"
                        + "}\n"
                        + "class \".*Controller\" as controller21 {\n"
                        + "}\n"
                        + "class1 +-- controller21\n"
                        + "note \"Controller\" as Controller1\n"
                        + "Controller1 .. controller21\n"
                        + "}\n"
                        + "note \"ImportingController\" as ImportingController\n"
                        + "ImportingController .. controller1\n"
                        + "ImportingController .. controller21\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenNestedMapping_whenTransform_thenCorrectPlantUml()
            throws NoMappingException, MappingToUmlTranslationFailedException,
                    ConceptAlreadyExistsException, UnrelatedMappingException, NoTripletException,
                    RelationAlreadyExistsException {
        // given
        String definesContentString =
                "definesContentMapping: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:definesMethod ?methodOrAttribute)"
                        + " -> (?class architecture:definesContent ?methodOrAttribute)";
        String definesContentSecondWhen =
                "(?class rdf:type famix:FamixClass)"
                        + " (?class famix:definesAttribute ?methodOrAttribute)";
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

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(doubleMapping);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title isDouble\n"
                        + "package isDouble1 <<Cloud>> {\n"
                        + "class \"?class\" as class {\n"
                        + "{method} ?content()\n"
                        + "}\n"
                        + "class -[bold]-> class::content\n"
                        + "note on link: definesContent\n"
                        + "}\n"
                        + "package isDouble2 <<Cloud>> {\n"
                        + "class \"?class1\" as class1 {\n"
                        + "{field} ?content1\n"
                        + "}\n"
                        + "class1 -[bold]-> class1::content1\n"
                        + "note on link: definesContent\n"
                        + "}\n"
                        + "package isDouble3 <<Cloud>> {\n"
                        + "class \"?class2\" as class2 {\n"
                        + "}\n"
                        + "class \"?class21\" as class21 {\n"
                        + "}\n"
                        + "class2 -[dashed]-> class21: <<imports>>\n"
                        + "}\n"
                        + "note \"Double\" as Double\n"
                        + "Double .. class\n"
                        + "Double .. class1\n"
                        + "Double .. class2\n"
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
                        + " (?class famix:definesMethod ?methodOrAttribute)"
                        + " -> (?class architecture:definesContent ?methodOrAttribute)";
        String definesContentSecondWhen =
                "(?class rdf:type famix:FamixClass)"
                        + " (?class famix:definesAttribute ?methodOrAttribute)";
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
                "(?class rdf:type famix:FamixClass)"
                        + " (?class famix:isInterface 'true'^^xsd:boolean)";
        String tripleThirdWhenString =
                "(?class rdf:type famix:FamixClass)" + " (?class famix:hasModifier 'abstract')";
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
                        + " (?triple famix:imports ?middleClass)"
                        + " (?middleClass famix:imports ?class)"
                        + " -> (?class architecture:connection ?triple)";
        RelationMapping connectionMapping =
                MappingParser.parseMapping(
                        connectionMappingString, relationManager, conceptManager);
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
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(multipleMapping);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title isMultiple\n"
                        + "package isDouble1 <<Cloud>> {\n"
                        + "class \"?double1\" as double1 {\n"
                        + "{method} ?content()\n"
                        + "}\n"
                        + "double1 -[bold]-> double1::content\n"
                        + "note on link: definesContent\n"
                        + "}\n"
                        + "package isDouble2 <<Cloud>> {\n"
                        + "class \"?double2\" as double2 {\n"
                        + "{field} ?content1\n"
                        + "}\n"
                        + "double2 -[bold]-> double2::content1\n"
                        + "note on link: definesContent\n"
                        + "}\n"
                        + "package isDouble3 <<Cloud>> {\n"
                        + "class \"?double3\" as double3 {\n"
                        + "}\n"
                        + "class \"?class2\" as class2 {\n"
                        + "}\n"
                        + "double3 -[dashed]-> class2: <<imports>>\n"
                        + "}\n"
                        + "package isTriple1 <<Cloud>> {\n"
                        + "class \"?triple1\" as triple1 {\n"
                        + "}\n"
                        + "}\n"
                        + "package isTriple2 <<Cloud>> {\n"
                        + "interface \"?triple2\" as triple2 {\n"
                        + "}\n"
                        + "}\n"
                        + "package isTriple3 <<Cloud>> {\n"
                        + "abstract \"?triple3\" as triple3 {\n"
                        + "}\n"
                        + "}\n"
                        + "class \"?middleClass\" as middleClass {\n"
                        + "}\n"
                        + "triple1 -[dashed]-> middleClass: <<imports>>\n"
                        + "triple2 -[dashed]-> middleClass: <<imports>>\n"
                        + "triple3 -[dashed]-> middleClass: <<imports>>\n"
                        + "middleClass -[dashed]-> double1: <<imports>>\n"
                        + "middleClass -[dashed]-> double2: <<imports>>\n"
                        + "middleClass -[dashed]-> double3: <<imports>>\n"
                        + "double1 -[bold]-> triple1\n"
                        + "note on link: connection\n"
                        + "double1 -[bold]-> triple2\n"
                        + "note on link: connection\n"
                        + "double1 -[bold]-> triple3\n"
                        + "note on link: connection\n"
                        + "double2 -[bold]-> triple1\n"
                        + "note on link: connection\n"
                        + "double2 -[bold]-> triple2\n"
                        + "note on link: connection\n"
                        + "double2 -[bold]-> triple3\n"
                        + "note on link: connection\n"
                        + "double3 -[bold]-> triple1\n"
                        + "note on link: connection\n"
                        + "double3 -[bold]-> triple2\n"
                        + "note on link: connection\n"
                        + "double3 -[bold]-> triple3\n"
                        + "note on link: connection\n"
                        + "note \"Double\" as Double\n"
                        + "Double .. double1\n"
                        + "Double .. double2\n"
                        + "Double .. double3\n"
                        + "note \"Triple\" as Triple\n"
                        + "Triple .. triple1\n"
                        + "Triple .. triple2\n"
                        + "Triple .. triple3\n"
                        + "note \"Multiple\" as Multiple\n"
                        + "Multiple .. double1\n"
                        + "Multiple .. double2\n"
                        + "Multiple .. double3\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
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
